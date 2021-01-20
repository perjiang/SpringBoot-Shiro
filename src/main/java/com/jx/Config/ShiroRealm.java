package com.jx.Config;

import com.jx.Beans.user;
import com.jx.Dao.UserDao;
import com.jx.Service.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.zip.Adler32;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserDao userDao;

    /**
     *
     * @param principals  doGetAuthenticationInfo返回对象的第一个参数
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println(principals.toString()+"------------------------------");
        String username = principals.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        set.add("admin");
        if (username.equals("zs")){
            info.setRoles(set);
        }
        return info;
    }

    /**
     *
     * @param token  suject.login(token)
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken tokenstr = (UsernamePasswordToken)token;
//        String passwrod = new String(tokenstr.getPassword());
        String username = tokenstr.getUsername();
        // 通过用户名去查找password
//        String passwrod = userService.getPasswrod(username);
        user user = userDao.SelectUserByName(username);
        if (user == null){
            throw  new AuthenticationException("error----");
        }
        String password = user.getPassword();
        String salt = user.getSalt();
        return new SimpleAuthenticationInfo(username,password, ByteSource.Util.bytes(salt),getName());
//        return null;
    }
}
