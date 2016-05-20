package net.xiuc.annotation;

import java.lang.annotation.*;

/**
 * 字段映射注解, 可以知道每个字段映射到数据库哪个schema的哪个table中
 * 并且根据主体对象来创建索引
 * 如果是嵌套类型的字段,必须要有 {@link FieldAttr#foreign()}, {@link FieldAttr#primary()}, 以及{@link FieldAttr#field()}
 * Created by 秀川 on 16/4/22.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldAttr {
    /**
     * 辅助对象与主对象关联的字段
     * 如果为空,则没有关联字段,即这个就是主对象的值
     * 如果不为空, 那么这个字段就是和主对象进行关联的
     * 外键的字段名称要与数据库的字段保持一致,使用驼峰形式,数据库名称使用下划线,
     * 主要是为了关联的时候可以通过get方法获取相应的值
     * @return  返回结果
     */
    String foreign() default "";

    /**
     * 主对象中的主键
     * 辅助对象与主对象的关联关系,{@link FieldAttr#foreign()}
     * 主键的字段名称要与数据库的字段保持一致,使用驼峰形式,数据库名称使用下划线,
     * 主要是为了关联的时候可以通过get方法获取相应的值
     * @return  返回结果
     */
    String primary() default "";

    /**
     * 映射到数据库表中的字段名称
     * 如果为空, 则取bean中字段名称的下划线格式
     * 如果不为空, 则取值
     * @return  返回映射到数据库表中的字段名称
     */
    String field() default "";

}
