package org.dee.config;

import org.dee.filter.JwtAuthenticationFilter;
import org.dee.mapper.RoleMapper;
import org.dee.mapper.UserMapper;
import org.dee.security.DbUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    private JwtAuthenticationFilter jwtFilter;
    @Bean
    public UserDetailsService userDetailsService(UserMapper userMapper, RoleMapper roleMapper) {
        return new DbUserDetailsService(userMapper,roleMapper);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers( "/auth/login", "/auth/register").permitAll() // 开放登录和注册接口
                        .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll() // 开放 Swagger 接口文档

                        .anyRequest().authenticated()
                )
                // 添加 JWT 认证过滤器
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
//                // 配置异常处理，返回 JSON 而不是重定向到登录页
                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint((request, response, authException) -> {
//
//                            response.setContentType("application/json;charset=UTF-8");
//                            response.setStatus(401);
//                            response.getWriter().write("{\"code\":401,\"message\":\"未授权访问\",\"data\":null}");
//                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(403);
                            response.getWriter().write("{\"code\":403,\"message\":\"权限不足\",\"data\":null}");
                        })
                )
                // 设置为无状态会话管理
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
