package com.zxw.dto;

/**
 * @Description: 图表结果DTO
 * @Author Zhouxw
 * @Date 2020/9/18 0018 17:02
 **/
public class UserChartDTO {
    private Long boy;
    private Long girl;
    private Long unknown;

    public UserChartDTO() {
    }

    public UserChartDTO(Long boy, Long girl, Long unknown) {
        this.boy = boy;
        this.girl = girl;
        this.unknown = unknown;
    }

    public Long getBoy() {
        return boy;
    }

    public void setBoy(Long boy) {
        this.boy = boy;
    }

    public Long getGirl() {
        return girl;
    }

    public void setGirl(Long girl) {
        this.girl = girl;
    }

    public Long getUnknown() {
        return unknown;
    }

    public void setUnknown(Long unknown) {
        this.unknown = unknown;
    }

    /**
     * 右键》Source》Generate toString()... 目的是为了属性能打印出来
     */
    @Override
    public String toString() {
        return "UserChartDTO{" +
                "boy=" + boy +
                ", girl=" + girl +
                ", unknown=" + unknown +
                '}';
    }
}
