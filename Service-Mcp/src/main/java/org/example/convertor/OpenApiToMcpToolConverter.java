package org.example.convertor;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import lombok.RequiredArgsConstructor;
import org.example.entity.McpTool;
import org.springdoc.webmvc.api.OpenApiWebMvcResource;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class OpenApiToMcpToolConverter {

    private final org.springdoc.core.providers.ObjectMapperProvider objectMapperProvider;
    private final OpenApiWebMvcResource openApiResource;

    public List<McpTool> convert() {
        OpenAPI openAPI = getOpenAPI();

        List<McpTool> tools = new ArrayList<>();

        openAPI.getPaths().forEach((path, pathItem) -> {
            pathItem.readOperationsMap().forEach((httpMethod, operation) -> {

                String toolName = operation.getOperationId();
                if (toolName == null || toolName.isBlank()) {
                    toolName = httpMethod.name().toLowerCase() + "_" + path.replace("/", "_");
                }

                McpTool tool = new McpTool();
                tool.setName(toolName);
                tool.setDescription(operation.getSummary());

                // input schema from requestBody
                Map<String, Object> inputSchema = extractInputSchema(operation);
                tool.setInputSchema(inputSchema);

                tools.add(tool);
            });
        });

        return tools;
    }

    private OpenAPI getOpenAPI() {
        try {
            String apiDocJson = Arrays.toString(openApiResource.openapiJson(null, null, null));
            ObjectMapper mapper = objectMapperProvider.jsonMapper();
            return mapper.readValue(apiDocJson, OpenAPI.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load OpenAPI doc", e);
        }
    }

    private Map<String, Object> extractInputSchema(Operation operation) {
        Map<String, Object> schema = new HashMap<>();

        if (operation.getRequestBody() != null) {
            operation.getRequestBody().getContent().forEach((contentType, mediaType) -> {
                if (mediaType.getSchema() != null) {
                    schema.put("body", mediaType.getSchema());
                }
            });
        }

        return schema;
    }
}
