package com.hd.epac.service;

import com.hd.epac.entity.User;

import java.sql.Timestamp;
import java.util.List;

public interface UserService {
    //登录
    public User CheckLogin(String username, String password);

    //新增用户
    public int AddUser(Integer userid, String username, String password, Timestamp registtime, String place, Integer age, String birth, Integer admin, Integer state, Integer gender);

    //更新用户
    public int UpdateUser(Integer userid, String username, String password, String place, Integer age, String birth, Integer admin, Integer state, Integer gender);

    //获取所有用户
    public List<User> FindAllUsers();

    //删除用户
    public int DelUser(Integer userid);

    //批量删除用户
    public int DelUsers(Integer userid);

    //更改用户状态
    public int UpdateState(Integer userid, Integer state);
}
