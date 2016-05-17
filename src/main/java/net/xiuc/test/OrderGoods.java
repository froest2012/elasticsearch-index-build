package net.xiuc.test;

import lombok.Data;
import net.xiuc.annotation.FieldAttr;
import net.xiuc.annotation.TableAttr;

import java.math.BigDecimal;

/**
 * 订单商品类
 * Created by 秀川 on 16/5/17.
 */
@TableAttr(schema = "venus", table = "venus_order_goods", valid = "is_deleted = 'N'")
@Data
public class OrderGoods {

    private String goodsId;

    @FieldAttr(field = "goods_sn")
    private String newGoodsSn;

    private String goodsName;

    private String goodsNumber;

    private BigDecimal goodsPrice;

    private BigDecimal activityName;

    private Long activityId;

    private Long activityGroupId;

    private String measureUnit;

    private BigDecimal soldPrice;

    private BigDecimal soldPriceAmount;


}
