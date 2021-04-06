package com.zxw.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 * @Description: 发送 消息(用户信息)的 websocket服务器端 工具类
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
public class WebSocketSendUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketSendUtil.class);
    /**
     * @Description 存储 websocket session
     */
    public static final Map<String, List<Session>> ONLINE_USER_SESSIONS = new ConcurrentHashMap<>();
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
    public static void sendMessage(String key, String message) {
        List<Session> list = ONLINE_USER_SESSIONS.get(key);
        list.stream().forEach(se -> {
            if(se.isOpen()){
                sendMessage(se, message);
            }
        });
    }
}