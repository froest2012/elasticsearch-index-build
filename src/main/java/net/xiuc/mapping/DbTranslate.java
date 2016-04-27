package net.xiuc.mapping;

import net.xiuc.annotation.FieldAttr;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 秀川 on 16/4/26.
 */
public class DbTranslate implements Translate {

    private Schema schema = new Schema();

    private List<Table> tableList = new ArrayList<Table>();

    private Map<String, Table> tableMap = new HashMap<String, Table>();//key=tableName,value=table
    /**
     * 使用反射方法分析注解
     * @param clazz 索引对象Class类
     * @param <T>   泛型-Class具体类
     */
    public <T> void translate(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            FieldAttr annotation = field.getAnnotation(FieldAttr.class);
            boolean main = annotation.main();
            String schemaName = annotation.schema();
            String tableName = annotation.table();
            String fieldName = annotation.field();
            String foreign = annotation.foreign();//可能不需要
            if(main){
                this.schema.setName(schemaName);
            }
            Table table = tableMap.get(tableName);
           if(table == null){
               table = Table.buildTable(tableName)
                       .add(fieldName)
                       .create();
               tableMap.put(tableName, table);
           }

        }
    }

}
