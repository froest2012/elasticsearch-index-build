package net.xiuc.test;

import lombok.Data;
import net.xiuc.annotation.TableAttr;

/**
 * 订单商品类
 * Created by 秀川 on 16/5/17.
 */
@TableAttr(schema = "shop", table = "db_goods", valid = "is_delete = 0")
@Data
public class GoodsOfOrder {

    private String goodsId;

    private String catId;

    private String brandId;

    private Integer packingValue;

    private String oeNum;

    private String goodsQualityType;

    private String goodsImg;
}
