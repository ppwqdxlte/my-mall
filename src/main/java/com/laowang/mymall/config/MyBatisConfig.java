package com.laowang.mymall.config;

import org.springframework.context.annotation.Configuration;
import org.mybatis.spring.annotation.MapperScan;
/**
 * @program: my-mall
 * @description: MyBatis配置类
 * @author: Laowang
 * @create: 2023-05-11 15:37
 */
@Configuration
@MapperScan({
        "com.laowang.mymall.mbg.mapper",
        "com.laowang.mymall.dao",
        "com.laowang.mymall.nosql.elasticsearch.dao"})
public class MyBatisConfig {
}
