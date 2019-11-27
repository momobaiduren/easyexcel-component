package com.component.easyexcel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author ZhangLong on 2019/11/4  10:25 上午
 * @version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportSheetDetail<M extends BaseReadModel> {
    private Class<M> mClass;

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
        if (Objects.isNull(sheetNo) || sheetNo < 0) {
            sheetNo = 0;
        }

        if (Objects.isNull(headRowNumber) || headRowNumber < 1) {
            headRowNumber = 1;
        }

        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet" + sheetNo;
        }

    }

}
