/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Material_BarCode_image_PEGA.java,v 1.14 2016/11/15 01:25:32 Jieyu_Fu Exp $
 */
package mes_sfis.client.ui.barcode;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import base.client.util.component.PMIDComboBox;
import base.client.util.component.P_Component_Meta;
import base.client.util.MessageUtil;
import base.client.util.StringUtil;
import base.client.enums.ToolBarItem;

import com.klg.jclass.table.data.JCEditableVectorDataSource;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import base.client.ui.BasePanel;
import base.enums.DataSourceType;
import base.vo.ResultVO;
import base.vo.BaseVO;
import base.client.util.BaseServletAgent;
import base.enums.CommandName;
import base.vo.UI_InitVO;
import mes_sfis.client.util.barcode.CartonPrint_1;

/**
 * The <code>Material_BarCode_image_PEGA</code> class.
 *
 * @version     $Name:  $, $Id: Material_BarCode_image_PEGA.java,v 1.14 2016/11/15 01:25:32 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Material_BarCode_image_PEGA.java,v $
 *          Revision 1.14  2016/11/15 01:25:32  Jieyu_Fu
 *          no message
 *
 *          Revision 1.13  2016/11/14 11:48:26  Jieyu_Fu
 *          no message
 *
 *          Revision 1.12  2016/11/14 11:44:00  Jieyu_Fu
 *          no message
 *
 *          Revision 1.11  2016/11/14 11:15:07  Jieyu_Fu
 *          no message
 *
 *          Revision 1.10  2016/11/14 10:48:15  Jieyu_Fu
 *          no message
 *
 *          Revision 1.9  2016/11/14 10:20:37  Jieyu_Fu
 *          no message
 *
 *          Revision 1.8  2016/11/14 06:59:32  Jieyu_Fu
 *          no message
 *
 *          Revision 1.7  2016/11/14 03:54:46  Jieyu_Fu
 *          no message
 *
 *          Revision 1.6  2016/11/14 03:43:27  Jieyu_Fu
 *          no message
 *
 *          Revision 1.5  2016/11/14 01:57:48  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2016/11/11 00:13:29  William_Tsai
 *          no message
 *
 *          Revision 1.2  2016/11/10 11:29:48  William_Tsai
 *          no message
 *
 *          Revision 1.1  2016/11/09 10:31:52  William_Tsai
 *          no message
 *
 *          Revision 1.1  2016/11/08 12:00:51  William_Tsai
 *          no message
 *
 *
 *          Revision 1.1  2016/10/29 05:50:54  William
 *          no message
 *
 *
 *
 */
