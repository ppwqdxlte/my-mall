package com.laowang.mymall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.time.Duration;

/**
 * @program: my-mall
 * @description: elasticsearch配置文件
 * @author: Laowang
 * @create: 2023-05-16 16:16
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
//        HttpHeaders compatibilityHeaders = new HttpHeaders();
//        compatibilityHeaders.add("X-Elastic-Product", "Elasticsearch");
        ClientConfiguration build = ClientConfiguration.builder()
                .connectedTo("192.168.3.128:9202")
                .withBasicAuth("elastic","6ZNa1LtVGDy*oTsdkov2")
                .withConnectTimeout(Duration.ofSeconds(5))  // 非必须
                .withSocketTimeout(Duration.ofSeconds(3))   // 非必须
//                .withDefaultHeaders(compatibilityHeaders)
//                .withHeaders(()->compatibilityHeaders)
                .build();
        return build;
    }
}
