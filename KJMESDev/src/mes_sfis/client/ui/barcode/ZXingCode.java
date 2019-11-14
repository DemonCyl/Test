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

/** * @Description: (�G??) * @author�Gluoguohui * @date�G2015-10-29 �U��05:27:13 */
public class ZXingCode {
    private static final int QRCOLOR = 0xFF000000;   //�q?�O�¦�
    private static final int BGWHITE = 0xFFFFFFFF;   //�I��?��


    /*public static void main(String[] args) throws WriterException
    {
        try
        {
            //getLogoQRCode("https://www.baidu.com/", null, "��?��ʫת��G??");
        	getLogoQRCode("https://www.baidu.com/", null, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/


    /** * �ͦ�?logo���G???�� * * @param qrPic * @param logoPic */
    public static String getLogoQRCode(String qrUrl,HttpServletRequest request,String productName)
    {
// String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/images/logoImages/llhlogo.png";
        //filePath�O�G??logo����?�A���O??����?�O��b?�ت��Y?��?�U�����A�ҥH��?�ΤW�����A��U�����`?�N�n
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

    /** * ?�G???���K�[Logo * * @param qrPic * @param logoPic */
    public String addLogo_QRCode(BufferedImage bim, File logoPic, LogoConfig logoConfig, String productName)
    {
        try
        {
            /** * ?���G???���A�}�۫�???�H */
            BufferedImage image = bim;
            Graphics2D g = image.createGraphics();

            /** * ?��Logo?�� */
            BufferedImage logo = ImageIO.read(logoPic);
            /** * ?�mlogo���j�p,���H?�m?�G???����20%,�]??�j??���G?? */
            int widthLogo = logo.getWidth(null)>image.getWidth()*3/10?(image.getWidth()*3/10):logo.getWidth(null), 
                heightLogo = logo.getHeight(null)>image.getHeight()*3/10?(image.getHeight()*3/10):logo.getWidth(null);

            /** * logo��b���� */
             int x = (image.getWidth() - widthLogo) / 2;
             int y = (image.getHeight() - heightLogo) / 2;
             /** * logo��b�k�U�� * int x = (image.getWidth() - widthLogo); * int y = (image.getHeight() - heightLogo); */

            //?�l?��?��
            g.drawImage(logo, x, y, 0, 0, null);
// g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
// g.setStroke(new BasicStroke(logoConfig.getBorder()));
// g.setColor(logoConfig.getBorderColor());
// g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();

            //��ӫ~�W?�K�[�W�h�A�ӫ~�W?���n��?�@�A?���̦h���?��C��?�N?��?�I����
            if (productName != null && !productName.equals("")) {
                //�s��?���A��?logo���G??�U���[�W��r
                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                //?�G??��s�����O
                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                //?��r��s�����O
                outg.setColor(Color.BLACK); 
                outg.setFont(new Font("���^",Font.BOLD,30)); //�r�^�B�r���B�r? 
                int strWidth = outg.getFontMetrics().stringWidth(productName);
                if (strWidth > 399) {
// //?��??�N�I���e������
// outg.drawString(productName, 0, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 5 ); //?��r
                    //?��??�N?��
                    String productName1 = productName.substring(0, productName.length()/2);
                    String productName2 = productName.substring(productName.length()/2, productName.length());
                    int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
                    int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
                    outg.drawString(productName1, 200  - strWidth1/2, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 );
                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg2 = outImage2.createGraphics();
                    outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                    outg2.setColor(Color.BLACK); 
                    outg2.setFont(new Font("Verdana",Font.BOLD,30)); //�r�^�B�r���B�r? 
                    outg2.drawString(productName2, 200  - strWidth2/2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight())/2 + 5 );
                    outg2.dispose();
                    outImage2.flush();
                    outImage = outImage2;
                }else {
                    outg.drawString(productName, 200  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 ); //?��r 
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

            //�G??�ͦ�����?�A���O???�ؤ��A��?�O��?�ͦ����G???�ܨ�ɭ��W���A�]���U�������N?�i�H�`?��
            //�i�H�ݨ�??��k��?��^���O??�G??��imageBase64�r�Ŧ�
            //�e�ݥ� <img src="data:image/png;base64,${imageBase64QRCode}"/> �䤤${imageBase64QRCode}??�G??��imageBase64�r�Ŧ�
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


    /** * �۫ت�l�ƤG?? * * @param bm * @return */
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

    /** * �ͦ��G??bufferedImage?�� * * @param content * ???�e * @param barcodeFormat * ???�� * @param width * ?��?�� * @param height * ?������ * @param hints * ?�m?? * @return */
    public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints)
    {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try
        {
            multiFormatWriter = new MultiFormatWriter();
            // ???�Ǥ�??�G???�e�A???���A�ͦ�?��?�סA�ͦ�?�����סA?�m??
            bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // ?�l�Q�ΤG???�u?��Bitmap?���A��???�¡]0xFFFFFFFF�^�ա]0xFF000000�^?��
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

    /** * ?�m�G??���榡?? * * @return */
    public Map<EncodeHintType, Object> getDecodeHintType()
    {
        // �Τ_?�mQR�G????
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // ?�mQR�G??��????�]H?�̰�??�^���^??�H��
        // ?�m??�覡
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
        // logo�q??��?��
        public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
        // logo�q??��?��
        public static final int DEFAULT_BORDER = 2;
        // logo�j�p�q??�Ӥ���1/5
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