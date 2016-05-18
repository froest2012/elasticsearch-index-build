package net.xiuc.build;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.xiuc.annotation.FieldAttr;
import net.xiuc.annotation.TableAttr;
import net.xiuc.util.Tools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 解析bean对象,转换成对应的sql
 * Created by 秀川 on 16/4/26.
 */
public class DbTranslate implements Translate {

    private static final Logger logger = LoggerFactory.getLogger(Translate.class);
    private Map<String, Table> tableMap = Maps.newHashMap();//key=schema.table,value=tableBuilder

    private Map<String, String> sqlMap = Maps.newHashMap();

    private static final Tools tools = Tools.INSTANCE;

    private Table.TableBuilder keyTableBuilder;

    /**
     * 使用反射方法分析注解
     *
     * @param clazz 索引对象Class类
     * @param <T>   泛型-Class具体类
     */
    public <T> void translate(Class<T> clazz) {
        List<Table.TableBuilder> res = Lists.newArrayList();
        analyzeBean(null, clazz, null, null, res);
        System.out.println();
    }

    private Table.TableBuilder analyzeBean(Table.TableBuilder parent, Class<?> clazz, String foreign, String primary,  List<Table.TableBuilder> res) {
        TableAttr tableAttr = clazz.getAnnotation(TableAttr.class);
        if (tableAttr == null) {
            logger.warn("当前对象非数据库映射对象");
            return null;
        }
        String schemaName = tableAttr.schema();
        String tableName = tableAttr.table();
        String valid = tableAttr.valid();

        Table.TableBuilder tableBuilder = Table.buildTableBuilder(schemaName, tableName);
        res.add(tableBuilder);
        if (parent != null) {
            tableBuilder.setParentBuilder(parent);
        }
        if (!StringUtils.isEmpty(valid)) {
            tableBuilder.setValid(valid);
        }
        if(!StringUtils.isEmpty(foreign)){
            tableBuilder.setForeign(foreign);
        }
        if(!StringUtils.isEmpty(primary)){
            tableBuilder.setPrimary(primary);
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            FieldAttr fieldAttr = field.getAnnotation(FieldAttr.class);
            if (fieldAttr == null) {
                tableBuilder.add(tools.toUnderScoreCase("", field.getName()), field.getName());
            } else {
                String foreignValue = fieldAttr.foreign();
                String primaryValue = fieldAttr.primary();
                if (isCustomType(field)) {
                    //递归,说明是自定义对象类型
                    try {
                        analyzeBean(tableBuilder, Class.forName(field.getType().getName()),foreignValue, primaryValue, res);
                    } catch (ClassNotFoundException e) {
                        logger.error("未找到该类", e);
                    }
                } else {
                    boolean main = fieldAttr.main();
                    String fieldName = fieldAttr.field();
                    if (main) {
                        keyTableBuilder = tableBuilder;
                    }
                    tableBuilder.add(tools.toUnderScoreCase(fieldName, field.getName()), field.getName());
                    if (!StringUtils.isEmpty(foreignValue)) {
                        tableBuilder.setForeign(foreignValue);
                    }
                    if (!StringUtils.isEmpty(primaryValue)) {
                        tableBuilder.setPrimary(primaryValue);
                    }
                }
            }

        }
        return tableBuilder;
    }

    /**
     * 是否是基础类型, 此处String也当做是基础类型
     *
     * @param field 字段
     *
     * @return
     */
    private boolean isCustomType(Field field) {
        Class type = field.getType();
        if (Integer.class == type ||
                Long.class == type ||
                Double.class == type ||
                Float.class == type ||
                Date.class == type ||
                String.class == type ||
                BigDecimal.class == type) {
            return false;
        }
        return true;
    }

    private Map<String, String> getSql(Schema schema) {
       return null;
    }

    /**
     * 把schema对应的table的信息给外面提供出去,但是不能修改map中的信息
     *
     * @return
     */
    public Map<String, Table> getTableMap() {
        return Collections.unmodifiableMap(tableMap);
    }

    /**
     * 提供给外部, 用于创建索引
     *
     * @return 返回map
     */
    public Map<String, String> getSqlMap() {
        return Collections.unmodifiableMap(sqlMap);
    }


}
