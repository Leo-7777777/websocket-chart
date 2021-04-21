# websocket-chart
前台新增页面调用新增接口，新增数据；后台Controller层中使用websocket注解，实现图表新增数据实时显示

# 一、svn地址
&emsp;https://
<br/>&emsp;账号
<br/>&emsp;密码
# 二、本地项目
## 1、使用软件
&emsp;IntelliJ IDEA 2019.3 x64
## 2、配置
### （1）环境
&emsp;jdk1.8
<br/>&emsp;tomcat85_8089
### （2）导入jar包
&emsp;项目 右键》Maven》Update Project...（Alt+F5）
### （3）配置文件application.properties
* spring.datasource.url=修改数据库信息，ip、数据库名；
* server.port=IDEA启动项目/浏览器访问项目地址中的端口号8090
## 3、启动
&emsp;SpringBoot项目的主配置文件ChartApplication.java》点击按钮Debug
## 4、启动后浏览器默认访问地址
&emsp;http://localhost:8090
## 5、注意
&emsp;SpringBoot项目在启动后，首先会去静态资源路径（resources/static）下查找 index.html 作为首页文件。
<br/>&emsp;如果在静态资源路径（resources/static）下找不到 index.html，则会到（resources/templates）目录下找 index.html（使用 Thymeleaf 模版）作为首页文件。
# 三、项目部署linux服务器
## 1、项目打包
## 2、linux服务器地址
&emsp;192.168.21.120
<br/>&emsp;root
<br/>&emsp;123456
## 3、部署位置：
&emsp;/opt/developsoft/servers/tomcat/tomcat85_8089
## 4、部署注意：
	（1）ps -ef | grep websocket-chart.jar查出进程号，kill -9 进程号；
	（2）替换jar；
	（3）./startup.sh 启动
## 5、启动后浏览器默认访问地址
&emsp;http://192.168.21.120:8089/websocket-chart
# 四、jenkins自动部署
## 1、浏览器地址
&emsp;http://
<br/>&emsp;账号
<br/>&emsp;密码
## 2、启动
&emsp; 
## 3、启动后浏览器默认访问地址
&emsp;