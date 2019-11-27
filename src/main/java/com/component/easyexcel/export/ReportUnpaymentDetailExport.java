package com.component.easyexcel.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.component.easyexcel.ExcelModel;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author zhanglong
 * @version V1.0
 * @date 2019-09-17  14:55
 */
@Data
public class ReportUnpaymentDetailExport implements ExcelModel {
    @ExcelProperty("公司编码")
    private String companyCode;
    @ExcelProperty("公司名称")
    private String companyName;
    @ExcelProperty("供应商名称")
    private String supplierName;
    @ExcelProperty("供应商编码")
    private String supplierCode;
    @ExcelProperty("供应商未对账总金额")
    private BigDecimal unAccountAmount;
    @ExcelProperty("供应商已确认财务未对账总金额")
    private BigDecimal confrimeAccountAmount;
    @ExcelProperty("财务已确认待开票总金额")
    private BigDecimal financeComfrimeAmount;
    @ExcelProperty("已开票未票核")
    private BigDecimal unCheckTiecktAmount;
    @ExcelProperty("已票核待生成付款单总金额")
    private BigDecimal checkTiecktAmount;
    @ExcelProperty("未付款金额")
    private BigDecimal unPaymentAmount;
    @ExcelProperty("信控")
    private BigDecimal creditControlAmount;
}
