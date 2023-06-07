package com.laowang.mymall.nosql.elasticsearch.service;

import com.laowang.mymall.nosql.elasticsearch.document.EsPmsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @program: my-mall
 * @description: ES-商品模块-搜索管理Service
 * @author: Laowang
 * @create: 2023-05-28 12:53
 */
public interface EsPmsProductService {
    /**
     * 从数据库中导入所有商品到ES
     */
    int importAll();

    /**
     * 根据id删除商品
     */
    void delete(Long id);

    /**
     * 根据id创建商品
     * (注意：从数据库拉取对应ID的商品到ES中，并非从无到有的创建！！
     * 这方法只是数据的搬运工！！！)
     */
    EsPmsProduct create(Long id);

    /**
     * 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索名称或者副标题
     */
    Page<EsPmsProduct> search(String keyword, Integer pageNum, Integer pageSize);
}
