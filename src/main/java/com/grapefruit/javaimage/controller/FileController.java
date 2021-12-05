/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import com.grapefruit.javaimage.entity.Grape;
import com.grapefruit.javaimage.http.req.BaseReq;
import com.grapefruit.javaimage.http.rsp.AjaxResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * excel上传与下载
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-04 5:28 上午
 */
@RestController
@RequestMapping("/")
public class FileController {

    @RequestMapping("/fileUpload")
    public AjaxResult fileUpload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        // 获取取消文件后缀的文件名(excel.xlsx)
        String originalFilename = file.getOriginalFilename();

        // 获取输入流
        InputStream inputStream = file.getInputStream();

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        //TODO 文件校验

        // 输出文件
        OutputStream os = new FileOutputStream("/Users/grpae/Desktop/" + originalFilename);
        workbook.write(os);
        os.flush();
        os.close();
        workbook.close();

        return AjaxResult.success();
    }

    @RequestMapping("/download")
    public void download(HttpServletResponse response, @RequestBody BaseReq req) throws IOException {
        String uid = req.getUid();
        System.out.println("uid:" + uid + " ==========>");

        // 创建excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作页
        XSSFSheet sheet = workbook.createSheet();
        // 设置第一列
        XSSFRow row = sheet.createRow(0);
        XSSFCell c0 = row.createCell(0);
        c0.setCellValue("Name");
        XSSFCell c1 = row.createCell(1);
        c1.setCellValue("Job");
        XSSFCell c2 = row.createCell(2);
        c2.setCellValue("Phone");

        // 模拟数据
        Grape a = new Grape("aaa", "driver", "1363636363636");
        Grape b = new Grape("bbb", "programer", "1363636363699");
        Grape c = new Grape("ccc", "student", "1363636363688");
        Grape a1 = new Grape("aaa", "driver", "1363636363636");
        Grape b2 = new Grape("bbb", "programer", "1363636363699");
        Grape c3 = new Grape("ccc", "student", "1363636363688");
        List<Grape> list = Arrays.asList(a, b, c, a1, b2, c3);

        // 将数据转换到excel文件
        for (int i = 0; i < list.size(); i++) {
            Grape grape = list.get(i);
            XSSFRow rowi = sheet.createRow(i + 1);

            XSSFCell ci0 = rowi.createCell(0);
            ci0.setCellValue(grape.getName());
            XSSFCell ci1 = rowi.createCell(1);
            ci1.setCellValue(grape.getJob());
            XSSFCell ci2 = rowi.createCell(2);
            ci2.setCellValue(grape.getPhone());
        }

        // 文件名
        String fileName = "下载.xls";

        response.setHeader("Content-type", "application/vnd.ms-excel");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=ffffff.xlsx");
        response.addHeader("Access-Control-Expose-Headers","Content-Disposition");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }


    @RequestMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
        //文件路径
        String path = "src/main/java/com/grapefruit/javaimage/controller/Template.xlsx";

        // 设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String("TemplateGrape.xlsx".getBytes("UTF-8"), "iso-8859-1"));
        response.addHeader("Access-Control-Expose-Headers","Content-Disposition");
        try {
            // 打开本地文件流
            InputStream inputStream = new FileInputStream(path);
            // 激活下载操作
            OutputStream os = response.getOutputStream();

            // 循环写入输出流 10KB
            byte[] b = new byte[10 * 1024 * 8];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
