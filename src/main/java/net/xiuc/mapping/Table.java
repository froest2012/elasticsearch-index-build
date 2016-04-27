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

    private Table(String name, List<String> columnList){
        this.name = name;
        this.columnList = columnList;
    }

    public static TableBuilder buildTable(String tableName){
        return new TableBuilder(tableName);
    }

    public static class TableBuilder {

        private String name;

        private List<String> columList;

        public TableBuilder(String tableName){
            this.name = tableName;
        }

        public TableBuilder add(List<String> columList){
            this.columList.addAll(columList);
            return this;
        }

        public TableBuilder add(String column){
            this.columList.add(column);
            return this;
        }

        public TableBuilder add(String... columns){
            for(String column : columns){
                this.columList.add(column);
            }
            return this;
        }

        public Table create(){
            return new Table(this.name, this.columList);
        }
    }
}
