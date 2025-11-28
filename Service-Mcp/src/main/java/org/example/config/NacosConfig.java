package org.example.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class NacosConfig {

    @Value("${spring.application.name}")
    String serviceName;
    @Value("${server.port}")
    Integer port;
    @Value("${nacos.server-addr}")
    String serverAddr;
    @Value("${nacos.namespace:}")
    String namespace;
    @Value("${nacos.username}")
    String username;
    @Value("${nacos.password}")
    String password;

    @Bean
    public ApplicationListener<ApplicationReadyEvent> nacosListener() {
        log.info("Nacos configuration - serverAddr: {}, namespace: {}", serverAddr, namespace);
     return event -> {
         try {
             NamingService namingService = NacosFactory.createNamingService(serverAddr);
             Map<String, String> metadata = new HashMap<>();


             metadata.put("mcp.enabled", "true");
             metadata.put("mcp.endpoint", "/mcp");
             metadata.put("mcp.protocol", "streamHttp");


             Instance instance = new Instance();

             instance.setIp("127.0.0.1");
             instance.setPort(port);
             instance.setServiceName(serviceName);

             instance.setMetadata(metadata);
             namingService.registerInstance(
                     serviceName,
                     namespace,
                    instance
             );

             System.out.println(">>> Registered to Nacos successfully!");
         } catch (Exception e) {
             throw new RuntimeException("Failed to register to Nacos", e);
         }

     };
 }

}
