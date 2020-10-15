package org.ydc.system.system.service;

import org.apache.ibatis.annotations.Param;
import org.ydc.system.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.ydc.system.system.utils.MenuUtils;
import org.ydc.system.system.utils.addMenu;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
public interface SysMenuService extends IService<SysMenu> {

    boolean addMenu(addMenu addMenu);

    boolean removeMenu(List<SysMenu> sysMenus);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * 根据用户ID查询拥有的菜单
     *
     * @param userId 用户ID
     * @return 用户的所有菜单
     */
    List<SysMenu> selectMenuByUserId(Long userId);

    /**
     * 根据用户登录名查询拥有的菜单
     *
     * @param loginName 用户登录名
     * @return 用户的所有菜单
     */
    List<SysMenu> selectMenuByLoginName(String loginName);
}
