package com.zxw.controller;

import com.ljx.wcneln._09util.constantutil.ConstantUtil;
import com.ljx.wcneln._09util.maputil.MapGetter;
import com.zxw.pojo.doo.UserDO;
import com.zxw.pojo.dto.UserChartDTO;
import com.zxw.service.UserService;
import com.alibaba.fastjson.JSON;
import com.zxw.utils.WebSocketSendUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description:
 * @FR功能需求：
 * @ImportJar:
 * @ApiGrammer规则：
        注解@RestController，是@ResponseBody和@Controller的组合注解。
 * @Remark:
 * @CodeBug解决:
 * @date 2021年3月24日 下午1:56:57
 * @author  ljx
 *
 */
@ServerEndpoint("/userws/{userId}")
@RestController()
public class UserController {
    @Resource
    UserService userService;
    /**
     * webUrl测试
     * Description: 根据用户id，列表查询用户表
     * @Param userId     用户id
     * @Param params        前台页面查询条件
     * @return: com.zxw.dto.UserChartDTO
     * Author: ljx
     * Date: 2021/3/25 0025 下午 4:32
     */
    @GetMapping("/user/{userId}/queryByUserId")
    public UserChartDTO selectUserChartDtoByUserId(@PathVariable String userId) {
        return userService.dealUserChartDtoByUserId(userId);
    }
    /**
     * webUrll测试http://localhost:8090/addAndQueryByUserId
     * Description: 新增一条用户数据；并返回拼装的用户数据（根据用户id，列表查询用户表）
     *
     * @Param user:
     * @return: com.zxw.model.User
     * Author: ljx
     * Date: 2021/3/24 0024 上午 10:14
     */
    @PostMapping("/user/addAndQueryByUserId")
    public UserDO addAndQueryByUserId(UserDO user) {
        // 保存数据
        UserDO result = userService.addUser(user);
        // 拼装数据DTO通知前端
        UserChartDTO userChartDTO = userService.dealUserChartDtoByUserId(user.getUserId());
        // 通知前端，向前台发送消息
        WebSocketSendUtil.sendMessageToWebsocketJs(user.getUserId(), JSON.toJSONString(userChartDTO));
        return result;
    }
    /**
     * Title: selectUserChartDTOByParams
     * Description: 根据用户id、前台页面条件，列表查询用户表
     * @Param userId:
     * @Param params:
     * @return: com.zxw.dto.UserChartDTO
     * Author: ljx
     * Date: 2021/3/25 0025 下午 5:34
     */
    @GetMapping("/user/{userId}/queryByParams")
    public UserChartDTO selectUserChartDtoByParams(@PathVariable String userId,@RequestParam Map<String,Object> params) {
        params.put("userId",userId);
        return userService.dealUserChartDtoByParams(params);
    }

    /**
     * webUrll测试http://localhost:8090/addAndQueryByUserId
     * Description: 新增一条用户数据；并返回拼装的用户数据（根据用户id、前台页面条件，列表查询用户表）
     *
     * @Param user:
     * @return: com.zxw.model.User
     * Author: ljx
     * Date: 2021/3/24 0024 上午 10:14
     */
    @PostMapping("/user/addAndQueryByParams")
    public UserDO addAndQueryByParams(UserDO user) {
        // 保存数据
        UserDO result = userService.addUser(user);
        String userId=user.getUserId();
        String emailParam=user.getEmail();
        Map<String, Object> params=new HashMap<String, Object>(MapGetter.defaultInitialCapacity());
        params.put("userId",userId);
        params.put("emailParam",emailParam);
        // 拼装的用户数据（根据用户id、前台页面条件，列表查询用户表）
        UserChartDTO userChartDTO = userService.dealUserChartDtoByParams(params);
        // 通知前端，向前台发送消息
        WebSocketSendUtil.sendMessageToWebsocketJs(user.getUserId(), JSON.toJSONString(userChartDTO));
        return result;
    }
    /*######################## 一、根据用户id，接收 消息(用户信息)的 websocket服务端 ########################*/
    /**
     * 当前台用户终端【浏览器】页面，使用js WebSocket；与服务器建立连接并完成握手后，前台会回调ws.onopen；后台调用@OnOpen注解的方法。
     * @param userId
     * @param session
     */
    @OnOpen
    public void openSession(@PathParam("userId") String userId, Session session) {
        List<Session> list = WebSocketSendUtil.ONLINE_USER_SESSIONS.get(userId);
        // 如果该用户当前是第一次连接/没有在别的终端登录
        if (null == list) {
            list = new ArrayList<>();
        }
        // 如果该用户当前不是第一次连接/已经在别的终端登录
        if (!list.contains(session)) {
            list.add(session);
        }
        WebSocketSendUtil.ONLINE_USER_SESSIONS.put(userId, list);
    }

    @OnMessage
    public void onMessage(@PathParam("userId") String userId, String message) {
        // 前台用户终端【浏览器】页面，ws.send发送的消息（或心跳信息）
        if(message.startsWith(ConstantUtil.TO_WEBSOCKET_OF_CLIENT_TYPE1)){
            System.out.println(userId + "前台用户终端【浏览器】页面，ws.send发送的消息（或心跳信息）：" + message);
            // --接收并处理消息

            // --通知前端，向前台发送消息

        }
        // 后台服务器的websocket客户端|ClientEndpoint|Java服务器，UserWebSocketClient.java发送的消息（或心跳信息）
        else if(message.startsWith(ConstantUtil.TO_WEBSOCKET_OF_CLIENT_TYPE2)){
            // --接收并处理消息
            // --通知前端，向前台发送消息
            // 从session中，获取当前登录用户id
            userId="";
            // 根据条件，列表查询用户表
            UserChartDTO userChartDTO =new UserChartDTO();
            WebSocketSendUtil.sendMessageToWebsocketJs(userId, JSON.toJSONString(userChartDTO));
        }
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId, Session session) {
        List<Session> list = WebSocketSendUtil.ONLINE_USER_SESSIONS.get(userId);
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
}
