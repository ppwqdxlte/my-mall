package com.laowang.mymall.mallmbg.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.laowang.mymall.mallmbg.model.SmsHomeBrand;
import com.laowang.mymall.mallmbg.model.SmsHomeBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface SmsHomeBrandMapper {
    long countByExample(SmsHomeBrandExample example);

    int deleteByExample(SmsHomeBrandExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsHomeBrand record);

    int insertSelective(SmsHomeBrand record);

    List<SmsHomeBrand> selectByExample(SmsHomeBrandExample example);

    SmsHomeBrand selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsHomeBrand record, @Param("example") SmsHomeBrandExample example);

    int updateByExample(@Param("record") SmsHomeBrand record, @Param("example") SmsHomeBrandExample example);

    int updateByPrimaryKeySelective(SmsHomeBrand record);

    int updateByPrimaryKey(SmsHomeBrand record);
}
