package com.jx.Config;

import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalYear;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 加密规则
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(3);
        return matcher;
    }


    /**
     * 注入自定义的Realm
     * @param matcher
     * @return
     */
    @Bean
    public ShiroRealm realm(HashedCredentialsMatcher matcher){
        ShiroRealm realm = new ShiroRealm();
        realm.setCredentialsMatcher(matcher);  // 把加密规则放进realm
        return realm;
    }

    /**
     * 注入secuManager
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;

    }

    /**
     * 使用shiro的注解
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager);
        return advisor;
    }

    /**
     * 让shiro的注解能够得到加载和执行
     * 使用aop功能
     * @return advisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

        /**
         * 将shiroBean 交给Spring容器回收
         * @return LifecycleBeanPostProcessor
         */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 配置shiro过滤器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager);
        filter.setLoginUrl("/login.html");
        filter.setUnauthorizedUrl("/login.html");
        Map<String,String> map = new HashMap<>();
        map.put("/", "anon");
        map.put("/login.html", "anon");
        map.put("/user/login", "anon");
        map.put("/regist", "anon");
        map.put("/user/regist", "anon");
        map.put("/static/**", "anon");
        map.put("/**", "authc");
        filter.setFilterChainDefinitionMap(map);
        return filter;
    }
}
