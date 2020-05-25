package com.esst.ts.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import javax.imageio.ImageIO;


/**
 * 图片添加水印
 * SHY
 */

public class WaterMarkUtil {
    // 水印透明度 
    private static float alpha = 0.25f;
    // 水印横向位置
    private static int positionWidth = 01;
    // 水印纵向位置
    private static int positionHeight = 01;
    // 水印文字颜色
    private static Color color = new Color(0, 0, 0);

    private static Integer degree = -15;

    public static String markImageByText(String logoText, String base64Img) {

        InputStream is = null;
        OutputStream os = null;
        byte[] b;
        try {
            BASE64Decoder base64de = new BASE64Decoder();
            BASE64Encoder base64en = new BASE64Encoder();

            b = base64de.decodeBuffer(base64Img);   //需处理base64 图片

            is = new java.io.ByteArrayInputStream(b);

            BufferedImage srcImg = ImageIO.read(is);

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint
                    (RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font

            srcImg.getWidth();

            BigDecimal fontPx = new BigDecimal(srcImg.getWidth()).divide(new BigDecimal(6), 2, BigDecimal.ROUND_HALF_EVEN);
            System.out.println(fontPx);
            BigDecimal fontSizePx = fontPx.divide(new BigDecimal("96"), 2, BigDecimal.ROUND_HALF_EVEN);
            System.out.println(fontSizePx);
            BigDecimal fontSize = fontSizePx.multiply(new BigDecimal("72"));
            System.out.println(fontSize);
            Font font = new Font("SansSerif", Font.BOLD, fontSize.intValue());

            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)

            //g.drawString(logoText, positionWidth, positionHeight);


            Integer x = srcImg.getWidth() * 2;
            Integer y = srcImg.getHeight() * 2;


            Integer fontX = (srcImg.getWidth() / 3);
            Integer fontY = (srcImg.getWidth() / 4);

            for (int i = 0; i < x / fontX; i++) {    //竖行
                for (int j = 0; j < y / fontY; j++) {   //横行
                    g.drawString(logoText, positionWidth + j * (fontX), positionHeight + fontY * i);
                }
            }
            // 9、释放资源
            g.dispose();
            // 10、生成图片

            //生成base64字符串
            ByteArrayOutputStream bot = new ByteArrayOutputStream();

            ImageIO.write(buffImg, "JPG", bot);
            byte[] rstByte = bot.toByteArray();
            base64Img = base64en.encode(rstByte);          //base64返回值

            /*os = new FileOutputStream("D:hecheng11.jpg");
            ImageIO.write(buffImg, "JPG", os);  //文件返回值*/


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return base64Img;
    }

    public static void setImageMarkOptions(float alpha, int positionWidth, int positionHeight, Font font, Color color) {
        if (alpha != 0.0f) WaterMarkUtil.alpha = alpha;
        if (positionWidth != 0) WaterMarkUtil.positionWidth = positionWidth;
        if (positionHeight != 0) WaterMarkUtil.positionHeight = positionHeight;
        //if (font != null) WaterMarkUtil.font = font;
        if (color != null) WaterMarkUtil.color = color;
    }


    public static void main(String[] args) {
        String logoText = "SINO";
        String baseImg ="";
        String base64Res = markImageByText(logoText, baseImg);
    }

}

