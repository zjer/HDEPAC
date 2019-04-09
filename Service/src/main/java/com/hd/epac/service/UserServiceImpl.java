package com.hd.epac.service;

import com.hd.epac.dao.UserDao;
import com.hd.epac.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User CheckLogin(String username, String password) {
        return userDao.checkLogin(username, password);
    }

    @Override
    public int AddUser(Integer userid, String username, String password, Timestamp registtime, String place, Integer age, String birth, Integer admin, Integer state, Integer gender) {
        return userDao.addUser(userid, username, password, registtime, place, age, birth, admin, state, gender);
    }

    @Override
    public int UpdateUser(Integer userid, String username, String password, String place, Integer age, String birth, Integer admin, Integer state, Integer gender) {
        return userDao.updateUser(userid, username, password, place, age, birth, admin, state, gender);
    }

    @Override
    public List<User> FindAllUsers() {
        List<User> list = userDao.findAllUsers();
        return list;
    }

    @Override
    public int DelUser(Integer userid) {
        return userDao.delUser(userid);
    }

    @Override
    public int DelUsers(String idLists) {
        String arr[] = idLists.split(",");
        List list = new ArrayList();
        for (int i = 0; i < arr.length; i ++) {
            list.add(Integer.parseInt(arr[i]));
        }
        return userDao.delUsers(list);
    }

    @Override
    public int UpdateState(Integer userid, Integer state) {
        return userDao.updateState(userid, state);
    }

    @Override
    public int ResetPWD(String idLists) {
        String arr[] = idLists.split(",");
        List list = new ArrayList();
        for (int i = 0; i < arr.length; i ++) {
            list.add(Integer.parseInt(arr[i]));
        }
        return userDao.resetPWD(list);
    }
}
