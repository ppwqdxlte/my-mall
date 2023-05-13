# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.11/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.11/maven-plugin/reference/html/#build-image)

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
## <span style="color: pink;">业务篇</span>
## <span style="color: pink;">技术要点篇</span>
## <span style="color: pink;">部署篇</span>
## <span style="color: pink;">参考篇</span>