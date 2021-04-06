package com.zxw.service;

import com.zxw.dto.UserChartDTO;
import com.zxw.model.UserDO;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDO> selectUserList();

    UserDO addUser(UserDO user);

    List<Map<String, Object>> selectSexNumListByGroupCode(String groupCode);
    UserChartDTO dealUserChartDtoByGroupCode(String groupCode);

    List<Map<String, Object>> selectSexNumListByParams(Map<String, Object> params);
    UserChartDTO dealUserChartDtoByParams(Map<String, Object> params);
}
