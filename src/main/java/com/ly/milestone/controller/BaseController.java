package com.ly.milestone.controller;

import com.ly.milestone.model.UserInfo;
import com.ly.milestone.service.IOauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lyc28724 on 2017/3/30.
 */
@Controller
public class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IOauthService oauthService;

    protected UserInfo userInfo;

    public UserInfo getUserInfo(HttpServletRequest request) {
        try {
            return oauthService.getUserInfoByToken(request);
        } catch (Exception e) {
            LOG.error("getUserInfo is failed:", e);
        }
        return null;
    }

    @RequestMapping("/")
    public String forward() {
        return "redirect:admin/index/";
    }
}
