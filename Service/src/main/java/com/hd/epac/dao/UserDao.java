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
    public User addUser(@Param("userid") Integer userid, @Param("username") String username, @Param("password") String password, @Param("registtime") Timestamp registtime, @Param("place") String place, @Param("age") Integer age, @Param("birth") String birth, @Param("admin") Integer admin, @Param("state") Integer state, @Param("sex") Integer sex);

    //获取所有用户
    public List<User> findAllUsers();
}