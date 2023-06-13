package com.laowang.mymall.oss.minio.util;

import com.laowang.mymall.oss.minio.config.MinIOProperties;
import com.laowang.mymall.oss.minio.enumeration.MinIOFileRule;
import com.laowang.mymall.oss.minio.exception.MinioUploadException;
import io.minio.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: my-mall
 * @description: minio-操作工具类
 * @author: Laowang
 * @create: 2023-06-10 10:35
 */
@Component
public class MinIOHelper {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOProperties minIOProperties;

    /**
     * 根据名称创建桶，存在就不创建，不存在创建
     *
     * @param bucketName 桶的名称
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        BucketExistsArgs bucket = BucketExistsArgs.builder().bucket(bucketName).build();
        if (!minioClient.bucketExists(bucket)) {
            MakeBucketArgs myBucket = MakeBucketArgs.builder().bucket(bucketName).build();
            minioClient.makeBucket(myBucket);
        }
    }

    /**
     * 上传文件
     *
     * @param file       文件对象
     * @param bucketName 桶名称
     * @param rule       访问策略
     * @return 桶名/上传后的文件名
     */
    public String uploadFile(MultipartFile file, String bucketName, MinIOFileRule rule) throws FileNotFoundException, MinioUploadException {
        // 判断file是否为空
        if (ObjectUtils.isEmpty(file)) {
            throw new FileNotFoundException();
        }
        // 检查桶名
        if (ObjectUtils.isEmpty(bucketName)) {
            bucketName = minIOProperties.getBucket();
            if (ObjectUtils.isEmpty(bucketName)) {
                throw new MinioUploadException("连默认的桶名都没有!!");
            }
        }
        // 检查规则
        if (ObjectUtils.isEmpty(rule)) {
            rule = MinIOFileRule.READ_WRITE;
        }
        // 上传文件
        InputStream inputStream = null;
        try {
            // 检查桶名是否存在
            createBucket(bucketName);
            // 获得不带路径的文件名
            String originalFilename = file.getOriginalFilename();
            // 新文件名 = rule标签_桶名_时间戳_原名
            String uploadedFileName = rule.getLabel() + "_" + bucketName + "_" + System.currentTimeMillis() + "_" + originalFilename;
            // 开始上传
            inputStream = file.getInputStream();
            PutObjectArgs build = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(uploadedFileName)
                    .contentType(file.getContentType())
                    .stream(inputStream, file.getSize(), minIOProperties.getPartSize())
                    .build();
            minioClient.putObject(build);
            return bucketName + "/" + uploadedFileName;
        } catch (Exception e) {
            throw new MinioUploadException("上传失败！");
        } finally {
            try {
                // 记得一定要关闭！！！
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
