package com.ly.milestone.controller;

import com.ly.milestone.model.User;
import com.ly.milestone.model.UserInfo;
import com.ly.milestone.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lyc28724 on 2017/2/28.
 */
@Controller
@RequestMapping("admin")
public class HomeController extends BaseController{

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    ApplicationEventPublisher publisher;


    @RequestMapping("home")
    public String home(HttpServletRequest request, Model model){
        try {
            UserInfo userInfo = getUserInfo(request);
            String sex = "male";
            List<User> userList = userService.queryUserBySex(sex);
            int userId = 2;
            User user = userService.queryUserByUserId(userId);
            model.addAttribute("userList", userList);
            model.addAttribute("user", user);
            model.addAttribute("userInfo", userInfo);
        } catch (Exception e) {
            LOG.error("access home is failed:", e);
        }
        return "home";
    }

    @RequestMapping("index")
    public String Index() {
        return "index";
    }

}
