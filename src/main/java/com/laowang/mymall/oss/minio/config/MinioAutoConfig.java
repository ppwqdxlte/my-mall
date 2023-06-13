package com.laowang.mymall.oss.minio.config;

import com.laowang.mymall.oss.minio.util.MinIOHelper;
import io.minio.MinioClient;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

/**
 * @program: my-mall
 * @description: minio-mymall-配置类
 * @author: Laowang
 * @create: 2023-06-10 10:15
 */
@Configuration
public class MinioAutoConfig {

    @Autowired
    private MinIOProperties minIOProperties;

    /**
     * minio-mymall-连接客户端对象，单粒
     *
     * @return 你是我唯一的姐
     */
    @Bean
    public MinioClient minioClient() {
        if (ObjectUtils.isEmpty(minIOProperties.getEndpoint())) {
            throw new BeanCreationException("minio.endpoint is null !! ");
        }
        if (ObjectUtils.isEmpty(minIOProperties.getAccessKey())) {
            throw new BeanCreationException("minio.accessKey is null !! ");
        }
        if (ObjectUtils.isEmpty(minIOProperties.getSecretKey())) {
            throw new BeanCreationException("minio.secretKey is null !! ");
        }
        return MinioClient.builder()
                .endpoint(minIOProperties.getEndpoint())
                .credentials(minIOProperties.getAccessKey(), minIOProperties.getSecretKey())
                .build();
    }

    /**
     * minio操作工具对象，单粒
     *
     * @return 你是我唯一的弟
     */
    @Bean
    public MinIOHelper getMinIOHelper() {
        return new MinIOHelper();
    }
}
