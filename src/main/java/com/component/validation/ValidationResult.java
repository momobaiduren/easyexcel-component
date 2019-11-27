package com.component.validation;

import javax.xml.bind.ValidationException;

/**
 * @author zhanglong
 * @description: 这个类可以是抽象类、也可以是接口（用于表达一个结果集类型的基类）
 * @date 2019-08-3113:04
 */
public abstract class ValidationResult {

   public abstract void isErrorThrowExp() throws ValidationException;

}
