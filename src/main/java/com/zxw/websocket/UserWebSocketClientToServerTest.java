package com.zxw.websocket;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

/**
 * @author ljx
 * @Description: websocket客户端，发送消息到websocket服务端
 * @FR功能需求：
 * @ImportJar:
 * @ApiGrammer规则：
 * @Remark:
 * @AlibabaCodeStatuteScanError：
 * @CodeBug解决:
 * @Debug调试：
 * @date 2021/4/13 0013 上午 9:46
 */
public class UserWebSocketClientToServerTest {
    public Session session;
    public static void main(String args[]) {
        // websocket客户端，发送消息到websocket服务端
        sendMessageToUserWebSocketServer(args);
    }
    /**
     * @Description: websocket客户端，发送消息到websocket服务端
     * @Param args:
     * @return:
      */
    public static void sendMessageToUserWebSocketServer(String args[]){
        UserWebSocketClientToServerTest client = new UserWebSocketClientToServerTest();
        client.connectUserWebSocketServer();
        String message = "无参";
        if (args != null && args.length > 0) {
            message = args[0];
        }
        try {
            client.session.getBasicRemote().sendText(message);
            client.session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description: websocket客户端，连接websocket服务端
     * @return:
      */
    protected void connectUserWebSocketServer(){
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://192.168.21.21:8090/websocket-chart/userws/";
        System.out.println("Connecting to" + uri);
        try {
            session = container.connectToServer(UserWebSocketClient.class, URI.create(uri));
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
