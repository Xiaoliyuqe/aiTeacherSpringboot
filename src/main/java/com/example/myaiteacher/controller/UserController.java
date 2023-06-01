package com.example.myaiteacher.controller;


import com.example.myaiteacher.common.Result;
import com.example.myaiteacher.entity.User;
import com.example.myaiteacher.entity.request.UserToken;
import com.example.myaiteacher.entity.request.UsnPsw;
import com.example.myaiteacher.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@CrossOrigin //解决跨域问题（前端后端端口不一致时）
@RestController //必写
@RequestMapping("/user") //整个类都在这个路径之后
public class UserController {

    UserService userService;

    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user){
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        String msg = userService.addUser(user);
        if (msg.equals(""))
            return Result.success();
        else
            return Result.error(msg);
    }

    @GetMapping("/changePassword")
    public Result changePassword(String username, String newPassword){
        newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        //System.out.println(username + "  666  " + newPassword);
        boolean is_ok = userService.changePassword(username, newPassword);
        if (is_ok)
            return Result.success();
        return Result.error("修改密码失败");
    }

//    @GetMapping("/deleteUser")
//    public Result deleteUser(String username){
//        //System.out.println("收到删除请求" + username);
//        boolean is_ok = userService.delete(username);
//        if (is_ok)
//            return Result.success();
//        return Result.error("删除失败");
//    }

    @PostMapping("/login")
    public Result login(HttpServletRequest hsr, @RequestBody UsnPsw usnPsw){
        //System.out.println(username + password);
        usnPsw.setPassword(DigestUtils.md5DigestAsHex(usnPsw.getPassword().getBytes()));
        User user = userService.login(usnPsw.getUsername(), usnPsw.getPassword());

        // 生成Token
        if (user != null){
            HttpSession session = hsr.getSession();
            //String token = UUID.randomUUID().toString();
            String token = session.getId();
            session.setAttribute("token", token);
            session.setAttribute("username", user.getUsername());

            UserToken ut = new UserToken();
            ut.setUser(user);
            ut.setToken(token);
            return Result.success(ut);
        }
        return Result.error("用户名或密码错误");
    }
}
