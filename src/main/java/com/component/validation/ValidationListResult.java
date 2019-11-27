package com.component.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhanglong
 * @description: 描述
 * @date 2019-08-3113:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationListResult<T> extends ValidationResult{
    private List<T> successData = new ArrayList<>();

    private Map<T, Map<String, String>> errorData = new HashMap<>();

    @Override
    public void isErrorThrowExp() {
        if(!errorData.isEmpty()) {
            for (Entry<T, Map<String, String>> entry : errorData.entrySet()) {
                T key = entry.getKey();
                Map<String, String> value = entry.getValue();
                throw new RuntimeException(key.toString() + ":" + value.toString());
            }
        }
    }

    public List<T> get(){
        return successData;
    }
}



