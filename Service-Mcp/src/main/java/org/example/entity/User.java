package org.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class User {

    @Schema(description = "用户ID", example = "1")

    private Long id;
    @Schema(description = "用户名", example = "donk")
    private String name;
    @Schema(description = "用户密码", example = "password123")
    private String password;

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
