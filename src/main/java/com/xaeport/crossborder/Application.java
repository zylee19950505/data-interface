package com.xaeport.crossborder;

import com.xaeport.crossborder.data.sources.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * spring-boot 启动入口
 * Created by zwj on 2017/07/18.
 */
@Configuration
@EnableTransactionManagement
@SpringBootApplication
@ServletComponentScan
@Import({DynamicDataSourceRegister.class})
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
