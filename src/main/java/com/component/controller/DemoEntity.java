package com.component.controller;

import com.alibaba.excel.annotation.ExcelProperty;
import com.component.easyexcel.BaseReadModel;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhanglong
 * @description: 描述
 * @date 2019-08-3122:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoEntity extends BaseReadModel {
    @ExcelProperty("姓名")
    private String username;
    @NotNull(message = "年龄不能为空")
    @ExcelProperty("年龄")
    private Integer age;

    @Override
    public String toString() {
        return "DemoEntity{" +
            "username='" + username + '\'' +
            ", age=" + age +
            ", errorMsg='" + errorMsg + '\'' +
            '}';
    }
}
