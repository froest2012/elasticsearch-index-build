package net.xiuc.mapping;

import java.util.List;

/**
 * 数据库对应的schema
 *
 * Created by 秀川 on 16/4/24.
 */
public class Schema {

    /**
     * schema对应的名称
     */
    private String name;

    /**
     * 数据库对应的表集合
     */
    private List<Table> tableList;

    public Schema(String name){
        this.name = name;
    }

}
