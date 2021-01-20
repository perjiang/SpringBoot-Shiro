package com.jx.Controller;

import com.jx.Service.UserServiceImpl;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/login")
    public String login(String username,String password)  {
        try {
            userService.checkLogin(username,password);
            System.out.println("ok");
        } catch (Exception e) {
            System.out.println("********error");
            return "/login.html";
        }
        return "index";
    }

    @RequiresRoles(value = "admin")
    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return "拥有该权限";
    }
}
