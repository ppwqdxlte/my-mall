### 账号密码：
用mall-tiny.sql创建的表用户密码为 admin/123456
用mall.sql创建的表，超级管理员用户密码可用 admin/micro123 test/123456
# <div style="color: purple;text-align: center;">大纲</div>
## <span style="color: pink;">架构篇</span>
1. 整合SpringBoot+MyBatis搭建基本骨架
   + VMware中运行mysql801容器，创建Mysql schema ‘tlmallswarmmymall’,执行sql脚本生成各表
   + 用Spring.initializer.io不带任何依赖生生成空的spring-boot项目，然后在pom.xml中添加各种依赖，一个个手动添加，简单复制粘贴容易依赖不全
   + 添加MyBatisConfig.java配置，修改application.xml添加基本配置，启动空项目，没报错说明数据库正常连接
   + 添加Generator,CommentGenerator,修改generator.properties，generatorConfig.xml配置，运行Generator根据数据库单表生成mapper接口，实体模型以及xml文件
   + 手动补充Service,ServiceImpl以及Controller,哦对了，在ServiceImpl里调用生成的Mapper接口，启动工程用ApiFox测试接口连通性和可用性
2. 整合Swagger-UI实现在线API文档
   + 添加springfox依赖，添加SwaggerConfig配置类，添加BeanPostProcessorConfig配置类解决注入缺失的问题
   + 启动项目访问http://localhost:8581/swagger-ui/index.html查看Swagger文档，显示出来就说明配置没啥问题了
   + 给Controller添加文档注解
   + 修改MyBatis Generator注释的生成规则：CommentGenerator为MyBatis Generator的自定义注释生成器，修改addFieldComment方法使其生成Swagger的@ApiModelProperty注解来取代原来的方法注释，添加addJavaFileComment方法，使其能在import中导入@ApiModelProperty，否则需要手动导入该类，在需要生成大量实体类时，是一件非常麻烦的事。
   + 重新运行Generator，会发现实体类的属性添加了@ApiModelProperty的注解，去xml删除重复生成的代码，启动项目查看swagger文档，发现实体类的属性终于有注释了！！！
3. 整合Redis实现缓存功能
   + 虚拟机启动redis容器（之前安装过）
   + pom添加redis依赖，spring节点添加redis配置，根节点添加自定义key配置
   + 添加RedisService接口：定义常用Redis操作；然后实现RedisServiceImpl
   + 【模拟】电话号码获取验证码和校验验证码(目前尚未实现 运营商发送短信的接口 和 会员注册MySQL存储的功能)，仅用Redis模拟操作
     + 添加UmsMember的Controller、Service、ServiceImpl
     + 打开RedisInsight软件查看Redis服务器数据情况，测试上述接口，并查看数据是否更新
4. 整合SpringSecurity和JWT实现认证和授权
   + 引入依赖：SpringSecurity(Spring安全标准认证授权框架),JWT(JSON-Web-Token数字签名的安全传输TOKEN),Hutool一套开源丰富工具包
   + 添加用于生成和解析JWT token的工具类JwtTokenUtil
   + 用Generator生成UmsAdmin,UmsPermission等若干相关代码
   + 添加UmsAdminService + Impl，添加UmsAdmin-Role关系的dao接口 + xml
   + 添加必要组件tokenFilter，entryPoint，accessDeniedHandler 以及 dto/AdminUserDetails
   + 添加SpringSecurity的配置类SecurityConfig
   + 添加请求参数类UmsAdminLoginParam以及添加控制器UmsAdminController
   + 修改Swagger的配置：实现调用接口自带Authorization头，略
   + 给PmsBrandController接口中的方法添加访问权限
   + 然后启动项目验证登录注册：test用户没有brand模块的权限，admin有，可以分别登录验证一下，登录后把Bearer +token串贴到Authorize按钮,略
5. 整合SpringTask实现定时任务 （以批量修改超时订单为例）比Quartz更简单方便
   + 认识Cron表达式：包含6~7个时间元素，在SpringTask中可用于指定任务的执行时间
   + 添加SpringTaskConfig定时任务配置
   + 添加OrderTimeOutCancelTask执行定时任务（什么样的任务取决于业务需求）
6. 整合Elasticsearch实现商品搜索
   + vmware添加Elasticsearch镜像并启动docker容器，验证
   + 添加中文分词器插件，windows或docker安装kibana，修改kibana.yml添加可访问的ES-url，浏览器访问kibana.略
   + 引入SpringDataElasticsearch依赖，它是Spring提供的一种以Spring Data风格来操作数据存储的方式，它可以避免编写大量的样板代码
   + 添加ElasticsearchConfig配置类(因为yml配置已经过时)
   + 添加Domain类（ES中对应文档类）EsProduct(最好和entity,vo,po等区分开不同类文件，毕竟注解都不一样)
   + 添加document/repository/dao.xml/Dao/Service/Controller略
     - 添加Swagger2Config文档的api扫描范围
     - 添加MyBatisConfig的mapper扫描范围
   + 启动验证
