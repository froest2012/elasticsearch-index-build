package net.xiuc.annotation;

import java.lang.annotation.*;

/**
 * 定义table级别的属性,table级别的属性需要放在主键或者外键字段上面
 * 如果这个对象对应的是主索引,那么{@link TableAttr} 需要放在主键上面
 * 如果这个对象对应的是从索引,那么{@link TableAttr} 需要放在外键上面
 * Created by 秀川 on 16/4/29.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableAttr {

    /**
     * 这个字段映射到哪一个数据库shema
     * 如果为空,这个字段就是主体对象的shema
     * 如果不为空,则取该值
     * @return  返回结果
     */
    String schema() default "";

    /**
     * 辅助对象与主对象关联的字段
     * 如果为空,则没有关联字段,即这个就是主对象的值
     * 如果不为空, 那么这个字段就是和主对象进行关联的
     * @return  返回结果
     */
    String foreign() default "";

    /**
     * 用于main=true或者foreign!=""的时候
     * 来表示这个表的那种数据是有效的,比如:is_deleted='N', status=1 等等
     * @return  返回结果
     */
    String valid() default "";
}
