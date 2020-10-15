package org.ydc.system.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.ydc.system.system.entity.SysMenu;
import org.ydc.system.system.entity.SysUser;
import org.ydc.system.system.mapper.SysUserMapper;
import org.ydc.system.system.service.SysMenuService;
import org.ydc.system.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    SysMenuService sysMenuService;

    @Override
    public List<SysMenu> getMenusByCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String loginName = new String();
        if (cookies != null) {
            // 遍历数组
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("LoginName")) {
                    // 取出cookie的值
                    loginName = cookie.getValue();
                }
            }
        }
        if(loginName==null){
            new Exception();
            return null;
        }
        return sysMenuService.selectMenuByLoginName(loginName);

    }
}
