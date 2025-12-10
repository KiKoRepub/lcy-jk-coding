package org.dee.service.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.dee.entity.Role;
import org.dee.entity.SysUser;
import org.dee.entity.UserRole;
import org.dee.entity.dto.user.UserRegisterDTO;
import org.dee.mapper.RoleMapper;
import org.dee.mapper.UserMapper;
import org.dee.mapper.UserRoleMapper;
import org.dee.service.UserService;
import org.dee.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public Long analyzeUserIdFromToken(String userToken) {

        String userName = analyzeUserNameFromToken(userToken);


        return userMapper.findByUsername(userName).getId();
    }

    @Override
    public String analyzeUserNameFromToken(String userToken) {

        if (userToken == null || userToken.isEmpty()) return null;
        Claims parse = JwtUtil.parse(userToken);

        return parse.getSubject();

    }

    @Override
    @Transactional
    public boolean registerNewUser(UserRegisterDTO dto) {

        boolean result;
        // 构造用户：先使用 BCrypt 进行散列，再由 PasswordEncryptedHandler 进行对称加密入库
        SysUser toSave = new SysUser();
        toSave.setUsername(dto.getUsername());
        toSave.setPassword(passwordEncoder.encode(dto.getPassword()));
        toSave.setEnabled(true);
        toSave.setCreateTime(LocalDateTime.now());
        toSave.setUpdateTime(LocalDateTime.now());
        result = userMapper.insert(toSave) >0;

        // 角色处理：默认 USER
        List<String> inputRoles = (dto.getRoles() == null || dto.getRoles().isEmpty())
                ? List.of("USER")
                : dto.getRoles().stream()
                    .map(String::toUpperCase).toList();



        for (String r : inputRoles) {
            Role role = roleMapper.findByName(r);

            UserRole ur = new UserRole();
            ur.setUserId(toSave.getId());
            ur.setRoleId(role.getId());
            ur.setCreateTime(LocalDateTime.now());
            result = userRoleMapper.insert(ur) > 0;
        }

        return result;
    }



}
