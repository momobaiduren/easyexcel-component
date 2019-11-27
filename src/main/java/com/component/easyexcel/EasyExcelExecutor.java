package com.component.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.component.controller.DemoEntity;
import com.component.utils.WebUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * create by ZhangLong on 2019-08-31
 *
 * @description: 执行器 直接调用方法 默认使用 javax.validation 默认校验器， 在实体类上添加 java.validation注解例如@NotNull(message
 * = "xxx不能为空")
 */
@Slf4j
public final class EasyExcelExecutor {

    private static final String ERROR_PREFIX = "error_";

    private EasyExcelExecutor() {
    }

    /**
     * create by ZhangLong on 2019-08-31
     *
     * @param excleDataConsumer 导入数据的消费处理 并且ExcleData非空 description 导入
     */
    public static <M extends BaseReadModel> void importExcel(
        Consumer<ExcleData<M>> excleDataConsumer,
        final MultipartFile file, final Class<M> clazz ) {
        ImportSheetDetail<M> importSheetDetail = new ImportSheetDetail<>();
        importSheetDetail.setMClass(clazz);
        importSheetDetail.validatedAndInit();
        importMoreExcel(excleDataConsumer, file, importSheetDetail);
    }

    public static <M extends BaseReadModel> void importExcelAndExportErrorData(
        Consumer<ExcleData<M>> excleDataConsumer,
        final MultipartFile file, final Class<M> clazz ) {
        ImportSheetDetail<M> importSheetDetail = new ImportSheetDetail<>();
        importSheetDetail.setMClass(clazz);
        importSheetDetail.validatedAndInit();
        importMoreExcelAndExportErrorData(excleDataConsumer, file, true, importSheetDetail);
    }

    public static <M extends BaseReadModel> void importMoreExcel(
        Consumer<ExcleData<M>> excleDataConsumer, final MultipartFile file,
        ImportSheetDetail<M>... importSheetDetails ) {
        importMoreExcelAndExportErrorData(excleDataConsumer, file, false, importSheetDetails);
    }

