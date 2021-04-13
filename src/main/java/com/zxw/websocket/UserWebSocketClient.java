package com.zxw.websocket;
/**
 * @author ljx
 * @Description: 数据库触发器的外部程序或接口
 * @FR功能需求：
        前台用户终端【浏览器】页面，使用js WebSocket；
        通过后台服务器的websocket服务器端|ServerEndpoint即UserWebSocketServer.java，维护Java服务器会话、用户终端的Sessions；
        与在后台服务器的websocket客户端|Java服务器|ClientEndpoint即UserWebSocketClient.java，进行双向通信；
        将UserWebSocketClient.java，构建成外部程序jar包；
        数据库建立insert触发器，当插入数据时调用外部程序jar包。
 * @ImportJar:
 * @ApiGrammer规则：
 * @Remark:
 * @AlibabaCodeStatuteScanError：
 * @CodeBug解决:
 * @Debug调试：
 * @date 2021/4/13 0013 上午 9:45
 */
public class UserWebSocketClient {

}
