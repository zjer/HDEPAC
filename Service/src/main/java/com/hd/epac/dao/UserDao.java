package com.hd.epac.dao;

import com.hd.epac.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public interface UserDao {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //登录
    public User checkLogin(@Param("username") String username, @Param("password") String password);

    //新增用户
    public int addUser(@Param("userid") Integer userid, @Param("username") String username, @Param("password") String password, @Param("registtime") Timestamp registtime, @Param("place") String place, @Param("age") Integer age, @Param("birth") String birth, @Param("admin") Integer admin, @Param("state") Integer state, @Param("gender") Integer gender);

    //更新用户
    public int updateUser(@Param("userid") Integer userid, @Param("username") String username, @Param("password") String password, @Param("place") String place, @Param("age") Integer age, @Param("birth") String birth, @Param("admin") Integer admin, @Param("state") Integer state, @Param("gender") Integer gender);

    //获取所有用户
    public List<User> findAllUsers();

    //删除用户
    public int delUser(@Param("userid") Integer userid);

    //批量删除用户
    public int delUsers(@Param("list") List list);

    //更改用户状态
    public int updateState(@Param("userid") Integer userid, @Param("state") Integer state);

    //初始化用户密码
    public int resetPWD(@Param("list") List list);

    //修改密码
    public int modifyPWD(@Param("userid") Integer userid, @Param("password") String password);

}