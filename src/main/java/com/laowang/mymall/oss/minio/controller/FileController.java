package com.laowang.mymall.oss.minio.controller;

import com.laowang.mymall.common.api.CommonResult;
import com.laowang.mymall.oss.minio.enumeration.MinIOFileRule;
import com.laowang.mymall.oss.minio.exception.MinioUploadException;
import com.laowang.mymall.oss.minio.util.MinIOHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

/**
 * @program: my-mall
 * @description: minio-操作文件controller
 * @author: Laowang
 * @create: 2023-06-10 14:42
 */
@Api(tags = "MinIO操作文件APIs")
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MinIOHelper minIOHelper;

    @ApiOperation(value = "上传文件")
    @PostMapping("/upload")
    @ResponseBody
    public CommonResult<String> upload(@RequestBody MultipartFile file,
                                       @RequestParam(required = false) String bucketName,
                                       @RequestParam(required = false) Integer ruleValue,
                                       HttpServletRequest request) {
        try {
            MinIOFileRule rule = MinIOFileRule.matchValue(ruleValue);
            String filePath = minIOHelper.uploadFile(file, bucketName, rule);
            return CommonResult.success(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (MinioUploadException e) {
            throw new RuntimeException(e);
        }
    }
}
