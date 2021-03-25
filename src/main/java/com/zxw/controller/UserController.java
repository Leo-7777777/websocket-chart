package com.zxw.controller;

import com.zxw.dto.UserChartDTO;
import com.zxw.model.UserDO;
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
import java.util.List;

/**
 *
 * @Description:
 * @FR功能需求：
 * @ImportJar:
 * @ApiGrammer规则：
 * @Remark:
 * @CodeBug解决:
 * @date 2021年3月24日 下午1:56:57
 * @author  ljx
 *
 */
@ServerEndpoint("/userws/{groupCode}")
@RestController()
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/user/list")
    public List<UserDO> userList() {
        return userService.selectUserList();
    }

    /**
     * @Author Zhouxw
     * @Date 2020/09/21 13:23
     * @Description 根据分组编码，获取用户信息
     * @Param [groupCode]
     * @Return com.zxw.dto.ChartDTO
     */
    @GetMapping("/user/{groupCode}")
    public UserChartDTO selectUserChartDTOByGroupCode(@PathVariable String groupCode) {
        return userService.dealUserChartDTOByGroupCode(groupCode);
    }
    /**
     * webUrll测试http://localhost:8090/add
     * Description: 新增一条用户数据；并返回拼装的用户数据
     * @Param user:
     * @return: com.zxw.model.User
     * Author: ljx
     * Date: 2021/3/24 0024 上午 10:14
      */
    @PostMapping("/user/add")
    public UserDO add(UserDO user) {
        //保存数据
        UserDO result = userService.addUser(user);
        //拼装数据DTO通知前端
        UserChartDTO userChartDTO = userService.dealUserChartDTOByGroupCode(user.getGroupCode());
        WebSocketSendUtil.sendMessage(user.getGroupCode(), JSON.toJSONString(userChartDTO));
        return result;
    }
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
