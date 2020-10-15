package org.ydc.system.system.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.session.SessionListener;
import org.ydc.system.system.shiro.UserRealm;

import java.util.*;



@Configuration
public class ShiroConfig {

        //shiroFilterFactoryBean
        @Bean
        public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
            ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
            //设置安全管理器
            shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
            //添加shiro的内置过滤器
        /*
            anon: 无需认证就可访问
            authc：必须认证才能访问
            user：必须拥有记住我功能才能访问
            perms: 拥有对某个资源的权限才能访问
            role:拥有某个角色权限才能访问
       */
            // 必须设置 SecurityManager
            shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
            // 如果不设置默认会自动寻找Web工程根目录下的"/myloginData.jsp"页面
            shiroFilterFactoryBean.setLoginUrl("/");
            // 登录成功后要跳转的链接
            shiroFilterFactoryBean.setSuccessUrl("/index");
            //未授权界面;
            shiroFilterFactoryBean.setUnauthorizedUrl("/403");
            //拦截器.
            Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

            //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
            filterChainDefinitionMap.put("/system/sysuser/getMenu","anon");
            filterChainDefinitionMap.put("/logout", "logout");
            filterChainDefinitionMap.put("/css/**","anon");
            filterChainDefinitionMap.put("/js/**","anon");
            filterChainDefinitionMap.put("/img/**","anon");
            filterChainDefinitionMap.put("/font-awesome/**","anon");
            filterChainDefinitionMap.put("/swagger-ui.html/**","anon");

            //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
            //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->

            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


            return shiroFilterFactoryBean;
        }

        //DafaultWebSecurituManager
        @Bean(name="securityManager")
        public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("UserRealm") UserRealm userRealm){
            DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
            //关联UserRealm
            securityManager.setRealm(userRealm);

            return securityManager;
        }

        //整合thymeleaf
        @Bean
        public ShiroDialect getShiroDialect(){
            return new ShiroDialect();
        }


        @Bean(name ="UserRealm")
        public UserRealm myShiroRealm(){
            UserRealm myShiroRealm = new UserRealm();
            return myShiroRealm;
        }

    //配置shiro session 的一个管理器
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getDefaultWebSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session过期时间
        sessionManager.setGlobalSessionTimeout(60*60*1000);
        return sessionManager;
    }
    /*加入注解的使用，不加入这个注解不生效--开始*/
    /**
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /*加入注解的使用，不加入这个注解不生效--结束*/

}
