package com.geekerit.springbootalipay.controller;

import com.geekerit.springbootalipay.domain.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aaryn
 */
@RestController
public class ExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) throws Exception {
        String[] tableHeaders = {"id", "姓名", "年龄"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);
        // 将第一行的三个单元格给合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        HSSFRow row = sheet.createRow(0);
        HSSFCell beginCell = row.createCell(0);
        beginCell.setCellValue("通讯录");
        beginCell.setCellStyle(cellStyle);
        row = sheet.createRow(1);
        // 创建表头
        //
        for (int i = 0; i < tableHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(tableHeaders[i]);
            cell.setCellStyle(cellStyle);
        }
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "张三", 20));
        users.add(new User(2L, "李四", 21));
        users.add(new User(3L, "王五", 22));
        for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);
            User user = users.get(i);
            row.createCell(0).setCellValue(user.getUserId());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getAge());
        }
        OutputStream outputStream = response.getOutputStream();
        response.reset();
        response.setContentType("application/msexcel");
        response.setHeader("Content-disposition", "attachment;filename=poiExport.xls");
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public void importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        logger.info("文件内容类型为{}",contentType);
        String name1 = file.getName();
        logger.info("name{}",name1);
        String originalFilename = file.getOriginalFilename();
        String[] split = originalFilename.split("\\.");
        if (split.length > 0){
            for (String s : split) {
                logger.info("截取后内容为{}",s);
            }
            String suffix = split[split.length - 1];
            logger.info("文件后缀为{}",suffix);
        }
        logger.info("originalFilename{}",originalFilename);
        long size = file.getSize();
        logger.info("size{}",size);
        Resource resource = file.getResource();
        logger.info("resource{}",resource);


        InputStream inputStream = file.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        //HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 2; i <= lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            int id = (int) row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();
            int age = (int) row.getCell(2).getNumericCellValue();
            System.out.println(id + "-" + name + "-" + age);
        }
    }

}
