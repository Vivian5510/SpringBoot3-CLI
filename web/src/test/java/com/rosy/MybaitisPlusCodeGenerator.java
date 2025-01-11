package com.rosy;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.List;

public class MybaitisPlusCodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/example", "root", "101673")
                .globalConfig(builder -> builder
                        .author("Rosy")
                        .outputDir("G:\\Material\\Codes\\springboot3.4.x-init\\main\\src\\main\\java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.rosy.main")
                        .entity("domain.entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .logicDeleteColumnName("isDeleted")
                        .versionColumnName("version")
                        .addTableFills(List.of(
                                new Column("create_time", FieldFill.INSERT),
                                new Column("create_by", FieldFill.INSERT),
                                new Column("update_time", FieldFill.INSERT_UPDATE),
                                new Column("update_by", FieldFill.INSERT_UPDATE)
                        ))
                        .controllerBuilder()
                        .enableRestStyle()
                )
                .strategyConfig(builder -> builder
                        .addInclude("user")
                        .addTablePrefix("")
                        .entityBuilder()
                        .enableLombok()
                )
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }
}
