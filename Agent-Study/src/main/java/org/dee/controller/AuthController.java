package org.dee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.poi.util.StringUtil;
import org.dee.entity.dto.user.UserLoginDTO;
import org.dee.entity.dto.user.UserRegisterDTO;
import org.dee.entity.vo.ResultBean;
import org.dee.entity.vo.user.UserLoginVo;
import org.dee.enums.ErrorCodeEnum;
import org.dee.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Tag(name = "认证管理")
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    // DB mappers
    private final org.dee.mapper.UserMapper userMapper;
    private final org.dee.mapper.RoleMapper roleMapper;
    private final org.dee.mapper.UserRoleMapper userRoleMapper;

    public AuthController(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          org.dee.mapper.UserMapper userMapper,
                          org.dee.mapper.RoleMapper roleMapper,
                          org.dee.mapper.UserRoleMapper userRoleMapper) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }

    // -------------------- 注册 --------------------
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ResultBean register(@RequestBody UserRegisterDTO dto) {
        if (dto == null || StringUtil.isBlank(dto.getUsername()) || StringUtil.isBlank(dto.getPassword())) {
            return ResultBean.error(ErrorCodeEnum.PARAM_ERROR, "用户名或密码不能为空");
        }

        // 检查是否已存在
        org.dee.entity.User exists = userMapper.findByUsername(dto.getUsername());
        if (exists != null) {
            return ResultBean.error(ErrorCodeEnum.SERVICE_ERROR, "用户已存在");
        }

        // 构造用户：先使用 BCrypt 进行散列，再由 PasswordEncryptedHandler 进行对称加密入库
        org.dee.entity.User toSave = new org.dee.entity.User();
        toSave.setUsername(dto.getUsername());
        toSave.setPassword(passwordEncoder.encode(dto.getPassword()));
        toSave.setEnabled(true);
        toSave.setCreateTime(java.time.LocalDateTime.now());
        toSave.setUpdateTime(java.time.LocalDateTime.now());
        userMapper.insert(toSave);

        // 角色处理：默认 USER
        List<String> inputRoles = (dto.getRoles() == null || dto.getRoles().isEmpty())
                ? java.util.List.of("USER")
                : dto.getRoles().stream().map(String::toUpperCase).toList();

        for (String r : inputRoles) {
            org.dee.entity.Role role = roleMapper.findByName(r);
            if (role == null) {
                role = new org.dee.entity.Role();
                role.setRoleName(r);
                role.setDescription(r + " role");
                roleMapper.insert(role);
            }
            org.dee.entity.UserRole ur = new org.dee.entity.UserRole();
            ur.setUserId(toSave.getId());
            ur.setRoleId(role.getId());
            ur.setCreateTime(java.time.LocalDateTime.now());
            userRoleMapper.insert(ur);
        }

        return ResultBean.success("注册成功");
    }

    // -------------------- JWT 登录 --------------------
    @PostMapping("/login")
    @Operation(summary = "用户登录 (JWT)")
    public ResultBean login(@RequestBody UserLoginDTO dto) {

        if (dto == null || isBlank(dto.getUsername()) || isBlank(dto.getPassword())) {
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
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) {
            return json(HttpStatus.UNAUTHORIZED, "未登录");
        }

        UserDetails details = (UserDetails) authentication.getPrincipal();
        return json(HttpStatus.OK, "OK", userInfo(details));
    }

    // -------------------- 角色 --------------------
    @GetMapping("/roles")
    @Operation(summary = "当前用户角色列表")
    public ResponseEntity<?> roles(Authentication authentication) {
        if (authentication == null) {
            return json(HttpStatus.UNAUTHORIZED, "未登录");
        }

        UserDetails details = (UserDetails) authentication.getPrincipal();
        return json(HttpStatus.OK, "OK", userInfo(details));
    }

    // -------------------- logout（无状态） --------------------
    @PostMapping("/logout")
    public ResultBean logout() {
        SecurityContextHolder.clearContext();
        return ResultBean.success("无状态登出成功");
    }

    // -------------------- 工具方法 --------------------
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private Map<String, Object> userInfo(UserDetails details) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", details.getUsername());
        map.put("roles", details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList());
        return map;
    }

    private ResponseEntity<Map<String, Object>> json(HttpStatus status, String message) {
        return json(status, message, null);
    }

    private ResponseEntity<Map<String, Object>> json(HttpStatus status, String msg, Object data) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", status.value());
        body.put("message", msg);
        body.put("data", data);
        return ResponseEntity.status(status).body(body);
    }
}
