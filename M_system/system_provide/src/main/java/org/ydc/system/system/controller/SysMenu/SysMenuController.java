package org.ydc.system.system.controller.SysMenu;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.ydc.config.R;
import org.ydc.system.system.controller.AbstractController;
import org.ydc.system.system.entity.SysMenu;
import org.ydc.system.system.entity.SysRoleMenu;
import org.ydc.system.system.utils.addMenu;
import org.ydc.system.system.service.SysMenuService;
import org.ydc.system.system.service.SysRoleMenuService;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
@Controller
@RequestMapping("/system/sysmenu/")
public class SysMenuController extends AbstractController {

    @Autowired
    SysMenuService SysMenuService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;




    //获取所有的菜单递归
    //把一个类赋值给另一个类  List<MenuUtils> sss =
    // 使用 BeanUtils.copyProperties(源字段, 新字段);新字段中的参数需要与源字段保持一致。需要的包：org.springframework.beans.BeanUtils;
/*    @ResponseBody
    @GetMapping("getAllMenu")
    @RequiresPermissions("system:menu:view")
    public List<MenuUtils> getAllMenu(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
      try {
          QueryWrapper<SysMenu> wrapperChapter = new QueryWrapper<>();
          List<SysMenu> list = SysMenuService.list(wrapperChapter);
          List<MenuUtils> utils = new LinkedList<>();
          for (SysMenu sysMenu : list) {
              MenuUtils menuUtils = new MenuUtils();
              menuUtils.setMenuId(sysMenu.getMenuId());
              menuUtils.setParentId(sysMenu.getParentId());
              menuUtils.setText(sysMenu.getMenuName());
              utils.add(menuUtils);
          }

       //   List<MenuUtils> bulid = PermissionHelper.bulid(utils);


          return utils;
      }catch (Exception e){
          return null;
      }
    }*/
    //获取所有的菜单
/*    @GetMapping("getAllMenu")
    public String getAllMenu(Model model, HttpServletRequest request){
        try {
          QueryWrapper<SysMenu> wrapperChapter = new QueryWrapper<>();
        List<SysMenu> list = SysMenuService.list(wrapperChapter);
            model.addAttribute("lists", list);
            return "/menu/menuShow";
        }catch (Exception e){
            logError(request, "[获取菜单失败]", e);
            return "/menu/menuShow";
        }
    }*/

    @GetMapping("getAllMenu")
    @RequiresPermissions("system:menu:view")
    public String getAllMenu(){
        return "/menu/menuShow";
    }


    /**
     *
     * @param request
     * @param sysMenu
     * @return 这个方法是menuShow.html和newUserShow.html关于 MenuTree完整的实现方法，
     */
    @PostMapping("bootStrapTableTree")
    @ResponseBody
    @RequiresPermissions("system:menu:view")
    public List<SysMenu> bootStrapTableTree(HttpServletRequest request ,@RequestBody(required = false)SysMenu sysMenu){
        try {

            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("menu_id");
            if(!StringUtils.isEmpty(sysMenu.getParentId())){
                wrapper.eq("parent_id",sysMenu.getParentId());
            }
            if(!StringUtils.isEmpty(sysMenu.getMenuName())){
                wrapper.like("menu_name",sysMenu.getMenuName());
            }
            if(!StringUtils.isEmpty(sysMenu.getPerms())){
                wrapper.like("perms",sysMenu.getPerms());
            }
            List<SysMenu> list = SysMenuService.list(wrapper);
            return list;
        }catch (Exception e){
            logError(request, "[获取菜单失败]", e);
            return null;
        }
    }


    @PostMapping("bootStrapTableTreeNewUserShow")
    @ResponseBody
    @RequiresPermissions("system:menu:view")
    public List<SysMenu> bootStrapTableTreeNewUserShow(HttpServletRequest request){
        try {

            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("menu_id");

            List<SysMenu> list = SysMenuService.list(wrapper);
            return list;
        }catch (Exception e){
            logError(request, "[获取菜单失败]", e);
            return null;
        }
    }


    //创建一个菜单
    @PostMapping("addmenu")
    @RequiresPermissions("system:menu:add")
    public R addmenu(@RequestBody addMenu addMenu,HttpServletRequest request){
       try {
           boolean b = SysMenuService.addMenu(addMenu);

           return R.ok();
       }catch (Exception e){
           logError(request, "[创建菜单失败]", e);
           return R.error();//如果创建失败了返回错误
       }
    }

    //修改一个菜单
    @ResponseBody
    @PostMapping("updataMenu")
    @RequiresPermissions("system:menu:update")
    public R updataMenu(@RequestBody SysMenu sysMenu,HttpServletRequest request){
        try {
            boolean b = SysMenuService.updateById(sysMenu);
            return R.ok();
        }catch (Exception e){
            logError(request, "[修改菜单失败]", e);
            return R.error();
        }
    }

    //根据菜单id删除菜单，并且删除与角色绑定的表
    @GetMapping("removeByd/{menuId}")
    @RequiresPermissions("system:menu:delete")
    public ModelAndView removeByid(HttpServletRequest request, @PathVariable String menuId){
     try {
         QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
         wrapper.eq("parent_id", menuId);
         List<SysMenu> list = SysMenuService.list(wrapper);

         QueryWrapper<SysMenu> wrapperChapter = new QueryWrapper<>();//删除menuid的菜单，并且删除parent_id与menuid相等的菜单
         wrapperChapter
                 .eq("menu_id", menuId)
                 .or()
                 .eq("parent_id", menuId);
         boolean remove = SysMenuService.remove(wrapperChapter);
         for(SysMenu sysMenu:list){
         QueryWrapper<SysRoleMenu> wrapperRoleMenu = new QueryWrapper<>();//删除角色菜单绑定表，带有要删除的菜单id 的信息
         wrapperRoleMenu.
                 eq("menu_id", sysMenu.getMenuId());
         sysRoleMenuService.remove(wrapperRoleMenu);}
         return new ModelAndView("redirect:/system/sysmenu/getAllMenu");//删除菜单成功了修改
     }catch (Exception e){
         logError(request, "[删除菜单失败]", e);
         return new ModelAndView("redirect:/system/sysmenu/getAllMenu");//删除菜单失败了修改
     }

    }

    //根据父菜id单查询所有它的子菜单
    @PostMapping("getChildren")
    @RequiresPermissions("system:menu:view")
    public String getChildren(String menuId){
        QueryWrapper<SysMenu> wrapperChapter = new QueryWrapper<>();
        wrapperChapter
                .eq("parent_id",menuId);
        List<SysMenu> list = SysMenuService.list(wrapperChapter);
        if (!StringUtils.isEmpty(list))
            return "";//如果查询成功了跳转
        else
            return "";//如果查询失败了跳转
    }

    //根据菜单id查询当前菜单信息
    @ResponseBody
    @GetMapping("getOneMenu/{menuId}")
    @RequiresPermissions("system:menu:view")
    public SysMenu getOneMenu(@PathVariable String menuId,HttpServletRequest request){
        SysMenu byId = SysMenuService.getById(menuId);
        return byId;
    }


    @GetMapping("GoTotreeMenu")
    public String GoTotreeMenu(){
        return "/menu/treeMenu";
    }

    @GetMapping("GoToZtreeMenu")
    public String GoToZtreeMenu(){
        return "/menu/ZtreeDemo";
    }

    @GetMapping("ZtreeMenu")
    public String ZtreeMenu(){
        return "/menu/ZtreeMenu";
    }
}

