package com.zxw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
* @Description: 首页
* @FR功能需求：
* @ImportJar:
* @ApiGrammer规则：
* @Remark:
    http://localhost:8090
    SpringBoot项目在启动后，首先会去静态资源路径（resources/static）下查找 index.html 作为首页文件。
    如果在静态资源路径（resources/static）下找不到 index.html，则会到（resources/templates）目录下找 index.html（使用 Thymeleaf 模版）作为首页文件。
* @AlibabaCodeStatuteScanError：
* @CodeBug解决:
* @Debug调试：
* @date
* @author  ljx
*
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    /**
     * webUrl测试http://localhost:8090/websocket-chart/index/toQueryByUserIdIndex
     * Description: 跳转单条件图表页面
     * @return: jspStr
      */
    @RequestMapping("/toQueryByUserIdIndex")
    public String  toQueryByUserIdIndexFun1(){
        String jspStr="general_jsp/queryByUserId/index";
        return jspStr;
    }
    public String  toQueryByUserIdIndexFun2(){
        String jspStr="forward:general_jsp/queryByUserId/index.html";
        return jspStr;
    }
    /**
     * webUrl测试http://localhost:8090/websocket-chart/index/toByUserIdAdd
     * @return
     */
    @RequestMapping("/toByUserIdAdd")
    public String  toByUserIdAdd(){
        String jspStr="general_jsp/queryByUserId/add";
        return jspStr;
    }
    /**
     * webUrl测试http://localhost:8090/websocket-chart/index/toQueryByUserIdAndParamsIndex
     * Description: 跳转多条件图表页面
     * @return: jspStr
      */
    @RequestMapping("/toQueryByUserIdAndParamsIndex")
    public String  toQueryByUserIdAndParamsIndexFun1(){
        String jspStr="general_jsp/queryByUserIdAndParams/index";
        return jspStr;
    }
    /**
     * webUrl测试http://localhost:8090/websocket-chart/index/toByUserIdAndParamsAdd
     * @return
     */
    @RequestMapping("/toByUserIdAndParamsAdd")
    public String  toByUserIdAndParamsAdd(){
        String jspStr="general_jsp/queryByUserIdAndParams/add";
        return jspStr;
    }
}
