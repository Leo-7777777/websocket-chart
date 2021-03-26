package com.zxw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Title: IndexController
 * Description: 首页
 * @Remark：
        http://localhost:8090
        SpringBoot项目在启动后，首先会去静态资源路径（resources/static）下查找 index.html 作为首页文件。
        如果在静态资源路径（resources/static）下找不到 index.html，则会到（resources/templates）目录下找 index.html（使用 Thymeleaf 模版）作为首页文件。
 * Author: ljx
 * Date: 2021/3/26 0026 上午 10:44
 */
@Controller
@RequestMapping("index")
public class IndexController {
    /**
     * webUrl测试http://localhost:8090/websocket-chart/index/toQueryByGroupCodeIndex
     * Description: 跳转单条件图表页面
     * @return: jspStr
      */
    @RequestMapping("toQueryByGroupCodeIndex")
    public String  toQueryByGroupCodeIndexJspFun1(){
        String jspStr="templates/general_jsp/queryByGroupCode/index";
        return jspStr;
    }
    public String  toQueryByGroupCodeIndexJspFun2(){
        String jspStr="forward:templates/general_jsp/queryByGroupCode/index.html";
        return jspStr;
    }
    /**
     * webUrl测试http://localhost:8090/websocket-chart/index/toQueryByGroupCodeAndParamsIndex
     * Description: 跳转多条件图表页面
     * @return: jspStr
      */
    @RequestMapping("toQueryByGroupCodeAndParamsIndex")
    public String  toQueryByGroupCodeAndParamsIndex1(){
        String jspStr="templates/general_jsp/queryByGroupCodeAndParams/index";
        return jspStr;
    }
}
