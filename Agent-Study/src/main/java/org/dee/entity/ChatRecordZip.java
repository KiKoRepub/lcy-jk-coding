package org.dee.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("chat_record_zip")
public class ChatRecordZip {

    @TableId
    @Schema(description = "ID")
    private Integer id;

    @TableField("user_id")
    @Schema(description = "User ID")
    private String userId;

    @TableField("conversation_id")
    @Schema(description = "Conversation ID")
    private String conversationId;

    @TableField("title")
    @Schema(description = "Title")
    private String title;

    @TableField("compressed_data")
    @Schema(description = "Compressed Data")
    private String compressedData;

    @TableField("created_at")
    @Schema(description = "Persistence Type: auto-自动持久化, manual-手动持久化")
    private String persistenceTypeCode;

    @TableField("persistence_time")
    @Schema(description = "Persistence Time")
    private LocalDateTime persistenceTime;

}
