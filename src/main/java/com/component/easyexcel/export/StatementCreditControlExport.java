package com.component.easyexcel.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.component.easyexcel.ExcelModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author zhanglong
 * @description: 描述
 * @date 2019-09-0511:08
 */
@Data
public class StatementCreditControlExport implements ExcelModel {
    @ExcelProperty(value = "申请单号")
    private String applyNo;
    @ExcelProperty(value = "供应商编码")
    private String supplierCode;
    @ExcelProperty(value = "供应商名称")
    private String supplierName;
    @ExcelProperty(value = "采购组织名称")
    private String purchaseOrgName;

    @ExcelProperty(value = "采购组织编码")
    private String purchaseOrgCode;
    @ExcelProperty(value = "状态")
    private String state;
    @ExcelProperty(value = "申请信控")
    private BigDecimal applyCreditControl;
    @ExcelProperty(value = "原始信控")
    private BigDecimal originalCreditControl;
    @ExcelProperty(value = "制单时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @ExcelProperty(value = "制单人")
    private String createBy;
    @ExcelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    @ExcelProperty(value = "审核人")
    private String updateBy;
}
