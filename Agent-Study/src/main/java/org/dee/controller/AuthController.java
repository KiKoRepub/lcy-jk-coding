package org.dee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.util.StringUtil;
import org.dee.entity.SysUser;
import org.dee.entity.dto.user.UserLoginDTO;
import org.dee.entity.dto.user.UserRegisterDTO;
import org.dee.entity.vo.ResultBean;
import org.dee.entity.vo.user.UserLoginVo;
import org.dee.enums.ErrorCodeEnum;
import org.dee.mapper.UserMapper;
import org.dee.service.UserService;
import org.dee.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Tag(name = "认证管理")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    // DB mappers
    private final UserMapper userMapper;


    // -------------------- 注册 --------------------
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ResultBean register(@RequestBody UserRegisterDTO dto) {
        // 参数检查
        if (dto == null || StringUtil.isBlank(dto.getUsername()) || StringUtil.isBlank(dto.getPassword())) {
            return ResultBean.error(ErrorCodeEnum.PARAM_ERROR, "用户名或密码不能为空");
        }
        // 检查是否已存在
        SysUser exists = userMapper.findByUsername(dto.getUsername());
        if (exists != null) {
            return ResultBean.error(ErrorCodeEnum.SERVICE_ERROR, "用户已存在");
        }
        // 注册新用户
        userService.registerNewUser(dto);

        return ResultBean.success("注册成功");
    }

    // -------------------- JWT 登录 --------------------
    @PostMapping("/login")
    @Operation(summary = "用户登录 (JWT)")
    public ResultBean login(@RequestBody UserLoginDTO dto) {

        if (dto == null || StringUtil.isBlank(dto.getUsername()) || StringUtil.isBlank(dto.getPassword())) {
            return ResultBean.error(ErrorCodeEnum.PARAM_ERROR, "用户名或密码不能为空");
        }

        UserDetails details;
        try {
            details = userDetailsService.loadUserByUsername(dto.getUsername());
        } catch (Exception ex) {
            return ResultBean.error(ErrorCodeEnum.AUTH_ERROR, "用户名或密码错误");
        }

        if (!passwordEncoder.matches(dto.getPassword(), details.getPassword())) {
            return ResultBean.error(ErrorCodeEnum.AUTH_ERROR, "用户名或密码错误");
        }

        // 生成 JWT
        String token = JwtUtil.generateAccess(details.getUsername(),
                details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        UserLoginVo vo = new UserLoginVo();
        vo.setUserName(details.getUsername());
        vo.setRoles(details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        vo.setToken(token);
        vo.setNote("请在请求头加入：Authorization: Bearer " + token);

        return ResultBean.success("登录成功", vo);
    }

    // -------------------- me --------------------
    @GetMapping("/me")
    @Operation(summary = "当前用户信息 (JWT)")
    public ResultBean me(HttpServletRequest request) {
        String userToken = request.getHeader("Authorization");
        if (userToken == null) {
            return ResultBean.error(ErrorCodeEnum.AUTH_ERROR, "未登录");
        }
        String username = userService.analyzeUserNameFromToken(userToken);
        UserDetails details = userDetailsService.loadUserByUsername(username);

        return ResultBean.success( "OK", userInfo(details));
    }

    // -------------------- 角色 --------------------
    @GetMapping("/roles")
    @Operation(summary = "当前用户角色列表")
    public ResultBean roles(HttpServletRequest request) {
        String userToken = request.getHeader("Authorization");
        if (userToken == null) {
            return ResultBean.error(ErrorCodeEnum.AUTH_ERROR, "未登录");
        }
        String username = userService.analyzeUserNameFromToken(userToken);
        UserDetails details = userDetailsService.loadUserByUsername(username);

        return ResultBean.success("OK",
                details.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
    }

    // -------------------- logout（无状态） --------------------
    @PostMapping("/logout")
    public ResultBean logout() {
        SecurityContextHolder.clearContext();
        return ResultBean.success("无状态登出成功");
    }

    // -------------------- 工具方法 --------------------

    private Map<String, Object> userInfo(UserDetails details) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", details.getUsername());
        map.put("roles", details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList());
        return map;
    }
}
