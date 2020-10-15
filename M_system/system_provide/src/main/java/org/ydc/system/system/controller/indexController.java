package org.ydc.system.system.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.ydc.config.R;
import org.ydc.system.system.entity.SysUser;
import org.ydc.system.system.service.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@CrossOrigin
public class indexController {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Autowired
    SysMenuService sysMenuService;

    @ResponseBody
    @PostMapping("/login")
    public R login(Model model,@RequestBody SysUser sysUser,HttpServletRequest request) {
        //1.获取Subject
        System.out.println(sysUser.getLoginName()+sysUser.getPassword());
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getLoginName(),sysUser.getPassword());
        //3.执行登录方法
        try {
            subject.login(token);

            //登录成功
            //跳转到test.html
            return R.ok();
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败:用户名不存在
            return R.error().data("message","登录失败:用户名或者密码错误");
        } catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            return R.error().data("message","登录失败:用户名不存在");
        }catch (LockedAccountException e){
            return R.error().data("message","登录失败:用户被禁用");
        }
    }

    @GetMapping("/")
    public String goLogin() {
        return "/login";
    }

    @GetMapping("index")
    public String goindex() {
        return "/index";
    }

    @RequestMapping("/403")
    public String forbidden(){
        return "/error/403";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/login";
    }

    @RequestMapping("/download")
    public String logout(){
        return "/downloadFile";
    }
}
