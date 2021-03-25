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

    int countBySexAndGroupCode(String sex, String groupCode);
    /**
     * Title: selectSexNumListByGroupCode
     * Description: 根据groupCode，列表查询
     * @Param groupCode:
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * Author: ljx
     * Date: 2021/3/25 0025 下午 4:44
      */
    @Query("SELECT sex AS sex,count(sex) AS sexNum FROM UserDO  WHERE groupCode = ?1 GROUP BY sex")
    List<Map<String, Object>> selectSexNumListByGroupCode(String groupCode);
    /**
     * Title: selectSexNumListByParams
     * Description: 根据groupCode、前台页面条件，列表查询
     * @Param groupCode:
     * @Param sexParam:
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * Author: ljx
     * Date: 2021/3/25 0025 下午 4:43
      */
    @Query(value = "SELECT sex AS sex,count(sex) AS sexNum FROM UserDO WHERE 1=1 and group_code= :groupCode  and if(:sexParam!=null,sex=:sexParam,1=1) and if(:emailParam!=null,email LIKE CONCAT('%',:emailParam,'%'),1=1) GROUP BY sex ",  nativeQuery = true)
    List<Map<String, Object>> selectSexNumListByParams(@Param("groupCode") String groupCode,@Param("sexParam") Integer sexParam,@Param("emailParam") String emailParam);
}
