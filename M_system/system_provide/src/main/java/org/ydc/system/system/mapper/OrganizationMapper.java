package org.ydc.system.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ydc.system.system.entity.Organization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 组织机构 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */
public interface OrganizationMapper extends BaseMapper<Organization> {

    @Delete("DELETE FROM Transfer.organization WHERE id=#{id}")
    int DeletePid(@Param("id") Long id);
}
