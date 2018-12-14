package com.zhangyingwei.cockroach2.samples.slidestalk.utils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.imageio.plugins.common.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Arrays;

/**
 * @author zhangyw
 * @date: 2018/12/14
 * @desc:
 */
public class ToPdf {
    public static void toPdf(String imageFolderPath, String pdfPath) {
        try {
            // 图片文件夹地址
            // String imageFolderPath = "D:/Demo/ceshi/";
            // 图片地址
            String imagePath = null;
            // PDF文件保存地址
            // String pdfPath = "D:/Demo/ceshi/hebing.pdf";
            // 输入流
            FileOutputStream fos = new FileOutputStream(pdfPath);
            // 创建文档
            Document doc = new Document(null, 0, 0, 0, 0);
            //doc.open();
            // 写入PDF文档
            PdfWriter.getInstance(doc, fos);
            // 读取图片流
            BufferedImage img = null;
            // 实例化图片
            Image image = null;
            // 获取图片文件夹对象
            File file = new File(imageFolderPath);
            File[] files = file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return !name.equals("ads.jpg");
                }
            });
            // 循环获取图片文件夹内的图片
            for (File file1 : files) {
                if (file1.getName().endsWith(".png")
                        || file1.getName().endsWith(".jpg")
                        || file1.getName().endsWith(".gif")
                        || file1.getName().endsWith(".jpeg")
                        || file1.getName().endsWith(".tif")) {
                    // System.out.println(file1.getName());
                    imagePath = imageFolderPath + "/" + file1.getName();
                    System.out.println(file1.getName());
                    // 读取图片流
                    try {
                        img = ImageIO.read(new File(imagePath));
                        doc.setPageSize(new Rectangle(img.getWidth(), img
                                .getHeight()));
                        image = Image.getInstance(imagePath);
                    } catch (Exception e) {
                        System.out.println(imagePath);
                    }
                    // 根据图片大小设置文档大小
                    // 实例化图片
                    // 添加图片到文档
                    doc.open();
                    if (image != null) {
                        doc.add(image);
                    }
                }
            }
            // 关闭文档
            if (doc.isOpen()) {
                doc.close();
            }
            System.out.println("toPDF:" + pdfPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File("D:\\books");
        Arrays.stream(file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.contains(".pdf");
            }
        })).forEach(cdir -> {
            Arrays.stream(cdir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return !name.contains(".pdf");
                }
            })).forEach(ccdir -> {
                String name = ccdir.getName();
                toPdf(ccdir.getPath(), cdir.getPath().concat("/").concat(name).concat(".pdf"));
            });
        });
    }
}
