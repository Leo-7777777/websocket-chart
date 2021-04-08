package com.zxw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

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
    /**
     * 存储 websocket session等，以记录每个用户下多个终端（PC、pad、phone）的连接
     */
    public static final Map<String, List<Session>> ONLINE_USER_SESSIONS = new ConcurrentHashMap<>();
    /*######################## 一、根据分组编码，接收 消息(用户信息)的 websocket服务器端 ########################*/
    /**
     * 当WebSocket客户端与服务器建立连接并完成握手后，前台会回调ws.onopen；后台调用@OnOpen注解的方法。
     * @param groupCode
     * @param session
     */
    @OnOpen
    public void openSession(@PathParam("groupCode") String groupCode,Session session) {
        // ##-------- 获取请求路径中携带的信息
        // {groupCode=dkh}
        Map<String, String> map = session.getPathParameters();
        // emailWsParam=1
        String str = session.getQueryString();
        // /userws/dkh?emailWsParam=1
        String uri = session.getRequestURI().toString();
        List<Session> list = ONLINE_USER_SESSIONS.get(groupCode);
        // 如果该用户当前是第一次连接/没有在别的终端登录
        if (null == list) {
            list = new ArrayList<>();
        }
        // 如果该用户当前不是第一次连接/已经在别的终端登录
        if (!list.contains(session)) {
            list.add(session);
        }
        ONLINE_USER_SESSIONS.put(groupCode, list);
    }

    @OnMessage
    public void onMessage(@PathParam("groupCode") String groupCode, String message) {
        System.out.println(groupCode + "客户端ws.send发送的消息（或心跳信息）：" + message);
    }

    @OnClose
    public void onClose(@PathParam("groupCode") String groupCode, Session session) {
        List<Session> list = ONLINE_USER_SESSIONS.get(groupCode);
        // 移除该用户的websocket session记录
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
    /*######################## 二、根据分组编码，发送 消息(用户信息)的 websocket服务器端 工具方法########################*/
    /**
     * @Author Zhouxw
     * @Date 2020/09/21 13:19
     * @Description 向客户端发送 消息
     * @Param [session, message]
     * @Return void
     */
    public static void sendMessage(Session session, String message) {
        if (session == null) {
            return;
        }
        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            return;
        }
        try {
            basic.sendText(message);
        } catch (IOException e) {
            logger.error("sendMessage IOException ", e);
        }
    }
    /**
     * Description: 这个根据业务情况详细设计
     * @CodeSteps： 根据分组编码，向客户端发送 消息(用户信息)
     * @Param key:
     * @Param message:
     * @return: void
     * Author: ljx
     * Date: 2021/3/24 0024 下午 3:35
     */
    public static void sendMessage(String key, String message) {
        List<Session> list = ONLINE_USER_SESSIONS.get(key);
        // 给用户的所有终端发送数据消息
        list.stream().forEach(se -> {
            if(se.isOpen()){
                sendMessage(se, message);
            }
        });
    }
}