package com.hd.epac.service;

import com.hd.epac.dao.UserDao;
import com.hd.epac.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /*
    * 登录
    * */
    @Override
    public User CheckLogin(String username, String password) {
        return userDao.checkLogin(username, password);
    }

    /*
     * 根据用户名查找
     * */
    @Override
    public User SearchUser(String username) {
        return userDao.searchUser(username);
    }

    /*
     * 添加用户
     * */
    @Override
    public int AddUser(Integer userid, String username, String password, Integer age, String birth, Integer admin, Integer state, Integer gender, String province, String city, String area, String chinesename) {
        return userDao.addUser(userid, username, password, age, birth, admin, state, gender, province, city, area, chinesename);
    }

    /*
     * 更新用户信息
     * */
    @Override
    public int UpdateUser(Integer userid, String username, String password, Integer age, String birth, Integer admin, Integer state, Integer gender, String province, String city, String area, String chinesename) {
        return userDao.updateUser(userid, username, password, age, birth, admin, state, gender, province, city, area, chinesename);
    }

    /*
     * 获取所有用户
     * */
    @Override
    public List<User> FindAllUsers() {
        List<User> list = userDao.findAllUsers();
        return list;
    }

    /*
     * 删除用户（单删）
     * */
    @Override
    public int DelUser(Integer userid) {
        return userDao.delUser(userid);
    }

    /*
     * 删除用户（多删）
     * */
    @Override
    public int DelUsers(String idLists) {
        String arr[] = idLists.split(",");
        List list = new ArrayList();
        for (int i = 0; i < arr.length; i ++) {
            list.add(Integer.parseInt(arr[i]));
        }
        return userDao.delUsers(list);
    }

    /*
     * 更改用户状态
     * */
    @Override
    public int UpdateState(Integer userid, Integer state) {
        return userDao.updateState(userid, state);
    }

    /*
     * 重置用户密码（批量重置）
     * */
    @Override
    public int ResetPWD(String idLists) {
        String arr[] = idLists.split(",");
        List list = new ArrayList();
        for (int i = 0; i < arr.length; i ++) {
            list.add(Integer.parseInt(arr[i]));
        }
        return userDao.resetPWD(list);
    }

    /*
     * 修改登录人密码
     * */
    @Override
    public int ModifyPWD(Integer userid, String password) {
        return userDao.modifyPWD(userid, password);
    }
}
