package org.ydc.system.system.controller.SysRole;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.ydc.config.R;
import org.ydc.system.system.controller.AbstractController;
import org.ydc.system.system.entity.*;
import org.ydc.system.system.service.*;
import org.ydc.system.system.shiro.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
@Controller
@RequestMapping("/system/sysrole")
public class SysRoleController extends AbstractController {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysUserRoleService  SysUserRoleService;

    private String prefix = "/system/sysrole";
    //增加一角色
    @ResponseBody
    @PostMapping("addOneRole")
    @RequiresPermissions("system:role:add")
    public boolean addOneRole(@RequestBody SysRole sysRole,HttpServletRequest request){
       try {
           boolean save = sysRoleService.save(sysRole);
           return save;
       }catch (Exception e){
           logError(request, "[增加角色失败]", e);
        return false;}
    }

    //通过角色的id来更新角色信息
    @ResponseBody
    @PostMapping("updateRole")
    @RequiresPermissions("system:role:update")
    public boolean updateRole(@RequestBody SysRole sysRole,HttpServletRequest request) {
       try {
           boolean b = sysRoleService.updateById(sysRole);
           return b;
       }catch (Exception e) {
           logError(request, "[获取角色失败]", e);
           return false;
       }
    }
    //获取全部的角色
//    @GetMapping("getAllRole")
//    @RequiresPermissions("system:role:view")
//    public String getAllRole(Model model, HttpServletRequest request) {
//        try {
//            //构建条件
//            QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
//            wrapper
//                    .orderByAsc("role_sort")
//                   .ne("role_id",0);
//            List<SysRole> lists = sysRoleService.list(wrapper);
//            model.addAttribute("rolesList",lists);
//            return "/role/roleShow";
//        } catch (Exception e) {
//            logError(request, "[获取角色失败]", e);
//            return "/index";//如果失败了跳转的页面
//        }
//    }

    @GetMapping("getAllRole")
    @RequiresPermissions("system:role:view")
    public String gotoRoleShow(){
        return "/role/roleShow";
    }

    @ResponseBody()
    @PostMapping("getAllRolePage/{current}/{limit}")
    @RequiresPermissions("system:role:view")
    public R getAllRole(@RequestBody(required = false) SysRole sysRole, HttpServletRequest request, @PathVariable Long current, @PathVariable Long limit) {
        try {
            //构建条件
            Page<SysRole> sysRolePage= new Page<>(current,limit);
            QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("role_sort");
            if(!StringUtils.isEmpty(sysRole.getRoleName())){
                wrapper.like("role_name",sysRole.getRoleName());
            }
            if(!StringUtils.isEmpty(sysRole.getStatus())){
                wrapper.eq("status",sysRole.getStatus());
            }
            if(!StringUtils.isEmpty(sysRole.getStatus())){
                wrapper.like("role_key",sysRole.getRoleKey());
            }
            sysRoleService.page(sysRolePage,wrapper);

            long total = sysRolePage.getTotal();//总记录数
            List<SysRole> records = sysRolePage.getRecords();//数据
            return R.ok().data("ListRole",records).data("total",total);
        } catch (Exception e) {
            logError(request, "[获取角色失败]", e);
            return R.error();
        }
    }
    //通过id获取角色
    @ResponseBody
    @GetMapping("getRoleById/{roleId}")
    @RequiresPermissions("system:role:view")
    public SysRole getRoleById(Model model, HttpServletRequest request, @PathVariable String roleId){

        SysRole byId = sysRoleService.getById(roleId);
        return byId;
    }

    //绑定一个角色
    @ResponseBody
    @PostMapping("addBinding")
    @RequiresPermissions("system:role:update")
    public R addBinding(@RequestBody SysUserRole SysUserRole){
        boolean save = SysUserRoleService.save(SysUserRole);
        return R.ok().data("sava",save);
    }

    //解绑一个角色
    @ResponseBody
    @PostMapping("removeBinding")
    @RequiresPermissions("system:role:update")
    public R removeBinding(@RequestBody SysUserRole SysUserRole){
        boolean b = SysUserRoleService.removeById(SysUserRole.getUserId());
        return R.ok().data("b",b);
    }

    //根据角色id，删除角色id。具体实现：1.删除角色与菜单的绑定，并且删除菜单 2.删除角色表信息 3.删除用户与角色的绑定
    @GetMapping("removeRoleById/{roleId}")
    @RequiresPermissions("system:role:delete")
    public ModelAndView removeRoleById(HttpServletRequest request, @PathVariable String roleId){
        //构建条件
      try {
          boolean b = sysRoleService.removeRoleByroleId(roleId);
          return new ModelAndView("redirect:"+prefix+"/getAllRole");//角色删除成功了修改
      }catch (Exception e){
          logError(request, "[删除角色失败]", e);
          return new ModelAndView("redirect:"+prefix+"/system/sysrole/getAllUser");//角色删除失败了修改
      }
    }

}

