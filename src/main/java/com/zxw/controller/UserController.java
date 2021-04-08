package com.zxw.controller;

import com.ljx.wcneln._09util.MapGetter;
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
    /**
     * webUrl测试
     * Description: 根据分组编码，列表查询用户表
     * @Param groupCode     分组编码
     * @Param params        前台页面查询条件
     * @return: com.zxw.dto.UserChartDTO
     * Author: ljx
     * Date: 2021/3/25 0025 下午 4:32
     */
    @GetMapping("/user/{groupCode}/queryByGroupCode")
    public UserChartDTO selectUserChartDtoByGroupCode(@PathVariable String groupCode) {
        return userService.dealUserChartDtoByGroupCode(groupCode);
    }
    /**
     * webUrll测试http://localhost:8090/addAndQueryByGroupCode
     * Description: 新增一条用户数据；并返回拼装的用户数据（根据分组编码，列表查询用户表）
     *
     * @Param user:
     * @return: com.zxw.model.User
     * Author: ljx
     * Date: 2021/3/24 0024 上午 10:14
     */
    @PostMapping("/user/addAndQueryByGroupCode")
    public UserDO addAndQueryByGroupCode(UserDO user) {
        //保存数据
        UserDO result = userService.addUser(user);
        //拼装数据DTO通知前端
        UserChartDTO userChartDTO = userService.dealUserChartDtoByGroupCode(user.getGroupCode());
        WebSocketSendUtil.sendMessage(user.getGroupCode(), JSON.toJSONString(userChartDTO));
        return result;
    }
    /**
     * Title: selectUserChartDTOByParams
     * Description: 根据分组编码、前台页面条件，列表查询用户表
     * @Param groupCode:
     * @Param params:
     * @return: com.zxw.dto.UserChartDTO
     * Author: ljx
     * Date: 2021/3/25 0025 下午 5:34
     */
    @GetMapping("/user/{groupCode}/queryByParams")
    public UserChartDTO selectUserChartDtoByParams(@PathVariable String groupCode,@RequestParam Map<String,Object> params) {
        params.put("groupCode",groupCode);
        return userService.dealUserChartDtoByParams(params);
    }

    /**
     * webUrll测试http://localhost:8090/addAndQueryByGroupCode
     * Description: 新增一条用户数据；并返回拼装的用户数据（根据分组编码、前台页面条件，列表查询用户表）
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
        String groupCode=user.getGroupCode();
        String emailParam=user.getEmail();
        Map<String, Object> params=new HashMap<String, Object>(MapGetter.defaultInitialCapacity());
        params.put("groupCode",groupCode);
        params.put("emailParam",emailParam);
        // 拼装的用户数据（根据分组编码、前台页面条件，列表查询用户表）
        UserChartDTO userChartDTO = userService.dealUserChartDtoByParams(params);
        // 通知前端
        WebSocketSendUtil.sendMessage(user.getGroupCode(), JSON.toJSONString(userChartDTO));
        return result;
    }
    /*######################## 一、根据分组编码，接收 消息(用户信息)的 websocket服务器端 ########################*/
    /**
     * 当WebSocket客户端与服务器建立连接并完成握手后，前台会回调ws.onopen；后台调用@OnOpen注解的方法。
     * @param groupCode
     * @param session
     */
    @OnOpen
    public void openSession(@PathParam("groupCode") String groupCode, Session session) {
        List<Session> list = WebSocketSendUtil.ONLINE_USER_SESSIONS.get(groupCode);
        // 如果该用户当前是第一次连接/没有在别的终端登录
        if (null == list) {
            list = new ArrayList<>();
        }
        // 如果该用户当前不是第一次连接/已经在别的终端登录
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
        List<Session> list = WebSocketSendUtil.ONLINE_USER_SESSIONS.get(groupCode);
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
