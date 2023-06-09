package com.laowang.mymall.nosql.mongodb.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @program: my-mall
 * @description: mg-会员浏览历史记录document
 * @author: Laowang
 * @create: 2023-06-08 14:54
 */
@Document
@Getter
@Setter
public class MgMemberReadHistory {
    @Id
    private String id;
    @Indexed
    private Long memberId;
    private String memberNickname;
    private String memberIcon;
    @Indexed
    private Long productId;
    private String productName;
    private String productPic;
    private String productSubTitle;
    private String productPrice;
    private Date createTime;

    // Lombok 省略了 getter and setter
}
