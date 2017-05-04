package com.ly.milestone.service;

import com.ly.milestone.model.User;
import com.ly.milestone.utils.PageHelper;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

/**
 * Created by lyc28724 on 2017/3/1.
 */
public interface IUserService {

    List<User> queryUserBySex(String sex);

    User queryUserByUserId(Integer userId);

    List<User> searchUserByPage(PageHelper<User> page);

    Long findTotalCountUsers(PageHelper<User> page);

    Integer insertUser(User user);

    Integer delUserByRecId(Long recId);

    User queryUserByRecId(Long recId);

    Integer editUserByRecId(User user);
}
