package org.agentlocal.controller;

import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private ReactAgent agent;

    @GetMapping("/hello")
    public String hello() throws GraphRunnerException {

        // threadId 是给定对话的唯一标识符
        long threadId = Thread.currentThread().getId();

        RunnableConfig runnableConfig = RunnableConfig.builder()
                .threadId(String.valueOf(threadId))
                .addMetadata("user_id", "1")
                .build();

        // 第一次调用
        AssistantMessage response = agent.call("what is the weather outside?", runnableConfig);
        System.out.println(response.getText());


        AssistantMessage response2 = agent.call("what about tomorrow?", runnableConfig);
        System.out.println(response2.getText());

        return "Hello from ChatController!";

    }

}
