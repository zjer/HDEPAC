package com.hd.epac.service;

import com.hd.epac.dao.UserDao;
import com.hd.epac.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User CheckLogin(String username, String password) {
        return userDao.checkLogin(username, password);
    }
}
