package org.dee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("role")
public class Role {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "角色ID")
    private Long id;
    @TableField("role_name")
    @Schema(description = "角色名称")
    private String roleName;

    @TableField("description")
    @Schema(description = "角色描述")
    private String description;

}
