package net.xiuc;

import net.xiuc.mapping.DbTranslate;
import net.xiuc.mapping.Translate;
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
