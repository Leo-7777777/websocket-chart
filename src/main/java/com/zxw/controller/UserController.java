package com.zxw.controller;

import com.zxw.dto.UserChartDTO;
import com.zxw.model.UserDO;
import com.zxw.service.UserService;
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
     * Description: 根据分组编码，列表查询用户表
     * @Param groupCode     分组编码
     * @Param params        前台页面查询条件
     * @return: com.zxw.dto.UserChartDTO
     * Author: ljx
     * Date: 2021/3/25 0025 下午 4:32
      */
    @GetMapping("/{groupCode}/queryByGroupCode")
    public UserChartDTO selectUserChartDTOByGroupCode(@PathVariable String groupCode) {
        return userService.dealUserChartDTOByGroupCode(groupCode);
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
    @PostMapping("/addAndQueryByGroupCode")
    public UserDO addAndQueryByGroupCode(UserDO user) {
        // 保存数据
        UserDO result = userService.addUser(user);
        String groupCode=user.getGroupCode();
        // 拼装的用户数据（根据分组编码，列表查询用户表）
        UserChartDTO userChartDTO = userService.dealUserChartDTOByGroupCode(groupCode);
        // 通知前端
        UserWebSocket.sendMessage(user.getGroupCode(), JSON.toJSONString(userChartDTO));
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
    @GetMapping("/{groupCode}/queryByParams")
    public UserChartDTO selectUserChartDTOByParams(@PathVariable String groupCode,@RequestParam Map<String,Object> params) {
        params.put("groupCode",groupCode);
        return userService.dealUserChartDTOByParams(params);
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
    @PostMapping("/addAndQueryByParams")
    public UserDO addAndQueryByParams(UserDO user) {
        // 保存数据
        UserDO result = userService.addUser(user);
        String groupCode=user.getGroupCode();
        String emailParam=user.getEmail();
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("groupCode",groupCode);
        params.put("emailParam",emailParam);
        // 拼装的用户数据（根据分组编码、前台页面条件，列表查询用户表）
        UserChartDTO userChartDTO = userService.dealUserChartDTOByParams(params);
        // 通知前端
        UserWebSocket.sendMessage(user.getGroupCode(), JSON.toJSONString(userChartDTO));
        return result;
    }
}