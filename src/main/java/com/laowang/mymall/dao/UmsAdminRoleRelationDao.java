package com.laowang.mymall.dao;

import com.laowang.mymall.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: my-mall
 * @description: 后台用户与角色管理自定义dao(本质就是mapper接口)
 * @author: Laowang
 * @create: 2023-05-14 17:40
 */
@Mapper
public interface UmsAdminRoleRelationDao {
    /**
     * 获取用户所有权限（包括+、-权限）
     * @param adminId 后台用户ID
     * @return 该用户拥有的权限列表
     */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
