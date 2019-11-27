package com.component.validation;

import java.util.function.Consumer;

/**
 * @author zhanglong
 * @description: 使用 javax.validation方式校验
 * @date 2019-08-31
 */
public class ValidationManager {
    /**
     * create by ZhangLong on 2019-08-31
     * @param consumer 如果是null的话 不额外处理校验结果，如果需要额外处理校验结果需要 {@Link Consumer}
     */
    public static ValidationExecutor validation(Consumer<ValidationResult> consumer){
        return new ValidationExecutor(consumer);
    }

}
