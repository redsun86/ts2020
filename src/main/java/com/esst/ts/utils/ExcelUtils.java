package com.esst.ts.utils;
import com.esst.ts.model.UserScoreRecordPOJO;
import okhttp3.Response;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtils {
    /**
     * 获取并解析excel文件，返回一个二维集合
     * @param file 上传的文件
     * @return 二维集合（第一重集合为行，第二重集合为列，每一行包含该行的列集合，列集合包含该行的全部单元格的值）
     */
    public static ArrayList<ArrayList<String>> analysis(MultipartFile file) {
        ArrayList<ArrayList<String>> row = new ArrayList<>();
        //获取文件名称
        String fileName = file.getOriginalFilename();
        try {
            //获取输入流
            InputStream in = file.getInputStream();
            //判断excel版本
            Workbook workbook = null;
            if (judegExcelEdition(fileName)) {
                workbook = new XSSFWorkbook(in);
            } else {
                workbook = new HSSFWorkbook(in);
            }

            //获取第一张工作表
            Sheet sheet = workbook.getSheetAt(1);
            //从第二行开始获取
            //int aa=sheet.getPhysicalNumberOfRows();
            //int bb=sheet.getLastRowNum();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //循环获取工作表的每一行
                if(sheet.getRow(i)==null){}
                else{
                    Row sheetRow = sheet.getRow(i);
                    //循环获取每一列
                    ArrayList<String> cell = new ArrayList<>();
                    //int ssss1=sheetRow.getPhysicalNumberOfCells();
                    //int ssss2=sheetRow.getLastCellNum();
                    for (int j = 0; j < 9; j++) {
                        //将每一个单元格的值装入列集合
                        if(sheetRow.getCell(j)==null){
                            cell.add("");
                        }
                        else{
                            cell.add(sheetRow.getCell(j).getStringCellValue());
                        }
                    }
                    //将装有每一列的集合装入大集合
                    row.add(cell);
                    //关闭资源
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return row;
    }

    /**
     * 判断上传的excel文件版本（xls为2003，xlsx为2017）
     * @param fileName 文件路径
     * @return excel2007及以上版本返回true，excel2007以下版本返回false
     */
    private static boolean judegExcelEdition(String fileName){
        if (fileName.matches("^.+\\.(?i)(xls)$")){
            return false;
        }else {
            return true;
        }
    }

    public static ResponseEntity<byte[]> exportEmp(List<UserScoreRecordPOJO> employeeList,Integer type) {
        //1.创建一个excel文档
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建文档摘要
        hssfWorkbook.createInformationProperties();
        //3.获取并配置文档摘要信息
        DocumentSummaryInformation docInfo = hssfWorkbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("信息");
        //文档管理员
        docInfo.setManager("esst");
        //文档所属公司
        docInfo.setCompany("esst");
        //文档版本
//        docInfo.setApplicationVersion(1);
        //4.获取文档摘要信息
        SummaryInformation summaryInformation = hssfWorkbook.getSummaryInformation();
        //文档标题
        summaryInformation.setAuthor("hopec");
        //文档创建时间
        summaryInformation.setCreateDateTime(new Date());
        //文档备注
        summaryInformation.setComments("文档备注");
        //5.创建样式
        //创建标题行的样式
        HSSFCellStyle headerStyle = hssfWorkbook.createCellStyle();
        //设置该样式的图案颜色为黄色
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        //设置图案填充的样式
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置日期相关的样式
        HSSFCellStyle dateCellStyle = hssfWorkbook.createCellStyle();
        //这里的m/d/yy 相当于yyyy-MM-dd
        //dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        if(type==0) {
            HSSFSheet sheet = hssfWorkbook.createSheet("班级成绩");
            //设置每一列的宽度
            sheet.setColumnWidth(0, 25 * 256);
            sheet.setColumnWidth(1, 25 * 256);
            sheet.setColumnWidth(2, 15 * 256);
            sheet.setColumnWidth(3, 25 * 256);
            sheet.setColumnWidth(4, 25 * 256);
            sheet.setColumnWidth(5, 25 * 256);
            sheet.setColumnWidth(6, 25 * 256);
            sheet.setColumnWidth(7, 25 * 256);
            sheet.setColumnWidth(8, 15 * 256);
            sheet.setColumnWidth(9, 25 * 256);
            //6.创建标题行
            HSSFRow r0 = sheet.createRow(0);
            HSSFCell c0 = r0.createCell(0);
            c0.setCellValue("日期");
            c0.setCellStyle(headerStyle);
            HSSFCell c1 = r0.createCell(1);
            c1.setCellValue("机器号");
            c1.setCellStyle(headerStyle);
            HSSFCell c2 = r0.createCell(2);
            c2.setCellStyle(headerStyle);
            c2.setCellValue("姓名");
            HSSFCell c3 = r0.createCell(3);
            c3.setCellStyle(headerStyle);
            c3.setCellValue("学号");
            HSSFCell c4 = r0.createCell(4);
            c4.setCellStyle(headerStyle);
            c4.setCellValue("任务单/试卷名称");
            HSSFCell c5 = r0.createCell(5);
            c5.setCellStyle(headerStyle);
            c5.setCellValue("模式");
            HSSFCell c6 = r0.createCell(6);
            c6.setCellStyle(headerStyle);
            c6.setCellValue("班级");
            HSSFCell c7 = r0.createCell(7);
            c7.setCellStyle(headerStyle);
            c7.setCellValue("总成绩");
            HSSFCell c8 = r0.createCell(8);
            c8.setCellStyle(headerStyle);
            c8.setCellValue("学习时长(分)");
            HSSFCell c9 = r0.createCell(9);
            c9.setCellStyle(headerStyle);
            c9.setCellValue("分组信息");

            for (int i = 0; i < employeeList.size(); i++) {
                UserScoreRecordPOJO employee = employeeList.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(employee.getStudyDate());
                row.createCell(1).setCellValue(employee.getIpAddress());
                row.createCell(2).setCellValue(employee.getUserTrueName());
                row.createCell(3).setCellValue(employee.getUserStNum());
                row.createCell(4).setCellValue(employee.getTaskName());
                if (employee.getStudyType() == 0) {
                    row.createCell(5).setCellValue("练习模式");
                } else {
                    row.createCell(5).setCellValue("考试模式");
                }
                row.createCell(6).setCellValue(employee.getClassName());
                row.createCell(7).setCellValue(employee.getScore());
                if (employee.getLearnTime() == null) {
                    row.createCell(8).setCellValue(0);
                } else {
                    row.createCell(8).setCellValue(employee.getLearnTime());
                }
                row.createCell(9).setCellValue(employee.getGroupName());
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders headers = new HttpHeaders();
            try {
                //将数据表这几个中文的字转码 防止导出后乱码
                Date date = new Date();
                String fileName = DateUtils.parseDate(date) + ".xls";
                headers.setContentDispositionFormData("attachment",
                        new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                hssfWorkbook.write(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<byte[]>(stream.toByteArray(),headers, HttpStatus.CREATED);
        }else
        {
            HSSFSheet sheet = hssfWorkbook.createSheet("个人成绩");
            //设置每一列的宽度
            sheet.setColumnWidth(0, 25 * 256);
            sheet.setColumnWidth(1, 25 * 256);
            sheet.setColumnWidth(2, 15 * 256);
            sheet.setColumnWidth(3, 25 * 256);
            sheet.setColumnWidth(4, 25 * 256);
            sheet.setColumnWidth(5, 25 * 256);
            sheet.setColumnWidth(6, 25 * 256);
            sheet.setColumnWidth(7, 25 * 256);
            sheet.setColumnWidth(8, 15 * 256);
            //6.创建标题行
            HSSFRow r0 = sheet.createRow(0);
            HSSFCell c0 = r0.createCell(0);
            c0.setCellValue("登录时间");
            c0.setCellStyle(headerStyle);
            HSSFCell c1 = r0.createCell(1);
            c1.setCellValue("学号");
            c1.setCellStyle(headerStyle);
            HSSFCell c2 = r0.createCell(2);
            c2.setCellStyle(headerStyle);
            c2.setCellValue("姓名");
            HSSFCell c3 = r0.createCell(3);
            c3.setCellStyle(headerStyle);
            c3.setCellValue("机器号");
            HSSFCell c4 = r0.createCell(4);
            c4.setCellStyle(headerStyle);
            c4.setCellValue("任务单/试卷名称");
            HSSFCell c5 = r0.createCell(5);
            c5.setCellStyle(headerStyle);
            c5.setCellValue("模式");
            HSSFCell c6 = r0.createCell(6);
            c6.setCellStyle(headerStyle);
            c6.setCellValue("IP地址");
            HSSFCell c7 = r0.createCell(7);
            c7.setCellStyle(headerStyle);
            c7.setCellValue("总成绩");
            HSSFCell c8 = r0.createCell(8);
            c8.setCellStyle(headerStyle);
            c8.setCellValue("学习时长(分)");

            for (int i = 0; i < employeeList.size(); i++) {
                UserScoreRecordPOJO employee = employeeList.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(employee.getStudyDate());
                row.createCell(1).setCellValue(employee.getUserStNum());
                row.createCell(2).setCellValue(employee.getUserTrueName());
                row.createCell(3).setCellValue(employee.getMacAddress());
                row.createCell(4).setCellValue(employee.getTaskName());
                if (employee.getStudyType() == 0) {
                    row.createCell(5).setCellValue("练习模式");
                } else {
                    row.createCell(5).setCellValue("考试模式");
                }
                row.createCell(6).setCellValue(employee.getIpAddress());
                row.createCell(7).setCellValue(employee.getScore());
                if (employee.getLearnTime() == null) {
                    row.createCell(8).setCellValue(0);
                } else {
                    row.createCell(8).setCellValue(employee.getLearnTime());
                }
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders headers = new HttpHeaders();
            try {
                //将数据表这几个中文的字转码 防止导出后乱码
                Date date = new Date();
                String fileName = DateUtils.parseDate(date) + ".xls";
                headers.setContentDispositionFormData("attachment",
                        new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                hssfWorkbook.write(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<byte[]>(stream.toByteArray(),headers, HttpStatus.CREATED);
        }
    }
}
