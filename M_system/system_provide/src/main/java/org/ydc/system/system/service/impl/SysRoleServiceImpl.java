package org.ydc.system.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.ydc.system.system.entity.*;
import org.ydc.system.system.service.SysRoleMenuService;
import org.ydc.system.system.shiro.StringUtils;
import org.ydc.system.system.utils.addRole;
import org.ydc.system.system.mapper.SysRoleMapper;
import org.ydc.system.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.ydc.system.system.service.SysUserRoleService;

import javax.management.relation.Role;
import java.util.*;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

        @Autowired
    SysUserRoleService sysUserRoleService;

        @Autowired
        SysRoleService sysRoleService;

        @Autowired
    SysRoleMenuService sysRoleMenuService;




        @Override
        public boolean addOneRole(addRole addRole) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(addRole.getRoleId());
        sysRole.setRoleName(addRole.getRoleName());
        sysRole.setRoleKey(addRole.getRoleKey());
        sysRole.setRoleSort(addRole.getRoleSort());
        sysRole.setDataScope(addRole.getDataScope());
        sysRole.setStatus(addRole.getStatus());
        sysRole.setDelFlag(addRole.getDelFlag());
        sysRole.setCreateBy(addRole.getCreateBy());
        sysRole.setCreateBy(addRole.getUpdateBy());
        sysRole.setRemark(addRole.getRemark());
            int insert = baseMapper.insert(sysRole);
        if (insert>0) {//如果增加就成功绑定
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(addRole.getUserId());
            sysUserRole.setRoleId(addRole.getRoleId());
            sysUserRoleService.save(sysUserRole);
            return true;
        }
        else
            return false;
    }


    @Override
    public Set<String> selectRoleKeys(Long userId) {
        List<SysRole> rolesByUserId = baseMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : rolesByUserId)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public boolean removeRoleByroleId(String roleId) {
        QueryWrapper<SysRoleMenu> wrapperRemoveRoleMenu = new QueryWrapper<>();//根据roleId删除角色与菜单的绑定
        wrapperRemoveRoleMenu
                .eq("role_id", roleId);
        sysRoleMenuService.remove(wrapperRemoveRoleMenu);

        QueryWrapper<SysRole> wrapperRemoveRole = new QueryWrapper<>();//根据roleId删除角色表的信息
        wrapperRemoveRole
                .eq("role_id", roleId);
        baseMapper.delete(wrapperRemoveRole);

        QueryWrapper<SysUserRole> wrapperUser = new QueryWrapper<>();//根据roleId删除角色与用户的绑定
        wrapperUser
                .eq("role_id", roleId);
        sysUserRoleService.remove(wrapperUser);
        return true;
    }


}



