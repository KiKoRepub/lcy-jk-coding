package org.dee.security;

import org.dee.entity.User;
import org.dee.mapper.RoleMapper;
import org.dee.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public DbUserDetailsService(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userMapper.findByUsername(username);
        if (Objects.isNull(dbUser) || !dbUser.isEnabled()) {
            throw new UsernameNotFoundException("User not found or disabled: " + username);
        }
        List<String> roleNames = roleMapper.selectRoleNamesByUserId(dbUser.getId());
        List<GrantedAuthority> authorities = roleNames.stream()
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 注意：当前密码经 TypeHandler 解密后是明文，使用 NoOpPasswordEncoder 进行比对。
        return org.springframework.security.core.userdetails.User
                .withUsername(dbUser.getUsername())
                .password(dbUser.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!dbUser.isEnabled())
                .build();
    }
}
