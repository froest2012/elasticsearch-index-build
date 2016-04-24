package net.xiuc.test;

import net.xiuc.annotation.Field;

/**
 * 测试注解
 * Created by 秀川 on 16/4/22.
 */
public class Customer {

    @Field(schema = "saint", table = "saint_customer", main = true)
    private String id;

    @Field
    private String companyName;

    @Field(schema = "saint", table = "saint_contacts", foreign = "customer_id")
    private String contactsName;

}
