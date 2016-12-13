package com.duanrong.zhongjin;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by xiao on 2015/12/22.
 */
public final class ImageUtil {

    /**
     * 把图片印刷到图片上
     *
     * @param pressImg --
     *            水印文件
     * @param targetImg --
     *            目标文件
     * @param x
     *            水印高度比
     * @param y
     *            水印宽度比
     *            
     * @param alpha 水印透明度
     */
    public final static   void  pressImage(String pressImg, String targetImg, double  x,  double  y, float alpha) {
        try  {
            //目标文件
            File _file = new  File(targetImg);
            Image src = ImageIO.read(_file);
            int  width = src.getWidth( null );
            int  height = src.getHeight( null );
            BufferedImage image = new  BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0 ,  0 , width, height,  null );
            //水印文件
            File _filebiao = new  File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            //src_biao = src_biao.getScaledInstance((int)(x*width), (int)(y*height), src_biao.SCALE_SMOOTH);
            int  wideth_biao = src_biao.getWidth(null );
            int  height_biao = src_biao.getHeight( null );
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            g.drawImage(src_biao, (width - wideth_biao) / 2 ,
                    (height - height_biao) / 2 , wideth_biao, height_biao,  null );


            //水印文件结束
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch  (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 把图片印刷到图片上
     *
     * @param pressImg --
     *            水印文件
     * @param targetImg --
     *            目标文件
     */
    public final static void  pressImage(String pressImg, String targetImg) {
        pressImage(pressImg, targetImg, 1,  1.2, 0.8f);
    }


    public   static   void  main(String[] args) {
        for(int i = 5; i<=20; i++){
            pressImage( "D:/image/wklogo.png","D:/image/"+i+".jpg");
        }

    }
}
