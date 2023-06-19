package com.laowang.mymall.mallmbg;

import com.laowang.mymall.mallmbg.util.MbgGeneratorConfigHelper;
import com.laowang.mymall.mallmbg.util.MymallDBHelper;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: my-mall
 * @description: MyBatis代码生成器Generator，代码逻辑改为一次性生成数据库中所有表的代码
 * @author: Laowang
 * @create: 2023-05-11 16:29
 */
public class Generator {
    public static void main(String[] args) throws Exception {
        //MBG 执行过程中的警告信息
        List<String> warnings = new ArrayList<String>();
        //当生成的代码重复时，覆盖原代码
        boolean overwrite = true;
        //读取mymall数据库全部的表名
        List<String> tableNames = MymallDBHelper.getAllTableNamesFromMymall();
        // for循环修改配置文件替换对应的表名
        for (String tableName : tableNames) {
            System.out.println(tableName);
            // 修改配置文件里要生成的表名
            MbgGeneratorConfigHelper.modifyTableNameInGeneratorConfig(tableName);
            // 读取MBG配置文件
            InputStream is = Files.newInputStream(new File("mall-mbg/src/main/resources/generatorConfigTmp.xml").toPath());
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);
            is.close();
            // 回调函数
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            //创建 MBG
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            //执行生成代码
            myBatisGenerator.generate(null);
            //输出警告信息
            for (String warning : warnings) {
                System.out.println(warning);
            }
//            break;
        }
        for (String tableName : tableNames) {
            // 修改mapper接口，添加import io.swagger.annotations.ApiModelProperty;
            String camelTableName = MbgGeneratorConfigHelper.switchTableOriginNameToCamel(tableName);
            System.out.println(camelTableName);
            MbgGeneratorConfigHelper.importApiModelPropertyToMapperInterface(camelTableName);
            MbgGeneratorConfigHelper.importAndAddAnnotationMapperToMapperInterface(camelTableName);
//            break;
        }
    }
}
