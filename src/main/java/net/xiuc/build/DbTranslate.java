package net.xiuc.build;

import com.google.common.collect.Lists;
import net.xiuc.annotation.FieldAttr;
import net.xiuc.annotation.TableAttr;
import net.xiuc.util.Tools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 解析bean对象,转换成对应的sql
 * Created by 秀川 on 16/4/26.
 */
public class DbTranslate implements Translate {

    private static final Logger logger = LoggerFactory.getLogger(Translate.class);

    private static final Tools tools = Tools.INSTANCE;

    private Table mainTable;
    /**
     * 使用反射方法分析注解
     *
     * @param clazz 索引对象Class类
     * @param <T>   泛型-Class具体类
     */
    public <T> List<Table> translate(Class<T> clazz) {
        List<Table> res = Lists.newArrayList();
        analyzeBean(null, clazz, null, null, res);
        return res;
    }

    private void analyzeBean(Table.TableBuilder parent, Class<?> clazz, String foreign, String primary, List<Table> res) {
        TableAttr tableAttr = clazz.getAnnotation(TableAttr.class);
        if (tableAttr == null) {
            logger.warn("当前对象非数据库映射对象");
            return ;
        }
        String schemaName = tableAttr.schema();
        String tableName = tableAttr.table();
        String valid = tableAttr.valid();

        Table.TableBuilder tableBuilder = Table.buildTableBuilder(schemaName, tableName);
        if (!StringUtils.isEmpty(valid)) {
            tableBuilder.setValid(valid);
        }
        if (!StringUtils.isEmpty(foreign)) {
            tableBuilder.setForeign(foreign);
        }
        if (!StringUtils.isEmpty(primary)) {
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
                        analyzeBean(tableBuilder, Class.forName(field.getType().getName()), foreignValue, primaryValue, res);
                    } catch (ClassNotFoundException e) {
                        logger.error("未找到该类", e);
                    }
                } else {
                    String fieldName = fieldAttr.field();
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
        if (parent != null) {
            tableBuilder.setParentBuilder(parent);
            res.add(tableBuilder.create());//当parent=null的时候,由mainTable存储;res相当于就是从表的信息
        }else {
            mainTable = tableBuilder.create();
        }
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

    public Table getMainTable() {
        return mainTable;
    }
}
