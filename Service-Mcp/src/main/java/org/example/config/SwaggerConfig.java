package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Swagger UI: http://localhost:/swagger-ui/index.html
 * OpenAPI JSON: http://localhost:/v3/api-docs
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI serviceMcpOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Service MCP API")
                        .description("OpenAPI documentation powered by springdoc-openapi")
                        .version("v1"));
    }


   /* // 下载 Swagger配置后的接口文档 对应的 OPENAI规范文件
    public static void main(String[] args) throws IOException {
        String basePath = "F:/DockerImages/McpAccessPoint/OpenApi";
        String filePath = basePath + "/AgentStudy.json";

        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:6363/v3/api-docs";

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();

            try (FileWriter fw = new FileWriter(filePath)){
                fw.write(json);
            }
            System.out.println("下载完成 api-docs.json");

        } catch (Exception e) {
            e.printStackTrace();
        }*/

}
