package org.client;



import io.a2a.A2A;
import io.a2a.client.*;
import io.a2a.client.config.ClientConfig;
import io.a2a.client.http.A2ACardResolver;
import io.a2a.client.http.JdkA2AHttpClient;
import io.a2a.client.transport.grpc.GrpcTransport;
import io.a2a.client.transport.grpc.GrpcTransportConfig;
import io.a2a.client.transport.jsonrpc.JSONRPCTransport;
import io.a2a.client.transport.jsonrpc.JSONRPCTransportConfig;
import io.a2a.client.transport.spi.interceptors.ClientCallContext;
import io.a2a.spec.AgentCard;
import io.a2a.spec.Message;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;


import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        // First, get the agent card for the A2A server agent you want to connect to
        AgentCard agentCard = new A2ACardResolver("http://localhost:10001")
                .getAgentCard();

// Specify configuration for the ClientBuilder
        ClientConfig clientConfig = new ClientConfig.Builder()
                .setAcceptedOutputModes(List.of("text"))
                .build();

// Create event consumers to handle responses that will be received from the A2A server
// (these consumers will be used for both streaming and non-streaming responses)
        List<BiConsumer<ClientEvent, AgentCard>> consumers = List.of(
                (event, card) -> {
                    if (event instanceof MessageEvent messageEvent) {
                        // handle the messageEvent.getMessage()

                    } else if (event instanceof TaskEvent taskEvent) {
                        // handle the taskEvent.getTask()

                    } else if (event instanceof TaskUpdateEvent updateEvent) {
                        // handle the updateEvent.getTask()

                    }
                }
        );

// Create a handler that will be used for any errors that occur during streaming
        Consumer<Throwable> errorHandler = error -> {
            // handle the error.getMessage()
            System.out.println("Streaming error: " + error.getMessage());
        };

//        getJSONRpcClient(agentCard, clientConfig, consumers, errorHandler);


        // Create a channel factory function that takes the agent URL and returns a Channel
        Function<String, Channel> channelFactory = agentUrl -> {
            System.out.println("Creating gRPC channel for agent URL: " + agentUrl);
            return ManagedChannelBuilder.forTarget(agentUrl)
                    .build();
        };

        Client client = Client
                .builder(agentCard)
                .clientConfig(clientConfig)
                .withTransport(GrpcTransport.class, new GrpcTransportConfig(channelFactory))
                .streamingErrorHandler(errorHandler)
                .build();


        sendMessage(client,"Tell me a joke about programmers.");

// You can also optionally specify a ClientCallContext with call-specific config to use
//        ClientCallContext context = new ClientCallContext();
//        client.sendMessage(message, clientCallContext);

    }

    private static void sendMessage(Client client,String message) {
        // Send a text message to the A2A server agent
        Message msg = A2A.toUserMessage(message);

// Send the message (uses configured consumers to handle responses)
// Streaming will automatically be used if supported by both client and server,
// otherwise the non-streaming send message method will be used automatically

        List<BiConsumer<ClientEvent, AgentCard>> customConsumers = List.of(
                (event, card) -> {
                    // handle this specific message's responses
                    System.out.println("Received event: " + event.getClass().getSimpleName());
                }
        );

// Create custom error handler
        Consumer<Throwable> customErrorHandler = error -> {
            // handle the error
            System.out.println("Custom error handler: " + error.getMessage());
        };


        client.sendMessage(msg,customConsumers,customErrorHandler);
    }

    private static Client getJSONRpcClient(AgentCard agentCard, ClientConfig clientConfig, List<BiConsumer<ClientEvent, AgentCard>> consumers, Consumer<Throwable> errorHandler) {
        // Create the client using the builder
        return Client
                .builder(agentCard)
                .clientConfig(clientConfig)
                .withTransport(JSONRPCTransport.class, new JSONRPCTransportConfig())
                .addConsumers(consumers)
                .streamingErrorHandler(errorHandler)
                .build();
    }


}
