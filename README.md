


> 项目GitHub地址：[https://github.com/niuxvdong/Guli_Project](https://github.com/niuxvdong/Guli_Project)







# 零、在线教育项目简介





## 工程结构



![](https://cdn.jsdelivr.net/gh/niuxvdong/pic@0908f1cbe6d7c1e3833cbd4cb7c92ed95b29718e/2021/11/21/f2b6638efe65d768689546bcd31e61f2.png)





## 模块说明



**guli-parent：在线教学根目录（父工程），管理四个子模块：**

- canal-client：canal数据库表同步模块（统计同步数据）
- common：公共模块父节点
    - common-util：工具类模块，所有模块都可以依赖于它
    - service-base：service服务的base包，包含service服务的公共配置类，所有service模块依赖于它
    - spring-security：认证与授权模块，需要认证授权的service服务依赖于它
- infrastructure：基础服务模块父节点
    - api-gateway：api网关服务
- service：api接口服务父节点
    - service-acl：用户权限管理api接口服务（用户管理、角色管理和权限管理等）
    - service-cms：cms api接口服务
    - service-edu：教学相关api接口服务
    - service-msm：短信api接口服务
    - service-order：订单相关api接口服务
    - service-oss：阿里云oss api接口服务
    - service-statistics：统计报表api接口服务
    - service-ucenter：会员api接口服务
    - service-vod：视频点播api接口服务



## 版本说明



**SpringBoot基于2.2.1，SpringCloud基于H版本！**



**其他版本：**



```xml
<properties>
    <java.version>1.8</java.version>
    <guli.version>0.0.1-SNAPSHOT</guli.version>
    <mybatis-plus.version>3.0.5</mybatis-plus.version>
    <velocity.version>2.0</velocity.version>
    <swagger.version>2.7.0</swagger.version>
    <aliyun.oss.version>2.8.3</aliyun.oss.version>
    <jodatime.version>2.10.1</jodatime.version>
    <poi.version>3.17</poi.version>
    <commons-fileupload.version>1.3.1</commons-fileupload.version>
    <commons-io.version>2.6</commons-io.version>
    <httpclient.version>4.5.1</httpclient.version>
    <jwt.version>0.7.0</jwt.version>
    <aliyun-java-sdk-core.version>4.3.3</aliyun-java-sdk-core.version>
    <aliyun-sdk-oss.version>3.1.0</aliyun-sdk-oss.version>
    <aliyun-java-sdk-vod.version>2.15.2</aliyun-java-sdk-vod.version>
    <aliyun-java-vod-upload.version>1.4.11</aliyun-java-vod-upload.version>
    <aliyun-sdk-vod-upload.version>1.4.11</aliyun-sdk-vod-upload.version>
    <fastjson.version>1.2.28</fastjson.version>
    <gson.version>2.8.2</gson.version>
    <json.version>20170516</json.version>
    <commons-dbutils.version>1.7</commons-dbutils.version>
    <canal.client.version>1.1.0</canal.client.version>
    <docker.image.prefix>zx</docker.image.prefix>
    <cloud-alibaba.version>0.2.2.RELEASE</cloud-alibaba.version>
</properties>
```













# 一、在线教育项目功能点







## 1、后台管理系统功能





### 登录功能



整合了 SpringSecurity 框架！



### 权限管理模块



- **菜单管理**：列表、添加、修改、删除
- **角色管理**：列表、添加、修改、删除、批量删除，**为角色分配菜单**
- **用户管理**：列表、添加、修改、删除、批量删除，**为用户分配角色**



**权限管理用到的表**





五张表，用户、角色、权限、以及相邻两表之间的关系表！



![](https://cdn.jsdelivr.net/gh/niuxvdong/pic@d9af1d59ff288c331ed0bfc684afc3e377814baa/2021/11/21/294ff51ddd3213813efb51dac0c07e26.png)

### 讲师管理模块



条件查询分页、列表、添加、修改、删除



### 课程分类模块



- **添加课程分类**：**读取Excel**里面课程分类数据，添加到数据库中
- **课程分类列表**：使用**树形结构**显示课程分类列表



### 课程管理模块

- 课程列表功能
- 添加课程：课程发布流程：第一步填写课程基本信息，第二步添加课程大纲（章节和小节），第三步课程信息确认，最终课程发布
- 添加小节上传课程视频：阿里oss和视频点播



**课程如何判断是否已经被发布了？**

使用status字段



**课程添加过程中，中途把课程停止添加，重新去添加新的课程，如何找到之前没有发布完成课程，继续进行发布？**

到课程列表中根据课程状态查询未发布的课程，点击课程右边超链接把课程继续发布完成！





### 统计分析模块

- 生成统计数据：远程调用其他模块接口，实现数据统计进行展示

- 统计数据图表显示：使用echarts显示



## 2、项目前台用户系统功能





### 首页数据显示



- 显示幻灯片功能：根据id排序显示前几条
- 显示热门课程：根据时间或播放量排序显示前几条
- 显示名师：根据时间或播放量排序显示前几条





### 注册功能

- 获取手机验证码：整合了阿里云短信服务



### 登录功能



- **普通登录和退出：使用token实现**



**SSO（单点登录）三种方式：**



![](https://cdn.jsdelivr.net/gh/niuxvdong/pic@a20e1fdd8f13705a86e664a4c94c6f1ada8a3b2f/2021/11/21/fb2f37402a062728ea8450570a0cd7c1.png)



**token生成方式：**



使用JWT生成token字符串！



**JWT有三部分组成：**

![](https://cdn.jsdelivr.net/gh/niuxvdong/pic@db5da1d5138fa553af0aafb0eeae6471bdefaeb6/2021/11/21/f180908652d358f0af9db95496292bc0.png)



**登录实现流程**

登录调用登录接口返回token字符串，把返回token字符串放到cookie里面，创建前端拦截器进行判断，如果cookie里面包含token字符串，把token字符串放到header里面。调用接口根据token获取用户信息，把用户信息放到cookie里面，进行显示！



- **微信扫码登录**



**OAuth2**



- 是针对特定问题**解决方案**
- 主要有两个问题：**开放系统间授权，分布式访问（单点登录）**
- **令牌机制**，按照规则生成字符串，字符串包含用户信息，发送请求时携带字符串！
- JWT是一种针对OAuth2的实现





**如何获取扫描人信息过程？**



扫描之后微信接口返回**code**（**临时票据**），拿着code值请求微信**固定地址**，得到两个值：**access_token（访问凭证）和openid（微信唯一标识）**,你拿着这两个值再去请求微信**固定的地址**，得到微信扫描人信息（比如昵称，头像等等）。





### 名师列表功能

条件查询带分页实现！



### 名师详情功能

显示名师的基本信息和所讲课程！



### 课程列表功能



- 条件查询分页列表功能



### 课程详情页



- 课程信息显示（包含课程基本信息，分类，讲师，课程大纲）
- 判断课程是否需要购买



### 课程视频在线播放



整合阿里云视频点播和阿里云视频播放器做到！





### 课程支付功能（微信支付）



1. 生成课程订单
2. 生成微信支付二维码
3. 微信最终支付



**微信支付实现流程：**

- 如果课程是收费课程，点击立即购买，生成课程订单
- 点击订单页面去支付，生成微信支付二维码
- 使用微信扫描支付二维码实现支付
- 支付之后，每隔3秒查询支付状态（是否支付成功），如果没有支付成功等待，如果支付成功之后，更新订单状态（已经支付状态），向支付记录表添加支付成功记录





# 二、在线教育项目技术点





## 1、前端技术点



> **在线教育项目采用前后端分离开发**



### vue

- 基本语法
- 常见指令 ： v-bind v-model v-if v-for v-html
- 绑定事件： v-on-click  @click
- 生命周期：created() 页面渲染之前   mounted()页面渲染之后



### ES6规范



箭头函数等等！



### Element-ui







### nodejs

是JavaScript运行环境，不需要浏览器直接运行js代码，模拟服务器效果！



### NPM



包管理工具，类似于Maven

npm命令： npm init   npm install 依赖名称



### Babel

转码器，可以把ES6代码转换成ES5代码



### 前端模块化

- 通过一个页面或者一个js文件，调用另外一个js文件里面的方法
- 问题：ES6的模块化无法在Node.js中执行，需要用Babel编辑成ES5后再执行



### vue-admin-template

- 后台系统使用 **vue-admin-template**
- 基于vue+Element-ui



### Nuxt

- 前台系统使用 **Nuxt**
- 基于vue
- **服务器渲染技术 利于SEO**



### Echarts

图表工具





## 2、后端技术点





> **项目采用微服务架构，每个模块都可以独立部署，独立运行！**



### SpringBoot



- SpringBoot本质是就是Spring，只是快速构建Spring工程脚手架

- SpringBoot配置文件
    - 配置文件类型：properties和yml
    - 配置文件加载顺序：bootstrap   application  application-dev



### SpringCloud



- 是很多框架总称，使用这些框架实现微服务架构，基于SpringBoot实现
- 组成框架有哪些？



![](https://cdn.jsdelivr.net/gh/niuxvdong/pic@c216c5f654221eaaff1a4a9f3a0907c5a041186a/2021/11/21/dd49b40d121bce562375d51bf78387f0.png)



- 项目中，使用阿里巴巴Nacos，替代SpringCloud一些组件



- Nacos
    - 使用Nacos作为注册中心
    - 使用Nacos作为配置中心



- Feign

服务调用，一个微服务调用另外一个微服务，实现远程调用



- Hystrix熔断器



容错处理！



- Gateway网关

SpringCloud之前zuul网关，目前Gateway网关！

可以统一解决跨域问题！







### MyBatisPlus



1. MyBatisPlus就是对MyBatis做增强
2. 自动填充，自动填充时间 @TableField
3. 乐观锁，version字段 @Version
4. 逻辑删除 @TableLogic
5. 代码生成器
6. 分布式id生成



**分布式id生成策略：[https://www.cnblogs.com/haoxinyue/p/5208136.html](https://www.cnblogs.com/haoxinyue/p/5208136.html)**



### EasyExcel



- 阿里巴巴提供操作excel工具，代码简洁，效率很高
- EasyExcel对poi进行封装，采用SAX方式解析（一行行解析）
- 项目应用在**添加课程分类，读取excel数据**



### SpringSecurity



1. 在项目整合框架实现权限管理功能
2. SpringSecurity框架组成：**认证和授权**
3. SpringSecurity登录认证过程



![](https://cdn.jsdelivr.net/gh/niuxvdong/pic@c3d735a9b95a18485040384d4164aab5b8961cf1/2021/11/21/278458a75aafac3387fc6108b8e6c25b.png)



- SpringSecurity代码执行过程



![](https://cdn.jsdelivr.net/gh/niuxvdong/pic@bef3afb29de0f77f7e78a414caf5cec478128211/2021/11/21/2de900adfd404eb419b980373a4a885e.png)







### Redis



- 首页数据通过Redis进行缓存
- Redis数据类型，string、set、list、hash、zset
- 使用Redis作为缓存，不太重要或者不经常改变数据适合放到Redis作为缓存



### Nginx

- 反向代理服务器
- 请求转发，负载均衡，动静分离



### OAuth2+JWT

- OAuth2针对特定问题解决方案
- JWT包含三部分（见功能点详解）



### HttpClient

- 发送请求返回响应的工具，不需要浏览器完成请求和响应的过程
- **应用场景**：微信登录获取扫描人信息，微信支付查询支付状态



### Cookie

**Cookie特点：**

- 客户端技术
- 每次发送请求带着cookie值进行发送
- cookie有默认会话级别，关闭浏览器cookie默认不存在了，
- 但是可以设置cookie有效时长 setMaxAge



### 微信登录

见功能点详解

### 微信支付

见功能点详解



### 阿里云OSS

- 文件存储服务器
- 添加讲师时候上传讲师头像



### 阿里云视频点播

- 视频上传、删除、播放
- 整合阿里云视频播放器进行视频播放
- 使用视频播放凭证



### 阿里云短信服务

- 注册时候，发送手机验证码



### Git

- 代码提交到远程Git仓库



### Docker+Jenkins

- 手动打包运行
- idea打包
- jenkins自动化部署过程







# 三、在线教育项目问题总结





## 1、前端问题-路由切换问题



- 多次路由跳转到同一个vue页面，页面中created方法只会执行一次
- 解决方案：使用vue监听watch



## 2、前端问题-ES6模块化运行问题



Nodejs不能直接运行ES6模块化代码，需要使用Babel把ES6模块化代码转换ES5代码执行



## 3、mp生成19位id值



mp生成id值是19位，JavaScript处理数字类型值时候，只会处理到16位



`@TableId(value = "id", type = IdType.ID_WORKER_STR)`



## 4、跨域问题



- 访问协议，ip地址，端口号，这三个如果有任何一个不一样，产生跨域
- 跨域解决：
    - 在Controller添加注解 @CrossOrigin
    - 通过网关解决



**网关统一解决跨域：**



```java
@Configuration
public class CorsConfig {
    // 网关跨域和controller 只能有一个，因此将此处注掉！
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
```



## 5、413问题



- 上传视频时候，因为Nginx有上传文件大小限制，如果超过Nginx大小，出现413
- 413错误：请求体过大
- 在Nginx配置客户端大小，http块中配置 `client_max_body_size 1024m;`
- 响应状态码：413 403（跨域）302（请求重定向）



## 6、Maven加载问题



- maven加载项目时候，默认不会加载src-java文件夹里面xml类型文件的
- 解决方案：
    - 直接复制xml文件到target目录
    - 通过配置实现



**application.properties文件**



```properties
#配置mapper xml文件的路径
mybatis-plus.mapper-locations=classpath:com/itnxd/eduservice/mapper/xml/*.xml
```



**pom.xml文件**



```xml
<!-- 项目打包时会将java目录中的*.xml文件也进行打包 -->
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```





# 四、项目面试总结





## 1、项目描述





![](https://cdn.jsdelivr.net/gh/niuxvdong/pic@aae1bb0f2a472dac9161e348d99dd20732eb76b3/2021/11/21/1a44500c57b823e56d89dd416c12819b.png)

在线教育系统，分为**前台网站系统和后台运营平台，B2C模式。**

前台用户系统包括课程、讲师、问答、文章几大大部分，使用了**微服务技术架构，前后端分离开发**。

**后端的主要技术架构是**：SpringBoot + SpringCloud + MyBatis-Plus + HttpClient + MySQL +Maven+EasyExcel+ nginx

**前端的架构是**：Node.js + Vue.js +element-ui+NUXT+ECharts

**其他**涉及到的中间件包括Redis、阿里云OSS、阿里云视频点播业务中使用了ECharts做图表展示，使用EasyExcel完成分类批量添加、注册分布式单点登录使用了JWT.



项目前后端分离开发，后端采用SpringCloud微服务架构，持久层用的是MyBatis-Plus，微服务分库设计，使用Swagger生成接口文档，接入了阿里云视频点播、阿里云OSS。系统分为前台用户系统和后台管理系统两部分。

前台用户系统包括：首页、课程、名师、问答、文章。

后台管理系统包括：讲师管理、课程分类管理、课程管理、统计分析、Banner管理、订单管理、权限管理等功能。





## 2、这是一个项目还是一个产品



**这是一个产品**

- 1.0版本是单体应用：SSM面试总结
- 2.0版本加入了SpringCloud，将一些关键业务和访问量比较大的部分分离了出去
- 目前独立出来的服务有教学服务、视频点播服务、用户服务、统计分析服务、网关服务



## 3、系统中都有那些角色？数据库是怎么设计的？



- 前台：会员（学员）
- 后台：系统管理员、运营人员
- 后台分库，每个微服务一个独立的数据库，使用了分布式id生成器



**分布式id生成策略：[https://www.cnblogs.com/haoxinyue/p/5208136.html](https://www.cnblogs.com/haoxinyue/p/5208136.html)**





## 4、视频点播是怎么实现的（流媒体你们是怎么实现的）



- 我们直接接入了阿里云的云视频点播。云平台上的功能包括视频上传、转码、加密、智能审核、监控统计等。
- 还包括视频播放功能，阿里云还提供了一个视频播放器。



## 5、前后端联调经常遇到的问题





- 请求方式post、get
- json、x-wwww-form-urlencoded混乱的错误
- 后台必要的参数，前端省略了
- 数据类型不匹配
- 空指针异常
- 分布式系统中分布式id生成器生成的id 长度过大（19个字符长度的整数），js无法解析（js智能解析16个长度：2的53次幂）
- id策略改成 ID_WORKER_STR



## 6、前后端分离项目中的跨域问题是如何解决的



- 后端服务器配置：我们的项目中是通过Spring注解解决跨域的 @CrossOrigin
- 也可以使用nginx反向代理、httpClient、网关





## 7、说说你做了哪个部分、遇到了什么问题、怎么解决的






分布式id生成器在前端无法处理，总是在后三位进行四舍五入。

分布式id生成器生成的id是19个字符的长度，前端javascript脚本对整数的处理能力只有2的53次方，也就是最多只能处理16个字符

解决的方案是把id在程序中设置成了字符串的性质



**了解：**

项目迁移到Spring-Cloud的时候，经过网关时，前端传递的cookie后端一只获取不了，看了cloud中**zuul**的源码，发现向下游传递数据的时候，zull默认过滤了敏感信息，将cookie过滤掉了。解决的方案是在配置文件中将请求头的过滤清除掉，使cookie可以向下游传递。







## 8、CAP理论



之前的笔记！

- [https://www.itnxd.cn/posts/34316.html#4%E3%80%81CAP%E7%90%86%E8%AE%BA](https://www.itnxd.cn/posts/29680.html#13%E3%80%81%E4%B8%89%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83%E5%BC%82%E5%90%8C%E7%82%B9)
- [https://www.itnxd.cn/posts/29680.html#13%E3%80%81%E4%B8%89%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83%E5%BC%82%E5%90%8C%E7%82%B9](https://www.itnxd.cn/posts/29680.html#13%E3%80%81%E4%B8%89%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83%E5%BC%82%E5%90%8C%E7%82%B9)



## 9、前端渲染和后端渲染有什么区别



- 前端渲染是返回json给前端，通过javascript将数据绑定到页面上
- 后端渲染是在服务器端将页面生成直接发送给服务器，有利于SEO的优化





## 10、能画一下系统架构图吗





![](https://cdn.jsdelivr.net/gh/niuxvdong/pic@6e163fdff63a060088bb64469fca095c4adc21fc/2021/11/21/cc495d45bd2cec0a1511244cd0181b80.png)







# 五、常用的工具类



## JwtUtils



```java
package com.itnxd.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author helen
 * @since 2019/10/16
 */
public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("nickname", nickname)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
```





## MD5



```java
package com.itnxd.commonutils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class MD5 {

    public static String encrypt(String strSrc) {
        try {
            char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5.encrypt("111111"));
    }

}
```



## 统一结果集R



```java
package com.itnxd.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果类！
 *
 * @author ITNXD
 * @create 2021-11-02 21:11
 */
@Data
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    // 私有构造：不能new
    private R() {
    }

    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
```



## RandomUtil



```java
package com.itnxd.commonutils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 获取随机数
 * 
 * @author qianyi
 *
 */
public class RandomUtil {

   private static final Random random = new Random();

   private static final DecimalFormat fourdf = new DecimalFormat("0000");

   private static final DecimalFormat sixdf = new DecimalFormat("000000");

   public static String getFourBitRandom() {
      return fourdf.format(random.nextInt(10000));
   }

   public static String getSixBitRandom() {
      return sixdf.format(random.nextInt(1000000));
   }

   /**
    * 给定数组，抽取n个数据
    * @param list
    * @param n
    * @return
    */
   public static ArrayList getRandom(List list, int n) {

      Random random = new Random();

      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

      // 生成随机数字并存入HashMap
      for (int i = 0; i < list.size(); i++) {

         int number = random.nextInt(100) + 1;

         hashMap.put(number, i);
      }

      // 从HashMap导入数组
      Object[] robjs = hashMap.values().toArray();

      ArrayList r = new ArrayList();

      // 遍历数组并打印数据
      for (int i = 0; i < n; i++) {
         r.add(list.get((int) robjs[i]));
         System.out.print(list.get((int) robjs[i]) + "\t");
      }
      System.out.print("\n");
      return r;
   }
}
```





## SwaggerConfig



```java
package com.itnxd.servicebase;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ITNXD
 * @create 2021-11-02 20:34
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                //.paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("网站-课程中心API文档")
                .description("本文档描述了课程中心微服务接口定义")
                .version("1.0")
                .contact(new Contact("ITNXD", "https://www.itnxd.cn/",
                        "158903258@qq.com"))
                .build();
    }
}
```





## HttpClient



```java
package com.itnxd.eduorder.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * http请求客户端
 * 
 * @author qy
 * 
 */
public class HttpClient {
   private String url;
   private Map<String, String> param;
   private int statusCode;
   private String content;
   private String xmlParam;
   private boolean isHttps;

   public boolean isHttps() {
      return isHttps;
   }

   public void setHttps(boolean isHttps) {
      this.isHttps = isHttps;
   }

   public String getXmlParam() {
      return xmlParam;
   }

   public void setXmlParam(String xmlParam) {
      this.xmlParam = xmlParam;
   }

   public HttpClient(String url, Map<String, String> param) {
      this.url = url;
      this.param = param;
   }

   public HttpClient(String url) {
      this.url = url;
   }

   public void setParameter(Map<String, String> map) {
      param = map;
   }

   public void addParameter(String key, String value) {
      if (param == null)
         param = new HashMap<String, String>();
      param.put(key, value);
   }

   public void post() throws ClientProtocolException, IOException {
      HttpPost http = new HttpPost(url);
      setEntity(http);
      execute(http);
   }

   public void put() throws ClientProtocolException, IOException {
      HttpPut http = new HttpPut(url);
      setEntity(http);
      execute(http);
   }

   public void get() throws ClientProtocolException, IOException {
      if (param != null) {
         StringBuilder url = new StringBuilder(this.url);
         boolean isFirst = true;
         for (String key : param.keySet()) {
            if (isFirst)
               url.append("?");
            else
               url.append("&");
            url.append(key).append("=").append(param.get(key));
         }
         this.url = url.toString();
      }
      HttpGet http = new HttpGet(url);
      execute(http);
   }

   /**
    * set http post,put param
    */
   private void setEntity(HttpEntityEnclosingRequestBase http) {
      if (param != null) {
         List<NameValuePair> nvps = new LinkedList<NameValuePair>();
         for (String key : param.keySet())
            nvps.add(new BasicNameValuePair(key, param.get(key))); // 参数
         http.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8)); // 设置参数
      }
      if (xmlParam != null) {
         http.setEntity(new StringEntity(xmlParam, Consts.UTF_8));
      }
   }

   private void execute(HttpUriRequest http) throws ClientProtocolException,
         IOException {
      CloseableHttpClient httpClient = null;
      try {
         if (isHttps) {
            SSLContext sslContext = new SSLContextBuilder()
                  .loadTrustMaterial(null, new TrustStrategy() {
                     // 信任所有
                     public boolean isTrusted(X509Certificate[] chain,
                           String authType)
                           throws CertificateException {
                        return true;
                     }
                  }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                  sslContext);
            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                  .build();
         } else {
            httpClient = HttpClients.createDefault();
         }
         CloseableHttpResponse response = httpClient.execute(http);
         try {
            if (response != null) {
               if (response.getStatusLine() != null)
                  statusCode = response.getStatusLine().getStatusCode();
               HttpEntity entity = response.getEntity();
               // 响应内容
               content = EntityUtils.toString(entity, Consts.UTF_8);
            }
         } finally {
            response.close();
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         httpClient.close();
      }
   }

   public int getStatusCode() {
      return statusCode;
   }

   public String getContent() throws ParseException, IOException {
      return content;
   }

}
```