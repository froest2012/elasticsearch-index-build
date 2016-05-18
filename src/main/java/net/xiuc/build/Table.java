package net.xiuc.build;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 数据库对应的表
 * Created by 秀川 on 16/4/24.
 */
public class Table {

    /**
     * 主键所在table
     */
    private TableBuilder parentBuilder;

    /**
     * 表所在的schema
     */
    private String schema;

    /**
     * 表名称
     */
    private String name;

    /**
     * 如果这个table是主索引,那么这个key不为空,否则这个为空,和foreign值相反
     */
    private String primary;

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
     * key:数据库对应的字段
     * value:索引中对应的字段的驼峰形式
     */
    private Map<String, String> columnMap = Maps.newHashMap();

    private Table(String schema, String name, String primary, String foreign, String valid, Map<String, String> columnMap, TableBuilder parentBuilder){
        this.schema = schema;
        this.name = name;
        this.primary = primary;
        this.foreign = foreign;
        this.valid = valid;
        this.columnMap = columnMap;
        this.parentBuilder = parentBuilder;
    }

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public String getPrimary() {
        return primary;
    }

    public String getForeign() {
        return foreign;
    }

    public String getValid() {
        return valid;
    }

    public TableBuilder getParentBuilder() {
        return parentBuilder;
    }

    public Map<String, String> getColumnMap() {
        return columnMap;
    }

    public static TableBuilder buildTableBuilder(String schema, String tableName){
        return new TableBuilder(schema, tableName);
    }

    public static class TableBuilder {

        private TableBuilder parentBuilder;

        private String schema;

        private String name;

        private String primary;

        private String foreign;

        private String valid;

        private Map<String, String> columnMap = Maps.newHashMap();

        public TableBuilder(String schema, String tableName){
            this.schema = schema;
            this.name = tableName;
        }

        public TableBuilder add(Map<String, String> columnMap){
            this.columnMap.putAll(columnMap);
            return this;
        }

        public TableBuilder add(String dbColumn, String indexColumn){
            this.columnMap.put(dbColumn, indexColumn);
            return this;
        }

        public void setForeign(String foreign) {
            if(StringUtils.isEmpty(this.foreign)) {
                this.foreign = foreign;
            }
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public void setValid(String valid) {
            if(StringUtils.isEmpty(this.valid)) {
                this.valid = valid;
            }
        }

        public void setParentBuilder(TableBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        public Table create(){
            return new Table(this.schema, this.name, this.primary, this.foreign, this.valid, this.columnMap, this.parentBuilder);
        }
    }
}
