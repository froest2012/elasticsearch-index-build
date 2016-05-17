package net.xiuc.test;

import lombok.Data;
import net.xiuc.build.Common;

/**
 * 测试注解
 * Created by 秀川 on 16/4/22.
 */
@Data
public class Customer implements Common {


    public String getPrimaryKey() {
        return null;
    }
}
