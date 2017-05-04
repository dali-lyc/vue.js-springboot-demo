package com.ly.milestone.service.impl;

import com.ly.milestone.model.UserInfo;
import com.ly.milestone.service.IOauthService;
import com.ly.milestone.utils.RedisHelper;
import com.ly.milestone.utils.WebUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.UUID;

import static com.ly.milestone.utils.WebUtils.postByJsonParameters;
import static com.ly.milestone.utils.Constants.*;

/**
 * Created by lyc28724 on 2017/3/30.
 */
@Service
public class OauthServiceImpl implements IOauthService {

    @Value("${applyTokenRedirectUri}")
    private String applyTokenRedirectUri;

    @Value("${stateApplyForCodeUri}")
    private String stateApplyForCodeUri;

    @Value("${responseType}")
    private String responseType;

    @Value("${scope}")
    private String scope;

    @Value("${clientId}")
    private String clientId;

    @Value("${getUserInfoByTokenUri}")
    private String getUserInfoByTokenUri;

    @Override
    public String applyForTokenFromSSO(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 如果cookie中没有token信息，通过state向SSO站点申请授权码
        String state = UUID.randomUUID().toString().replaceAll("-", "");
        // 用于防止跨站请求伪造（CSRF）攻击
        Cookie cookie = new Cookie("state", state);
        cookie.setPath(WEBROOT_URI_PATH);
        response.addCookie(cookie);
        StringBuffer fullUri = new StringBuffer();
        String redirectUri = URLEncoder.encode(applyTokenRedirectUri, CHARSET_UTF8);
        String returnUri = "";
        if (null != request.getAttribute("returnUri")) {
            returnUri = URLEncoder.encode(request.getAttribute("returnUri").toString(), CHARSET_UTF8);
        }
        fullUri.append(stateApplyForCodeUri)
                .append("?response_type=").append(responseType)
                .append("&scope=").append(scope)
                .append("&client_id=").append(clientId)
                .append("&redirect_uri=").append(redirectUri)
                .append("&state=").append(state)
                .append("&return_uri=").append(returnUri);
        return fullUri.toString();
    }

    @Override
    public Boolean checkState(HttpServletRequest request, HttpServletResponse response, String state) {
        if (null != request.getCookies()) {
            for (Cookie cookie : request.getCookies()) {
                if ("state".equals(cookie.getName())) {
                    if (state.equals(cookie.getValue())) {
                        cookie.setMaxAge(0);
                        cookie.setPath(WEBROOT_URI_PATH);
                        response.addCookie(cookie);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public UserInfo getUserInfoByToken(HttpServletRequest request) {
//        String accessToken = request.getAttribute(ACCESS_TOKEN).toString();
        String accessToken = WebUtils.getCookieValueByName(request, ACCESS_TOKEN);
        UserInfo userInfo = RedisHelper.getObject(accessToken, UserInfo.class);
        if (null == userInfo) {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put(ACCESS_TOKEN, accessToken);
            String returnParams = postByJsonParameters(getUserInfoByTokenUri, jsonParams);
            JSONObject json = JSONObject.fromObject(returnParams);
            if (json.containsKey("username")) {
//                userInfo.setWorkId(json.getString("workId"));
                userInfo = (UserInfo) JSONObject.toBean(json, UserInfo.class);
                RedisHelper.setObject(accessToken, 43200, userInfo);
            }
        }
        return userInfo;
    }

}
