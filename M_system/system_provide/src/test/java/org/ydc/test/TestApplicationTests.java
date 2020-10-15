package org.ydc.test;



import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@ComponentScan("org.ydc")
@SpringBootApplication
@MapperScan("org.ydc.system.system.mapper")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {
   /* <select id="MytreeMenu" resultMap="MyMenus">
    select sys_menu.menu_id,sys_menu.menu_name,sys_menu.parent_id,sys_menu.order_num from Transfer.sys_menu
            </select>

    <resultMap id="MyMenus" type="org.ydc.system.system.utils.MenuUtils">
        <result property="menuId" column="menu_id"/>
        <result property="text" column="menu_name"/>
        <result property="parentId" column="parent_id"/>
        <result property="orderNum" column="order_num"/>
    </resultMap>*/


}
