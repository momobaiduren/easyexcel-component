package com.component.controller;

import com.component.easyexcel.EasyExcelExecutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhanglong
 * @title: ExcelDemoController
 * @projectName spring-cloud-demo
 * @description: excel Demo
 * @date 2019-07-3015:00
 */
@Slf4j
@RestController
@RequestMapping(value = "/excel", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExcelDemoController {


    @PostMapping("importData")
    public void importExcel(@RequestParam("file") MultipartFile file ) throws RuntimeException {
//        EasyExcelExecutor.importExcel(excleData -> {
//            System.out.println(excleData.get());
//        }, file, DemoEntity.class);
        EasyExcelExecutor.importExcelAndExportErrorData(excleData -> {
            System.out.println(excleData.get());
        }, file, DemoEntity.class);
        //穿到后台进行处理 这里直接返回了
    }

    static List<DemoEntity> data;

    static {
        data = new ArrayList<>();
        data.add(new DemoEntity("zhangsan", 23));
        data.add(new DemoEntity("lisi", 24));
        data.add(new DemoEntity("wangwu", 25));
    }

    @PostMapping("exportData")
    public void exportData() throws RuntimeException {
        EasyExcelExecutor.exportResponse(DemoEntity.class,"zhanglong","zhanglong",data);
    }
}
