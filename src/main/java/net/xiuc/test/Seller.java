package net.xiuc.test;

import lombok.Data;
import net.xiuc.annotation.FieldAttr;
import net.xiuc.annotation.TableAttr;

/**
 * Created by 秀川 on 16/5/17.
 */
@TableAttr(schema = "shop", table = "db_seller", valid = "is_deleted = 'N'")
@Data
public class Seller {

    private String sellerType;

    @FieldAttr(field = "company_name")
    private String sellerCompanyName;
}
