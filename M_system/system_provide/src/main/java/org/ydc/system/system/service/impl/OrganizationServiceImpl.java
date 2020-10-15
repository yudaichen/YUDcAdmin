package org.ydc.system.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ydc.system.system.entity.Organization;
import org.ydc.system.system.entity.SysUser;
import org.ydc.system.system.mapper.OrganizationMapper;
import org.ydc.system.system.service.OrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    //根据父公司id，查询子公司
    @Override
    public List<Organization> findChildren(String id) {
        QueryWrapper<Organization> wrapperChapter = new QueryWrapper<>();
        wrapperChapter
                .eq("pid", id);

        List<Organization> organizations = baseMapper.selectList(wrapperChapter);
        return organizations;
    }

    //得到所有的父公司
    @Override
    public List<Organization> MainCompany(String dataScope, Long deptId) {
        QueryWrapper<Organization> wrapperChapter = new QueryWrapper<>();
        if (dataScope == "1") {//如果是字符串1 获取全部数据
            wrapperChapter.
                    isNotNull("id");
        }
        if (dataScope == "3") {
            wrapperChapter.
                    eq("code", deptId);
        }
        if (dataScope == "4") {
            wrapperChapter.
                    eq("code", deptId);
            wrapperChapter.
                    and(wrapper -> wrapper.eq("pid", deptId));
        }
        List<Organization> organizations = baseMapper.selectList(wrapperChapter);
        return organizations;
    }

    @Override
    public int GODeletePid(Long id) {
        return baseMapper.DeletePid(id);
    }


    //============递归删除组织机构==================================
    public void removeChildById(Long id) {
        //1 创建list集合，用于封装所有组织机构id值
        List<Long> idList = new ArrayList<>();
        //2 向idList集合设置删除组织机构id
        this.selectPermissionChildById(id, idList);
        //把当前id封装到list里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //2 根据当前组织机构id，查询组织机构里面子机构id，封装到list集合
    private void selectPermissionChildById(Long id, List<Long> idList) {
        //查询组织机构里面子机构id
        QueryWrapper<Organization> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
        List<Organization> childIdList = baseMapper.selectList(wrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.stream().forEach(item -> {
            //封装idList里面
            idList.add(item.getId());
            //递归查询
            this.selectPermissionChildById(item.getId(), idList);
        });
    }


}
