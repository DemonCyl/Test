/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Material_BarCode_table_PEGA.java,v 1.20 2018/04/10 15:08:15 Lucy6_Lu Exp $
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
import java.util.*;
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
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.Paragraph;
/**
 * The <code>Material_BarCode_table_PEGA</code> class.
 *
 * @version     $Name:  $, $Id: Material_BarCode_table_PEGA.java,v 1.20 2018/04/10 15:08:15 Lucy6_Lu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Material_BarCode_table_PEGA.java,v $
 *          Revision 1.20  2018/04/10 15:08:15  Lucy6_Lu
 *          Rejected commit: Default
 *
 *          Revision 1.19  2016/11/22 08:14:59  Jieyu_Fu
 *          no message
 *
 *          Revision 1.18  2016/11/16 00:31:03  Jieyu_Fu
 *          no message
 *
 *          Revision 1.17  2016/11/15 11:08:05  Jieyu_Fu
 *          no message
 *
 *          Revision 1.16  2016/11/15 08:34:39  Jieyu_Fu
 *          no message
 *
 *          Revision 1.15  2016/11/15 01:38:09  Jieyu_Fu
 *          no message
 *
 *          Revision 1.14  2016/11/15 01:29:28  Jieyu_Fu
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
public class Material_BarCode_table_PEGA extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Material_BarCode_table_PEGA.java,v 1.20 2018/04/10 15:08:15 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	String vCUST_PART_NO;
	String vBATCH_NO;
	String vPartNo;
    String vCARTON_PEGA_OID;
	Material_BarCode_table_PEGA self;
	public Material_BarCode_table_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	JTextField jtfMO;
	JTextField jtfLabelQTY;
	JCheckBox jcboxPrint;
	JTextArea jta;
	static PdfPTable			pdftable;
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
	public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth) {
		PdfContentByte cd = writer.getDirectContent();
		Barcode128 code39ext = new Barcode128();
		code39ext.setStartStopText(false);
		code39ext.setExtended(true);
		code39ext.setTextAlignment(Element.ALIGN_LEFT);
		code39ext.setBarHeight(15f);
		code39ext.setX(0.5f);

		code39ext.setCode(codeStr);
		Image image39 = code39ext.createImageWithBarcode(cd, null, null);

		PdfPCell barcodeCell = new PdfPCell(image39);
		barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		barcodeCell.setPadding(2);
		barcodeCell.setBorder(0);
		return barcodeCell;
	}
	
	private void doPrint(boolean isPrint,String printNum){
		String printStr = "";
		String memo = "";
		int num=Integer.parseInt(printNum);
		
		String filePath = "d:/Material_Det_PEGA_"+new Date().getTime()+".pdf";
		try {
			Document document = new Document(new com.lowagie.text.Rectangle(145, 35), -25, 10, 0, 0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
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
				memo = row.get("MEMO").toString();
				createPdfTable(writer,printStr);
				document.add(pdftable);
			}				
			document.close();
			writer.close();
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
	public PdfPCell createCell(String cellStr, Font font, int rowHeight, int borderNum) {
		PdfPCell newCell = new PdfPCell(new Paragraph(cellStr, font));
		newCell.setBorder(borderNum);
		if (rowHeight > 0) {
			newCell.setMinimumHeight(rowHeight);
		}

		return newCell;
	}
	public void createPdfTable(PdfWriter writer,String mmo) {
		pdftable = new PdfPTable(2);
		pdftable.setTotalWidth(130);
		int[] a = { 30, 115 };
		try {
			pdftable.setWidths(a);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pdftable.setLockedWidth(true);
		pdftable.getDefaultCell().setBorder(0);//PdfPCell.NO_BORDER
		pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell pega = new PdfPCell(new Paragraph("", null));
		pega.setBorder(0);
		pdftable.addCell(pega);
		pdftable.addCell(createBarCode(writer, mmo, a[1]));

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
