package com.ly.milestone.dao;

import com.ly.milestone.model.User;
import com.ly.milestone.utils.PageHelper;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by lyc28724 on 2017/3/1.
 */
@Repository
public interface IUserDao {

    List<User> queryUserBySex(String sex);

    User queryUserByUserId(Integer userId);

    List<User> searchUserByPage(PageHelper<User> page);

    Long findTotalCountUsers(PageHelper<User> page);

    Integer insertUser(User user);

    Integer delUser(Long recId);

    User queryUserByRecId(Long recId);

    Integer editUserByRecId(User user);
}
