package com.ljx.wsc.repository;

import com.ljx.wsc.pojo.doo.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 *
* @Description:
* @FR功能需求：
* @ImportJar:
* @ApiGrammer规则：
* @Remark:
* @AlibabaCodeStatuteScanError：
* @CodeBug解决:
* @Debug调试：
* @date
* @author  ljx
*
 */
@Repository
public interface UserRepository extends JpaRepository<UserDO, Long> {
    /**
     * 根据性别、userId，查询用户表条数
     * @param sex       性别
     * @param userId 用户id
     * @return
     */
    int countBySexAndUserId(String sex, String userId);
    /**
     * 根据userId，列表查询
     * @param userId 用户id
     * @return
     */
    @Query("SELECT sex AS sex,count(sex) AS sexNum FROM UserDO  WHERE userId = ?1 GROUP BY sex")
    List<Map<String, Object>> selectSexNumListByUserId(String userId);
    /**
     * 根据userId、前台页面条件，列表查询
     * @param userId
     * @param emailParam
     * @return
     */
    @Query(value = "SELECT sex AS sex,count(sex) AS sexNum FROM UserDO WHERE 1=1 and user_id= :userId  and if(:emailParam!='',email LIKE CONCAT('%',:emailParam,'%'),1=1) GROUP BY sex ",  nativeQuery = true)
    List<Map<String, Object>> selectSexNumListByParams(@Param("userId") String userId,@Param("emailParam") String emailParam);
}
