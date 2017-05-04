package com.ly.milestone.controller;

import com.ly.milestone.model.ResponseResult;
import com.ly.milestone.model.User;
import com.ly.milestone.model.UserInfo;
import com.ly.milestone.service.IOauthService;
import com.ly.milestone.service.IUserService;
import com.ly.milestone.utils.PageHelper;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ly.milestone.utils.Constants.ERR_CODE;
import static com.ly.milestone.utils.Constants.SUCC_CODE;


/**
 * Created by lyc28724 on 2017/2/28.
 */

@RestController
@RequestMapping("admin")
public class RestfulController {

    private static final Logger LOG = LoggerFactory.getLogger(RestfulController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IOauthService oauthService;


    @RequestMapping(value = "loadList", method = RequestMethod.POST)
    public PageHelper<User> loadList(@RequestBody PageHelper<User> page) {
        try {
            page.setTotalCount(userService.findTotalCountUsers(page));
            page.setContent(userService.searchUserByPage(page));
            return page;
        } catch (Exception e) {
            LOG.error("invoking loadList is failed:", e);
        }
        return null;
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public List<User> list(@RequestBody PageHelper<User> page) {
        try {
            List<User> userList = userService.searchUserByPage(page);
            return userList;
        } catch (Exception e) {
            LOG.error("invoking list is failed:", e);
        }
        return null;
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public ResponseResult addUser(@RequestBody User user) {
        ResponseResult responseResult = new ResponseResult();
        try {
            userService.insertUser(user);
            responseResult.setCode(SUCC_CODE);
        } catch (Exception e) {
            responseResult.setCode(ERR_CODE);
            LOG.error("addUser is failed:", e);
        }
        return responseResult;

    }

    @RequestMapping(value = "userDetail", method = RequestMethod.POST)
    public ResponseResult<User> userDetail(Long recId) {
        ResponseResult responseResult = new ResponseResult();
        try {
            User user = userService.queryUserByRecId(recId);
            responseResult.setData(user);
            responseResult.setCode(SUCC_CODE);
        } catch (Exception e) {
            responseResult.setCode(ERR_CODE);
            LOG.error("userDetail is failed:", e);
        }
        return responseResult;
    }

    @RequestMapping(value = "editUser", method = RequestMethod.POST)
    public ResponseResult<User> editUser(@RequestBody User user) {
        ResponseResult responseResult = new ResponseResult();
        try {
            userService.editUserByRecId(user);
            responseResult.setCode(SUCC_CODE);
        } catch (Exception e) {
            responseResult.setCode(ERR_CODE);
            LOG.error("editUser is failed:", e);
        }
        return responseResult;
    }

    @RequestMapping(value = "delUser", method = RequestMethod.POST)
    public ResponseResult delUser(Long recId) {
        ResponseResult responseResult = new ResponseResult();
        try {
            userService.delUserByRecId(recId);
            responseResult.setCode(SUCC_CODE);
        } catch (Exception e) {
            responseResult.setCode(ERR_CODE);
            LOG.error("delUser is failed:", e);
        }
        return responseResult;

    }

    @RequestMapping("map")
    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("name", "lili");
            map.put("age", "20");
        } catch (Exception e) {
            LOG.error("invoking map is failed:", e);
        }
        return map;
    }

    @RequestMapping("object")
    public User object() {
        try {
            int userId = 1;
            User user = userService.queryUserByUserId(userId);
            return user;
        } catch (Exception e) {
            LOG.error("invoking object is failed:", e);
        }
        return null;
    }

    @RequestMapping("userInfo")
    public String userInfo(HttpServletRequest request) {
        try {
            UserInfo userInfo = oauthService.getUserInfoByToken(request);
            return "callback(" + JSONObject.fromObject(userInfo).toString() +")";
        } catch (Exception e) {
            LOG.error("invoking userInfo is failed:", e);
        }
        return null;
    }

}
