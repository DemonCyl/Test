package mes_sfis.client.ui.barcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import it.sauronsoftware.base64.Base64;

/** * @Description: (二??) * @author：luoguohui * @date：2015-10-29 下午05:27:13 */
public class ZXingCode {
    private static final int QRCOLOR = 0xFF000000;   //默?是黑色
    private static final int BGWHITE = 0xFFFFFFFF;   //背景?色


    /*public static void main(String[] args) throws WriterException
    {
        try
        {
            //getLogoQRCode("https://www.baidu.com/", null, "跳?到百度的二??");
        	getLogoQRCode("https://www.baidu.com/", null, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/


    /** * 生成?logo的二???片 * * @param qrPic * @param logoPic */
    public static String getLogoQRCode(String qrUrl,HttpServletRequest request,String productName)
    {
// String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/images/logoImages/llhlogo.png";
        //filePath是二??logo的路?，但是??中我?是放在?目的某?路?下面的，所以路?用上面的，把下面的注?就好
        String filePath = "D:/x123.png";  //TODO
        String content = qrUrl;
        try
        {
            ZXingCode zp = new ZXingCode();
            BufferedImage bim = zp.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 10, 10, zp.getDecodeHintType());
            return zp.addLogo_QRCode(bim, new File(filePath), new LogoConfig(), productName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** * ?二???片添加Logo * * @param qrPic * @param logoPic */
    public String addLogo_QRCode(BufferedImage bim, File logoPic, LogoConfig logoConfig, String productName)
    {
        try
        {
            /** * ?取二???片，并构建???象 */
            BufferedImage image = bim;
            Graphics2D g = image.createGraphics();

            /** * ?取Logo?片 */
            BufferedImage logo = ImageIO.read(logoPic);
            /** * ?置logo的大小,本人?置?二???片的20%,因??大??掉二?? */
            int widthLogo = logo.getWidth(null)>image.getWidth()*3/10?(image.getWidth()*3/10):logo.getWidth(null), 
                heightLogo = logo.getHeight(null)>image.getHeight()*3/10?(image.getHeight()*3/10):logo.getWidth(null);

            /** * logo放在中心 */
             int x = (image.getWidth() - widthLogo) / 2;
             int y = (image.getHeight() - heightLogo) / 2;
             /** * logo放在右下角 * int x = (image.getWidth() - widthLogo); * int y = (image.getHeight() - heightLogo); */

            //?始?制?片
            g.drawImage(logo, x, y, 0, 0, null);
// g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
// g.setStroke(new BasicStroke(logoConfig.getBorder()));
// g.setColor(logoConfig.getBorderColor());
// g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();

            //把商品名?添加上去，商品名?不要太?哦，?里最多支持?行。太?就?自?截取啦
            if (productName != null && !productName.equals("")) {
                //新的?片，把?logo的二??下面加上文字
                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                //?二??到新的面板
                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                //?文字到新的面板
                outg.setColor(Color.BLACK); 
                outg.setFont(new Font("黑体",Font.BOLD,30)); //字体、字型、字? 
                int strWidth = outg.getFontMetrics().stringWidth(productName);
                if (strWidth > 399) {
// //?度??就截取前面部分
// outg.drawString(productName, 0, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 5 ); //?文字
                    //?度??就?行
                    String productName1 = productName.substring(0, productName.length()/2);
                    String productName2 = productName.substring(productName.length()/2, productName.length());
                    int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
                    int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
                    outg.drawString(productName1, 200  - strWidth1/2, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 );
                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg2 = outImage2.createGraphics();
                    outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                    outg2.setColor(Color.BLACK); 
                    outg2.setFont(new Font("Verdana",Font.BOLD,30)); //字体、字型、字? 
                    outg2.drawString(productName2, 200  - strWidth2/2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight())/2 + 5 );
                    outg2.dispose();
                    outImage2.flush();
                    outImage = outImage2;
                }else {
                    outg.drawString(productName, 200  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 ); //?文字 
                }
                outg.dispose();
                outImage.flush();
                image = outImage;
            }
            logo.flush();
            image.flush();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(image, "png", baos);

            //二??生成的路?，但是???目中，我?是把?生成的二???示到界面上的，因此下面的折行代?可以注?掉
            //可以看到??方法最?返回的是??二??的imageBase64字符串
            //前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/> 其中${imageBase64QRCode}??二??的imageBase64字符串
            //ImageIO.write(image, "png", new File("C:/Users/jieyu_fu/Desktop/TDC-" + new Date().getTime() + "test.png")); //TODO 
			ImageIO.write(image, "png", new File("D:/test.png"));
            String imageBase64QRCode =  new String(Base64.encode(baos.toByteArray()));
            baos.close();
            return imageBase64QRCode;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /** * 构建初始化二?? * * @param bm * @return */
    public BufferedImage fileToBufferedImage(BitMatrix bm)
    {
        BufferedImage image = null;
        try
        {
            int w = bm.getWidth(), h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return image;
    }

    /** * 生成二??bufferedImage?片 * * @param content * ???容 * @param barcodeFormat * ???型 * @param width * ?片?度 * @param height * ?片高度 * @param hints * ?置?? * @return */
    public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints)
    {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try
        {
            multiFormatWriter = new MultiFormatWriter();
            // ???序分??：???容，???型，生成?片?度，生成?片高度，?置??
            bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // ?始利用二???据?建Bitmap?片，分???黑（0xFFFFFFFF）白（0xFF000000）?色
            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
        return image;
    }

    /** * ?置二??的格式?? * * @return */
    public Map<EncodeHintType, Object> getDecodeHintType()
    {
        // 用于?置QR二????
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // ?置QR二??的????（H?最高??）具体??信息
        // ?置??方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);//M,L,H,Q

        return hints;
    }
}

    class LogoConfig
    {
        // logo默??框?色
        public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
        // logo默??框?度
        public static final int DEFAULT_BORDER = 2;
        // logo大小默??照片的1/5
        public static final int DEFAULT_LOGOPART = 5;

        private final int border = DEFAULT_BORDER;
        private final Color borderColor;
        private final int logoPart;

        /** * Creates a default config with on color {@link #BLACK} and off color * {@link #WHITE}, generating normal black-on-white barcodes. */
        public LogoConfig()
        {
            this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
        }

        public LogoConfig(Color borderColor, int logoPart)
        {
            this.borderColor = borderColor;
            this.logoPart = logoPart;
        }

        public Color getBorderColor()
        {
            return borderColor;
        }

        public int getBorder()
        {
            return border;
        }

        public int getLogoPart()
        {
            return logoPart;
        }
    }