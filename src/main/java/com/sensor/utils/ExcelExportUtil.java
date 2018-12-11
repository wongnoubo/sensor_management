package com.sensor.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;//对应excel文件的工作簿
import org.apache.poi.hssf.usermodel.HSSFSheet;//对应excel文件的表空间
import org.apache.poi.hssf.usermodel.HSSFCellStyle;//对应excel文件的表单元格样式
import org.apache.poi.hssf.usermodel.HSSFRow;//对应excel文件的表的行
import org.apache.poi.hssf.usermodel.HSSFCell;//对应excel文件的表单元格
/*import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.CellRangeAddress;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.net.URLEncoder;

import com.sensor.domain.Sensor;*/
import com.sensor.service.SensorService;

@Component
public class ExcelExportUtil {
    private static Logger logger = Logger.getLogger(ExcelExportUtil.class);
    @Resource
    private SensorService sensorService;
    private static ExcelExportUtil excelExportUtil;

    @PostConstruct
    public void init(){
        excelExportUtil = this;
        excelExportUtil.sensorService = this.sensorService;
    }

   public static HSSFWorkbook generateExcel(ArrayList<Integer> list,String sensorName) {
       HSSFWorkbook book = new HSSFWorkbook();//创建一个工作簿
       if(!list.isEmpty()) {
           try {
              // File desFile = new File("F:\\excels\\sensor\\" + sensorName + ".xls");//.xls
               //FileOutputStream fos = new FileOutputStream(desFile);
               HSSFSheet sheet = book.createSheet(sensorName);//创建一个工作表
               sheet.autoSizeColumn(1, true);//自适应列宽度

               //填充表头标题
               HSSFRow firstRow = sheet.createRow(0);//第几行（从0开始）
               HSSFCell firstCell = firstRow.createCell(0, HSSFCell.CELL_TYPE_STRING);
               firstCell.setCellValue("读数");

               for (int j = 0; j < list.size(); j++) {
                   HSSFRow dataRow = sheet.createRow(j + 1);//从第三行开始
                   int val = list.get(j);
                   HSSFCell cell2 = dataRow.createCell(0, HSSFCell.CELL_TYPE_STRING);//从第三行开始新建一个cell
                   cell2.setCellValue(val);
               }
               logger.debug("数据写入完毕");
           } catch (Exception ex) {
               logger.debug("excel导出失败");
               ex.printStackTrace();
           }
       }
       else {
           logger.debug("数据为空");
       }
       return book;
   }

    public static HSSFWorkbook generateCpuTempExcel(ArrayList<Double> list,String sensorName){
        HSSFWorkbook book = new HSSFWorkbook();//创建一个工作簿
        if(!list.isEmpty()) {
            try {
                HSSFSheet sheet = book.createSheet(sensorName);//创建一个工作表
                sheet.autoSizeColumn(1, true);//自适应列宽度

                //填充表头标题
                HSSFRow firstRow = sheet.createRow(0);//第几行（从0开始）
                HSSFCell firstCell = firstRow.createCell(0, HSSFCell.CELL_TYPE_STRING);
                firstCell.setCellValue("读数");

                for (int j = 0; j < list.size(); j++) {
                    HSSFRow dataRow = sheet.createRow(j + 1);//从第三行开始
                    double val = list.get(j);
                    HSSFCell cell2 = dataRow.createCell(0, HSSFCell.CELL_TYPE_STRING);//从第三行开始新建一个cell
                    cell2.setCellValue(val);
                }
                logger.debug("数据写入完毕");
            } catch (Exception ex) {
                logger.debug("excel导出失败");
                ex.printStackTrace();
            }
        }
        else {
            logger.debug("数据为空");
        }
        return book;
    }
}
