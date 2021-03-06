## 1.定义

在用户未打开App时，App主动向用户推送服务器最新消息。如下图：

![](http://upload-images.jianshu.io/upload_images/944365-e46b65bb38969df7.png?imageMogr2/auto-orient/strip|imageView2/2/w/1240)

**消息推送的本质是：App将服务器更新的信息推送给用户。**

## 2.作用

* 产品的角度：功能需要，比如说资讯类产品的新闻推送、工具类产品的公告推送等等
* 运营的角度：活动运营需要，比如说电商类产品的促销活动；召回用户 / 提高活跃度等等

## 3.储备知识

### 3.1 操作系统有自身的消息推送功能（系统级别）

* 系统级别：任何时候都可以推送给用户，且不会被系统杀死
* Android的消息推送服务称为：C2DM（Cloudto Device Messaging）

### 3.2 推送的本质与原理

* 消息推送的本质是：App将服务器更新的信息推送给用户，即App获取服务器信息，再推送给用户

* App从服务器获取最新消息的基本方式（原理）有3种：Push、Pull 和 SMS

* 具体如下

![](http://upload-images.jianshu.io/upload_images/944365-5a3fb1c6f96860b8.png?imageMogr2/auto-orient/strip|imageView2/2/w/1240 "示意图")4.

## 4.解决方案

经总结，Android中实现消息推送的有7种主流解决方案，接下来将一一介绍。

### 4.1 C2DM

* 定义：Cloud to Device Messaging，云端推送

> Android系统级别的消息推送服务-Google出品

* 原理：基于Push方式，C2DM服务负责处理诸如消息排队等事务，并向运行于目标设备上的应用程序分发这些消息。如下图：
* ![](/assets/c2dm.png)

C2DM原理

* 优点：C2DM提供了一个简单的、轻量级的机制，允许服务器可以通知移动应用程序直接与服务器进行通信，以便于从服务器获取应用程序更新和用户数据。
* 缺点：
  1. 依赖于Google官方提供的C2DM服务器，但在国内使用Google服务需要翻墙，成本较大；
  2. 需要用户手机安装Google服务。但由于Android机型、系统的碎片化 
     &
      国内环境，国内的Android系统都自动去除Google服务，假如要使用C2DM服务，这意味着用户还得去安装Google服务，成本较大。

#### 4.2 轮询

* 原理：基于Pull方式，应用程序隔固定时间
  **主动**
  与服务器进行连接并查询是否有新的消息
* 缺点：
  1. 成本大，需要自己实现与服务器之间的通信，例如消息排队等；
  2. 到达率不确定，考虑轮询的频率：太低可能导致消息的延迟；太高，更费客户端的资源（CPU资源、网络流量、系统电量）和服务器资源（网络带宽）

### 4.3 SMS

* 定义：短信发送
* 原理：基于Push方式，通过拦截SMS消息并且解析消息内容来了解服务器的意图，并获取其显示内容进行处理。
* 优点：可实现完全的实时操作
* 缺点：成本相对较高。因为目前来说，很难找到免费的短消息发送网关来实现这种方案，只能通过向运营商缴纳相应的短信费用

### 4.4 MQTT协议

* 定义：轻量级的消息发布/订阅协议
* 原理：基于Push方式，wmqtt.jar 是IBM提供的MQTT协议的实现，原理如下图：

![](/assets/mqttt.png)

MQTT协议原理

> 更多关于MQTT协议：
>
> 1. [项目实例源](https://github.com/tokudu/AndroidPushNotificationsDemo)
> 2. [一个采用PHP书写的服务器端](https://github.com/tokudu/PhpMQTTClient)
> 3. [Jar包下载地址](http://www-01.ibm.com/support/docview.wss?rs=171&uid=swg24006006)
>    ，并加入自己的Android应用程序中。
> 4. 拓展：RSMB是从MQTT协议引申出来的另外一种解决方案：简单的MQTT代理，详情
>    [请点击](http://www.alphaworks.ibm.com/tech/rsmb)

### 4.5 XMPP协议

* 定义：Extensible Messageing and Presence Protocol，可扩展消息与存在协议，是基于可扩展标记语言（XML）的协议，是目前主流的四种IM协议之一

> 其他三种：

* 即时信息和空间协议（IMPP）

* 空间和即时信息协议（PRIM）

* 即时通讯和空间平衡扩充的进程开始协议SIP（SIMPLE）

* 原理：XMPP中定义了三个角色，分别是客户端、服务器和网关  
  **客户端**

  1. 通过 TCP/IP与XMPP 服务器连接，然后在之上传输与即时通讯相关的指令（XML）；
  2. 解析组织好的 XML 信息包；
  3. 理解消息数据类型。

> * XMPP的核心：XML流传输协议（在网络上分片断发送XML的流协议），也是即时通讯指令的传递基础，即XMPP用TCP传的是XML流
>
> * 与即时通讯相关的指令，在以前要么用2进制的形式发送（比如QQ），要么用纯文本指令加空格加参数加换行符的方式发送（比如MSN）。
>
> * XMPP传输的即时通讯指令的逻辑与以往相仿，只是协议的形式变成了XML格式的纯文本。

**服务器**

1. 监听客户端连接，并直接与客户端应用程序通信（客户端信息记录）
2. 与其他 XMPP 服务器通信；

**网关**：与异构即时通信系统进行通信

> 异构系统包括SMS（短信），MSN，ICQ等

通信能够在这三者的任意两个之间双向发生。

**原理流程**

![](/assets/xmpp.png)

原理流程

* 优点：

  1. 开源：可通过修改其源代码来适应我们的应用程序。
  2. 简单：XML易于解析和阅读；将复杂性从客户端转移到了服务器端
  3. 可拓展性强：继承了在XML环境中灵活的发展性，可进一步对协议进行扩展，实现更为完善的功能。

  > GTalk、QQ、IM等都用这个协议

* 缺点：如果将消息从服务器上推送出去，则不管消息是否成功到达客户端手机上。

* 源码实例：有一个很棒的基于XMPP协议的java开源Android push notification：Androidpn[项目地址](http://sourceforge.net/projects/androidpn/)，大家有兴趣可以去看看

> 更多关于XMPP协议更加详细[请点击](http://www.cnblogs.com/hanyonglu/archive/2012/03/04/2378956.html)

### 4.6 使用第三方平台

现今主流的推送平台分为

1. 手机厂商类：小米推送、华为推送。
2. 第三方平台类：友盟推送、极光推送、云巴（基于MQTT）
3. BAT大厂的平台推送：阿里云移动推送、腾讯信鸽推送、百度云推送

具体各推送平台的优缺点请看我写的文章：[Android推送：第三方消息推送平台详细解析](http://www.jianshu.com/p/d77eaca4e52a)

### 4.7 自己搭建

如果你的产品对于消息推送具备较高的功能和性能要求，同时对安全性要求非常高的话，自己搭建可能是最好的方式，但这种方式无疑成本是最高的。

## 5、TCP长连接

### 5.1什么是长连接

先说短连接, 短连接是通讯双方有数据交互时就建立一个连接, 数据发送完成后，则断开此连接。

![](http://www.52im.net/data/attachment/forum/201605/30/135650a2bt78lf8b44i82t.png "Android端消息推送总结：实现原理、心跳保活、遇到的问题等\_1.png")

长连接就是大家建立连接之后, 不主动断开. 双方互相发送数据, 发完了也不主动断开连接, 之后有需要发送的数据就继续通过这个连接发送.

TCP连接在默认的情况下就是所谓的长连接, 也就是说连接双方都不主动关闭连接, 这个连接就应该一直存在.。

### 5.2长连接突然断开的情况

在实际网络中情况是复杂的, 这个连接可能会被切断. 比如客户端到服务器的链路因为故障断了, 或者服务器宕机了, 或者是你家网线被人剪了, 这些都是一些莫名其妙的导致连接被切断的因素, 还有几种比较特殊的。

**1、NAT超时**

因为 IP v4 的 IP 量有限，运营商分配给手机终端的 IP 是运营商内网的 IP，手机要连接 Internet，就需要通过运营商的网关做一个网络地址转换\(Network Address Translation，NAT\)。简单的说运营商的网关需要维护一个外网 IP、端口到内网 IP、端口的对应关系，以确保内网的手机可以跟 Internet 的服务器通讯

![](http://www.52im.net/data/attachment/forum/201603/03/215126cr55bqc64c4fcjf7.png "移动端IM实践：实现Android版微信的智能心跳机制\_QQ20160303-2.png")

因为IPv4地址不足, 或者我们想通过无线路由器上网, 我们的设备可能会处在一个NAT设备的后面, 生活中最常见的NAT设备是家用路由器.

NAT设备会在IP封包通过设备时修改源/目的IP地址. 对于家用路由器来说, 使用的是网络地址端口转换\(NAPT\), 它不仅改IP, 还修改TCP和UDP协议的端口号, 这样就能让内网中的设备共用同一个外网IP. 举个例子, NAPT维护一个类似下表的NAT表：

![](http://www.52im.net/data/attachment/forum/201605/30/135836xb0d992e23oe9rzj.png "Android端消息推送总结：实现原理、心跳保活、遇到的问题等\_QQ20160530-0.png")

NAT设备会根据NAT表对出去和进来的数据做修改, 比如将192.168.0.3:8888发出去的封包改成120.132.92.21:9202, 外部就认为他们是在和120.132.92.21:9202通信. 同时NAT设备会将120.132.92.21:9202收到的封包的IP和端口改成192.168.0.3:8888, 再发给内网的主机, 这样内部和外部就能双向通信了, 但如果其中192.168.0.3:8888 == 120.132.92.21:9202这一映射因为某些原因被NAT设备淘汰了, 那么外部设备就无法直接与192.168.0.3:8888通信了。

我们的设备经常是处在NAT设备的后面, 比如在大学里的校园网, 查一下自己分配到的IP, 其实是内网IP, 表明我们在NAT设备后面, 如果我们在寝室再接个路由器, 那么我们发出的数据包会多经过一次NAT.

国内移动无线网络运营商在链路上一段时间内没有数据通讯后, 会淘汰NAT表中的对应项, 造成链路中断。

**2、网络状态变化**

手机网络和WIFI网络切换、网络断开和连上等情况有网络状态的变化，也会使长连接变为无效连接，需要监听响应的网络状态变化事件，重新建立Push长连接。

3、**DHCP的租期**

目前测试发现安卓系统对DHCP的处理有Bug, DHCP租期到了不会主动续约并且会继续使用过期IP, 这个问题会造成TCP长连接偶然的断连。

## 6、心跳包

### 6.1心跳包的作用

那为什么要有心跳包呢? 其实主要是为了防止上面提到的NAT超时, 既然一些NAT设备判断是否淘汰NAT映射的依据是一定时间没有数据, 那么客户端就主动发一个数据。

当然, 如果仅仅是为了防止NAT超时, 可以让服务器来发送心跳包给客户端, 不过这样做有个弊病就是, 万一连接断了, 服务器就再也联系不上客户端了. 所以心跳包必须由客户端发送, 客户端发现连接断了, 还可以尝试重连服务器。

所以心跳包的主要作用是防止NAT超时, 其次是探测连接是否断开。

链路断开, 没有写操作的TCP连接是感知不到的, 除非这个时候发送数据给服务器, 造成写超时, 否则TCP连接不会知道断开了. 主动kill掉一方的进程, 另一方会关闭TCP连接, 是系统代进程给服务器发的FIN. TCP连接就是这样, 只有明确的收到对方发来的关闭连接的消息\(收到RST也会关闭, 大家都懂\), 或者自己意识到发生了写超时, 否则它认为连接还存在。

### 6.2心跳包的时间间隔

发送心跳包势必要先唤醒设备, 然后才能发送, 如果唤醒设备过于频繁, 或者直接导致设备无法休眠, 会大量消耗电量, 而且移动网络下进行网络通信, 比在wifi下耗电得多. 所以这个心跳包的时间间隔应该尽量的长, 最理想的情况就是根本没有NAT超时, 比如刚才我说的两台在同一个wifi下的电脑, 完全不需要心跳包. 这也就是网上常说的长连接, 慢心跳。

现实是残酷的, 根据网上的一些说法, 中移动2/3G下, NAT超时时间为5分钟, 中国电信3G则大于28分钟, 理想的情况下, 客户端应当以略小于NAT超时时间的间隔来发送心跳包。

wif下, NAT超时时间都会比较长, 据说宽带的网关一般没有空闲释放机制, GCM有些时候在wifi下的心跳比在移动网络下的心跳要快, 可能是因为wifi下联网通信耗费的电量比移动网络下小。

### 6.3服务器如何处理心跳包

如果客户端心跳间隔是固定的, 那么服务器在连接闲置超过这个时间还没收到心跳时, 可以认为对方掉线, 关闭连接. 如果客户端心跳会动态改变, 如上节提到的微信心跳方案, 应当设置一个最大值, 超过这个最大值才认为对方掉线. 还有一种情况就是服务器通过TCP连接主动给客户端发消息出现写超时, 可以直接认为对方掉线.

