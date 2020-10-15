package org.ydc.system.system.service;

import org.ydc.system.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.ydc.system.system.utils.addRole;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
public interface SysRoleService extends IService<SysRole> {

    boolean addOneRole(addRole addRole);


    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
   Set<String> selectRoleKeys(Long userId);

    /**
     * 根据角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    boolean removeRoleByroleId(String roleId);

}
