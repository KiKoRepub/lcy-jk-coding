import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NacosTest {


    private static final String NACOS_BASE = "http://localhost:8848";

    @Test
    public void getNacosMcpServer() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:8848/nacos/v3/admin/ai/mcp?mcpName=Mcp-Server-Local&version=2.0.0&namespaceId=mcp"))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        String login = login(client);

        System.out.println(getMcpInfo(client, login));
        System.out.println(login);



    }
    private static String login(HttpClient client) throws IOException, InterruptedException {
        String loginUrl = NACOS_BASE + "/nacos/v1/auth/login";

        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(URI.create(loginUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("username=nacos&password=nacos"))
                .build();

        HttpResponse<String> response =
                client.send(loginRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("Login Response: " + response.body());

        // 从 JSON 中取出 accessToken
        // 登录返回格式例子：
        // {"accessToken":"xxxxxx","tokenTtl":18000,"globalAdmin":true}
        String body = response.body();
        String tokenKey = "\"accessToken\":\"";
        int start = body.indexOf(tokenKey);
        if (start == -1) {
            throw new RuntimeException("登录失败，未找到 accessToken");
        }
        start += tokenKey.length();
        int end = body.indexOf("\"", start);
        return body.substring(start, end);
    }

    // -------------------- 调用 MCP API --------------------
    private static String getMcpInfo(HttpClient client, String token)
            throws IOException, InterruptedException {

        String url = NACOS_BASE +
                "/nacos/v3/admin/ai/mcp?mcpName=Mcp-Server-Local&version=2.0.0&namespaceId=mcp";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> resp =
                client.send(req, HttpResponse.BodyHandlers.ofString());

        return resp.body();
    }
}
