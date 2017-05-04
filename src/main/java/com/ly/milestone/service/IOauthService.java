package com.ly.milestone.service;

import com.ly.milestone.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lyc28724 on 2017/3/30.
 */
public interface IOauthService {

    /**
     * 向SSO站点申请访问令牌
     *
     * @param response
     * @return
     */
    String applyForTokenFromSSO(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 检查认证服务器回传过来的state是否与客户端传过去的一致，防止CSRF攻击
     *
     * @param request
     * @param response
     * @param state
     * @return
     */
    Boolean checkState(HttpServletRequest request, HttpServletResponse response, String state);

    /**
     * 根据token获取用户信息
     *
     * @param request
     * @return
     */
    UserInfo getUserInfoByToken(HttpServletRequest request);
}
