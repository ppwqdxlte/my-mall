package com.laowang.mymall.service.impl;

import com.github.pagehelper.PageHelper;
import com.laowang.mymall.mallmbg.mapper.PmsBrandMapper;
import com.laowang.mymall.mallmbg.model.PmsBrand;
import com.laowang.mymall.mallmbg.model.PmsBrandExample;
import com.laowang.mymall.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: my-mall
 * @description: 品牌管理Service实现类
 * @author: Laowang
 * @create: 2023-05-12 15:03
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
    @Autowired
    private PmsBrandMapper brandMapper;

    @Override
    public List<PmsBrand> listAllBrands() {
        // 新建example对象不格外设置属性的话就是：没有条件，查询所有
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrand brand) {
        // insertSelective方法和insert方法区别就是选择性设置属性值，后者都是默认值
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public PmsBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listBrandsByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return brandMapper.selectByExample(new PmsBrandExample());
    }
}
