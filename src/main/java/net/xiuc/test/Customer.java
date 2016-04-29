package net.xiuc.test;

import lombok.Data;
import net.xiuc.annotation.FieldAttr;
import net.xiuc.annotation.TableAttr;
import net.xiuc.mapping.Common;

/**
 * 测试注解
 * Created by 秀川 on 16/4/22.
 */
@Data
public class Customer implements Common {

    @TableAttr(schema = "saint")
    @FieldAttr(table = "saint_customer", main = true)
    private String id;

    @FieldAttr
    private String companyName;

    @TableAttr(schema = "saint", foreign = "customer_id", valid = "is_deleted = 'N'")
    @FieldAttr(table = "saint_contacts")
    private String contactsName;

    @TableAttr(schema = "saint")
    @FieldAttr(table = "saint_contacts")
    private String contactsMobile;

    public String getPrimaryKey() {
        return id;
    }
}
