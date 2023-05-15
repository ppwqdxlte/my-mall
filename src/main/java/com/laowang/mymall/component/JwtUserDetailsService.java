package com.laowang.mymall.component;

import com.laowang.mymall.dto.AdminUserDetails;
import com.laowang.mymall.mbg.model.UmsAdmin;
import com.laowang.mymall.mbg.model.UmsPermission;
import com.laowang.mymall.service.UmsAdminService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: my-mall
 * @description: 依赖循环，怕了怕了
 * @author: Laowang
 * @create: 2023-05-15 12:54
 */
@Component
public class JwtUserDetailsService {

    @Autowired
    private UmsAdminService2 umsAdminService2;

    /**
     * 这里DIY自己的Bean实现UserDetailsService接口，类似于匿名内部类的作用
     * @return UserDetailsService匿名实现类的对象
     */
    @Bean
    public UserDetailsService userDetailsService(){
        // 获取登录用户信息,此lambda表达式实现的是loadUserByUsername(String username)方法
        return username -> {
            // 看吧！在这里从数据库查的后台用户！！
            UmsAdmin admin = umsAdminService2.getAdminByUsername(username);
            if (admin != null){
                // 进一步查询该用户的所有权限列表(包含角色权限以及定制的+、-权限)
                List<UmsPermission> permissionList = umsAdminService2.getPermissionList(admin.getId());
                return new AdminUserDetails(admin,permissionList);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }
}
