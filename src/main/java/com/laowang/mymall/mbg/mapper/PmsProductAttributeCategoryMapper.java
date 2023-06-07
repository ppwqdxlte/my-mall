package com.laowang.mymall.mbg.mapper;

import com.laowang.mymall.mbg.model.PmsProductAttributeCategory;
import com.laowang.mymall.mbg.model.PmsProductAttributeCategoryExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsProductAttributeCategoryMapper {
    long countByExample(PmsProductAttributeCategoryExample example);

    int deleteByExample(PmsProductAttributeCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductAttributeCategory record);

    int insertSelective(PmsProductAttributeCategory record);

    List<PmsProductAttributeCategory> selectByExample(PmsProductAttributeCategoryExample example);

    PmsProductAttributeCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductAttributeCategory record, @Param("example") PmsProductAttributeCategoryExample example);

    int updateByExample(@Param("record") PmsProductAttributeCategory record, @Param("example") PmsProductAttributeCategoryExample example);

    int updateByPrimaryKeySelective(PmsProductAttributeCategory record);

    int updateByPrimaryKey(PmsProductAttributeCategory record);
}