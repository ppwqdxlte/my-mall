package com.laowang.mymall.nosql.elasticsearch.repository;

import com.laowang.mymall.nosql.elasticsearch.document.EsPmsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @program: my-mall
 * @description: ES-商品模块-操作类
 * @Note: 相当于Mybatis中的Mapper.java + mapper.XML
 * @author: Laowang
 * @create: 2023-05-28 12:36
 */
@Component
public interface EsPmsProductRepository extends ElasticsearchRepository<EsPmsProduct,Long> {
    /**
     * 商品搜索查询
     * @param name          商品名称
     * @param subTitle      商品标题
     * @param keywords      商品关键字
     * @param pageable      分页信息
     * @return              商品分页内容
     */
    Page<EsPmsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable pageable);
}