    /**
     * create by ZhangLong on 2019-08-31
     *
     * @param excleDataConsumer 导入数据的消费处理 并且ExcleData非空
     * @param response 不为null实现导出处理 description 导入
     */
    @SuppressWarnings("all")
    public static <M extends BaseReadModel> void importMoreExcelAndExportErrorData(
        Consumer<ExcleData<M>> excleDataConsumer, final MultipartFile file,
        boolean isErrorMsgRespose, ImportSheetDetail<M>... importSheetDetails ) {
        ExcleData<M> excleData = new ExcleData<>();
        Objects.requireNonNull(file, "导入文件不能为空");
        Objects.requireNonNull(importSheetDetails, "导入输入信息不能为空");
        try (InputStream inputStream = file.getInputStream()) {
            //新版本方法
            ExcelReader excelReader = EasyExcel.read(inputStream).build();
            List<ExportSheetDetail<M>> errorSheetDetails = new ArrayList<>();
            List<ReadSheet> readSheets = new ArrayList<>(importSheetDetails.length);
            for (ImportSheetDetail<M> importSheetDetail : importSheetDetails) {
                importSheetDetail.validatedAndInit();
                Integer sheetNo = importSheetDetail.getSheetNo();
                excleData.initSheetData(sheetNo);
                ReadSheet readSheet = EasyExcel
                    .readSheet(sheetNo)
                    .headRowNumber(importSheetDetail.getHeadRowNumber())
                    .head(importSheetDetail.getMClass())
                    .registerReadListener(
                        new ExcelEventListener(excleData, isErrorMsgRespose ? () -> {
                            if (CollectionUtils
                                .isNotEmpty(excleData.errorDataBySheetNum(sheetNo))) {
                                ExportSheetDetail<M> excelModelSheetDetail = new ExportSheetDetail<>();
                                excelModelSheetDetail.setMClass(importSheetDetail.getMClass());
                                excelModelSheetDetail
                                    .setData(excleData.errorDataBySheetNum(sheetNo));
                                excelModelSheetDetail
                                    .setSheetName(importSheetDetail.getSheetName());
                                excelModelSheetDetail.setSheetNo(sheetNo);
                                excelModelSheetDetail
                                    .setHeadRowNumber(importSheetDetail.getHeadRowNumber());
                                errorSheetDetails.add(excelModelSheetDetail);
                            }
                        } : null)).build();
                readSheets.add(readSheet);
            }
            excelReader.read(readSheets.toArray(new ReadSheet[importSheetDetails.length]));
            excelReader.finish();
            if (Objects.nonNull(excleDataConsumer)) {
                excleDataConsumer.accept(excleData);
            }
            if (isErrorMsgRespose) {
                if (CollectionUtils.isNotEmpty(errorSheetDetails)) {
                    String originalFilename = file.getOriginalFilename();
                    exportMoreSheet(ERROR_PREFIX +
                            originalFilename.substring(0, originalFilename.lastIndexOf(".")),
                        errorSheetDetails);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static <M extends BaseReadModel> void repeatedRead( final MultipartFile file )
        throws IOException {
        ExcleData<BaseReadModel> excleData = new ExcleData<>();
        ExcelReader excelReader = EasyExcel.read(file.getInputStream()).build();
        // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
        ReadSheet readSheet1 =
            EasyExcel.readSheet(0).head(DemoEntity.class)
                .registerReadListener(new ExcelEventListener(excleData, null)).build();
        ReadSheet readSheet2 =
            EasyExcel.readSheet(1).head(DemoEntity.class)
                .registerReadListener(new ExcelEventListener(excleData, null)).build();
        // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
        excelReader.read(readSheet1, readSheet2);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    public static <M extends ExcelModel> void exportMoreSheet( String fileName,
        List<ExportSheetDetail<M>> sheetDetails ) {
        Objects.requireNonNull(sheetDetails, "导出信息不能为空");
        HttpServletResponse response = Objects.requireNonNull(WebUtils.getResponse());
        checkResponse(fileName, response);
        try (OutputStream outputStream = response.getOutputStream()) {
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            for (ExportSheetDetail<M> sheetDetail : sheetDetails) {
                sheetDetail.validatedAndInit();
                WriteSheet writeSheet = EasyExcel
                    .writerSheet(sheetDetail.getSheetNo(), sheetDetail.getSheetName()).build();
                excelWriter.write(sheetDetail.getData(), writeSheet);
            }
            excelWriter.finish();
            outputStream.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private static void checkResponse( String fileName, HttpServletResponse response ) {
        if (Objects.isNull(response)) {
            throw new RuntimeException("未绑定响应{@HttpServletResponse}参数");
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileNameEncoded = null;
        try {
            fileNameEncoded = URLEncoder
                .encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("导出文件名编码异常：", e);
        }
        response.setHeader("Content-disposition",
            "attachment;filename=" + fileNameEncoded + ExcelTypeEnum.XLSX.getValue());
    }

    /**
     * create by ZhangLong on 2019/10/21 description 导出到服务本地指定文件目录下
     */
    @SuppressWarnings("all")
    public static <M extends ExcelModel> void exportResponse( Class<M> clazz, File excleFile,
        String sheetName,
        List<M> dataList ) {
        Objects.requireNonNull(excleFile, "dataListFunction could not be null");
        if (excleFile.exists()) {
            try (OutputStream outputStream = new FileOutputStream(excleFile)) {
                //新版本方法
                if (CollectionUtils.isNotEmpty(dataList)) {
                    EasyExcel.write(outputStream, clazz).sheet(0).doWrite(dataList);

                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            try {
                excleFile.createNewFile();
                exportResponse(clazz, excleFile, sheetName, dataList);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * created by zhanglong and since  2019/11/27 10:23 上午
     *
     * @description 默认导出第一sheet 标题行为第一行
     */
    public static <M extends ExcelModel> void exportResponse( Class<M> mClass, String fileName,
        String sheetName, List<M> dataList ) {
        ExportSheetDetail<M> excelModelExportSheetDetail = new ExportSheetDetail<>(mClass, dataList,
            sheetName, 1, 0);
        List<ExportSheetDetail<M>> exportSheetDetails = new ArrayList<>();
        exportSheetDetails.add(excelModelExportSheetDetail);
        exportMoreSheet(fileName, exportSheetDetails);


    }
}
