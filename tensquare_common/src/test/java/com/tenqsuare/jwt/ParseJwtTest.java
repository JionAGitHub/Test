package com.tenqsuare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwtTest {

    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NiIsImlhdCI6MTYwMDQxNjIwNywic3ViIjoi6amsIiwiZXhwIjoxNjAwNDE2MjY3LCJyb2xlIjoiYWRtaW4ifQ.jQa3ZTNHLn2Awqg79W65zS3nnYV5tsOIoDXXLDtBoa0")
                .getBody();
        System.out.println("用户id："+claims.getId());
        System.out.println("用户名"+claims.getSubject());
        System.out.println("登陆时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()) );
        System.out.println("用户角色："+claims.get("role"));
    }
}
