package org.ydc.system.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.ydc.system.system.entity.SysMenu;
import org.ydc.system.system.entity.SysRoleMenu;
import org.ydc.system.system.shiro.StringUtils;
import org.ydc.system.system.utils.MenuUtils;
import org.ydc.system.system.utils.addMenu;
import org.ydc.system.system.mapper.SysMenuMapper;
import org.ydc.system.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.ydc.system.system.service.SysRoleMenuService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    //传入一个带有角色id的的对象，创建菜单时跟角色绑定在一起
    @Override
    public boolean addMenu(addMenu addMenu) {
     try {
         SysMenu sysMenu = new SysMenu();
         sysMenu.setMenuId(addMenu.getMenuId());
         sysMenu.setMenuName(addMenu.getMenuName());
         sysMenu.setParentId(addMenu.getParentId());
         sysMenu.setOrderNum(addMenu.getOrderNum());
         sysMenu.setUrl(addMenu.getUrl());
         sysMenu.setTarget(addMenu.getTarget());
         sysMenu.setMenuType(addMenu.getMenuType());
         sysMenu.setVisible(addMenu.getVisible());
         sysMenu.setPerms(addMenu.getPerms());
         sysMenu.setIcon(addMenu.getIcon());
         sysMenu.setCreateBy(addMenu.getCreateBy());
         sysMenu.setUpdateBy(addMenu.getUpdateBy());
         sysMenu.setUpdateBy(addMenu.getRemark());
         int insert = baseMapper.insert(sysMenu);//向菜单表添加一条记录
         if(insert>0){//如果成功了，在角色和菜单关联表中添加一条记录
             SysRoleMenu sysRoleMenu = new SysRoleMenu();
             sysRoleMenu.setMenuId(addMenu.getMenuId());
             sysRoleMenu.setRoleId(addMenu.getMenuId());
             sysRoleMenuService.save(sysRoleMenu);
             return true;
         }else
             return false;
     }catch (Exception e){
         return false;
     }
    }

   //删除一个菜单,并且删除与角色关联的表,可以批量删除
    @Override
    public boolean removeMenu(List<SysMenu> sysMenus) {
      try {
          for (SysMenu Menu : sysMenus) {//SysMenu类型的菜单，增强for循环遍历结合
              sysRoleMenuService.removeById(Menu.getMenuId());
          }
          for(SysMenu Menu:sysMenus){
              QueryWrapper<SysRoleMenu> wrapperChapter = new QueryWrapper<>();
              wrapperChapter
                      .eq("menu_id",Menu.getMenuId());
              sysRoleMenuService.remove(wrapperChapter);
          }
          return true;
      }catch (Exception e) {
          return false;
      }
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId)
    {
        List<String> perms = baseMapper.selectPermsByUserId(userId);
        System.out.println(perms);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        System.out.println(permsSet);
        return permsSet;
    }

    @Override
    public List<SysMenu> selectMenuByUserId(Long userId) {
        return baseMapper.selectMenuByUserId(userId);
    }

    @Override
    public List<SysMenu> selectMenuByLoginName(String loginName) {
        return baseMapper.selectMenuByLoginName(loginName);
    }


}
