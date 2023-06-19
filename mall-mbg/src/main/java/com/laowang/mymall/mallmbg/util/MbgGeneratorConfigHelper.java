package com.laowang.mymall.mallmbg.util;

import com.laowang.mymall.mallmbg.Generator;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

/**
 * @program: my-mall
 * @description: 修改generator配置文件的工具类
 * @author: Laowang
 * @create: 2023-06-16 10:19
 */
public class MbgGeneratorConfigHelper {

    public static void modifyTableNameInGeneratorConfig(String tableName) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Generator.class.getResourceAsStream("/generatorConfig.xml"))));
        BufferedWriter bufferedWriter = null;
        try {
            File newFile = new File("mall-mbg/src/main/resources/generatorConfigTmp.xml");
            if (newFile.exists()) {
                boolean delete = newFile.delete();
                if (delete) {
                    System.out.println("删除旧的临时配置文件");
                }
            } else {
                boolean newFile1 = newFile.createNewFile();
                if (newFile1) {
                    System.out.println("创建文件成功");
                }
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(newFile.toPath())));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("tableName")) {
                    String oldName = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                    line = line.replace(oldName, tableName);
                }
                System.out.println(line);
                bufferedWriter.write(line + "\n");
                bufferedWriter.flush();
            }
            bufferedWriter.close();
            System.out.println("创建临时配置文件完毕");
        } catch (Exception e) {
            System.err.println("咋个错了？" + e.getLocalizedMessage());
            System.err.println(e.getMessage());
        } finally {
            try {
                bufferedReader.close();
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("为啥报错？" + e.getMessage());
            }
        }
    }

    public static String switchTableOriginNameToCamel(String tableName) {
        String[] ss = tableName.split("_");
        StringBuffer sb = new StringBuffer();
        for (String s : ss) {
            sb.append(s.replaceFirst(s.substring(0, 1), s.substring(0, 1).toUpperCase()));
        }
        return sb.toString();
    }

    public static void importApiModelPropertyToMapperInterface(String newTableName) {
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            File originFile = new File("mall-mbg/src/main/java/com/laowang/mymall/mallmbg/model/" + newTableName + ".java");
            br = new BufferedReader(new FileReader(originFile));
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
                if (line.contains("package com")){
                    sb.append("\n").append("import io.swagger.annotations.ApiModelProperty;").append("\n");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // 之所以用FileWriter这个类是因为它可以覆盖掉原文件
        FileWriter fw = null;
        File originFile2 = new File("mall-mbg/src/main/java/com/laowang/mymall/mallmbg/model/" + newTableName + ".java");
        try {
            fw = new FileWriter(originFile2);
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void importAndAddAnnotationMapperToMapperInterface(String camelName){
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            File originFile = new File("mall-mbg/src/main/java/com/laowang/mymall/mallmbg/mapper/" + camelName + "Mapper.java");
            br = new BufferedReader(new FileReader(originFile));
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.contains("public interface")){
                    sb.append("\n").append("@Mapper").append("\n");
                }
                sb.append(line).append("\n");
                if (line.contains("package com")){
                    sb.append("\n").append("import org.apache.ibatis.annotations.Mapper;").append("\n");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // 之所以用FileWriter这个类是因为它可以覆盖掉原文件
        FileWriter fw = null;
        File originFile2 = new File("mall-mbg/src/main/java/com/laowang/mymall/mallmbg/mapper/" + camelName + "Mapper.java");
        try {
            fw = new FileWriter(originFile2);
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        // 给所有mapper接口添加 类注解 @Mapper
        List<String> originTableNames = MymallDBHelper.getAllTableNamesFromMymall();
        for (String originTableName : originTableNames) {
            String camelName = switchTableOriginNameToCamel(originTableName);
            importAndAddAnnotationMapperToMapperInterface(camelName);
        }
    }
}
