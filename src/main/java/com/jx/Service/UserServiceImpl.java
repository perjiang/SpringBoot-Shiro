package com.jx.Service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl {

    public void checkLogin(String username,String password) throws IncorrectCredentialsException {
            // 使用shiro的suject对象完成登录
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            subject.login(token);

    }

}
