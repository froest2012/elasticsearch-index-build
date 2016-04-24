package net.xiuc.annotation;

import java.lang.annotation.*;

/**
 * 字段映射注解, 可以知道每个字段映射到数据库哪个schema的哪个table中
 * 并且根据主体对象来创建索引
 * Created by 秀川 on 16/4/22.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Field {

    /**
     * 映射到数据库表中的字段名称
     * 如果为空, 则取bean中字段名称的下划线格式
     * 如果不为空, 则取值
     * @return  返回映射到数据库表中的字段名称
     */
    String field() default "";

    /**
     * 这个字段映射到哪一个数据库shema
     * 如果为空,这个字段就是主体对象的shema
     * 如果不为空,则取该值
     * @return  返回结果
     */
    String schema() default "";

    /**
     * 这个字段在哪个数据库表中
     * 如果为空, 这个字段的表结构就是主体对象的table
     * 如果不为空, 则取该值
     * @return  返回结果
     */
    String table() default "";

    /**
     * 是否是主体对象
     * 主对象才设置为true, 每一个bean中只有一个主对象
     * @return  返回结果
     */
    boolean main() default false;

    /**
     * 辅助对象与主对象关联的字段
     * 如果为空,则没有关联字段,即这个就是主对象的值
     * 如果不为空, 那么这个字段就是和主对象进行关联的
     * @return  返回结果
     */
    String foreign() default "";
}
