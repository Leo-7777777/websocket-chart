package com.zxw.controller;

import com.zxw.dto.UserChartDTO;
import com.zxw.model.UserDO;
import com.zxw.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/list")
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
    @GetMapping("/{groupCode}")
    public UserChartDTO selectUserChartDTOByGroupCode(@PathVariable String groupCode) {
        return userService.dealUserChartDTOByGroupCode(groupCode);
    }

    /**
     * webUrll测试http://localhost:8090/add
     * Description: 新增一条用户数据；并返回拼装的用户数据
     *
     * @Param user:
     * @return: com.zxw.model.User
     * Author: ljx
     * Date: 2021/3/24 0024 上午 10:14
     */
    @PostMapping("/add")
    public UserDO add(UserDO user) {
        //保存数据
        UserDO result = userService.addUser(user);
        //拼装数据DTO通知前端
        UserChartDTO userChartDTO = userService.dealUserChartDTOByGroupCode(user.getGroupCode());
        UserWebSocket.sendMessage(user.getGroupCode(), JSON.toJSONString(userChartDTO));
        return result;
    }
}