package com.zxw.service;

import com.zxw.dto.UserChartDTO;
import com.zxw.model.UserDO;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDO> selectUserList();

    UserDO addUser(UserDO user);

    List<Map<String, Object>> selectSexNumListByGroupCode(String groupCode);

    UserChartDTO dealUserChartDTOByGroupCode(String groupCode);
}
