package net.xiuc;

import net.xiuc.build.DbTranslate;
import net.xiuc.build.Translate;
import net.xiuc.test.Customer;

/**
 * Created by 秀川 on 16/4/27.
 */
public class MainTest {
    public static void main(String[] args) {
        Translate translate = new DbTranslate();
        translate.translate(Customer.class);
    }
}
