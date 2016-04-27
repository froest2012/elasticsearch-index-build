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
     * 表示这个表的哪些数据是有效的,用于where语句后面做条件
     */
    private String valid;

    /**
     * 数据库表对应的字段列表
     */
    private List<String> columnList = Lists.newArrayList();

    private Table(String name, String key, String foreign, String valid, List<String> columnList){
        this.name = name;
        this.key = key;
        this.foreign = foreign;
        this.valid = valid;
        this.columnList = columnList;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getForeign() {
        return foreign;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public static TableBuilder buildTable(String tableName){
        return new TableBuilder(tableName);
    }

    public String getValid() {
        return valid;
    }

    public static class TableBuilder {

        private String name;

        private String key;

        private String foreign;

        private String valid;

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

        public void setValid(String valid) {
            if(StringUtils.isEmpty(this.valid)) {
                this.valid = valid;
            }
        }

        public Table create(){
            return new Table(this.name, this.key, this.foreign, this.valid, this.columList);
        }
    }
}
