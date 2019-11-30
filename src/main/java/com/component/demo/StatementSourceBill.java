package com.component.demo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.component.easyexcel.ExcelModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 * 源数据表
 *
 * @author zhanglong
 * @since 2019-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("statement_source_bill")
public class StatementSourceBill implements ExcelModel {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER)
    private Long Id;

    private Integer billType;

    private String billNo;

    private Date happenDate;

    private String statementNo;

    private BigDecimal paymentAmount;

    private BigDecimal deductAmount;

    private BigDecimal statementAmount;

    private String companyCode;

    private String companyName;

    private String costCode;

    private String costName;

    private Integer statementState;

    private String relationNo;

    private String purchaseNo;

    private String happenPlaceCode;

    private String happenPlaceName;

    private String settlePlaceCode;

    private String settlePlaceName;

    private String supplierCode;

    private String supplierName;

    private Integer frozenStatus;

    private String createBy;

    private Date createTime;

    private Date updateTime;

    private String updateBy;

    private Date statementTime;

    private Date financeStatementTime;

    private String checkTicketNo;

    private String paymentNo;

//    private String notSatisfyPaymentNo;

    private Integer checkTicketStatus;
    /**
     * 冲销凭证（结算单号）
     */
    private String writeOffBillNo;
    /**
     * 结算扣点
     */
    private BigDecimal settlementDeductionPoint;
    /**
     * 供应商主税率
     */
    private BigDecimal supplierMainTaxRate;
    //供应商税码
    private String supplierMainTaxCode;

    //付款状态 0-未生成付款单 1-已生成付款单-未审核
    //2-已生成付款单-已审核 3-已生成付款单-已发起付款
    //4-已付款成功
    private Integer paymentStatus;

    /**
     * 税额
     */
    private BigDecimal taxAmount;
    /**
     * @describ 账期时间
     */
    private Date accountPeriod;
    /**
     * @desc 票核状态
     */
    private Integer auditTicketStatus;

    //冻结人
    private String freezeBy;
    //冻结时间
    private Date freezeTime;
    //冻结原因
    private String freezeReasons;

    //信控值
    private BigDecimal creditControlAmount;
}
