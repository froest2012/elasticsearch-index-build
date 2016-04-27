package net.xiuc.mapping;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

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
     * 如果这个table是主索引,那么这个key不为空,否则这个为空,和foreign值相反
     */
    private String key;

    /**
     * 外键-对应主索引的主键
     * 如果这个table是从索引,那么这个foreign部位空,否则这个为空,和key值相反
     */
    private String foreign;

    /**
     * 数据库表对应的字段列表
     */
    private List<String> columnList = Lists.newArrayList();

    private Table(String name, String key, String foreign, List<String> columnList){
        this.name = name;
        this.key = key;
        this.foreign = foreign;
        this.columnList = columnList;
    }

    public static TableBuilder buildTable(String tableName){
        return new TableBuilder(tableName);
    }

    public static class TableBuilder {

        private String name;

        private String key;

        private String foreign;

        private List<String> columList = Lists.newArrayList();

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

        public void setForeign(String foreign) {
            if(StringUtils.isEmpty(this.foreign)) {
                this.foreign = foreign;
            }
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Table create(){
            return new Table(this.name, this.key, this.foreign, this.columList);
        }
    }
}
