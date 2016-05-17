package net.xiuc.test;

import lombok.Data;
import net.xiuc.annotation.TableAttr;

import java.util.Date;

/**
 * Created by 秀川 on 16/5/17.
 */
@TableAttr(schema = "saint", table = "saint_order_info_ext", valid = "is_deleted = 'N'")
@Data
public class OrderInfoExt {

    private String customerId;

    private String secretaryPostscript;

    private Boolean isInvoice;

    private Date delayTime;

    private Long userSigneeId;

    private String userSignee;

    private String orderExec;

    private Long orderExecId;

    private String roleSignee;

    private Long userOperatorId;

    private String userOperator;

    private Long sale1Id;

    private String sale1;

    private String sale1Role;

    private Long sale2Id;

    private String sale2;

    private String sale2Role;

    private String achievementSale1;

    private Long achievementSale1Id;

    private String achievementSale2;

    private Long achievementSale2Id;

    private String deptSignee;

    private String achievementDesc;

    private String bigAreaSignee;

    private String areaSignee;

    private String groupSignee;

    private String userOperatorRole;

    private String userOperatorGroup;

    private String userOperatorArea;

    private String userOperatorBigArea;

    private String userOperatorDept;

    private String sale1Group;

    private String sale1Area;

    private String sale1BigArea;

    private String sale1Dept;

    private String sale2Group;

    private String sale2Area;

    private String sale2BigArea;

    private String sale2Dept;

    private String achievementSale1Role;

    private String achievementSale1Group;

    private String achievementSale1Area;

    private String achievementSale1BigArea;

    private String achievementSale1Dept;

    private String achievementSale2Role;

    private String achievementSale2Group;

    private String achievementSale2Area;

    private String achievementSale2BigArea;

    private String achievementSale2Dept;

    private String orderExecRole;

    private String orderExecGroup;

    private String orderExecArea;

    private String orderExecBigArea;

    private String orderExecDept;

    //2015-09-24添加字段 start
    private String achievementService;

    private Long achievementServiceId;

    private String achievementServiceRole;

    private String achievementServiceGroup;

    private String achievementServiceArea;

    private String achievementServiceBigArea;

    private String achievementServiceDept;
}
