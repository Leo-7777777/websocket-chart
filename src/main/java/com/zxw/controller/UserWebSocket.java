package com.zxw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
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
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * 记录每个用户下多个终端的连接
     */
    private static Map<String, Set<UserWebSocket>> userWebSocketMap = new ConcurrentHashMap<>();
    /**
     * 连接特征groupCode
     */
    private String groupCode;
    /**
     * 存储 websocket session；
     */
    private Session session;
    /**
     * 消息（或心跳信息）
     */
    private String message;

    /*######################## 一、根据分组编码，接收 消息(用户信息)的 websocket服务器端 ########################*/
    /**
     * 当WebSocket客户端与服务器建立连接并完成握手后，前台会回调ws.onopen；后台调用@OnOpen注解的方法。
     * @param groupCode
     * @param session
     */
    @OnOpen
    public void openSession(@PathParam("groupCode") String groupCode, Session session) {
        this.session=session;
        this.groupCode=groupCode;
        onlineCount++;
        // 根据该用户当前是否已经在别的终端登录进行添加操作
        if (userWebSocketMap.containsKey(this.groupCode)) {
            logger.debug("当前用户 groupCode:{}已有其他终端登录",this.groupCode);
            // 增加该用户set中的连接实例
            userWebSocketMap.get(this.groupCode).add(this);
        }else {
            logger.debug("当前用户 groupCode:{}第一个终端登录",this.groupCode);
            Set<UserWebSocket> addUserSet = new HashSet<>();
            addUserSet.add(this);
            userWebSocketMap.put(this.groupCode, addUserSet);
        }
        logger.debug("用户{}登录的终端个数是为{}",groupCode,userWebSocketMap.get(this.groupCode).size());
        logger.debug("当前在线用户数为：{},所有终端个数为：{}",userWebSocketMap.size(),onlineCount);
    }

    @OnMessage
    public void onMessage(@PathParam("groupCode") String groupCode, String message) {
        System.out.println(groupCode + "客户端ws.send发送的消息（或心跳信息）：" + message);
        this.message=message;
    }

    @OnClose
    public void onClose(@PathParam("groupCode") String groupCode, Session session) {
        // 移除当前用户终端登录的websocket信息,如果该用户的所有终端都下线了，则删除该用户的记录
        if (userWebSocketMap.get(this.groupCode).size() == 0) {
            userWebSocketMap.remove(this.groupCode);
        }else{
            userWebSocketMap.get(this.groupCode).remove(this);
        }
        logger.debug("用户{}登录的终端个数是为{}",this.groupCode,userWebSocketMap.get(this.groupCode).size());
        logger.debug("当前在线用户数为：{},所有终端个数为：{}",userWebSocketMap.size(),onlineCount);
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
     * Title: sendMessage
     * Description: 根据分组编码，向客户端发送 消息(用户信息)
     * @Param key:
     * @Param message:
     * @return: void
     * Author: ljx
     * Date: 2021/3/24 0024 下午 3:35
     */
    public static boolean sendMessage(String groupCode, String message) {
        if (userWebSocketMap.containsKey(groupCode)) {
            logger.debug(" 给用户 groupCode为：{}的所有终端发送消息：{}",groupCode,message);
            for (UserWebSocket userWs : userWebSocketMap.get(groupCode)) {
                logger.debug("sessionId为:{}",userWs.session.getId());
                try {
                    userWs.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.debug(" 给用户 groupCode为：{}发送消息失败",groupCode);
                    return false;
                }
            }
            return true;
        }
        logger.debug("发送错误：当前连接不包含 groupCode为：{}的用户",groupCode);
        return false;
    }
}