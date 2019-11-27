package com.component.easyexcel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

/**
 * @author ZhangLong on 2019/10/16  5:56 下午
 * @version V1.0
 */
public class ExcleData<M extends BaseReadModel> {

    private Map<Integer, SheetData> datas = new ConcurrentHashMap<>();

    public Map<Integer, SheetData> get() {
        return datas;
    }

    public List<M> dataBySheetNum( int sheetNum ) {
        if (Objects.nonNull(datas.get(sheetNum))) {
            return datas.get(sheetNum).getData();
        }
        return Collections.emptyList();
    }

    public List<M> errorDataBySheetNum( int sheetNum ) {
        if (Objects.nonNull(datas.get(sheetNum))) {
            return datas.get(sheetNum).getErrorData();
        }
        return Collections.emptyList();
    }

    public void dataAdd( int sheetNum, M model ) {
        if (datas.containsKey(sheetNum)) {
            datas.get(sheetNum).getData().add(model);
        } else {
            SheetData sheetData = new SheetData();
            sheetData.getData().add(model);
            datas.put(sheetNum, sheetData);
        }
    }

    /**
     * create by ZhangLong on 2019/10/16 description 不要数据data/errorData循环中直接使用调用该方法,循环中进行remove可能会导致
     * {@link java.util.ConcurrentModificationException}
     */
    public void errorModelAdd( int sheetNum, M model ) {
        SheetData sheetData = datas.get(sheetNum);
        sheetData.getData().remove(model);
        sheetData.getErrorData().add(model);
    }

    public void initSheetData( Integer sheetNo ) {
        datas.put(sheetNo, new SheetData());
    }

    @Data
    public class SheetData {

        SheetData() {
            data = new ArrayList<>();
            errorData = new ArrayList<>();
        }

        List<M> data;
        List<M> errorData;
    }
}
