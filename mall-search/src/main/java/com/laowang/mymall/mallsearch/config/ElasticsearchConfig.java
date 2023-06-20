package com.laowang.mymall.mallsearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.time.Duration;

/**
 * @program: my-mall
 * @description: elasticsearch配置
 * @author: Laowang
 * @create: 2023-06-20 12:06
 */
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    /**
     * 这是官网推荐的ES8最简单配置方法
     * 生成三个Bean：ElasticsearchClient RestClient ElasticsearchOperations
     * @return 客户端配置类
     */
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("192.168.3.128:9202")
                .withBasicAuth("elastic","6ZNa1LtVGDy*oTsdkov2")
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                .build();
    }
}
