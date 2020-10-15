package org.ydc.system.system.utils;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.ydc.system.system.entity.SysMenu;
import java.io.Serializable;
import java.util.List;


@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MenuUtils implements Serializable {
    @JsonProperty("id")//属性名json时，这个指定字段的名字
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    @JsonProperty("name")
    @ApiModelProperty(value = "菜单名称")
    private String text;

    @JsonProperty("pId")
    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "是否打开")
    private boolean open=true;

    @JsonProperty("Children")
    @ApiModelProperty(value = "下级")
    @TableField(exist = false)
    private List<MenuUtils> nodes;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<MenuUtils> getNodes() {
        return nodes;
    }

    public void setNodes(List<MenuUtils> nodes) {
        this.nodes = nodes;
    }

    public MenuUtils() {
    }

    public MenuUtils(Long menuId, String text, Long parentId, Integer orderNum, boolean open, List<MenuUtils> nodes) {
        this.menuId = menuId;
        this.text = text;
        this.parentId = parentId;
        this.orderNum = orderNum;
        this.open = open;
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "MenuUtils{" +
                "menuId=" + menuId +
                ", text='" + text + '\'' +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                ", open=" + open +
                ", nodes=" + nodes +
                '}';
    }
}
