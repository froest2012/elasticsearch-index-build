package net.xiuc.build;

import java.util.List;

/**
 * 把索引的bean对象映射成数据库对应的schema和表对象
 *
 * Created by 秀川 on 16/4/24.
 */
public interface Translate {
    <T> List<Table> translate(Class<T> clazz);
    Table getMainTable();
}
