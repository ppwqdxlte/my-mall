package com.laowang.mymall.nosql.elasticsearch.dao;

import com.laowang.mymall.nosql.elasticsearch.document.EsPmsProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: my-mall
 * @description: ES-商品模块-Dao
 * @author: Laowang
 * @create: 2023-05-28 13:51
 */
@Mapper
public interface EsPmsProductDao {

    List<EsPmsProduct> getAllEsProductList(@Param("id") Long id);
}
