package org.ydc.system.system.utils;




import org.ydc.system.system.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建菜单数据
 * </p>
 *
 * @author qy
 * @since 2020-9-16
 */
public class PermissionHelper {

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    public static List<MenuUtils> bulid(List<MenuUtils> treeNodes) {
        List<MenuUtils> trees = new ArrayList<>();
        for (MenuUtils treeNode : treeNodes) {
            if (treeNode.getParentId()==0) {
                treeNode.setOrderNum(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static MenuUtils findChildren(MenuUtils treeNode, List<MenuUtils> treeNodes) {
        treeNode.setNodes(new ArrayList<MenuUtils>());

        for (MenuUtils it : treeNodes) {
            if(treeNode.getMenuId().equals(it.getParentId())) {
                int level = treeNode.getOrderNum() + 1;
                it.setOrderNum(level);
                if (treeNode.getNodes() == null) {
                    treeNode.setNodes(new ArrayList<>());
                }
                treeNode.getNodes().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }




}