package com.laowang.mymall.service;

import com.laowang.mymall.mallmbg.model.UmsAdmin;
import com.laowang.mymall.mallmbg.model.UmsPermission;

import java.util.List;

/**
 * 后台管理员Service2-专门用于解决依赖循环的，只在JwtUserDetailsService中使用
 * Created by laowang on 2023/5/14.
 */
public interface UmsAdminService2 {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(Long adminId);
}
