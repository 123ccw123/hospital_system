package com.ccw.generator;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MyBatisPlusGenerator {
    public static void main(String[] args) {
// 代码生成器
            AutoGenerator mpg = new AutoGenerator();
// 全局配置
            GlobalConfig gc = new GlobalConfig();
//String projectPath = System.getProperty("user.dir");
//gc.setOutputDir(projectPath + "/src/main/java");
            gc.setOutputDir("生成的代码");
            gc.setAuthor("zs");
            gc.setOpen(false);
            gc.setFileOverride(false);//重新生成文件时是否覆盖
            gc.setServiceName("%sService");
//实体属性 Swagger2 注解
//gc.setSwagger2(false);
            mpg.setGlobalConfig(gc);
// 数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
//dsc.setUrl("jdbc:mysql://127.0.0.1:3306/demo?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true");
            dsc.setUrl("jdbc:mysql:///offcnpe");
            dsc.setDriverName("com.mysql.jdbc.Driver");
            dsc.setUsername("root");
            dsc.setPassword("root");
            dsc.setDbType(DbType.MYSQL);
            mpg.setDataSource(dsc);
// 包配置
            PackageConfig pc = new PackageConfig();
            pc.setModuleName(null);
            pc.setParent("com.ccw");
            pc.setController("controller");
            pc.setEntity("entity");
            pc.setService("service");
            pc.setServiceImpl("service.impl");
            pc.setMapper("mapper");
            mpg.setPackageInfo(pc);
            // 配置模板
/* TemplateConfig templateConfig = new TemplateConfig();
templateConfig.setXml(null);
mpg.setTemplate(templateConfig);*/
// 策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setNaming(NamingStrategy.underline_to_camel);
            strategy.setColumnNaming(NamingStrategy.underline_to_camel);
            strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
                    strategy.setEntityLombokModel(true);
            strategy.setRestControllerStyle(true);
            strategy.setEntityLombokModel(true);
            String
                    tableNames="t_checkgroup,t_checkgroup_checkitem,t_checkitem,t_member,t_menu," +
                    "t_order,t_ordersetting," +
                    "t_permission,t_role,t_role_menu," +
                    "t_role_permission,t_setmeal,t_setmeal_checkgroup,t_user,t_user_role";
            strategy.setInclude(tableNames.split(","));
            strategy.setControllerMappingHyphenStyle(true);
            strategy.setTablePrefix("t_");
            mpg.setStrategy(strategy);
            mpg.execute();
        }

    }
