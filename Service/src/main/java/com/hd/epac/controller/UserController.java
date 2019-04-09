package com.hd.epac.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hd.epac.entity.User;
import com.hd.epac.service.UserService;
import com.hd.epac.util.ResultUtil;
import com.hd.epac.util.ValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
        User user = userService.CheckLogin(username, password);
        if (user == null) {
            return ResultUtil.error("用户名或者密码不正确，请重新输入！");
        } else {
            request.getSession().setAttribute("userInfo", user);
            return ResultUtil.success("登录成功！", user);
        }
    }

    @PostMapping(value = "/addUser")
    @ResponseBody
    public ResultUtil AddUser(@RequestParam(value = "userid", required = false) Integer userid, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "registtime", required = false) Timestamp registtime, @RequestParam(value = "place", required = false) String place, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "birth", required = false) String birth, @RequestParam(value = "admin", required = false) Integer admin, @RequestParam(value = "state", required = false) Integer state, @RequestParam(value = "gender", required = false) Integer gender) {
        System.out.println(userid + ',' + username + ',' + password + ',' + registtime + ',' + place + ',' + age + ',' + birth + ',' + admin + ',' + state + ',' + gender);
        Object user = userService.AddUser(userid, username, password, registtime, place, age, birth, admin, state, gender);
        return ResultUtil.success("添加成功！", user);
    }

    @PostMapping(value = "/updateUser")
    @ResponseBody
    public ResultUtil UpdateUser(@RequestParam(value = "userid", required = false) Integer userid, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "place", required = false) String place, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "birth", required = false) String birth, @RequestParam(value = "admin", required = false) Integer admin, @RequestParam(value = "state", required = false) Integer state, @RequestParam(value = "gender", required = false) Integer gender) {
        System.out.println(userid + ',' + username + ',' + password + ',' + place + ',' + age + ',' + birth + ',' + admin + ',' + state + ',' + gender);
        Object user = userService.UpdateUser(userid, username, password, place, age, birth, admin, state, gender);
        return ResultUtil.success("修改成功！", user);
    }

    @PostMapping(value = "/getUsers")
    @ResponseBody
    public ResultUtil FindAllUsers(@RequestParam(value = "pageIndex", required = false) Integer pageIndex, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<User> list = userService.FindAllUsers();
        PageInfo pageInfo = new PageInfo(list);
        System.out.println(pageInfo);
        return ResultUtil.success("查询成功！", pageInfo);
    }

    @PostMapping(value = "/delUser")
    @ResponseBody
    public ResultUtil DelUser(@RequestParam(value = "userid", required = false) Integer userid) {
        System.out.println(userid);
        Object user = userService.DelUser(userid);
        return ResultUtil.success("删除成功！", user);
    }

    @PostMapping(value = "/delUsers")
    @ResponseBody
    public ResultUtil DelUsers(@RequestParam(value = "userid", required = false) Integer userid) {
        System.out.println(userid);
        Object user = userService.DelUsers(userid);
        return ResultUtil.success("删除成功！", user);
    }

    @PostMapping(value = "/updateState")
    @ResponseBody
    public ResultUtil UpdateState(@RequestParam(value = "userid", required = false) Integer userid, @RequestParam(value = "state", required = false) Integer state) {
        System.out.println(userid + ',' + state);
        Object user = userService.UpdateState(userid, state);
        return ResultUtil.success("状态修改成功！", user);
    }
}
