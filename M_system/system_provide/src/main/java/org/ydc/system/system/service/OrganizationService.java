package org.ydc.system.system.service;

import org.ydc.system.system.entity.Organization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组织机构 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
public interface OrganizationService extends IService<Organization> {
    public List<Organization> findChildren(String id);

    public List<Organization> MainCompany(String dataScope,Long deptId);

    public int GODeletePid(Long id);

    public void removeChildById(Long id);//递归删除组织机构

}
