package org.ydc.system.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ydc.system.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ydc.system.system.utils.MenuUtils;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("select distinct m.perms from sys_menu m  left join sys_role_menu rm on m.menu_id = rm.menu_id left join sys_user_role ur on rm.role_id = ur.role_id left join sys_role r on r.role_id = ur.role_id where m.visible = '0' and r.status = '0' and ur.user_id=#{userId}")
    public List<String> selectPermsByUserId(@Param("userId") Long userId);//根据用户id查询出所有

    @Select("select distinct * from sys_menu m  left join sys_role_menu rm on m.menu_id = rm.menu_id left join sys_user_role ur on rm.role_id = ur.role_id left join sys_role r on r.role_id = ur.role_id where m.visible = '0' and r.status = '0' and ur.user_id=#{userId}")
    public List<SysMenu> selectMenuByUserId(@Param("userId") Long userId);//根据用户id查询角色拥有的菜单

    @Select("SELECT DISTINCT * FROM sys_menu m LEFT JOIN sys_role_menu rm ON m.menu_id = rm.menu_id LEFT JOIN sys_user_role ur ON rm.role_id = ur.role_id LEFT JOIN sys_role r ON r.role_id = ur.role_id LEFT JOIN sys_user u ON u.user_id = ur.user_id WHERE m.visible = '0' AND r.STATUS = '0' AND u.login_name = #{loginName}")
    public List<SysMenu> selectMenuByLoginName(@Param("loginName") String loginName);//根据用户登录名查询角色拥有的菜单
}
