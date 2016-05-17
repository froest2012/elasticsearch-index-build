package net.xiuc.test;

import lombok.Data;
import net.xiuc.annotation.TableAttr;

/**
 * Created by 秀川 on 16/5/17.
 */
@TableAttr(schema = "shop", table = "db_order_info_openplatform", valid = "is_deleted = 'N'")
@Data
public class OrderInfoOpenPlatform {
    private String orderId;//对应于orderInfo的orderId
    private String settleStatus;//'结算状态,WJS:未结算，BYJS:已结算',
    private String settleTime;// '结算时间',
    private String needSettleAmount;//'需要给供应商的结算金额',
    private String hasSettleAmount;//'已经给供应商支付的结算金额',
    private String commissionRate;// '供应商佣金比率'
}