package com.zxw.service.impl;

import com.zxw.dto.UserChartDTO;
import com.zxw.model.UserDO;
import com.zxw.repository.UserRepository;
import com.zxw.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户信息实现
 * @Author Zhouxw
 * @Date 2020/9/18 0018 15:20
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;

    @Override
    public List<UserDO> selectUserList() {
        return userRepository.findAll();
    }

    @Transient
    @Override
    public UserDO addUser(UserDO user) {
        return userRepository.save(user);
    }

    @Override
    public List<Map<String, Object>> selectSexNumListByGroupCode(String groupCode) {
        return userRepository.selectSexNumListByGroupCode(groupCode);
    }
    @Override
    public UserChartDTO dealUserChartDtoByGroupCode(String groupCode) {
        UserChartDTO userChartDTO = new UserChartDTO();
        List<Map<String, Object>> mapList = selectSexNumListByGroupCode(groupCode);
        if (null != mapList && mapList.size() > 0) {
            mapList.forEach((Map<String, Object> map) -> {
                switch ((int) map.get("sex")) {
                    case 0:
                        userChartDTO.setGirl((long) map.get("sexNum"));
                        break;
                    case 1:
                        userChartDTO.setBoy((long) map.get("sexNum"));
                        break;
                    default:
                        userChartDTO.setUnknown((long) map.get("sexNum"));
                        break;
                }
            });
        }
        return userChartDTO;
    }

    @Override
    public List<Map<String, Object>> selectSexNumListByParams(Map<String, Object> params) {
        String groupCode= (String)params.get("groupCode");
        String emailParam=(String)params.get("emailParam");
        return userRepository.selectSexNumListByParams(groupCode,emailParam);
    }
    @Override
    public UserChartDTO dealUserChartDtoByParams(Map<String, Object> params) {
        UserChartDTO userChartDTO = new UserChartDTO();
        List<Map<String, Object>> mapList = selectSexNumListByParams(params);
        if (null != mapList && mapList.size() > 0) {
            mapList.forEach((Map<String, Object> map) -> {
                switch ((int) map.get("sex")) {
                    case 0:
                        userChartDTO.setGirl(((BigInteger)map.get("sexNum")).longValue());
                        break;
                    case 1:
                        userChartDTO.setBoy(((BigInteger)map.get("sexNum")).longValue());
                        break;
                    default:
                        userChartDTO.setUnknown(((BigInteger)map.get("sexNum")).longValue());
                        break;
                }
            });
        }
        return userChartDTO;
    }
}
