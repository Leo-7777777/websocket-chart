package com.ljx.wsc.controller;

import com.ljx.wsc.pojo.doo.UserDO;
import com.ljx.wsc.websocket.UserWebSocketServer;
import com.ljx.wscneln._09util.maputil.MapGetter;
import com.ljx.wsc.pojo.dto.UserChartDTO;
import com.ljx.wsc.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
/**
 *
* @Description: 用户信息
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
@RestController
@RequestMapping("/user")
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
    @GetMapping("/{userId}/queryByUserId")
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
    @PostMapping("/addAndQueryByUserId")
    public UserDO addAndQueryByUserId(UserDO user) {
        // 保存数据
        UserDO result = userService.addUser(user);
        String userId=user.getUserId();
        // 拼装的用户数据（根据用户id，列表查询用户表）
        UserChartDTO userChartDTO = userService.dealUserChartDtoByUserId(userId);
        // 通知前端，向前台发送消息
        UserWebSocketServer.sendMessageToWebsocketJs(user.getUserId(), JSON.toJSONString(userChartDTO));
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
    @GetMapping("/{userId}/queryByParams")
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
    @PostMapping("/addAndQueryByParams")
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
        UserWebSocketServer.sendMessageToWebsocketJs(user.getUserId(), JSON.toJSONString(userChartDTO));
        return result;
    }
}
