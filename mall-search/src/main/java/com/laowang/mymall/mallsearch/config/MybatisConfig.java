package com.laowang.mymall.mallsearch.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: my-mall
 * @description: mybatis配置
 * @author: Laowang
 * @create: 2023-06-20 13:00
 */
@Configuration
@MapperScan({"com.laowang.mymall.mallsearch.mapper"})
public class MybatisConfig {
}
