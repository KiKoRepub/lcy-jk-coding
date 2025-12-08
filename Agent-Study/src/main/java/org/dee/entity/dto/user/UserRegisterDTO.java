package org.dee.entity.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private List<String> roles;
}
