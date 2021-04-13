package com.zxw.websocket;

import com.ljx.wcneln._09util.constantutil.ConstantUtil;

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
    private String uri = "ws://192.168.21.21:8090/websocket-chart/userws/";
    private Session session;
    public void main(String[] args) {
        // websocket客户端，发送消息到websocket服务端
        sendMessageToUserWebSocketServer(args);
    }
    /**
     * @Description: websocket客户端，发送消息到websocket服务端
     * @Param args:
     * @return:
      */
    public void sendMessageToUserWebSocketServer(String[] args){
        // websocket客户端，连接websocket服务端
        connectUserWebSocketServer();
        String message = "无参";
        if (args != null && args.length > 0) {
            message = args[0];
        }
        try {
            this.session.getBasicRemote().sendText(ConstantUtil.TO_WEBSOCKET_OF_CLIENT_TYPE2+"_"+message);
            this.session.close();
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
