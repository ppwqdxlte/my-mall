package com.laowang.mymall.mbg.mapper;

import com.laowang.mymall.mbg.model.UmsRolePermissionRelation;
import com.laowang.mymall.mbg.model.UmsRolePermissionRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UmsRolePermissionRelationMapper {
    long countByExample(UmsRolePermissionRelationExample example);

    int deleteByExample(UmsRolePermissionRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsRolePermissionRelation record);

    int insertSelective(UmsRolePermissionRelation record);

    List<UmsRolePermissionRelation> selectByExample(UmsRolePermissionRelationExample example);

    UmsRolePermissionRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsRolePermissionRelation record, @Param("example") UmsRolePermissionRelationExample example);

    int updateByExample(@Param("record") UmsRolePermissionRelation record, @Param("example") UmsRolePermissionRelationExample example);

    int updateByPrimaryKeySelective(UmsRolePermissionRelation record);

    int updateByPrimaryKey(UmsRolePermissionRelation record);
}