package org.agentlocal.tool;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.function.BiFunction;

public class UserLocationTool implements BiFunction<String, ToolContext, String> {
    @Override
    public String apply(
            @ToolParam(description = "The user query") String query,
            ToolContext toolContext) {
        String userId = (String) toolContext.getContext().get("userId");
        return "1".equals(userId)
                ? "User is located in New York City."
                : "User location is unknown.";
    }
}
