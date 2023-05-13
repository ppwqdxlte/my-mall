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
## <span style="color: pink;">业务篇</span>
## <span style="color: pink;">技术要点篇</span>
## <span style="color: pink;">部署篇</span>
## <span style="color: pink;">参考篇</span>