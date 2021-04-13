package com.zxw.websocket;

import com.ljx.wcneln._09util.constantutil.ConstantUtil;
import com.ljx.wcneln._09util.maputil.MapGetter;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        sendMessageToUserWebSocketServer();
    }
    /**
     * @Description: websocket客户端，发送消息到websocket服务端
     * @Param args:
     * @return:
      */
    public void sendMessageToUserWebSocketServer(){
        // 查询数据库数据
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        // websocket客户端，连接websocket服务端
        connectUserWebSocketServer();
        // 发送到websocket服务端，的消息
        String message = "";
        if (list != null && list.size() > 0) {
            message = list.toString();
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
