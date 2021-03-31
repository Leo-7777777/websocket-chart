package com.zxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/**
 * Description: springboot主配置文件——启动程序
 * @Param null:
 * @return: null
 * Author: ljx
 * Date: 2021/3/19 0019 下午 1:06
  */
@EnableWebSocket
@SpringBootApplication
public class ChartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChartApplication.class, args);
		System.out.println("服务启动成功！");
	}

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
