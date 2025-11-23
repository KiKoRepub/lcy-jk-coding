package org.dee.controller.test;

import org.dee.callBack.MyMcpToolCallBackProvider;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test/nacos")
public class NacosMcpController {
    @Autowired
    private ChatClient chatClient;

    @Autowired
    private Map<String, MyMcpToolCallBackProvider> mcpToolCallbackProviderMap;


    private static final String NACOS_ROUTER_NAME = "nacos-mcp-router";



    @GetMapping("/mcp")
    public String testNacosMcp(@RequestParam("message") String message){

        ToolCallback[] nacosToolCallbacks = mcpToolCallbackProviderMap.get(NACOS_ROUTER_NAME)
                .getToolCallbacks();

        // 不能直接问问题，需要明确指出让它从现有工具中去查询其他工具来解决需求问题。
        String messagePrefix = "尝试利用现有的工具进行其他工具的查询，然后告诉我:";

        ChatResponse response = chatClient.prompt()
                .user(messagePrefix + message)
                .toolCallbacks(nacosToolCallbacks)
                .call()
                .chatResponse();


        String text = response.getResult().getOutput().getText();

        System.out.println("Nacos MCP Router Response: " + text);

        return text;
    }
    @GetMapping("/list")
    public String listMcpRouters(){
        ToolCallback[] nacosToolCallbacks = mcpToolCallbackProviderMap.get(NACOS_ROUTER_NAME)
                .getToolCallbacks();
        String message = "List available MCP servers from your nacos router,which is maybe a toolCallback";


        ChatResponse response = chatClient.prompt()
                .user(message)
                .toolCallbacks(nacosToolCallbacks)
                .call()
                .chatResponse();


        String text = response.getResult().getOutput().getText();

//        System.out.println("Nacos MCP Router Response: " + text);
        return text;
    }
}
