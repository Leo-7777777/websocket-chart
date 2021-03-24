package com.zxw.repository;

import com.zxw.model.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserRepository extends JpaRepository<UserDO, Long> {

    int countBySexAndGroupCode(String sex, String groupCode);

    @Query("SELECT sex AS sex,count(sex) AS sexNum FROM UserDO  WHERE groupCode = ?1 GROUP BY sex")
    List<Map<String, Object>> selectSexNumListByGroupCode(String groupCode);
}
