package com.zxw.controller;

import com.zxw.utils.WebSocketSendUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @Description: 接收 消息(用户信息)的 websocket服务器端
 * @FR功能需求：
 * @ImportJar:
 * @ApiGrammer规则：
注解@ServerEndpoint 是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址。
 * @Remark:
 * @CodeBug解决:
 * @date 2021年3月24日 下午1:36:00
 * @author  ljx
 *
 */
@ServerEndpoint("/userws/{groupCode}")
@Component
public class UserWebSocket {
    private static final Logger logger = LoggerFactory.getLogger(UserWebSocket.class);
    /*######################## 一、根据分组编码，接收 消息(用户信息)的 websocket服务器端 ########################*/
    @OnOpen
    public void openSession(@PathParam("groupCode") String groupCode, Session session) {
        List<Session> list = WebSocketSendUtil.ONLINE_USER_SESSIONS.get(groupCode);
        if (null == list) {
            list = new ArrayList<>();
        }

        if (!list.contains(session)) {
            list.add(session);
        }
        WebSocketSendUtil.ONLINE_USER_SESSIONS.put(groupCode, list);
    }

    @OnMessage
    public void onMessage(@PathParam("groupCode") String groupCode, String message) {
        System.out.println(groupCode + "客户端ws.send发送的消息（或心跳信息）：" + message);
    }

    @OnClose
    public void onClose(@PathParam("groupCode") String groupCode, Session session) {
        //当前的Session 移除
        List<Session> list = WebSocketSendUtil.ONLINE_USER_SESSIONS.get(groupCode);
        list.remove(session);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Throwable msg " + throwable.getMessage());
    }
}