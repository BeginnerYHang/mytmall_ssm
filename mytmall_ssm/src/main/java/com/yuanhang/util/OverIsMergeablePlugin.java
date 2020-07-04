package com.yuanhang.util;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author yuanhang
 * @Description :MybatisGenerator插件是Mybatis官方提供的，这个插件存在一个固有的Bug，即当第一次生成了CategoryMapper.xml之后，再次运行会
 * 导致CategoryMapper.xml生成重复内容，而影响正常的运行。该插件用于解决此问题
 * @date 2020-05-31 10:14
 */
public class OverIsMergeablePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
