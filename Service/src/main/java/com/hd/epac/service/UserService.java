package com.hd.epac.service;

import com.hd.epac.entity.User;

import java.util.List;

public interface UserService {
    //登录
    public User CheckLogin(String username, String password);

    //根据用户名查找
    public User SearchUser(String username);

    //新增用户
    public int AddUser(Integer userid, String username, String password, Integer age, String birth, Integer admin, Integer state, Integer gender, String province, String city, String area);

    //更新用户
    public int UpdateUser(Integer userid, String username, String password, Integer age, String birth, Integer admin, Integer state, Integer gender, String province, String city, String area);

    //获取所有用户
    public List<User> FindAllUsers();

    //删除用户
    public int DelUser(Integer userid);

    //批量删除用户
    public int DelUsers(String idLists);

    //更改用户状态
    public int UpdateState(Integer userid, Integer state);

    //初始化用户密码
    public int ResetPWD(String idLists);

    //修改密码
    public int ModifyPWD(Integer userid, String password);
}
