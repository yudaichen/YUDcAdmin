package org.ydc.system.system;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.ydc.system.system.service.OrganizationService;
import org.ydc.system.system.service.SysUserService;


@EnableTransactionManagement
@ComponentScan("org.ydc")
@SpringBootApplication
@MapperScan("org.ydc.system.system.mapper")
public class SystemProvideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemProvideApplication.class, args);
    }

}
