package com.laowang.mymall.service.impl;

import com.laowang.mymall.common.utils.JwtTokenUtil;
import com.laowang.mymall.dao.UmsAdminRoleRelationDao;
import com.laowang.mymall.mbg.mapper.UmsAdminMapper;
import com.laowang.mymall.mbg.model.UmsAdmin;
import com.laowang.mymall.mbg.model.UmsAdminExample;
import com.laowang.mymall.mbg.model.UmsPermission;
import com.laowang.mymall.service.UmsAdminService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: my-mall
 * @description: 后台管理员Service实现类-专门解决依赖循环的
 * @author: Laowang
 * @create: 2023-05-14 17:12
 */
@Service
public class UmsAdminServiceImpl2 implements UmsAdminService2 {
    /**
     * 日志工具
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl2.class);

    /**
     * mapper接口（mbg自动生成）
     */
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    /**
     * admin-role关联mapper接口(手写添加)
     */
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    /**
     * 通过用户名获取UmsAdmin对象
     *
     * @param username 用户名
     * @return null-查无此人；非空-UmsAdmin后台用户对象
     */
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(example);
        if (umsAdmins != null && umsAdmins.size() > 0) {
            return umsAdmins.get(0);
        }
        return null;
    }

    /**
     * 获取用户的权限列表（包括+、-权限）
     * @param adminId 后台用户的ID
     * @return 权限列表
     */
    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminRoleRelationDao.getPermissionList(adminId);
    }
}
