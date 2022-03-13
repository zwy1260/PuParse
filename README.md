# **<font color="red">Fiddler简介</font>**

- Fiddler是位于客户端和服务器端的HTTP代理

- 目前最常用的http抓包工具之一
- 功能非常强大，是web调试的利器
  - 监控浏览器所有的HTTP/HTTPS流量
  - 查看、分析请求内容细节
  - 伪造客户端请求和服务器响应
  - 测试网站的性能
  - 解密HTTPS的web会话
  - 全局、局部断点功能
  - 第三方插件

## Session List(会话列表)

- Fiddler抓取到的每条http请求（每一条称为一个session)
- 主要包含了请求的ID编号、状态码、协议、主机名、URL、内容类型body大小、进程信息、自定义备注等信息

## Statistics(统计)

- HTTP请求的性能和其他数据分析，如DNS解析的时间,建立FCP/IP连接的时间
  消耗等信息

## AutoResponder（自动响应器)

- AutoResponder可用于拦截某一请求，进行如下操作:
  - 重定向到本地的资源
  - 使用Fiddler的内置响应自定义响应

![image-20220311174136064](E:\MuMu\GitHub\homework\README.assets\image-20220311174136064.png)



## HTTPS抓包

- 点击Tools > Fiddler Options >HTTPS。

- 勾选Decrypt HTTPS Traffic



## ioS设备抓包-Fiddler设置

- 点击Tools > Fiddler options > Connections.
- 勾选Allow remote computers to connect.
- 重启Fiddler
- 确保防火墙允许 Fiddler进程可以远程连接
- ios设备连接wiFi
- 确保ios设备可以访问到http: //FiddlerMachineIP:8888 ，该地址会返回Fiddler Echo service页面

![image-20220311180311519](E:\MuMu\GitHub\homework\README.assets\image-20220311180311519.png)



## Android设备抓包

- 打开设置 > WLAN >连接上的WLAN设置
- 点击代理>手动，设置主机名为Fiddler所在主机的IP，端口为Fiddler监听端口
- 打开Android设备浏览器，访问http : /lipv4 :8888/
- 点击页面底部FiddlerRoot certificate下载证书
- 打开设置>更多设置>系统安全>加密与凭据>从存储设备安装
- 选择下载好的FiddlerRoot.cer进行安装
  浏览器打开https: // www.baidu . com，已经可以抓取HTTPS包了
- <font color="red">注意:测试完毕，记得关闭代理，否则手机无法上网</font>



