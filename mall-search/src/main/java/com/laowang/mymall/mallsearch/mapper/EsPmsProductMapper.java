package com.laowang.mymall.mallsearch.mapper;

import com.laowang.mymall.mallsearch.document.EsPmsProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: my-mall
 * @description: es-mybatis-商品模块商品管理mapper
 * @author: Laowang
 * @create: 2023-06-20 13:03
 */
@Mapper
public interface EsPmsProductMapper {

    /**
     * 把mysql数据库中 商品模块的商品信息 导入到elasticsearch服务器中
     * 如果id不为空，就是导入单个商品信息；
     * 如果id为空，就是导入所有商品信息
     */
    List<EsPmsProduct> getPmsProductList(Long id);
}
