package org.agentlocal.config;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import jakarta.annotation.Resource;
import org.agentlocal.tool.UserLocationTool;
import org.agentlocal.tool.WeatherTool;
import org.agentlocal.vo.ResponseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {
    private static final Logger log = LoggerFactory.getLogger(AgentConfig.class);
    @Resource
    @Qualifier("ollamaChatModel")
    private ChatModel chatModel;

    private static final String SYSTEM_PROMPT = """
            You are an expert weather forecaster, who speaks in puns.

            You have access to two tools:

            - get_weather_for_location: use this to get the weather for a specific location
            - get_user_location: use this to get the user's location
B
            If a user asks you for the weather, make sure you know the location.
            If you can tell from the question that they mean wherever they are,
            use the get_user_location tool to find their location.
          """;

    @Bean
    public ReactAgent reactAgent() {
        log.info("——————————Agent 初始化开始 ");


        // 初始化 ChatModel
//        DashScopeApi dashScopeApi = DashScopeApi.builder()
//                .apiKey("sk-18ad79918c634898b20a35b0531a2b40")
//                .build();
//
//        ChatModel chatModel = DashScopeChatModel.builder()
//                .dashScopeApi(dashScopeApi)
//                .build();



        log.info("————————创建工具");
        // 创建工具回调
        ToolCallback getWeatherTool = FunctionToolCallback
                .builder("getWeatherForLocation", new WeatherTool())
                .description("Get weather for a given city")
                .inputType(String.class)
                .build();

        ToolCallback getUserLocationTool = FunctionToolCallback
                .builder("getUserLocation", new UserLocationTool())
                .description("Retrieve user location based on user ID")
                .inputType(String.class)
                .build();

        log.info("————————工具创建完成");


        ReactAgent agent = ReactAgent.builder()
                .name("weather_agent")
                .model(chatModel)

                .tools(getWeatherTool, getUserLocationTool)
                .systemPrompt(SYSTEM_PROMPT)
                .saver(new MemorySaver())
                .outputType(ResponseFormat.class)
                .build();


        log.info("————————Agent 初始化完成 ");

        return agent;
    }


}
