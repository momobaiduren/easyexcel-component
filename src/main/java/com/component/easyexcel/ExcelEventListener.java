package com.component.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.component.validation.ValidationEntityResult;
import com.component.validation.ValidationManager;
import java.util.Objects;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhanglong
 * @describ 事件解析监听处理器
 * @date 2019-06-01  13:09
 */
@Slf4j
@Data
public class ExcelEventListener<M extends BaseReadModel> extends AnalysisEventListener<M> {

    private ExcleData<M> excleData;

    private Runnable runnable;

    ExcelEventListener(ExcleData<M> excleData, Runnable runnable) {
        this.excleData = excleData;
        this.runnable = runnable;
    }

    @Override
    public void invoke(M model, AnalysisContext analysisContext) {
        //检查每一个需要校验的数据
        ValidationEntityResult<M> validationEntityResult = ValidationManager
                .validation(null).validateEntity(model);
        Integer sheetNo = analysisContext.readSheetHolder().getSheetNo();
        if (!validationEntityResult.hasError()) {
            excleData.dataAdd(sheetNo, model);
        } else {
            model.setErrorMsg(String.format("检查错误：%s", validationEntityResult.errorMsgs()));
            excleData.errorModelAdd(sheetNo, model);
        }
        System.out.println(model);
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (Objects.nonNull(runnable)) {
            runnable.run();
        }
//        String logMsg = String
//            .format("excle表格导入数据数量(条)：%d条,解析成功数量(条)：%d条,校验失败数量(条)：%d条",
//                analysisContext.readSheetHolder().getApproximateTotalRowNumber(),
//                excleData.get().size(),
//                excleData.errorData().size());
//        log.warn(logMsg);
//        long startTime = System.currentTimeMillis();
//        excleData.errorModelAdd(modelAndErrMsg);
//        if (Objects.nonNull(excleDataConsumer)) {
//            excleDataConsumer.accept(excleData);
//        }
//        consumer.accept(excleData);
//
//        long endTime = System.currentTimeMillis();
//        log.warn(String.format("表格数据处理时长(ms):%d", endTime - startTime));
    }

}
