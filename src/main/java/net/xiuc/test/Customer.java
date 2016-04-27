package net.xiuc.test;

import lombok.Data;
import net.xiuc.annotation.FieldAttr;

/**
 * 测试注解
 * Created by 秀川 on 16/4/22.
 */
@Data
public class Customer {

    @FieldAttr(schema = "saint", table = "saint_customer", main = true)
    private String id;

    @FieldAttr
    private String companyName;

    @FieldAttr(schema = "saint", table = "saint_contacts", foreign = "customer_id")
    private String contactsName;

    @FieldAttr(schema = "saint", table = "saint_contacts", foreign = "customer_id")
    private String contactsMobile;

}
