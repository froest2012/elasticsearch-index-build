package net.xiuc.mapping;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.xiuc.annotation.FieldAttr;
import net.xiuc.annotation.TableAttr;
import net.xiuc.util.Tools;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.Strings;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析bean对象,转换成对应的sql
 * Created by 秀川 on 16/4/26.
 */
public class DbTranslate implements Translate {

    private Schema schema = new Schema();

    private Map<String, Table> tableMap = Maps.newHashMap();//key=schema.table,value=tableBuilder

    private Map<String, String> sqlMap = Maps.newHashMap();

    private static final Tools tools = Tools.INSTANCE;
    /**
     * 主索引对应的表
     */
    private String tableName;
    /**
     * 使用反射方法分析注解
     * @param clazz 索引对象Class类
     * @param <T>   泛型-Class具体类
     */
    public <T> void translate(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Table.TableBuilder> tableBuilderMap = new HashMap<String, Table.TableBuilder>();
        for(Field field : fields){
            FieldAttr fieldAnnotation = field.getAnnotation(FieldAttr.class);
            TableAttr tableAnnotation = field.getAnnotation(TableAttr.class);
            boolean main = fieldAnnotation.main();
            String schemaName = "";
            String foreign = "";
            String valid = "";
            String tableName = fieldAnnotation.table();
            String fieldName = tools.toUnderScoreCase(fieldAnnotation.field(), field.getName());
            if(main){
                schemaName = tableAnnotation.schema();
                this.schema.setName(schemaName);
                this.tableName = tableName;
            } else if(tableAnnotation != null) {
                schemaName = getSchemaName(tableAnnotation.schema());
                foreign = tableAnnotation.foreign();
                valid = tableAnnotation.valid();
            } else {
                schemaName = this.schema.getName();
            }
            tableName = getTableName(tableName);

            Table.TableBuilder tableBuilder;
            String tableBuilderKey = schemaName + "." +tableName;
            if(StringUtils.isEmpty(schemaName)
                    && StringUtils.isEmpty(tableName)){
                tableBuilderKey = this.schema.getName() + "." + this.tableName;
            }
            tableBuilder = tableBuilderMap.get(tableBuilderKey);//如果注解中schema和table为空,则取主table
            if(tableBuilder == null){
                tableBuilder = Table.buildTable(tableName);
            }

            tableBuilder.setForeign(foreign);
            tableBuilder.setValid(valid);
            tableBuilder.add(fieldName);
            tableBuilderMap.put(tableBuilderKey, tableBuilder);
        }
        List<Table> tableList = Lists.newArrayList();
        for(Map.Entry<String, Table.TableBuilder> entry : tableBuilderMap.entrySet()){
            Table table = entry.getValue().create();
            tableMap.put(entry.getKey(), table);
            tableList.add(table);
        }
        this.schema.setTableList(tableList);
        this.sqlMap = getSql(this.schema);
    }

    private Map<String, String> getSql(Schema schema){
        String schemaName = schema.getName();
        Map<String, String> sqlMap = Maps.newHashMap();
        for(Table table : schema.getTableList()){
            StringBuilder sb = new StringBuilder("select ");
            for(String column : table.getColumnList()){
                sb.append(column).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(" from ").append(schemaName).append(".").append(table.getName());
            if(!StringUtils.isEmpty(table.getValid())){
                sb.append(" where ").append(table.getValid());
            }
            sqlMap.put(schemaName + "." + table.getName(), sb.toString());
        }
        return sqlMap;
    }

    /**
     * 提供给外部, 用于创建索引
     * @return  返回map
     */
    public Map<String, String> getSqlMap() {
        return Collections.unmodifiableMap(sqlMap);
    }

    /**
     * 如果注解中的schema字段{@link TableAttr}为空,则取主schema的值 {@link DbTranslate#schema}
     * @param schemaName    注解中的schema字段
     * @return
     */
    private String getSchemaName(String schemaName){
        return StringUtils.isEmpty(schemaName) ? this.schema.getName() : schemaName;
    }

    /**
     * 如果注解中的table字段{@link FieldAttr}为空,则取主table的值 {@link DbTranslate#tableName}
     * @param tableName     注解中table字段
     * @return
     */
    private String getTableName(String tableName){
        return StringUtils.isEmpty(tableName) ? this.tableName : tableName;
    }

}
