package org.agentremote.producer;

import io.a2a.server.PublicAgentCard;
import io.a2a.spec.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Collections;
import java.util.List;


@ApplicationScoped
public class WeatherAgentCardProducer {

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    private int httpPort;
    @Produces
    @PublicAgentCard
    public AgentCard agentCard() {
        return new AgentCard.Builder()
                .name("Weather Agent")
                .description("Helps with weather")
                .preferredTransport(TransportProtocol.GRPC.asString())
                .url("localhost:"+httpPort)
                .version("1.0.0")
                .capabilities(new AgentCapabilities.Builder()
                        .streaming(true)
                        .pushNotifications(false)
                        .stateTransitionHistory(false)
                        .build())
                .defaultInputModes(Collections.singletonList("text"))
                .defaultOutputModes(Collections.singletonList("text"))
                .skills(Collections.singletonList(new AgentSkill.Builder()
                        .id("weather_search")
                        .name("Search weather")
                        .description("Helps with weather in cities or states")
                        .tags(Collections.singletonList("weather"))
                        .examples(List.of("weather in LA, CA"))
                        .build()))
                .protocolVersion("0.3.0")
                .additionalInterfaces(
                        List.of(
                                new AgentInterface(TransportProtocol.GRPC.asString(),
                                        "localhost:" + httpPort),
                                new AgentInterface(
                                        TransportProtocol.JSONRPC.asString(),
                                        "http://localhost:" + httpPort)))
                .build();
    }
}