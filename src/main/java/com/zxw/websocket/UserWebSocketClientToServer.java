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
 * @date 2021/4/13 0013 下午 6:05
 */
public class UserWebSocketClientToServer {

    /*######################## 二、发送 消息的 websocket客户端 工具方法########################*/
    /**
     * websocket服务端地址
     */
    private static String uri = "ws://192.168.21.21:8090/websocket-chart/userws/"+"dkh";
    private static Session session;
    public static  void main(String[] args) {
        // websocket客户端，发送消息到websocket服务端
        sendMessageToUserWebSocketServer();
    }
    /**
     * @Description: websocket客户端，连接websocket服务端
     * @return:
     */
    protected static void connectUserWebSocketServer(){
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
    /**
     * @Description: websocket客户端，发送消息到websocket服务端
     * @Param args:
     * @return:
     */
    public static void sendMessageToUserWebSocketServer(){
        // websocket客户端，连接websocket服务端
        connectUserWebSocketServer();
        // 发送到websocket服务端，的消息“数据库表，有数据变动”
        String message = "dbTableCurrentHaveUpdate";
        try {
            session.getBasicRemote().sendText(ConstantUtil.TO_WEBSOCKET_OF_CLIENT_TYPE2+"_"+message);
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
