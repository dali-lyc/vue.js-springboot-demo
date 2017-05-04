package com.ly.milestone.service.impl;

import com.ly.milestone.dao.IUserDao;
import com.ly.milestone.model.User;
import com.ly.milestone.service.IUserService;
import com.ly.milestone.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lyc28724 on 2017/3/1.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public List<User> queryUserBySex(String sex) {
        return userDao.queryUserBySex(sex);
    }

    @Override
    public User queryUserByUserId(Integer userId) {
        return userDao.queryUserByUserId(userId);
    }

    @Override
    public List<User> searchUserByPage(PageHelper<User> page) {
        return userDao.searchUserByPage(page);
    }

    @Override
    public Long findTotalCountUsers(PageHelper<User> page) {
        return userDao.findTotalCountUsers(page);
    }

    @Override
    public Integer insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public Integer delUserByRecId(Long recId) {
        return userDao.delUser(recId);
    }

    @Override
    public User queryUserByRecId(Long recId) {
        return userDao.queryUserByRecId(recId);
    }

    @Override
    public Integer editUserByRecId(User user) {
        return userDao.editUserByRecId(user);
    }
}
