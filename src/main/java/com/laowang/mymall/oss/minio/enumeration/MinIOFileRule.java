package com.laowang.mymall.oss.minio.enumeration;

import lombok.Getter;

/**
 * @program: my-mall
 * @description: minio-BucketAccessRules桶访问策略
 * @note: 该枚举类实现的是Bucket Access Rules配置内容，方便开发者调用，不在用于记忆前缀
 * @author: Laowang
 * @create: 2023-06-10 10:57
 */
@Getter
public enum MinIOFileRule {

    WRITE_ONLY(0,"w"),
    READ_ONLY(1,"r"),
    READ_WRITE(2,"r-w");

    private Integer value;
    private String label;

    MinIOFileRule(Integer value,String label){
        this.value = value;
        this.label = label;
    }

    public static MinIOFileRule matchValue(Integer value){
        for (MinIOFileRule minIOFileRule : values()) {
            if (minIOFileRule.getValue() == value.intValue()){
                return minIOFileRule;
            }
        }
        return READ_WRITE;
    }
}

