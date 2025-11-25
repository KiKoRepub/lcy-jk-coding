package org.dee.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RedisChatMessageDTO {

    @Schema(description = "使用的工具集")
    private List<String> toolCalls;
    @Schema(description = "用户消息")
    private String  userMessage;
    @Schema(description = "机器人回复")
    private String  botResponse;

    public RedisChatMessageDTO(String userMessage, String botResponse) {
        this.toolCalls = new ArrayList<>();
        this.userMessage = userMessage;
        this.botResponse = botResponse;
    }

    public RedisChatMessageDTO(List<String> toolCalls, String userMessage, String botResponse) {
        this.toolCalls = toolCalls;
        this.userMessage = userMessage;
        this.botResponse = botResponse;
    }

}
