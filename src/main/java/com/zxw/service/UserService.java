package com.zxw.service;

import com.zxw.dto.UserChartDTO;
import com.zxw.model.UserDO;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 列表查询用户表
     * @return
     */
    List<UserDO> selectUserList();

    /**
     * 新增用户表一条数据
     * @param user
     * @return
     */
    UserDO addUser(UserDO user);

    /**
     * 根据groupCode，列表查询用户表
     * @param groupCode
     * @return
     */
    List<Map<String, Object>> selectSexNumListByGroupCode(String groupCode);

    /**
     * 根据groupCode，查询用户表数据；并处理数据
     * @param groupCode
     * @return
     */
    UserChartDTO dealUserChartDtoByGroupCode(String groupCode);

    /**
     * 根据查询条件，查询用户表数据
     * @param params
     * @return
     */
    List<Map<String, Object>> selectSexNumListByParams(Map<String, Object> params);

    /**
     * 根据g查询条件，查询用户表数据；并处理数据
     * @param params
     * @return
     */
    UserChartDTO dealUserChartDtoByParams(Map<String, Object> params);
}
