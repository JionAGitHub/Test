package com.tensquare.friend.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;


@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private  JwtUtil jwtUtil;

    public  boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        /* compiled code */
        System.out.println("经过拦截器");
        //无论如何都放行，具体能不能操作还是要在具体的操作中去判断。
        //拦截器只是负责把有请求头中包含token的令牌进行一次解析验证。
        String  header = request.getHeader("Authorization");


        if(header!=null &&  !"".equals(header)){
            if(header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if(roles!=null && roles.equals("admin")){
                        request.setAttribute("claims_admin",claims);
                    }
                    if(roles!=null &&roles.equals("user")){
                        request.setAttribute("claims_user",claims);
                    }

                }catch (Exception e){
                    throw new RuntimeException("令牌有误！");
                }

            }
        }

        return true;
    }

}
