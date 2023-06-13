package com.laowang.mymall.oss.minio.exception;

/**
 * @program: my-mall
 * @description: minio-上传异常
 * @author: Laowang
 * @create: 2023-06-10 12:53
 */
public class MinioUploadException extends Exception{

    private String message;

    public MinioUploadException(String message){
        super(message);
    }
}
