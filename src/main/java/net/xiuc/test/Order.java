package net.xiuc.test;

import lombok.Data;
import net.xiuc.annotation.FieldAttr;
import net.xiuc.annotation.NestedAttr;
import net.xiuc.annotation.TableAttr;
import net.xiuc.build.Common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主索引
 * Created by 秀川 on 16/5/16.
 */
@TableAttr(schema = "venus", table = "venus_order_info", valid = "is_deleted = 'N'")
@Data
public class Order implements Common{

    @FieldAttr(main = true)
    private Long id;

    private String orderSn;

    private String parentId;

    private String shopId; //user_id

    private String saleId;

    private String saleName;

    private String orderStatus;

    private String shippingStatus;

    private String payStatus;

    private String companyName;

    private String consignee;

    private String country;

    private String province;

    private String city;

    private String district;

    private String street;

    private String address;

    private String mobile;

    private String shippingId;

    private String shippingName;

    private String payId;

    private String payName;

    private BigDecimal goodsAmount;

    private BigDecimal shippingFee;

    private BigDecimal orderAmount;

    private String referer;

    private Date addTime;

    private Date confirmTime;

    private Date payTime;

    private Date shippingTime;

    private BigDecimal discount;

    private Long warehouseId;

    private String businessNote; //comment

    private String tradeStatus;

    private Integer userType;

    private String orderFlags;

    private Long sellerId;

    private String sellerNick;

    private String postscript;

    private Long orderServiceId;

    private String attributes;

    private BigDecimal bonus;

    private String invType;

    /*====================saint_order_info_ext=====================*/
    @NestedAttr
    @FieldAttr(field = "order_info_ext", foreign = "order_id", primary = "id")
    private OrderInfoExt orderInfoExt;

    /*====================db_users_ext=====================*/
    @NestedAttr
    @FieldAttr(field = "users_ext", foreign = "id", primary = "shop_id")
    private UsersExt usersExt;

    /*====================db_seller=====================*/
    @NestedAttr
    @FieldAttr(field = "seller", foreign = "id", primary = "seller_id")
    private Seller seller;

    /*====================db_order_info_openplatform=====================*/
    @NestedAttr
    @FieldAttr(field = "open_platform_order", foreign = "order_id", primary = "id")
    private OrderInfoOpenPlatform orderInfoOpenPlatform;

    /*====================venus_order_goods=====================*/
    @NestedAttr
    @FieldAttr(field = "order_goods", foreign = "order_id", primary = "id")
    private OrderGoods orderGoods;

    public String getPrimaryKey() {
        return id.toString();
    }

}
