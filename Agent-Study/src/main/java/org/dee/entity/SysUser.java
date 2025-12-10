package org.dee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.dee.handler.PasswordEncryptedHandler;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class SysUser {
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "用户ID")
    private Long id;

    @TableField("username")
    @Schema(description = "用户名")
    private String username;

    @TableField(value = "password",typeHandler = PasswordEncryptedHandler.class)
    @Schema(description = "密码")
    private String password;

    @TableField("enabled")
    @Schema(description = "是否启用")
    private boolean enabled = true;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}