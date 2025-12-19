package org.dee.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserLoginDTO {

    @Schema(description = "用户名", example = "john_doe")
    private String username;

    @Schema(description = "密码", example = "P@ssw0rd!")
    private String password;

}