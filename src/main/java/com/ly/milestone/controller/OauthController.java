package com.ly.milestone.controller;

import com.ly.milestone.service.IOauthService;
import com.ly.milestone.utils.RedisHelper;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ly.milestone.utils.WebUtils.postByJsonParameters;
import static com.ly.milestone.utils.Constants.*;

/**
 * Created by lyc28724 on 2017/3/30.
 */
@Controller
@RequestMapping("oauth")
public class OauthController {

    private static final Logger LOG = LoggerFactory.getLogger(OauthController.class);

    @Value("${applyTokenRedirectUri}")
    private String applyTokenRedirectUri;

    @Value("${codeApplyForTokenUri}")
    private String codeApplyForTokenUri;

    @Value("${ssoLogoutUri}")
    private String ssoLogoutUri;

    @Value("${cookieExpireTime}")
    private int cookieExpireTime;

    @Value("${responseType}")
    private String responseType;

    @Value("${clientId}")
    private String clientId;

    @Value("${scope}")
    private String scope;

    @Value("${grantType}")
    private String grantType;

    @Value("${clientSecret}")
    private String clientSecret;

    @Autowired
    private IOauthService oauthService;


    /**
     * 向SSO站点申请访问令牌
     *
     * @param response
     * @return
     */
    @RequestMapping("applyfortoken")
    public String applyForToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String applyForTokenUri = oauthService.applyForTokenFromSSO(request, response);
            return "redirect:" + applyForTokenUri;
        } catch (Exception e) {
            LOG.error("applyfortoken is failed:", e);
            return null;
        }
    }


    /**
     * 回调接口
     *
     * @param request
     * @param response
     * @param state
     * @param code
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("authorization_code_callback")
    public String authorizationCodeCallback(HttpServletRequest request, HttpServletResponse response, String state, String code, String returnUri, Model model) throws Exception {
        try {
            Boolean stateIsValid = oauthService.checkState(request, response, state);
            if (stateIsValid) {
                JSONObject jsonParams = new JSONObject();
                jsonParams.put("client_id", clientId);
                jsonParams.put("client_secret", clientSecret);
                jsonParams.put("redirect_uri", applyTokenRedirectUri);
                jsonParams.put("code", code);
                jsonParams.put("grant_type", grantType);
                // 通过接收到的授权码到SSO站点申请令牌
                String returnParams = postByJsonParameters(codeApplyForTokenUri, jsonParams);
                JSONObject jsonResponse = JSONObject.fromObject(returnParams);
                String accessToken = jsonResponse.getString(ACCESS_TOKEN);
                if (null != accessToken) {
                    Cookie cookie = new Cookie(ACCESS_TOKEN, accessToken);
                    cookie.setMaxAge(cookieExpireTime);
                    cookie.setPath(WEBROOT_URI_PATH);
                    response.addCookie(cookie);
                    if (StringUtils.isNotEmpty(returnUri)) {
                        return "redirect:" + returnUri;
                    }
                    return "redirect:/admin/home";
                }
                model.addAttribute("error", "Invalid code");
                return "oauth/oauth_error";
            } else {
                model.addAttribute("error", "Invalid state");
                return "oauth/oauth_error";
            }
        } catch (Exception e) {
            LOG.error("authorization_code_callback is failed:", e);
            return null;
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            StringBuffer fullLogoutUri = new StringBuffer().append(ssoLogoutUri);
            if (null != request.getCookies()) {
                for (Cookie cookie : request.getCookies()) {
                    if (ACCESS_TOKEN.equals(cookie.getName())) {
                        fullLogoutUri.append("?").append(ACCESS_TOKEN).append("=").append(cookie.getValue());
                        cookie.setMaxAge(0);
                        cookie.setPath(WEBROOT_URI_PATH);
                        response.addCookie(cookie);
                        RedisHelper.del(cookie.getValue());
                        break;
                    }
                }
            }
            return "redirect:" + fullLogoutUri.toString();
        } catch (Exception e) {
            LOG.error("logout is failed:", e);
            return null;
        }
    }
}
