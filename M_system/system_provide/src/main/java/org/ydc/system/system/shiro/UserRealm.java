package org.ydc.system.system.shiro;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.ydc.system.system.entity.SysUser;
import org.ydc.system.system.service.SysMenuService;
import org.ydc.system.system.service.SysRoleService;
import org.ydc.system.system.service.SysUserRoleService;
import org.ydc.system.system.service.SysUserService;


import javax.annotation.Resource;
import java.util.*;

/**
 * Created by yangqj on 2017/4/21.
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0)
    {
        //获取登录用户名
        String user = (String) arg0.getPrimaryPrincipal();
        System.out.println(user);
        // 角色列表
        Set<String> roles = new HashSet<String>();
        // 功能列表
        Set<String> menus = new HashSet<String>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限
        QueryWrapper<SysUser> wrapperChapter = new QueryWrapper<>();
        wrapperChapter
                .eq("login_name",user);
        SysUser one = sysUserService.getOne(wrapperChapter);//查询用户
        if (user.equals("admin"))
        {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        }
        else
        {
            roles = sysRoleService.selectRoleKeys(one.getUserId());
            System.out.println("\n\n\n\n\n\n"+"加入角色！！！！！！！！！！！！！！！！！！！！！！！！！！！！！"+roles);
            menus = sysMenuService.selectPermsByUserId(one.getUserId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            System.out.println("\n\n\n\n\n\n"+"加入权限！！！！！！！！！！！！！！！！！！！！！！！！！！！！！"+menus);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //认证
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();//得到用户名
        String password = new String((char[])token.getCredentials());//得到密码
        QueryWrapper<SysUser> wrapperChapter = new QueryWrapper<>();
        wrapperChapter
                .eq("login_name",username)
                .eq("password",password);
        SysUser user = sysUserService.getOne(wrapperChapter);//查询用户

        if(user==null) throw new UnknownAccountException();
        if (user.getStatus().equals("1")) {
            throw new LockedAccountException(); // 帐号锁定
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser",user);

        return new SimpleAuthenticationInfo(username, password, getName());
    }


    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}



