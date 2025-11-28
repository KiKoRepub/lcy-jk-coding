package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.convertor.OpenApiToMcpToolConverter;
import org.example.entity.McpTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mcp")
public class McpRegistryController {
    @Value("${spring.application.name}")
    private String serviceName;
    private final OpenApiToMcpToolConverter converter;

    @GetMapping("/tools")
    public List<McpTool> listTools() {
        return converter.convert();
    }


    @GetMapping("/manifest.json")
    public Map<String, Object> manifest() {

        Map<String, Object> manifest = new HashMap<>();
        manifest.put("name", serviceName);
        manifest.put("description", "MCP Server powered by OpenAPI");
        manifest.put("protocol", "streamHttp");

        return manifest;
    }
}
