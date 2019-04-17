package com.hd.epac.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hd.epac.entity.User;
import com.hd.epac.service.UserService;
import com.hd.epac.util.ResultUtil;
import com.hd.epac.util.ValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    /*
     * 生成验证码
     * */
    @GetMapping(value = "/getAuthCode")
    public String validateCodeUtil(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();

        ValidateCodeUtil codeUtil = new ValidateCodeUtil(120, 40, 4, 100);
        session.setAttribute("code", codeUtil.getCode());
        codeUtil.write(response.getOutputStream());
        System.out.println(session.getAttribute("code").toString().toLowerCase());
        return null;
    }

    /*
     * 登录
     * */
    @PostMapping(value = "/login")
    @ResponseBody
    public ResultUtil CheckLogin(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "code", required = false) String code, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object objCode = session.getAttribute("code");
        System.out.println(objCode);
        System.out.println(username + ',' + password + ',' + code);
        String _code = objCode.toString().toLowerCase();
        if(!code.equals(_code)){
            return ResultUtil.error("验证码错误，请重新输入！");
        }
        User checkUser = userService.SearchUser(username);
        User user = userService.CheckLogin(username, password);
        if (checkUser == null) {
            return ResultUtil.error("用户名不存在");
        } else if (checkUser.getState() == 0) {
            return ResultUtil.error("该用户已停用");
        } else {
            if (user == null) {
                return ResultUtil.error("用户名或者密码不正确，请重新输入！");
            } else {
                String string = username + password + new Date();
                byte[] bytes = string.getBytes();
                String token = (new BASE64Encoder()).encodeBuffer(bytes);
                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("userid", user.getUserid());
                map.put("username", user.getUsername());
                map.put("admin", user.getAdmin());
                map.put("createtime", user.getCreatetime());
                map.put("logintime", user.getLogintime());
                return ResultUtil.success("登录成功！", map);
            }
        }
    }

    /*
     * 添加用户
     * */
    @PostMapping(value = "/addUser")
    @ResponseBody
    public ResultUtil AddUser(@RequestParam(value = "userid", required = false) Integer userid, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "birth", required = false) String birth, @RequestParam(value = "admin", required = false) Integer admin, @RequestParam(value = "state", required = false) Integer state, @RequestParam(value = "gender", required = false) Integer gender, @RequestParam(value = "province", required = false) String province, @RequestParam(value = "city", required = false) String city, @RequestParam(value = "area", required = false) String area) {
        System.out.println(userid + ',' + username + ',' + password + ',' + age + ',' + birth + ',' + admin + ',' + state + ',' + gender + ',' + province + ',' + city + ',' + area);
        Object user = userService.AddUser(userid, username, password, age, birth, admin, state, gender, province, city, area);
        return ResultUtil.success("添加成功！", user);
    }

    /*
     * 更新用户信息
     * */
    @PostMapping(value = "/updateUser")
    @ResponseBody
    public ResultUtil UpdateUser(@RequestParam(value = "userid", required = false) Integer userid, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "birth", required = false) String birth, @RequestParam(value = "admin", required = false) Integer admin, @RequestParam(value = "state", required = false) Integer state, @RequestParam(value = "gender", required = false) Integer gender, @RequestParam(value = "province", required = false) String province, @RequestParam(value = "city", required = false) String city, @RequestParam(value = "area", required = false) String area) {
        System.out.println(userid + ',' + username + ',' + password + ',' + age + ',' + birth + ',' + admin + ',' + state + ',' + gender + ',' + province + ',' + city + ',' + area);
        Object user = userService.UpdateUser(userid, username, password, age, birth, admin, state, gender, province, city, area);
        return ResultUtil.success("修改成功！", user);
    }

    /*
     * 获取所有用户
     * */
    @PostMapping(value = "/getUsers")
    @ResponseBody
    public ResultUtil FindAllUsers(@RequestParam(value = "pageIndex", required = false) Integer pageIndex, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<User> list = userService.FindAllUsers();
        PageInfo pageInfo = new PageInfo(list);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("list", pageInfo.getList());
        System.out.println(map);
        return ResultUtil.success("查询成功！", map);
    }

    /*
     * 删除用户（单删）
     * */
    @PostMapping(value = "/delUser")
    @ResponseBody
    public ResultUtil DelUser(@RequestParam(value = "userid", required = false) Integer userid) {
        System.out.println(userid);
        Object user = userService.DelUser(userid);
        return ResultUtil.success("删除成功！", user);
    }

    /*
     * 删除用户（多删）
     * */
    @PostMapping(value = "/delUsers")
    @ResponseBody
    public ResultUtil DelUsers(@RequestParam(value = "idLists", required = false) String idLists) {
        System.out.println(idLists);
        Object user = userService.DelUsers(idLists);
        return ResultUtil.success("删除成功！", user);
    }

    /*
     * 更改用户状态
     * */
    @PostMapping(value = "/updateState")
    @ResponseBody
    public ResultUtil UpdateState(@RequestParam(value = "userid", required = false) Integer userid, @RequestParam(value = "state", required = false) Integer state) {
        System.out.println(userid + ',' + state);
        Object user = userService.UpdateState(userid, state);
        return ResultUtil.success("状态修改成功！", user);
    }

    /*
     * 重置用户密码（批量重置）
     * */
    @PostMapping(value = "/resetPWD")
    @ResponseBody
    public ResultUtil ResetPWD(@RequestParam(value = "idLists", required = false) String idLists) {
        System.out.println(idLists);
        Object user = userService.ResetPWD(idLists);
        return ResultUtil.success("初始化密码成功！", user);
    }

    /*
     * 修改登录人密码
     * */
    @PostMapping(value = "/modifyPWD")
    @ResponseBody
    public ResultUtil ModifyPWD(@RequestParam(value = "userid", required = false) Integer userid, @RequestParam(value = "password", required = false) String password) {
        System.out.println(userid + ',' + password);
        Object user = userService.ModifyPWD(userid, password);
        return ResultUtil.success("密码修改成功！", user);
    }
}
