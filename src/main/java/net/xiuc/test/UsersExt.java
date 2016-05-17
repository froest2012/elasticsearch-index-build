package net.xiuc.test;

import lombok.Data;
import net.xiuc.annotation.TableAttr;

/**
 * Created by 秀川 on 16/5/17.
 */
@TableAttr(schema = "shop", table = "db_users_ext", valid = "is_deleted = 'N'")
@Data
public class UsersExt {

    private Integer validOrderCount;
}
