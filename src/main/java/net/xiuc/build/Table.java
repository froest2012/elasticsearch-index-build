package net.xiuc.build;

import com.google.common.collect.Maps;
import com.google.common.hash.HashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Iterator;
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
    private final TableBuilder parentBuilder;

    /**
     * 表所在的schema
     */
    private final String schema;

    /**
     * 表名称
     */
    private final String name;

    /**
     * 如果这个table是主索引,那么这个key不为空,否则这个为空,和foreign值相反
     */
    private final String primary;

    /**
     * 外键-对应主索引的主键
     * 如果这个table是从索引,那么这个foreign部位空,否则这个为空,和key值相反
     */
    private final String foreign;

    /**
     * 表示这个表的哪些数据是有效的,用于where语句后面做条件
     */
    private final String valid;

    /**
     * key:数据库对应的字段
     * value:索引中对应的字段的驼峰形式
     */
    private Map<String, String> columnMap = Maps.newHashMap();


    private Table(String schema, String name, String primary, String foreign, String valid, Map<String, String> columnMap, TableBuilder parentBuilder) {
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

    public static TableBuilder buildTableBuilder(String schema, String tableName) {
        return new TableBuilder(schema, tableName);
    }

    /**
     * 通过主键的id,批量获取当前table查询数据的sql
     * @param primaryList   父级table的{@link Table#primary}列表
     * @param start         数据库分页开始位置
     * @param limit         数据库分页获取的条数
     * @return
     */
    public String sqlBuild(List<Number> primaryList, Integer start, Integer limit) {
        String condition = buildSelectColumn();
        if (condition == null) return null;
        StringBuilder sb = new StringBuilder("select ");
        sb.append(condition);
        sb.append(" from ").append(this.name);
        String where = buildConditon(primaryList);
        if(where != null){
            sb.append(where);
        }
        if (start != null && start >= 0) {
            sb.append(" limit ").append(start);
        }
        if (limit != null && limit > 0) {
            sb.append(" , ").append(limit);
        }
        return sb.toString();
    }

    private String buildConditon(List<Number> primaryList) {
        if (this.parentBuilder == null ||
                primaryList == null ||
                primaryList.size() <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(" where ");
        if(!StringUtils.isEmpty(this.valid)){
            sb.append(this.valid).append(" and ");
        }
        sb.append(this.foreign).append(" in (");
        Iterator<Number> it = primaryList.iterator();
        while (it.hasNext()) {
            Number id = it.next();
            sb.append(Long.valueOf(id.toString())).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    private String buildSelectColumn() {
        if (this.columnMap.size() <= 0) return null;
        StringBuilder sb = new StringBuilder();
        for (String column : columnMap.keySet()) {
            sb.append(column).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" ");
        return sb.toString();
    }

    public boolean equals(TableBuilder other){
        return EqualsBuilder.reflectionEquals(this, other);
    }

    public int hashcode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public static class TableBuilder {

        private TableBuilder parentBuilder;

        private String schema;

        private String name;

        private String primary;

        private String foreign;

        private String valid;

        private Map<String, String> columnMap = Maps.newHashMap();

        public TableBuilder(String schema, String tableName) {
            this.schema = schema;
            this.name = tableName;
        }

        public TableBuilder add(Map<String, String> columnMap) {
            this.columnMap.putAll(columnMap);
            return this;
        }

        public TableBuilder add(String dbColumn, String indexColumn) {
            this.columnMap.put(dbColumn, indexColumn);
            return this;
        }

        public void setForeign(String foreign) {
            if (StringUtils.isEmpty(this.foreign)) {
                this.foreign = foreign;
            }
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public void setValid(String valid) {
            if (StringUtils.isEmpty(this.valid)) {
                this.valid = valid;
            }
        }

        public void setParentBuilder(TableBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        public Table create() {
            return new Table(this.schema, this.name, this.primary, this.foreign, this.valid, this.columnMap, this.parentBuilder);
        }

        public boolean equals(TableBuilder other){
            return EqualsBuilder.reflectionEquals(this, other);
        }

        public int hashcode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }
}
