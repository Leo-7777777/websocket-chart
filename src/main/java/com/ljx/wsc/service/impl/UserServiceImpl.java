package com.ljx.wsc.service.impl;

import com.ljx.wsc.service.UserService;
import com.ljx.wsc.pojo.dto.UserChartDTO;
import com.ljx.wsc.pojo.doo.UserDO;
import com.ljx.wsc.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
/**
 *
* @Description: 用户信息实现
* @FR功能需求：
* @ImportJar:
* @ApiGrammer规则：
* @Remark:
* @AlibabaCodeStatuteScanError：
* @CodeBug解决:
* @Debug调试：
* @date ${DATE} ${TIME}
* @author  ljx
*
 */
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
    public List<Map<String, Object>> selectSexNumListByUserId(String userId) {
        return userRepository.selectSexNumListByUserId(userId);
    }
    @Override
    public UserChartDTO dealUserChartDtoByUserId(String userId) {
        UserChartDTO userChartDTO = new UserChartDTO();
        List<Map<String, Object>> mapList = selectSexNumListByUserId(userId);
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
        String userId= (String)params.get("userId");
        String emailParam=(String)params.get("emailParam");
        return userRepository.selectSexNumListByParams(userId,emailParam);
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
