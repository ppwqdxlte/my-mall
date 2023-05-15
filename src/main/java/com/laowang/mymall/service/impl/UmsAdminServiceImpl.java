package com.laowang.mymall.service.impl;

import com.laowang.mymall.common.utils.JwtTokenUtil;
import com.laowang.mymall.dao.UmsAdminRoleRelationDao;
import com.laowang.mymall.mbg.mapper.UmsAdminMapper;
import com.laowang.mymall.mbg.model.UmsAdmin;
import com.laowang.mymall.mbg.model.UmsAdminExample;
import com.laowang.mymall.mbg.model.UmsPermission;
import com.laowang.mymall.service.UmsAdminService;
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
 * @description: 后台管理员Service实现类
 * @author: Laowang
 * @create: 2023-05-14 17:12
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    /**
     * 日志工具
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    /**
     * jwt.tokenHead
     */
    @Value("${jwt.tokenHead}")
    private String tokenHead;
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
     * JWT-token工具(手写非框架自带)
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    /**
     * 密码编码器(Security框架自带)
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 用户细节服务(Security框架自带)
     */
    @Autowired
    private UserDetailsService userDetailsService;

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
     * 后台用户注册
     *
     * @param umsAdminParam 待注册的新用户
     * @return null-已经存在注册失败，非空-新用户注册成功
     */
    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        // 查询用户是否已经存在
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdminParam.getUsername());
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(example);
        if (umsAdmins.size() > 0) {
            return null;
        }
        // 准备新用户
        UmsAdmin na = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, na);
        na.setCreateTime(new Date());
        na.setStatus(1);
        // 加密密码
        String encodePwd = passwordEncoder.encode(na.getPassword());
        na.setPassword(encodePwd);
        // 添加到数据库
        int insert = umsAdminMapper.insert(na);
        return insert == 1 ? na : null;
    }

    /**
     * 后台用户登录
     * @param username 用户名
     * @param password 密码
     * @return null-登录失败；非空-JwtToken
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            // TODO 我没闹明白这个 原理，怎么通过name查的UserDetails呢？并且还知道password呢？
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确！");
            }
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e){
            LOGGER.warn("登录异常:{}",e.getMessage());
        }
        return token;
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
