package com.hd.epac.controller;

import com.hd.epac.entity.User;
import com.hd.epac.service.UserService;
import com.hd.epac.util.ResultUtil;
import com.hd.epac.util.ValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

        return null;
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ResultUtil CheckLogin(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(username + ',' + password);
        User user = userService.CheckLogin(username, password);
        if (user == null) {
            return ResultUtil.error("用户名或者密码不正确，请重新输入！");
        } else {
            request.getSession().setAttribute("userInfo", user);
            return ResultUtil.success("登录成功！", user);
        }
    }
}
