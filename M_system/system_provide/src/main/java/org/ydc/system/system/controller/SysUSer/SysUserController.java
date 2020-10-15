package org.ydc.system.system.controller.SysUSer;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.ydc.config.R;
import org.ydc.system.system.controller.AbstractController;
import org.ydc.system.system.entity.*;
import org.ydc.system.system.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
@Controller
@RequestMapping("/system/sysuser")
@CrossOrigin
public class SysUserController extends AbstractController {

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

    private String prefix = "/system/sysuser";

        //添加用户
    @ResponseBody
    @PostMapping("addUser")
    @RequiresPermissions("system:user:add")
    public String addUser(Model model,@RequestBody SysUser sysUser,HttpServletRequest request){
      try {
            boolean sava = sysUserService.save(sysUser);
          return "ok";
        }catch(Exception e){
            logError(request, "[添加用户失败]", e);
        }

        return "ok";
    }

    //修改用户
    @ResponseBody
    @PostMapping("updateUser")
    @RequiresPermissions("system:user:update")
    public String updateUser(Model model, @RequestBody SysUser sysUser, HttpServletRequest request){
       try {
           boolean update = sysUserService.updateById(sysUser);
           return "ok";
       }catch(Exception e){
           logError(request, "[修改用户失败]", e);
       }
        return "false";
    }


    //根据id删除账号，并且删除用户与角色的绑定
    @GetMapping("removeUser/{userId}")
    @RequiresPermissions("system:user:delete")
    public ModelAndView removeUser(Model model, @PathVariable Long userId, HttpServletRequest request){
        try {
            boolean remove = sysUserService.removeById(userId);
            sysUserRoleService.removeById(userId);
            return new ModelAndView("redirect:"+prefix+"/getAllUser");//修改成功了跳转
        }catch(Exception e){
            logError(request, "[修改用户失败]", e);
            return new ModelAndView("redirect:"+prefix+"/getAllUser");//修改用户失败了跳转
        }
    }


    @GetMapping("/getAllUser")
    @RequiresPermissions(value = {"system:user:view","system:organ:view"})
    public String getAllUser(Model model,HttpServletRequest request){
       try{
           QueryWrapper<SysUser> wrapperAdmin = new QueryWrapper<>();
           wrapperAdmin
                   .eq("login_name", "admin");
           SysUser admin = sysUserService.getOne(wrapperAdmin);
           model.addAttribute("admin", admin);

           QueryWrapper<SysUser> wrapperOther = new QueryWrapper<>();
           wrapperOther
                   .ne("login_name", "admin")
                   .orderByAsc("user_id");
           List<SysUser> list = sysUserService.list(wrapperOther);
           model.addAttribute("list", list);
       }catch(Exception e){
           logError(request, "[获取用户失败]", e);
       }
        return "/user/userShow";
    }

    @ResponseBody
    @PostMapping("/getAllUserTable/{current}/{limit}")
    @RequiresPermissions(value = {"system:user:add","system:organ:add"})
    public R getAllUserTable(HttpServletRequest request,@RequestBody(required = false) SysUser sysUser, @PathVariable Long current, @PathVariable Long limit){
        try {
            Page<SysUser> sysUserPage = new Page<>(current,limit);
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("user_id");
            if(!StringUtils.isEmpty(sysUser.getDeptId())){
                wrapper.eq("dept_id",sysUser.getDeptId());
            }
            if(!StringUtils.isEmpty(sysUser.getLoginName())){
                wrapper.like("login_name",sysUser.getLoginName());
            }
            if(!StringUtils.isEmpty(sysUser.getPhonenumber())){
                wrapper.eq("phonenumber",sysUser.getPhonenumber());
            }
            if(!StringUtils.isEmpty(sysUser.getStatus())){
                if(sysUser.getStatus().equals("0"))
                    wrapper.eq("status","0");
                if(sysUser.getStatus().equals("1"))
                    wrapper.eq("status","1");
            }

            sysUserService.page(sysUserPage,wrapper);
            long total = sysUserPage.getTotal();//总记录数
            List<SysUser> records = sysUserPage.getRecords();//数据
            return R.ok().data("ListUser",records).data("total",total);

        }catch(Exception e){
            logError(request, "[获取用户失败]", e);
        }
        return null;
    }

    @ResponseBody
    @GetMapping("/getAllUserString")
    @RequiresPermissions(value = {"system:user:view","system:organ:view"})
    public List<SysUser> getAllUserString(Model model, HttpServletRequest request){
        try{
            QueryWrapper<SysUser> wrapperAdmin = new QueryWrapper<>();
            wrapperAdmin
                    .orderByAsc("user_id");
            List<SysUser> list = sysUserService.list(wrapperAdmin);
            return list;

        }catch(Exception e){
            logError(request, "[获取用户失败]", e);
            return null;
        }
    }

    @ResponseBody
    @GetMapping("/goUserById/{userId}")
    @RequiresPermissions("system:user:update")
    public SysUser goUserById(@PathVariable String userId){
        SysUser byId = sysUserService.getById(userId);
        return byId;
    }

    //根据用户名登录名，获取对应的校色，获取菜单的权限
    @ResponseBody
    @PostMapping("getMenu")
    public List<SysMenu> getMenu(HttpServletRequest request) {
       try {

           List<SysMenu> menusByCookie = sysUserService.getMenusByCookie(request);
           return menusByCookie;
       }catch (Exception e){
           logError(request, "[获取用户拥有的菜单失败]", e);
           return null;
       }
    }

    @GetMapping("/getOrganUser")
    @RequiresPermissions(value = {"system:user:add","system:organ:add"})
    public String getOrganUser(Model model,HttpServletRequest request){
        try {
            QueryWrapper<SysUser> wrapperAdmin = new QueryWrapper<>();
            wrapperAdmin
                    .eq("login_name", "admin");
            SysUser admin = sysUserService.getOne(wrapperAdmin);
            model.addAttribute("admin", admin);

            QueryWrapper<SysUser> wrapperOther = new QueryWrapper<>();
            wrapperOther
                    .ne("login_name", "admin")
                    .orderByAsc("user_id");
            List<SysUser> list = sysUserService.list(wrapperOther);
            model.addAttribute("list", list);
        }catch(Exception e){
            logError(request, "[获取用户失败]", e);
        }
        return "/menu/ZtreeMenu";
    }


    @ResponseBody
    @GetMapping("/getUserByOrgan/{id}")
    @RequiresPermissions(value = {"system:user:add","system:organ:add"})
    public List<SysUser> getUserByOrgan(Model model, HttpServletRequest request, @PathVariable String id){
        try {
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper
                    .eq("dept_id", id)
                    .orderByAsc("user_id");;
            List<SysUser> list = sysUserService.list(wrapper);
            return list;

        }catch(Exception e){
            logError(request, "[获取用户失败]", e);
        }
        return null;
    }



}

