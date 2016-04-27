package net.xiuc.mapping;

import com.google.common.collect.Maps;
import net.xiuc.annotation.FieldAttr;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.Strings;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 秀川 on 16/4/26.
 */
public class DbTranslate implements Translate {

    private Schema schema = new Schema();

    private Map<String, Table> tableMap = Maps.newHashMap();//key=schema.table,value=tableBuilder

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
            FieldAttr annotation = field.getAnnotation(FieldAttr.class);
            boolean main = annotation.main();
            String schemaName = annotation.schema();
            String tableName = annotation.table();
            String fieldName = annotation.field();
            String foreign = annotation.foreign();
            String key = "";
            Table.TableBuilder tableBuilder = null;
            String tableBuilderKey = schemaName + "." +tableName;
            if(StringUtils.isEmpty(schemaName)
                    && StringUtils.isEmpty(tableName)){
                tableBuilderKey = this.schema.getName() + "." + this.tableName;
            }
            tableBuilder = tableBuilderMap.get(tableBuilderKey);//如果注解中schema和table为空,则取主table
            if(tableBuilder == null){
                tableBuilder = Table.buildTable(tableName);
            }
            if(Strings.isEmpty(fieldName)){//TODO 索引的字段不一定是下划线
                fieldName = Strings.toUnderscoreCase(field.getName());
            }
            if(main){
                this.schema.setName(schemaName);
                this.tableName = tableName;
                key = fieldName;
                tableBuilder.setKey(key);
            }
            tableBuilder.setForeign(foreign);
            tableBuilder.add(fieldName);
            tableBuilderMap.put(tableBuilderKey, tableBuilder);
        }
        for(Map.Entry<String, Table.TableBuilder> entry : tableBuilderMap.entrySet()){
            tableMap.put(entry.getKey(), entry.getValue().create());
        }
    }

}