public class Material_BarCode_image_PEGA extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Material_BarCode_image_PEGA.java,v 1.14 2016/11/15 01:25:32 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    
	String vCUST_PART_NO;
	String vBATCH_NO;
	String vPartNo;
    String vCARTON_PEGA_OID;
	Material_BarCode_image_PEGA self;
	public Material_BarCode_image_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	JTextField jtfMO;
	JTextField jtfLabelQTY;
	JCheckBox jcboxPrint;
	JTextArea jta;
	
	
	void init(){
		self=this;
		self.setUILayout(null);
		
		JLabel jLabel_MO = new JLabel("請輸入工單號 : ",JLabel.RIGHT);
		jLabel_MO.setBounds(new Rectangle(10,45,100,25));
		add(jLabel_MO,null);
		jta = new JTextArea();
		jta.setBounds(new Rectangle(120,150,400,200));
		add(jta,null);
		jta.setEditable(false);
		
		jtfMO = new JTextField();
		jtfMO.setBounds(new Rectangle(120,45,250,25));
		add(jtfMO,null);
		
		
		
		JLabel jLabel_LabelQTY = new JLabel("打印張數 : ",JLabel.RIGHT);
		jLabel_LabelQTY.setBounds(new Rectangle(10,105,100,25));
		add(jLabel_LabelQTY,null);
		
		jtfLabelQTY = new JTextField();
		jtfLabelQTY.setText("1");
		jtfLabelQTY.setBounds(new Rectangle(120,105,250,25));
		add(jtfLabelQTY,null);
		
		jcboxPrint = new JCheckBox("連線打印機");
		jcboxPrint.setBounds(new Rectangle(400,105,100,25));
		add(jcboxPrint,null);
		jcboxPrint.setSelected(true);
		
		
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	
	
	public void singleQuery() {
		String MO = jtfMO.getText().trim();
		jta.setText("");
		if(MO.equals("")){
			setMessage("請輸入工單號!");
			return;
		}
		
		try {
			String sql = "select i.ima01 from sfb_file t , ima_file i where t.sfb05 = i.ima01 and t.sfb01 = '"+MO+"'";
			Hashtable ht = getDBDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
			if(ht == null){
				setMessage("Tiptop 查無此工單");
				return;
			}
			vPartNo = ht.get("IMA01").toString();
			jta.append("PartNo : "+vPartNo+"\n");
			
			Vector arg = new Vector();
			arg.add(vPartNo);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_CARTON_PEGA_PG");
			bvo.setFunctionName("Query_CARTON_INFO");
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
		  	if(result.size()<2){
		  		setMessage("MES 無定義廠內料號與客戶料號對應");
				return;
		  	}
			Hashtable row = (Hashtable)result.get(1);
			vCUST_PART_NO = row.get("CUST_PART_NO").toString();
			vBATCH_NO = row.get("BATCH_NO").toString();
			vCARTON_PEGA_OID = row.get("CARTON_PEGA_OID").toString();
			jta.append("CustPartNo : "+vCUST_PART_NO+"   BATCH_NO : "+vBATCH_NO+"\n");
			jtfMO.setEditable(false);
			setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel,ToolBarItem.Exit});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void cancel() {
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
		jtfMO.setEditable(true);
		jta.setText("");
		vPartNo = null;
		vCUST_PART_NO = null;
		vBATCH_NO = null;
		vCARTON_PEGA_OID = null;
	}
	
	public void print() {
		
		
		String printNum=jtfLabelQTY.getText().trim();
		if(printNum.equals("")){
			setMessage("請輸入打印份數!");
			return;
		}
		
		if(!isMatcherPatternStr(printNum,"[0-9]")){
			setMessage("請輸入打印份數(數字 0-9 )!");
			return;
		}
		
		
		doPrint(jcboxPrint.isSelected(),printNum);
		
		
	}
	
	private void doPrint(boolean isPrint,String printNum){
		String printStr = "";
		String memo = "";
		int num=Integer.parseInt(printNum);
		
		String filePath = "d:/IfYouLikeYouMayDeleteMe.pdf";
		try {
			Document document = new Document(new com.lowagie.text.Rectangle(130,30));
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			 //BarcodeEAN code=new  BarcodeEAN();
			Barcode128 barcode = new Barcode128();
			barcode.setCodeType(BarcodeEAN.EAN13);
			barcode.setTextAlignment(Element.ALIGN_LEFT);
			//barcode.setBarHeight(barcode.getSize() * 0.8f);//設置條碼高度
			//barcode.setSize(barcode.getSize()* 0.5f);
			
			PdfContentByte pcb = writer.getDirectContent();
			for(int i=1;i<=num;i++){
				
				Vector arg = new Vector();
				arg.add(vCARTON_PEGA_OID);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_MATERIAL_PEGA_PG");
				bvo.setFunctionName("Query_MATERIAL_INFO");
				ResultVO rvo = bsa.doFunc(bvo);
			  	Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				printStr = row.get("CUST_BARCODE").toString();
				//memo = row.get("MEMO").toString();
				
				
				barcode.setCode(printStr);
				barcode.setStartStopText(false);
				Image img1 = barcode.createImageWithBarcode(pcb, null, null);
				img1.setAbsolutePosition(5, 10);
				//img1.setXYRatio((float)0.5);
				//img1.setWidthPercentage((float)0.5);
				pcb.addImage(img1,120,0,0,20,5,7);//width,0,0,hight,x,y
				
				if(!memo.equals("")){
					BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED); 
					Font font = new Font(bfChinese,4);  
					pcb.beginText();
					pcb.setFontAndSize(bfChinese, 4);
					pcb.showTextAligned(PdfContentByte.ALIGN_LEFT,   memo,   5,   6,   0);
					pcb.endText();
				}
				document.newPage();
			}					
			document.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("export ok");
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("打印出錯！");
			}
		}else{
			setMessage("資料已匯出到   d:/IfYouLikeYouMayDeleteMe.pdf ");
		}
	}
	
	
	
	
	
	
	
	
	
	public static boolean printPdf(String pdfPath){   
        try{   
            Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + pdfPath);   
            return true;   
        }catch(Exception e){   
            e.printStackTrace();   
            return false;   
        }   
    }
	public Hashtable getDBDATA(String sql,String ds,UI_InitVO uiVO)throws Exception{
		String reString = "";
		Vector arg = new Vector();
		arg.add(sql);
	    BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
		BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
		bvo.setDataSourceType(ds);
		ResultVO rvo = bsa1.doFunc(bvo);
		Vector result = rvo.getData();
		Hashtable ht = null;
		if(result.size() == 2){			
			ht = (Hashtable)result.elementAt(1);
		}
		return ht;
	}
	public boolean isMatcherPatternStr(String valueStr,String patternStr){
		    int charNum=0;
		    char[] chs = valueStr.toCharArray();
			for(char ch:chs){
				Pattern pattern = Pattern.compile(patternStr);
				Matcher matcher = pattern.matcher(String.valueOf(ch));
				if(matcher.matches() == false){
					JOptionPane.showMessageDialog(null, "請輸入正確的打印條碼內容！");
					return false;
				}
			}
			return true;
	 }

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void email() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void export() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void help() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multiQuery() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Hashtable<String, P_Component_Meta> needValidateComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReportOid(String reportOid) {
		// TODO Auto-generated method stub
		
	}

	

}
