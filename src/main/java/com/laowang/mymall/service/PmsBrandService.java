package com.laowang.mymall.service;

import com.laowang.mymall.mallmbg.model.PmsBrand;

import java.util.List;

/**
 * @program: my-mall
 * @description: 品牌管理Service
 * @author: Laowang
 * @create: 2023-05-12 14:23
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrands();
    int createBrand(PmsBrand brand);
    int deleteBrand(Long id);
    int updateBrand(Long id,PmsBrand brand);
    PmsBrand getBrand(Long id);
    List<PmsBrand> listBrandsByPage(int pageNum,int pageSize);
}
