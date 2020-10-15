package org.ydc.system.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.ydc.system.system.utils.MenuUtils;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author testjava
 * @since 2020-08-31
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Organization对象", description="组织机构")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部门编号")
    private String code;

    @ApiModelProperty(value = "排序")
    private String name;

    @ApiModelProperty(value = "部门地址")
    private String address;

    @ApiModelProperty(value = "部门联系人或负责人")
    private String contact;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @JsonProperty("pId")
    @ApiModelProperty(value = "父级主键，上级部门")
    private Long pid;

    @ApiModelProperty(value = "排序")
    private Integer seq;

    @TableField(fill= FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否打开")
    @TableField(exist = false)
    private boolean open=true;

}
