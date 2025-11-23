package org.example.tool;

import org.example.annotion.MyTool;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

@MyTool("天气查询 MCP 工具")
public class WeatherTool {

    @Tool(description = "Get weather information by city name")
    public String getWeather(@ToolParam(description = "City name") String cityName) {
        System.out.println("Fetching weather for city: " + cityName);
        return "Sunny in " + cityName;
    }
}