package org.ydc.system.system.service;


import org.ydc.system.system.entity.SysMenu;
import org.ydc.system.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据前端请求查询出用户登录名，再查询拥有的菜单
     *
     * @param httpServlet 前端请求
     * @return 菜单列表
     */
    public List<SysMenu> getMenusByCookie(HttpServletRequest httpServlet);

}
