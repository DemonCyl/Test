/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Barcode_1606_Group.java,v 1.3 2018/01/23 04:45:06 Lucy6_Lu Exp $
 */

package mes_sfis.client.ui.barcode;
import base.client.enums.ToolBarItem;
import base.client.util.component.PDateTimeTextField;
import base.client.util.component.P_Component_Meta;
import base.client.ui.BasePanel;
import base.vo.UI_InitVO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Barcode_1606_Group extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Barcode_1606_Group.java,v 1.3 2018/01/23 04:45:06 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2017 PEGATRON Inc. All Rights Reserved.";
	public JLabel type,top,num,date,to;
	public JTextField tfType,tfTop,tfNum,tfTo;
	public PDateTimeTextField tfDate;
	Document document;
	PdfPTable table;
	int width[] = {17,16,17,16,17,17};
	PdfPCell cell = null;
	public Barcode_1606_Group(UI_InitVO uiVO) {
		super(uiVO);
		setUILayout(null);
		type = new JLabel("規格：",JLabel.RIGHT);
		type.setForeground(new Color(106,106,255));
		type.setBounds(50,30,100,25);

		top = new JLabel("料號前綴：",JLabel.RIGHT);
		top.setForeground(new Color(106,106,255));
		top.setBounds(50,70,100,25);

		num = new JLabel("數量：",JLabel.RIGHT);
		num.setForeground(new Color(106,106,255));
		num.setBounds(50,110,100,25);

		to = new JLabel("到：",JLabel.RIGHT);
		to.setForeground(new Color(106,106,255));
		to.setBounds(230,110,20,25);

		date = new JLabel("日期：",JLabel.RIGHT);
		date.setForeground(new Color(106,106,255));
		date.setBounds(50,150,100,25);

		tfType = new JTextField(20);
		tfType.setBounds(180,30,150,25);

		tfTop = new JTextField(20);
		tfTop.setBounds(180,70,150,25);

		tfNum = new JTextField(20);
		tfNum.setBounds(180,110,50,25);

		tfTo = new JTextField(20);
		tfTo.setBounds(280,110,50,25);

		tfDate = new PDateTimeTextField(uiVO,"PDEndTime",150,true,true);
		tfDate.setBounds(180,150,150,25);

		add(type);add(top);add(num);add(date);add(tfType);add(tfTop);add(tfNum);add(tfDate);add(to);add(tfTo);
		setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel,ToolBarItem.Exit});
	}

	public String GetTime(){
		int i = 'A';
		String timestr = null;
		String time = tfDate.getText().toString().trim();
		if(time.equals("")){
			JOptionPane.showConfirmDialog(null,"請輸入時間","提示",JOptionPane.OK_OPTION);
		}else{
			String year = time.substring(2, 4);
			String month = time.substring(5,7);
			String day = time.substring(8,10);

			System.out.println("-------------:"+year);

			int m = Integer.parseInt(month);
			int n = Integer.parseInt(day);
			System.out.println("-------------:"+m);
			System.out.println("-------------:"+n);

			if(m>9){
				month = (char)(i+m-10)+"";
			}
			if(n>9){
				day = (char)(i+n-10)+"";
			}if(m<=9){
				month = m+"";
			}
			if(n<=9){
				day = n+"";
			}
			timestr = year+month+day;
			System.out.println("=========:"+timestr);
			System.out.println("=========:"+timestr);
		}
		return timestr;
	}
	public void Createdesc(int size,String PN,String time,int beginNum,int endNum) throws DocumentException, IOException, InterruptedException {
		document = new Document(new com.lowagie.text.Rectangle(250, 270),3,3,3,3);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\text.pdf"));
		//打?文件
		document.open();
		//添加?容
		// document.add(new Paragraph("HD content here"));
		table = new PdfPTable(6);
		table.setWidths(width);
		System.out.println("*************=========");
		String name = null;
		for(int i=beginNum;i<=endNum;i++){
			String kk = i+"";
//            name = "l1917000J750200006,13A0-5EMCD01,810-05405,18000,7502,N/A";

			if(kk.length()==1){
				name = PN+"0000"+i+time+"C";
			}
			if(kk.length()==2){
				name = PN+"000"+i+time+"C";
			}
			if(kk.length()==3){
				name = PN+"00"+i+time+"C";
			}
			if(kk.length()==4){
				name = PN+"0"+i+time+"C";
			}
			if(kk.length()==5){
				name = PN+i+time+"C";
			}
			CodeZXing.CreateImage(name);
			com.lowagie.text.Image image1 = com.lowagie.text.Image.getInstance("D:\\2008.png");
			image1.scaleAbsolute(5*size,5*size);
			cell = new PdfPCell(image1);
			cell.setFixedHeight(50);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
		}
		document.add(table);

		//??文?
		document.close();
		//????器
		writer.close();
		
	}
	public void daPrint(){
		String PN = tfTop.getText().toString().trim();
		String strSize =  tfType.getText().toString().trim();
		String beginstr = tfNum.getText().toString().trim();
		String endstr = tfTo.getText().toString().trim();
		String time = GetTime();
		if(PN.equals("")||strSize.equals("")||beginstr.equals("")||endstr.equals("")||time.equals("")){
			JOptionPane.showConfirmDialog(null,"提示輸入不能為空","提示",JOptionPane.OK_OPTION);

		}else {
			int size = Integer.parseInt(strSize);
			int beginNum = Integer.parseInt(beginstr);
			int endNum = Integer.parseInt(endstr);
			if((endNum-beginNum)%6!=5){
				endNum = endNum+(5-(endNum-beginNum)%6);
			}
			try {
			System.out.println("========="+endNum);
				Createdesc(size,PN,time,beginNum,endNum);
				System.out.println("*************555555=========");
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("*************666666=========");
			printPdf("D:\\text.pdf");
		}
	}
	public void printPdf(String pdfPath){
		try{
			Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + pdfPath);
			System.out.println("*****************---");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void singleQuery() {
	}

	public void cancel() {
		tfType.setText("");
		tfTo.setText("");
		tfTop.setText("");
		tfDate.setDateText("");
		tfNum.setText("");
		setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Exit});
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
	public void print(){
		String size = tfType.getText().toString().trim();
		String beginstr = tfNum.getText().toString().trim();
		String endstr = tfTo.getText().toString().trim();
		String regex = "^[0-9]*$";
		boolean flg = Pattern.matches(regex, size);
		boolean fff = Pattern.matches(regex, beginstr);
		boolean ggg = Pattern.matches(regex, endstr);
		if(flg == false){
			JOptionPane.showConfirmDialog(null,"規格欄位請輸入數字","提示",JOptionPane.OK_OPTION);
		}else if(fff = false){
			JOptionPane.showConfirmDialog(null,"數量欄位請輸入數字","提示",JOptionPane.OK_OPTION);
		}else if(ggg = false){
			JOptionPane.showConfirmDialog(null,"數量欄位請輸入數字","提示",JOptionPane.OK_OPTION);
		}
		else{
			daPrint();
		}
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
