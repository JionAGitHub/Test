package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //authorizeRequests所有security全注解配置实现的开端，表示开始说明需要的权限。
        //需要的额权限分为两部分，第一部分是拦截的路径，第二部分访问该路径需要的权限。
        //antMatchers表示拦截什么路径，permitAll任何权限都可以访问。
        //anyRequest()任意的请求，authenticated()认证后才能访问。
        //.and().csrf().disable();固定写法，表示使csrf失效。
        http
                .authorizeRequests()//授权
                .antMatchers("/**").permitAll()//permitAll():不需要任何权限访问所有
                .anyRequest().authenticated()//认证
                .and().csrf().disable();
    }
}
