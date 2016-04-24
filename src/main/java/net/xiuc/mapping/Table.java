package net.xiuc.mapping;

import java.util.List;

/**
 * 数据库对应的表
 * Created by 秀川 on 16/4/24.
 */
public class Table {

    /**
     * 表名称
     */
    private String name;

    /**
     * 数据库表对应的字段列表
     */
    private List<String> columnList;

    public Table(String name){
        this.name = name;
    }
}
