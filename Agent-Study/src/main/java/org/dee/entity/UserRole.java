package org.dee.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_role")
public class UserRole {

    @TableField("user_id")
    @Schema(description = "用户ID")
    private Long userId;

    @TableField("role_id")
    @Schema(description = "角色ID")
    private Long roleId;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
