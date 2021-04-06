package com.zxw.repository;

import com.zxw.model.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserRepository extends JpaRepository<UserDO, Long> {
    /**
     * 根据性别、groupCode，查询用户表条数
     * @param sex       性别
     * @param groupCode 分组编码
     * @return
     */
    int countBySexAndGroupCode(String sex, String groupCode);
    /**
     * 根据groupCode，列表查询
     * @param groupCode 分组编码
     * @return
     */
    @Query("SELECT sex AS sex,count(sex) AS sexNum FROM UserDO  WHERE groupCode = ?1 GROUP BY sex")
    List<Map<String, Object>> selectSexNumListByGroupCode(String groupCode);
    /**
     * 根据groupCode、前台页面条件，列表查询
     * @param groupCode
     * @param emailParam
     * @return
     */
    @Query(value = "SELECT sex AS sex,count(sex) AS sexNum FROM UserDO WHERE 1=1 and group_code= :groupCode  and if(:emailParam!='',email LIKE CONCAT('%',:emailParam,'%'),1=1) GROUP BY sex ",  nativeQuery = true)
    List<Map<String, Object>> selectSexNumListByParams(@Param("groupCode") String groupCode,@Param("emailParam") String emailParam);
}
