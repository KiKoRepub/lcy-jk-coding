package org.example.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpBeanConfiguration {

    @Qualifier("distributedAsyncToolCallback")
    ToolCallbackProvider tools;


    @Bean
    public ToolCallbackProvider toolCallbackProvider() {
        System.out.println("=== MCP ToolCallbackProvider Bean Initialized ===");
        System.out.println(tools);
        return tools;
    }
}