7. 整合MongoDB实现文档操作
   + 添加SpringDataMongoDB依赖，以Spring Data的方式操作MongoDB，
   + 【注意】点进依赖可知驱动版本为4.6.1，需要去spring.io官网查询兼容的数据库服务器版本！
     - https://docs.spring.io/spring-data/mongodb/docs/3.4.11/reference/html/#compatibility.matrix 【文档版本3.4.11根据pom文件可知】
     - 然后创建兼容版本的mongodb服务器docker容器！5.0.X都行，mongodb官网只提供5.0.18的社区版安装包，仅凭docker search mongo:5.0.18肯定搜不到，但能直接安装docker pull mongo:5.0.18
   + 去mongodb01容器里访问mongo,创建数据库（mymallportal）和用户（usermymall）【注意】务必先创建数据库，然后use该库，在库里创建user并授权!!!
   + 修改application.yml配置
   + 添加document/repository/service/impl/controller等
      - 添加swagger配置
   + 接口测试
8. 整合RabbitMQ实现延迟消息
   + 业务场景说明：用于解决用户下单以后，订单超时如何取消订单的问题。
     - 用户进行下单操作（会有锁定商品库存、使用优惠券、积分一系列的操作）
     - 生成订单，获取订单的id
     - 获取到设置的订单超时时间（假设设置的为20分钟不支付取消订单）
     - 按订单超时时间发送一个延迟消息给RabbitMQ，让它在订单超时后触发取消订单的操作
     - 如果用户没有支付，进行取消订单操作（释放锁定商品库存、返还优惠券、返回积分一系列操作）。
   + pom添加SpringAMQP的rabbitmq依赖，版本支持到2.7.5；再添加Lombok依赖，版本支持到1.18.24
   + 虚拟机安装RabbitMQ服务器的docker,选用最新版本3.12.0，开放端口号5671
   + application.yml中添加rabbitmq的配置
   + rabbitmq管理页面添加用户usermymall和密码，添加虚拟host(mymall),并给usermymall赋予mymall的权限
   + 启动不报错就说明配置没问题
   + 添加消息队列枚举配置类QueueEnum
     - 添加RabbitMQ的配置类
     - 重启项目，去rabbitMQ管理页面查看 Exchanges && Queues,发现多了刚才配置的交换器和队列
     - 添加延迟消息的发送者CancelOrderSender
     - 添加取消订单消息的接收者CancelOrderReceiver
     - 添加OmsPortalOrderService接口 和 实现类...Impl
     - 添加OmsPortalOrderController控制器，添加swagger配置扫描包
     - 重启项目测试接口
9. 整合OSS实现文件上传(阿里云对象存储服务 Object Storage Service)
   + 收费项目了解一下OSS处理流程就行，不购买了，我个人当然选择用开源免费版的minIO搭建私有云对象存储服务了！
10. 整合minIO实现文件上传(开源的对我最合适)
    + 引入依赖，参考官方文档 https://www.minio.org.cn/download.shtml#/docker
    + 根据pom安装适合版本的 minio 服务器容器;服务器docker pull minio/minio
    + 访问http://192.168.3.128:9091设置用户/访问账户/组/桶，并操作一下
    + 查看官方JavaSDK-minio指导手册https://min.io/docs/minio/linux/developers/java/minio-java.html
    + 配置application.yml
    + 添加minio属性类MinioProperties,配置类，读写策略，工具类，API接口等
    + 启动测试
11. 项目架构模块化组件化(之前项目结构是一个Spring-boot整体，之后逐渐拆分各种子模块)
    * 注意：自打my-mall成了父模块，build构建时无法自动生成resources资源文件，必须手动复制粘贴到目标输出目录
    + 创建mall-demo子模块：运行查看不报错就说明分离方法可行
    + 先分离mall-mbg代码生成
    + 分离出mall-search弹性搜索
## <span style="color: pink;">业务篇</span>
1. mall数据库表结构概览
    + PowerDesigner数据库设计文件
      - 商品管理：https://github.com/macrozheng/mall-learning/blob/master/document/pdm/mall_pms.pdm
      - 订单管理：https://github.com/macrozheng/mall-learning/blob/master/document/pdm/mall_oms.pdm
      - 营销管理：https://github.com/macrozheng/mall-learning/blob/master/document/pdm/mall_sms.pdm
      - 内容管理：https://github.com/macrozheng/mall-learning/blob/master/document/pdm/mall_cms.pdm
      - 用户管理：https://github.com/macrozheng/mall-learning/blob/master/document/pdm/mall_ums.pdm
    + MindMaster功能思维导图
      - 商品管理：https://github.com/macrozheng/mall-learning/blob/master/document/mind/pms.emmx
      - 订单管理：https://github.com/macrozheng/mall-learning/blob/master/document/mind/oms.emmx
      - 营销管理：https://github.com/macrozheng/mall-learning/blob/master/document/mind/sms.emmx
      - 内容管理：https://github.com/macrozheng/mall-learning/blob/master/document/mind/cms.emmx
      - 用户管理：https://github.com/macrozheng/mall-learning/blob/master/document/mind/ums.emmx
2. 商品模块数据库表解析（一）
3. 商品模块数据库表解析（二）
4. 订单模块数据库表解析（一）
5. 订单模块数据库表解析（二）
6. 订单模块数据库表解析（三）
7. 营销模块数据库表解析（一）
8. 营销模块数据库表解析（二）
9. 营销模块数据库表解析（三）
10. 权限控制（菜单/路由(资源)/角色/用户），问题是能不能更细粒度？比如精确到表字段等？
## <span style="color: pink;">技术要点篇</span>
## <span style="color: pink;">部署篇</span>
## <span style="color: pink;">参考篇</span>