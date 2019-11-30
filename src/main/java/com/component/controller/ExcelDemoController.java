package com.component.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.component.demo.StatementSourceBill;
import com.component.demo.StatementSourceBillMapper;
import com.component.easyexcel.EasyExcelExecutor;
import com.component.easyexcel.ExcelModel;
import com.component.easyexcel.ExportSheetDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    public void importExcel(@RequestParam("file") MultipartFile file) throws RuntimeException {
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

    @Autowired
    private StatementSourceBillMapper statementStatementAccountMapper;

    @PostMapping("exportData")
    public void exportData() throws RuntimeException {
//        EasyExcelExecutor.exportResponse(DemoEntity.class,"zhanglong","zhanglong",data);
//        List<StatementSourceBill> statementStatementAccounts = statementStatementAccountMapper.selectList(qw);
//        EasyExcelExecutor.exportResponse(StatementSourceBill.class,"zhanglong","zhanglong",statementStatementAccounts);
        long start = System.currentTimeMillis();
        List<ExportSheetDetail<StatementSourceBill>> exportSheetDetails = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            long l = System.currentTimeMillis();
            QueryWrapper<StatementSourceBill> qw = new QueryWrapper<>();
            qw.ge("id",(i-1)*5000);
            qw.lt("id",(i)*5000);
            List<StatementSourceBill> statementSourceBills = statementStatementAccountMapper.selectList(qw);
            System.out.println("sql"+i+":" + (System.currentTimeMillis()-l));
            ExportSheetDetail<StatementSourceBill> exportSheetDetail = new ExportSheetDetail<>(StatementSourceBill.class,
                    statementSourceBills,null,1,i-1);
            exportSheetDetails.add(exportSheetDetail);
        }
        EasyExcelExecutor.exportMoreSheet("zhanglong", exportSheetDetails);
        System.out.println(System.currentTimeMillis()-start);
        System.out.println((System.currentTimeMillis()-start)/1000);
    }

    @GetMapping("insertData")
    public void insertData() {
        StatementSourceBill statementSourceBill = statementStatementAccountMapper.selectById(1167907327760683010L);
        statementSourceBill.setId(null);
        for (int i = 0; i < 900000; i++) {
            StatementSourceBill statementSourceBilli = statementSourceBill;
            statementSourceBilli.setId(i + 10001L);
            statementStatementAccountMapper.insert(statementSourceBill);
        }
    }
}
