package org.dee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("rag_user_doc_record")
public class RagUserDocRecord {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID，雪花算法生成")
    private Long id;

    @TableField("user_id")
    @Schema(description = "用户ID")
    private String userId;

    @TableField("doc_name")
    @Schema(description = "文档 名称")
    private String docName;
    @TableField("doc_url")
    @Schema(description = "文档 URL")
    private String docUrl;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("deleted")
    @Schema(description = "是否删除，0未删除，1已删除")
    private int deleted;


}
