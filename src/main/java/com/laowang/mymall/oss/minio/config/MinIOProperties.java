package com.laowang.mymall.oss.minio.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: my-mall
 * @description: minio-客户端-连接参数
 * @notation: io.minio官方已经有了一个MinioProperties故而避免重名写成大写IO
 * @author: Laowang
 * @create: 2023-06-10 10:01
 */
@Data
@Component
public class MinIOProperties {

    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.bucket}")
    private String bucket;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    /**
     * 分片大小
     */
    private long partSize = 5 * 1024 * 1024;
}
