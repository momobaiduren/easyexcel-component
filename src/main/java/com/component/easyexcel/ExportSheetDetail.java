package com.component.easyexcel;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ZhangLong on 2019/11/4  10:25 上午
 * @version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportSheetDetail<M extends ExcelModel> {

    @NotNull(message = "model类类型不能为空")
    private Class<M> mClass;
    @NotEmpty(message = "导出数据不能为空")
    private List<M> data;

    private String sheetName;
    /**
     * description 默认为1从第一行开始
     */
    private Integer headRowNumber;
    /**
     * description 从0开始
     */
    private Integer sheetNo;

    public void validatedAndInit() {
        Objects.requireNonNull(mClass, "class could not be null");

        Objects.requireNonNull(data, "data could not be null");

        if (Objects.isNull(headRowNumber) || headRowNumber < 1) {
            headRowNumber = 1;
        }

        if (Objects.isNull(sheetNo) || sheetNo < 0) {
            sheetNo = 0;
        }

        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet" + sheetNo;
        }

    }
}
