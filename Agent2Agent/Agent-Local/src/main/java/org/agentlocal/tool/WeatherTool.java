package org.agentlocal.tool;


import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

public class WeatherTool implements BiFunction<String, ToolContext,String> {


    @Override
    public String apply(
            @ToolParam(description = "The city name") String city,
            ToolContext toolContext) {

        return "The weather in " + city + " is sunny with a high of 25°C and a low of 15°C.";
    }
}
