package com.laowang.mymall.component;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @program: my-mall
 * @description: 避免依赖循环，单独弄个Bean
 * @author: Laowang
 * @create: 2023-05-15 12:35
 */
@Component
public class JwtPasswordEncoder {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
