package net.xiuc.annotation;

import java.lang.annotation.*;

/**
 * 用于嵌套对象的注解, 只是用来说明这个是子对象
 * Created by 秀川 on 16/5/17.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NestedAttr {

}
