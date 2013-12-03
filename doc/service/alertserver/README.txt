AlertServer 使用说明

AlertServer(AS)是一个收集设备alert并转发给订阅者的服务器。

工作原理：
AS会在本地打开一个TCP端口以监听客户端（订阅者）的连接，当客户端连接后会要求客户端发送用户名及密码用以在数据库查询授权。
当授权通过后，订阅者就可以获取到相应的alert。
AS同时会在本地建立一个WebService服务器以接收alert。接收到的alert中会包含订阅者的名字（可能有多个），AS会根据该名字转发给相应的订阅者。

AS配置文件：alertserver.properties

Alert收集：
收集alert由WebService方式实现，接口地址可以在配置文件中设置
WS接口地址： WEB_SERVICE_IP + ':' + WEB_SERVICE_PORT + '/' + WEB_SERVICE_NAME

TCP服务端配置：
配置ALERT_SERVER_PORT可以定义服务端的监听端口。

数据库配置：
DB_DRIVER、DB_URL等配置用以设置数据库连接。
该数据库连接用以执行订阅者的身份验证。身份验证逻辑由DB_AUTH_VERIFY_SQL控制，当SQL执行结果为"1"时验证通过。
DB_AUTH_PWD_IS_DIGEST_BY_MD5为true的时候表示密码用MD5散列后存储在数据库，false表示直接明文存储。

启动：
执行service/alertserver/bin/startup.bat即可启动AlertServer

检查：
1. 在IE中输入如下URL以检查webservice是否启动
http://${ws_ip}:${ws_port}/${ws_name}/sendInfo?wsdl
如： http://127.0.0.1:9902/alertserver/sendInfo?wsdl

2. 打开cmd，输入命令: netstat /a | findstr ${ALERT_SERVER_PORT}，以检查是否在指定端口上启动监听。

3. 如果检查无误，那么就可以配置相应的alert源和alert接收端来接入alertServer以完成业务流程测试。