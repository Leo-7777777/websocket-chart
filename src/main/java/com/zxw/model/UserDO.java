package com.zxw.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDO {
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 分组编码
     */
    @Column(nullable = false)
    private String groupCode;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private Integer sex;
    @Column
    private String email;

    public UserDO() {
    }

    public UserDO(String groupCode, String userName, Integer sex, String email) {
        this.groupCode = groupCode;
        this.userName = userName;
        this.sex = sex;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
