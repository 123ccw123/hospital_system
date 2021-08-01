package com.ccw;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
public class Test1 {
    @Test
    public void method() throws IOException {
        //读取excle文件对象
        InputStream stream = new FileInputStream("C:\\Users\\ccw\\Desktop\\test.xls\\");
        //封装excle对象
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        //获取工作簿对象
        XSSFSheet sheet1 = workbook.getSheet("sheet1");
        //获取最后一行的行号
        int num = sheet1.getLastRowNum();
        //获取文件中所有的内容
        for (int i = 0; i <= num; i++) {
            //获取行对象
            XSSFRow row = sheet1.getRow(i);
            //或取最后一列的列号
            short cellnum = row.getLastCellNum();
            //获取列对象
            for (int j = 0; j < cellnum; j++) {
                XSSFCell cell = row.getCell(j);
                String value = cell.getStringCellValue();
                System.out.println(value);
            }

        }
    }
    @Test
    public void method1() throws IOException {
        //创建excle对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建一个工作簿对象
        XSSFSheet sheet = workbook.createSheet();
        //创建行
        XSSFRow row = sheet.createRow(0);
        //创建列
        XSSFCell cell = row.createCell(0);
        //给行添加值
        cell.setCellValue("你好！");
        //创建列
        XSSFCell cell1 = row.createCell(1);
        //给行添加值
        cell1.setCellValue("11");

        //创建行
        XSSFRow row1 = sheet.createRow(1);
        //创建列
        XSSFCell cell2 = row1.createCell(0);
        //给行添加值
        cell2.setCellValue("你不好！");
        //创建列
        XSSFCell cell3 = row1.createCell(1);
        //给行添加值
        cell3.setCellValue("111");
        //输出信息到文件
        FileOutputStream stream = new FileOutputStream("C:\\Users\\ccw\\Desktop\\test1.xlsx");
        workbook.write(stream);
    }
}
