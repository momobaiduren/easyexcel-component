package com.component.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

/**
 * @author zhanglong
 * @description:
 * @date 2019-08-3112:33
 */
public class ValidationExecutor {

    private Consumer<ValidationResult> consumer;

    public ValidationExecutor(Consumer<ValidationResult> consumer){
        this.consumer = consumer;
    }

    public <T> ValidationListResult<T> validateList(List<T> dataList){
        return validateList(dataList,null);
    }

    public <T> ValidationListResult<T> validateList(List<T> dataList, Validator validator) {
        if (Objects.isNull(validator)){
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        ValidationListResult<T> result = new ValidationListResult<>();
        Validator finalValidator = validator;
        dataList.forEach(t -> {
            Set<ConstraintViolation<T>> set = finalValidator.validate(t, Default.class);
            if (set != null && set.size() != 0) {
                Map<String, String> errorMsg = new HashMap<>();
                for (ConstraintViolation<T> cv : set) {
                    if(errorMsg.containsKey(cv.getPropertyPath().toString())) {
                        errorMsg.put(cv.getPropertyPath().toString(),
                            errorMsg.get(cv.getPropertyPath().toString())+";"+cv.getMessage());
                    }else {
                        errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
                    }
                }
                if(errorMsg.isEmpty()) {
                    result.getSuccessData().add(t);
                }else {
                    result.getErrorData().put(t, errorMsg);
                }
            }
        });
        if (Objects.nonNull(consumer)){
            consumer.accept(result);
        }
        return result;
    }
    public <T> ValidationEntityResult<T> validateEntity( T data){
        return validateEntity(data, null);
    }
    public <T> ValidationEntityResult<T> validateEntity( T data, Validator validator) {
        if (Objects.isNull(validator)){
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        ValidationEntityResult<T> validationEntityResult = new ValidationEntityResult<>();
        validationEntityResult.setData(data);
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(data, Default.class);
        Optional.ofNullable(constraintViolationSet).ifPresent(constraintViolations -> constraintViolations.forEach(constraintViolation ->{
            if(validationEntityResult.getErrorMsgs().containsKey(constraintViolation.getPropertyPath().toString())) {
                validationEntityResult.getErrorMsgs().put(constraintViolation.getPropertyPath().toString(),
                    validationEntityResult.getErrorMsgs().get(constraintViolation.getPropertyPath().toString())+";"+constraintViolation.getMessage());
            }else {
                validationEntityResult.getErrorMsgs().put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
        }));
        if (Objects.nonNull(consumer)){
            consumer.accept(validationEntityResult);
        }
        return validationEntityResult;
    }

}
