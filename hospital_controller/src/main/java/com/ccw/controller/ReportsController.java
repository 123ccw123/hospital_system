package com.ccw.controller;

import com.ccw.commons.MessageConstant;
import com.ccw.commons.Result;
import com.ccw.service.MemberService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportsController {
    @Reference
    private MemberService memberService;
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){

        try {
            HashMap<String, Object> map = new HashMap<>();
            ArrayList<String> months = new ArrayList<>();
            //获取当前月份
            Calendar instance = Calendar.getInstance();
            //将当前月份转化为过去的十二个月
            instance.add(Calendar.MONTH,-12);
            for( int i = 0; i < 12; i++ ){
                instance.add(Calendar.MONTH,1);
                //将得到的时间转化为需要的格式
                months.add(new SimpleDateFormat("yyyy.MM").format(instance.getTime()));
            }
            map.put("month",months);
            //根据月份获取注册会员的数量
            List<Integer> count = memberService.findMemberCount(months);
            map.put("memberCount",count);
            return new Result(true,map);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try {
            HashMap<String, Object> map = new HashMap<>();
            List<Map<String, Object>> list = memberService.finCountBySetmeal();
            //新建一个集合存放套餐名称
            ArrayList<String> list1 = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : list) {
                String name = (String) stringObjectMap.get("name");
                list1.add(name);
            }
            map.put("stemealNames",list1);
            map.put("setmealCount",list);
            return new Result(true,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String, Object> businessReportData = memberService.findBusinessReportData();
            return new Result(true,businessReportData);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
    /*将统计数据导出到excle中*/
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletResponse resp){
        Map<String, Object> result = memberService.findBusinessReportData();
        //定义输出文件的路径
        String filePath="C:\\Users\\ccw\\Desktop\\report_template.xlsx";
        //取出返回结果数据，准备将报表数据写入到Excel文件中
        String reportDate = (String) result.get("reportDate");
        Integer todayNewMember = (Integer) result.get("todayNewMember");
        Integer totalMember = (Integer) result.get("totalMember");
        Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
        Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
        Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
        Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
        Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
        Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
        Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
        Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

        //准备一个流对象读取文件内容
        try {
            FileInputStream is = new FileInputStream(filePath);
            //将对象写入excle
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);

            //日期
            XSSFRow row = sheet.getRow(2);
            XSSFCell cell = row.getCell(5);
            cell.setCellValue(reportDate);

            //新增会员数
            row=sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            //总会员数
            row.getCell(7).setCellValue(totalMember);

            //本周新增会员数
            row=sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            //本月新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum=12;
            for (Map map : hotSetmeal) {
                String name = (String) map.get("name");
                long num = (long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(num);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }
            //将excel下载到本地
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("content-Disposition","attachment;filename=report.xlsx");
            ServletOutputStream outputStream = resp.getOutputStream();
            //将数据写入到本地
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
