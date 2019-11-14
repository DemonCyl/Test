//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mes_sfis.client.ui.barcode;

import base.client.ui.BasePanel;
import base.client.util.StringUtil;
import base.client.util.component.PDateTimeTextField;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import mes_sfis.client.ui.barcode.CodeZXing;
import mes_sfis.client.util.DataHandler;

public class Barcode_1901FOX extends BasePanel {
	public static final String VERSION = "$Name:  $, $Id: Barcode_1901FOX.java,v 1 1111/11/11 11:11:11 KingWolf Exp $";
	public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	private Barcode_1901FOX self;
	private Font f15;
	private Font f14;
	private Font f14b;
	private Font f15c;
	private Font f14c;
	private static PdfPTable pdftable;
	private JTabbedPane typeChange;
	private String type1 = "外箱標籤詳細"; 
	private String type2 = "外箱標籤二維碼";
	private String type3 = "棧板標籤";
	private JPanel panel1 = new JPanel((LayoutManager)null);
	private JPanel panel2 = new JPanel((LayoutManager)null);
	private JPanel panel3 = new JPanel((LayoutManager)null);
	private JTextField textPro = new JTextField();
	private JComboBox textAPN = new JComboBox();
	private JComboBox textHHPN = new JComboBox();
	private JTextField textDes = new JTextField();
	private JTextField textRev = new JTextField();
	private JTextField textSupplier = new JTextField();
	private JTextField textPin = new JTextField();
	private JTextField textXing = new JTextField();
	private JTextField textCountry = new JTextField();
	private PDateTimeTextField PDTime;
	private JTextField textQty = new JTextField();
	private JTextField jtfLabelQTY = new JTextField();
	private JCheckBox jcboxPrint;
	private JTextField textApplePN = new JTextField();
	private JComboBox comboboxButtonType = new JComboBox();
	private JComboBox comboboxClassType = new JComboBox();
	private JComboBox comboboxLineType = new JComboBox();
	private JComboBox textHHPN2 = new JComboBox();
	private JTextField textLotNo = new JTextField();
	private JTextField textQty2 = new JTextField();
	private JTextField textPKGID = new JTextField();
	private JComboBox textMfrPN = new JComboBox();
	private PDateTimeTextField PDTime2;
	private JTextField jtfLabelQTY2 = new JTextField();
	private JCheckBox jcboxPrint2;
	private String HHPN = "HH P/N:";
	private String LotNo = "Lot No:";
	private String Qty = "QTY:";
	private String PKGID = "PKG ID:";
	private String MfrPN = "Mfr P/N:";
	private String DateCode = "Date Code:";
	private String ChangNo = "廠商簡碼";
	private String LcStart = "RSA";
	private String Config_Code = "0";
	private String HDBT = "HDBT";
	private String RGBT = "RGBT";
	private String VLBT = "VLBT";
	private String baiClass = "白班";
	private String yeClass = "夜班";
	private boolean isItemLinkApp = true;
	private boolean isItemLinkHH = true;
	private DataHandler dh;

	public Barcode_1901FOX(UI_InitVO var1) {
		super(var1);
		this.dh = new DataHandler(var1);
		this.init();
	}

	private void init() {
		this.self = this;
		this.self.setUILayout(new BorderLayout());
		this.typeChange = new JTabbedPane();
		this.typeChange.addTab(this.type1, this.panel1);
		this.typeChange.addTab(this.type2, this.panel2);
		this.typeChange.addTab(this.type3, this.panel3);
		this.add(this.typeChange, "Center");
		this.panelAddComponent1();
		this.panelAddComponent2();
		this.panelAddComponent3();
		this.setStatus(new int[]{14, 2, 15});
	}

	private void panelAddComponent1() {
		JLabel var1 = new JLabel("Program");
		var1.setBounds(0, 0, 100, 25);
		this.panel1.add(var1);
		this.textPro.setBounds(110, 0, 150, 25);
		this.panel1.add(this.textPro);
		this.textPro.setText("MP");
		JLabel var2 = new JLabel("Apple PN");
		var2.setBounds(0, 35, 100, 25);
		this.panel1.add(var2);
		this.textAPN.setBounds(110, 35, 150, 25);
		this.panel1.add(this.textAPN);
		this.textAPN.setEditable(true);
		this.textAPN.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent var1) {
				String var2 = StringUtil.NulltoSpace(Barcode_1901FOX.this.textAPN.getSelectedItem()).trim();
				if(!"".equals(var2) && Barcode_1901FOX.this.isItemLinkApp) {
					Barcode_1901FOX.this.setPNInfo("APPLE_PN", var2, true);
				}

			}
		});
		JLabel var3 = new JLabel("HH PN");
		var3.setBounds(0, 70, 100, 25);
		this.panel1.add(var3);
		this.textHHPN.setBounds(110, 70, 150, 25);
		this.panel1.add(this.textHHPN);
		this.textHHPN.setEditable(true);
		this.textHHPN.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent var1) {
				String var2 = StringUtil.NulltoSpace(Barcode_1901FOX.this.textHHPN.getSelectedItem()).trim();
				if(!"".equals(var2) && Barcode_1901FOX.this.isItemLinkHH) {
					Barcode_1901FOX.this.setPNInfo("HH_PN", var2, false);
				}

			}
		});
		JLabel var4 = new JLabel("Des");
		var4.setBounds(0, 105, 100, 25);
		this.panel1.add(var4);
		this.textDes.setBounds(110, 105, 150, 25);
		this.panel1.add(this.textDes);
		JLabel var5 = new JLabel("Rev");
		var5.setBounds(0, 140, 100, 25);
		this.panel1.add(var5);
		this.textRev.setBounds(110, 140, 150, 25);
		this.panel1.add(this.textRev);
		JLabel var6 = new JLabel("Supplier");
		var6.setBounds(0, 175, 100, 25);
		this.panel1.add(var6);
		this.textSupplier.setBounds(110, 175, 150, 25);
		this.panel1.add(this.textSupplier);
		this.textSupplier.setText("CaseTek");
		JLabel var7 = new JLabel("品牌");
		var7.setBounds(0, 210, 100, 25);
		this.panel1.add(var7);
		this.textPin.setBounds(110, 210, 150, 25);
		this.panel1.add(this.textPin);
		this.textPin.setText("無品牌");
		JLabel var8 = new JLabel("型號");
		var8.setBounds(0, 245, 100, 25);
		this.panel1.add(var8);
		this.textXing.setBounds(110, 245, 150, 25);
		this.panel1.add(this.textXing);
		this.textXing.setText("無型號");
		JLabel var9 = new JLabel("Country of origin");
		var9.setBounds(0, 280, 100, 25);
		this.panel1.add(var9);
		this.textCountry.setBounds(110, 280, 150, 25);
		this.panel1.add(this.textCountry);
		this.textCountry.setText("中國");
		JLabel var10 = new JLabel("Date Code");
		var10.setBounds(0, 315, 100, 25);
		this.panel1.add(var10);
		this.PDTime = new PDateTimeTextField(this.uiVO, "PDTime", 150, true, true);
		this.PDTime.setBounds(110, 315, 150, 25);
		this.panel1.add(this.PDTime);
		JLabel var11 = new JLabel("Qty");
		var11.setBounds(0, 350, 100, 25);
		this.panel1.add(var11);
		this.textQty.setBounds(110, 350, 150, 25);
		this.panel1.add(this.textQty);
		JLabel var12 = new JLabel("請輸入打印數量");
		var12.setBounds(0, 385, 100, 25);
		this.panel1.add(var12);
		this.jtfLabelQTY.setBounds(110, 385, 150, 25);
		this.jtfLabelQTY.setText("1");
		this.panel1.add(this.jtfLabelQTY);
		this.jcboxPrint = new JCheckBox("連線打印机");
		this.jcboxPrint.setBounds(270, 385, 100, 25);
		this.panel1.add(this.jcboxPrint);
		this.jcboxPrint.setSelected(true);
		this.setPNItem();
	}

	private void panelAddComponent2() {
		JLabel var1 = new JLabel(this.HHPN);
		var1.setBounds(0, 0, 100, 25);
		this.panel2.add(var1);
		this.textHHPN2.setBounds(110, 0, 150, 25);
		this.panel2.add(this.textHHPN2);
		this.textHHPN2.setEditable(true);
		this.textHHPN2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent var1) {
				String var2 = StringUtil.NulltoSpace(Barcode_1901FOX.this.textHHPN2.getSelectedItem()).trim();
				if(!"".equals(var2) && Barcode_1901FOX.this.isItemLinkHH) {
					Barcode_1901FOX.this.setPNInfo2("HH_PN", var2, false);
				}

			}
		});
		JLabel var2 = new JLabel(this.LotNo);
		var2.setBounds(0, 35, 100, 25);
		this.panel2.add(var2);
		var2.setVisible(false);
		this.textLotNo.setBounds(110, 35, 150, 25);
		this.panel2.add(this.textLotNo);
		this.textLotNo.setVisible(false);
		JLabel var3 = new JLabel(this.Qty);
		var3.setBounds(0, 35, 100, 25);
		this.panel2.add(var3);
		this.textQty2.setBounds(110, 35, 150, 25);
		this.panel2.add(this.textQty2);
		JLabel var4 = new JLabel(this.ChangNo);
		var4.setBounds(0, 70, 100, 25);
		this.panel2.add(var4);
		this.textPKGID.setBounds(110, 70, 150, 25);
		this.panel2.add(this.textPKGID);
		this.textPKGID.setText("RS");
		JLabel var5 = new JLabel(this.MfrPN);
		var5.setBounds(0, 105, 100, 25);
		this.panel2.add(var5);
		this.textMfrPN.setBounds(110, 105, 150, 25);
		this.panel2.add(this.textMfrPN);
		this.textMfrPN.setEditable(true);
		this.textMfrPN.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent var1) {
				String var2 = StringUtil.NulltoSpace(Barcode_1901FOX.this.textMfrPN.getSelectedItem()).trim();
				if(!"".equals(var2) && Barcode_1901FOX.this.isItemLinkApp) {
					Barcode_1901FOX.this.setPNInfo2("RS_PN", var2, true);
				}

			}
		});
		JLabel var6 = new JLabel(this.DateCode);
		var6.setBounds(0, 140, 100, 25);
		this.panel2.add(var6);
		this.PDTime2 = new PDateTimeTextField(this.uiVO, "PDTime2", 150, true, true);
		this.PDTime2.setBounds(110, 140, 150, 25);
		this.panel2.add(this.PDTime2);
		JLabel var7 = new JLabel("Apple PN");
		var7.setBounds(0, 175, 100, 25);
		this.panel2.add(var7);
		this.textApplePN.setBounds(110, 175, 150, 25);
		this.panel2.add(this.textApplePN);
		JLabel var8 = new JLabel("按鍵種類");
		var8.setBounds(0, 210, 100, 25);
		this.panel2.add(var8);
		this.comboboxButtonType.setBounds(110, 210, 150, 25);
		this.panel2.add(this.comboboxButtonType);
		this.comboboxButtonType.removeAllItems();
		this.comboboxButtonType.addItem(this.HDBT);
		this.comboboxButtonType.addItem(this.RGBT);
		this.comboboxButtonType.addItem(this.VLBT);
		this.comboboxButtonType.setSelectedIndex(0);
		JLabel var9 = new JLabel("班別");
		var9.setBounds(0, 245, 100, 25);
		this.panel2.add(var9);
		this.comboboxClassType.setBounds(110, 245, 150, 25);
		this.panel2.add(this.comboboxClassType);
		this.comboboxClassType.removeAllItems();
		this.comboboxClassType.addItem(this.baiClass);
		this.comboboxClassType.addItem(this.yeClass);
		this.comboboxClassType.setSelectedIndex(0);
		JLabel var10 = new JLabel("線體");
		var10.setBounds(0, 280, 100, 25);
		this.panel2.add(var10);
		this.comboboxLineType.setBounds(110, 280, 150, 25);
		this.panel2.add(this.comboboxLineType);
		this.comboboxLineType.removeAllItems();

		for(int var11 = 1; var11 < 11; ++var11) {
			String var12;
			if(var11 < 10) {
				var12 = "0" + var11;
			} else {
				var12 = Integer.toString(var11);
			}

			this.comboboxLineType.addItem(var12);
		}

		this.comboboxLineType.setSelectedIndex(0);
		JLabel var13 = new JLabel("請輸入打印數量");
		var13.setBounds(0, 315, 100, 25);
		this.panel2.add(var13);
		this.jtfLabelQTY2.setBounds(110, 315, 150, 25);
		this.jtfLabelQTY2.setText("1");
		this.panel2.add(this.jtfLabelQTY2);
		this.jcboxPrint2 = new JCheckBox("連線打印机");
		this.jcboxPrint2.setBounds(270, 315, 100, 25);
		this.panel2.add(this.jcboxPrint2);
		this.jcboxPrint2.setSelected(true);
	}

	private void panelAddComponent3() {
	}

	private String getFlowNum(String var1, String var2) {
		String var3 = "00001";
		String var4 = "select max(flownum_" + var1 + ") NN from SFIS_LABLE_1901FOX where to_char(fn_time,\'YYYY-MM-DD\') = \'" + var2 + "\'";

		try {
			Hashtable var5 = this.dh.getDataOne(var4, "jdbc/MultiCompanyCurrentDB");
			if(var5 == null || var5.get("NN") == null) {
				return var3;
			}

			var3 = Integer.toString(Integer.parseInt(var5.get("NN").toString()) + 1);

			for(int var6 = var3.length(); var6 < 5; ++var6) {
				var3 = "0" + var3;
			}
		} catch (Exception var7) {
			var7.printStackTrace();
		}

		return var3;
	}

	public void singleQuery() {
		this.setStatus(new int[]{14, 2, 15});
	}

	public void cancel() {
		this.setStatus(new int[]{14, 2, 15});
	}

	public void print() {
		String var2 = "1";
		int var4 = this.typeChange.getSelectedIndex();
		String var1;
		boolean var3;
		if(var4 == 0) {
			var1 = this.jtfLabelQTY.getText().trim();
			var3 = this.jcboxPrint.isSelected();
			if(this.PDTime.getText().equals("")) {
				JOptionPane.showMessageDialog((Component)null, "請選擇日期");
				return;
			}

			var2 = this.textQty.getText().trim();
		} else {
			if(var4 != 1) {
				JOptionPane.showMessageDialog((Component)null, "暫無此功能打印");
				return;
			}

			var1 = this.jtfLabelQTY2.getText().trim();
			var3 = this.jcboxPrint2.isSelected();
			if(this.PDTime2.getText().equals("")) {
				JOptionPane.showMessageDialog((Component)null, "請選擇日期");
				return;
			}

			if(this.textApplePN.getText().trim().length() < 4) {
				JOptionPane.showMessageDialog((Component)null, "請輸入正確的APPLE PN");
				return;
			}

			var2 = this.textQty2.getText().trim();
		}

		if(var1.equals("")) {
			JOptionPane.showMessageDialog((Component)null, "請輸入打印數量");
		} else if(!this.isMatcherPatternStr(var1, "[0-9]")) {
			JOptionPane.showMessageDialog((Component)null, "請輸入數字（0-9 )!");
		} else if(!this.isMatcherPatternStr(var2, "[0-9]")) {
			JOptionPane.showMessageDialog((Component)null, "QTY為數字");
		} else {
			this.doPrint(var3, var1, var4);
		}
	}

	public PdfPCell createBarCode(PdfWriter var1, String var2, int var3, Float var4) {
		PdfContentByte var5 = var1.getDirectContent();
		Barcode128 var6 = new Barcode128();
		var6.setStartStopText(false);
		var6.setExtended(true);
		var6.setTextAlignment(0);
		var6.setBarHeight(var4.floatValue());
		var6.setX(0.5F);
		var6.setFont((BaseFont)null);
		var6.setCode(var2);
		Image var7 = var6.createImageWithBarcode(var5, (BaseColor)null, (BaseColor)null);
		PdfPCell var8 = new PdfPCell(var7);
		var8.setHorizontalAlignment(0);
		var8.setPadding(1.0F);
		var8.disableBorderSide(15);
		return var8;
	}

	private void doPrint(boolean var1, String var2, int var3) {
		int var4 = Integer.parseInt(var2);
		String var5 = "d:/FOX.pdf";

		try {
			Document var6;
			if(var3 == 0) {
				var6 = new Document(new Rectangle(220.0F, 230.0F), 10.0F, 0.0F, 30.0F, 0.0F);
			} else {
				var6 = new Document(new Rectangle(280.0F, 180.0F), 10.0F, 0.0F, 30.0F, 0.0F);
			}

			PdfWriter var7 = PdfWriter.getInstance(var6, new FileOutputStream(var5));
			var6.open();

			for(int var8 = 1; var8 <= var4; ++var8) {
				if(var3 == 0) {
					this.printNoBarCode(var7);
				} else {
					this.createPdfTable(var7);
				}

				var6.add(pdftable);
			}

			var6.close();
			var7.close();
		} catch (FileNotFoundException var9) {
			var9.printStackTrace();
		} catch (DocumentException var10) {
			var10.printStackTrace();
		} catch (Exception var11) {
			var11.printStackTrace();
		}

		System.out.println("export ok");
		if(var1) {
			if(!printPdf(var5)) {
				JOptionPane.showMessageDialog((Component)null, "");
			}
		} else {
			JOptionPane.showMessageDialog((Component)null, "地址" + var5);
		}

	}

	public static boolean printPdf(String var0) {
		try {
			Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + var0);
			return true;
		} catch (Exception var2) {
			var2.printStackTrace();
			return false;
		}
	}

	public boolean isMatcherPatternStr(String var1, String var2) {
		boolean var3 = false;
		char[] var4 = var1.toCharArray();
		char[] var5 = var4;
		int var6 = var4.length;

		for(int var7 = 0; var7 < var6; ++var7) {
			char var8 = var5[var7];
			Pattern var9 = Pattern.compile(var2);
			Matcher var10 = var9.matcher(String.valueOf(var8));
			if(!var10.matches()) {
				JOptionPane.showMessageDialog((Component)null, "WAIT");
				return false;
			}
		}

		return true;
	}

	public PdfPCell createCell(String var1, Font var2, int var3, int var4) {
		PdfPCell var5 = new PdfPCell(new Paragraph(var1, var2));
		var5.setBorder(var4);
		if(var3 > 0) {
			var5.setMinimumHeight((float)var3);
		}

		return var5;
	}

	public void createPdfTable(PdfWriter var1) {
		this.initFont();
		pdftable = new PdfPTable(3);
		pdftable.setTotalWidth(270.0F);
		int[] var2 = new int[]{45, 10, 45};

		try {
			pdftable.setWidths(var2);
		} catch (DocumentException var24) {
			var24.printStackTrace();
		}

		try {
			pdftable.setLockedWidth(true);
			pdftable.getDefaultCell().setBorder(0);
			pdftable.setHorizontalAlignment(0);
			String var3 = this.textPKGID.getText().trim();

			for(int var4 = var3.length(); var4 < 5; ++var4) {
				var3 = "0" + var3;
			}

			String var26 = "H";
			String var5 = this.PDTime2.getText();
			SimpleDateFormat var6 = new SimpleDateFormat("yyyy-MM-dd");
			var5 = var6.format(var6.parse(var5));
			int var7 = Integer.parseInt(var5.substring(5, 7));
			String var8 = Integer.toString(var7);
			if(var7 == 10) {
				var8 = "A";
			} else if(var7 == 11) {
				var8 = "B";
			} else if(var7 == 12) {
				var8 = "C";
			}

			var8 = var5.substring(0, 4) + var8 + var5.substring(8, 10);
			String var9 = this.getFlowNum(var26, var5);
			var3 = var26 + var3 + var8 + var9;
			String var10 = this.textApplePN.getText().trim();
			String var11 = this.comboboxButtonType.getSelectedItem().toString();
			String var12 = "2";
			if(var11.equals(this.RGBT)) {
				var12 = "3";
			} else if(var11.equals(this.HDBT) || var11.equals(this.VLBT)) {
				var12 = "2";
			}

			String var13 = this.comboboxClassType.getSelectedItem().toString();
			if(var13.equals(this.baiClass)) {
				var13 = "1";
			} else {
				var13 = "2";
			}

			Calendar var14 = Calendar.getInstance();
			var14.setFirstDayOfWeek(2);
			var14.setTime(var6.parse(var5));
			String var15 = String.valueOf(var14.get(3));
			if(var15.length() < 2) {
				var15 = "0" + var15;
			}

			String var16 = String.valueOf(var14.get(7));
			String var17 = String.valueOf(var14.get(1)).substring(3, 4) + var15 + var16;
			var10 = this.LcStart + var10.substring(var10.length() - 4, var10.length()) + var11 + var12 + this.Config_Code + var17 + var13 + this.comboboxLineType.getSelectedItem().toString() + "0" + var9;
			this.textLotNo.setText(var3);
			String var18 = this.textHHPN2.getSelectedItem().toString().trim();
			String var19 = this.textLotNo.getText().trim();
			pdftable.addCell(this.createCell(this.HHPN + var18, this.f14, 0, 0));
			pdftable.addCell(this.createCell("", this.f14, 0, 0));
			pdftable.addCell(this.createCell(this.LotNo + var19, this.f14, 0, 0));
			pdftable.addCell(this.createBarCode(var1, var18, var2[0], Float.valueOf(17.0F)));
			pdftable.addCell(this.createCell("", this.f14, 0, 0));
			pdftable.addCell(this.createBarCode(var1, var19, var2[2], Float.valueOf(17.0F)));
			String var20 = this.textQty2.getText().trim();
			pdftable.addCell(this.createCell(this.Qty + var20, this.f14, 0, 0));
			pdftable.addCell(this.createCell("", this.f14, 0, 0));
			pdftable.addCell(this.createCell(this.PKGID + var3, this.f14, 0, 0));
			pdftable.addCell(this.createBarCode(var1, var20, var2[0], Float.valueOf(17.0F)));
			pdftable.addCell(this.createCell("", this.f14, 0, 0));
			pdftable.addCell(this.createBarCode(var1, var3, var2[2], Float.valueOf(17.0F)));
			String var21 = this.textMfrPN.getSelectedItem().toString().trim();
			pdftable.addCell(this.createCell(this.MfrPN + var21, this.f14, 0, 0));
			CodeZXing.CreateImage(var18 + "," + var20 + "," + var21 + "," + var5 + "," + var10 + "," + var19 + "," + var3);
			Image var22 = Image.getInstance("D:\\2008.png");
			var22.scaleAbsolute(50.0F, 50.0F);
			PdfPCell var23 = new PdfPCell(this.createCell("", this.f14, 0, 0));
			var23.setBorder(0);
			var23.setRowspan(6);
			pdftable.addCell(var23);
			var23 = new PdfPCell(var22);
			var23.setBorder(0);
			var23.setRowspan(6);
			var23.setVerticalAlignment(5);
			var23.setHorizontalAlignment(5);
			pdftable.addCell(var23);
			pdftable.addCell(this.createBarCode(var1, var21, var2[0], Float.valueOf(17.0F)));
			pdftable.addCell(this.createCell(this.DateCode + var5, this.f14, 0, 0));
			pdftable.addCell(this.createBarCode(var1, var5, var2[0], Float.valueOf(17.0F)));
			pdftable.addCell(this.createCell("L/C:" + var10, this.f14, 0, 0));
			pdftable.addCell(this.createBarCode(var1, var10, var2[0], Float.valueOf(17.0F)));
			this.updateFln(var26, var5, var9);
		} catch (Exception var25) {
			var25.printStackTrace();
		}

	}

	private void printNoBarCode(PdfWriter var1) {
		this.initFont();
		pdftable = new PdfPTable(2);
		pdftable.setTotalWidth(200.0F);
		int[] var2 = new int[]{40, 60};

		try {
			pdftable.setWidths(var2);
		} catch (DocumentException var5) {
			var5.printStackTrace();
		}

		try {
			pdftable.setLockedWidth(true);
			pdftable.getDefaultCell().setBorder(15);
			pdftable.setHorizontalAlignment(0);
			System.out.println("pdftable.addCell(this.createCell(\"\", this.f15, 0, 7));");
			pdftable.addCell(this.createCell("", this.f15, 0, 7));
			pdftable.addCell(this.createCell("HHZZ", this.f15, 0, 11));
			pdftable.addCell(this.createCell("BU:", this.f15, 0, 15));
			pdftable.addCell(this.createCell("IDPBG-FATP-ZZ-PUR", this.f14b, 0, 15));
			pdftable.addCell(this.createCell("Project:", this.f15, 0, 15));
			pdftable.addCell(this.createCell("Lotus", this.f14b, 0, 15));
			pdftable.addCell(this.createCell("Program:", this.f15, 0, 15));
			pdftable.addCell(this.createCell(this.textPro.getText().trim(), this.f14b, 0, 15));
			pdftable.addCell(this.createCell("Buyer:", this.f15, 0, 15));
			pdftable.addCell(this.createCell("侯新景(579-87580)", this.f14c, 0, 15));
			pdftable.addCell(this.createCell("Apple PN:", this.f15, 0, 15));
			pdftable.addCell(this.createCell(this.textAPN.getSelectedItem() == null?"":this.textAPN.getSelectedItem().toString().trim(), this.f14b, 0, 15));
			pdftable.addCell(this.createCell("HH PN:", this.f15, 0, 15));
			pdftable.addCell(this.createCell(this.textHHPN.getSelectedItem() == null?"":this.textHHPN.getSelectedItem().toString().trim(), this.f14b, 0, 15));
			pdftable.addCell(this.createCell("Des:", this.f15, 0, 15));
			pdftable.addCell(this.createCell(this.textDes.getText().trim(), this.f14b, 0, 15));
			pdftable.addCell(this.createCell("Rev:", this.f15, 0, 15));
			pdftable.addCell(this.createCell(this.textRev.getText().trim(), this.f14b, 0, 15));
			pdftable.addCell(this.createCell("Supplier:", this.f15, 0, 15));
			pdftable.addCell(this.createCell(this.textSupplier.getText().trim(), this.f14b, 0, 15));
			pdftable.addCell(this.createCell("品牌:", this.f15c, 0, 15));
			pdftable.addCell(this.createCell(this.textPin.getText().trim(), this.f14c, 0, 15));
			pdftable.addCell(this.createCell("型號:", this.f15c, 0, 15));
			pdftable.addCell(this.createCell(this.textXing.getText().trim(), this.f14c, 0, 15));
			pdftable.addCell(this.createCell("Country of origin:", this.f15, 0, 15));
			pdftable.addCell(this.createCell(this.textCountry.getText().trim(), this.f14c, 0, 15));
			pdftable.addCell(this.createCell("Date Code:", this.f15, 0, 15));
			pdftable.addCell(this.createCell(this.PDTime.getText().substring(0, 10), this.f14b, 0, 15));
			pdftable.addCell(this.createCell("Qty:", this.f15, 0, 15));
			pdftable.addCell(this.createCell(this.textQty.getText().trim(), this.f14b, 0, 15));
			pdftable.addCell(this.createCell("", this.f15, 0, 7));
			pdftable.addCell(this.createCell("Confidential", this.f15, 0, 11));
		} catch (Exception var4) {
			var4.printStackTrace();
		}

	}

	private void setPNItem() {
		String var1 = "select t.APPLE_PN,t.HH_PN,t.RS_PN from SFIS_LABLE_1901FOX_BOM t";

		try {
			Vector var2 = this.dh.getDataVector(var1, "jdbc/MultiCompanyCurrentDB");
			this.textAPN.removeAllItems();
			this.textHHPN.removeAllItems();
			this.textAPN.addItem("");
			this.textHHPN.addItem("");
			this.textHHPN2.removeAllItems();
			this.textMfrPN.removeAllItems();
			this.textHHPN2.addItem("");
			this.textMfrPN.addItem("");

			for(int var3 = 1; var3 < var2.size(); ++var3) {
				Hashtable var4 = (Hashtable)var2.get(var3);
				this.textAPN.addItem(var4.get("APPLE_PN"));
				this.textHHPN.addItem(var4.get("HH_PN"));
				this.textHHPN2.addItem(var4.get("HH_PN"));
				this.textMfrPN.addItem(var4.get("RS_PN"));
			}
		} catch (Exception var5) {
			var5.printStackTrace();
		}

	}

	private void setPNInfo2(String var1, String var2, boolean var3) {
		if(var3) {
			this.isItemLinkHH = !this.isItemLinkApp;
		} else {
			this.isItemLinkApp = !this.isItemLinkHH;
		}

		String var4 = "select t.* from SFIS_LABLE_1901FOX_BOM t where t." + var1 + " = \'" + var2 + "\' and rownum = 1";

		try {
			Hashtable var5 = this.dh.getDataOne(var4, "jdbc/MultiCompanyCurrentDB");
			if(var5 != null) {
				if(var3) {
					this.textHHPN2.setSelectedItem(var5.get("HH_PN").toString());
				} else {
					this.textMfrPN.setSelectedItem(var5.get("RS_PN").toString());
				}

				this.textApplePN.setText(var5.get("APPLE_PN").toString());
				String var6 = var5.get("COMPONENT").toString();
				if(var6.length() > 1) {
					var6 = var6.substring(0, 1);
					if(this.HDBT.substring(0, 1).equals(var6)) {
						this.comboboxButtonType.setSelectedItem(this.HDBT);
					} else if(this.RGBT.substring(0, 1).equals(var6)) {
						this.comboboxButtonType.setSelectedItem(this.RGBT);
					} else if(this.VLBT.substring(0, 1).equals(var6)) {
						this.comboboxButtonType.setSelectedItem(this.VLBT);
					}
				}
			}
		} catch (Exception var7) {
			var7.printStackTrace();
		}

		if(var3) {
			this.isItemLinkHH = !this.isItemLinkHH;
		} else {
			this.isItemLinkApp = !this.isItemLinkApp;
		}

	}

	private void setPNInfo(String var1, String var2, boolean var3) {
		if(var3) {
			this.isItemLinkHH = !this.isItemLinkApp;
		} else {
			this.isItemLinkApp = !this.isItemLinkHH;
		}

		String var4 = "select t.* from SFIS_LABLE_1901FOX_BOM t where t." + var1 + " = \'" + var2 + "\' and rownum = 1";

		try {
			Hashtable var5 = this.dh.getDataOne(var4, "jdbc/MultiCompanyCurrentDB");
			if(var5 != null) {
				if(var3) {
					this.textHHPN.setSelectedItem(var5.get("HH_PN").toString());
				} else {
					this.textAPN.setSelectedItem(var5.get("APPLE_PN").toString());
				}

				this.textDes.setText(var5.get("DESCRIPTION").toString());
				this.textRev.setText(var5.get("REV").toString());
			}
		} catch (Exception var6) {
			var6.printStackTrace();
		}

		if(var3) {
			this.isItemLinkHH = !this.isItemLinkHH;
		} else {
			this.isItemLinkApp = !this.isItemLinkApp;
		}

	}

	private void updateFln(String var1, String var2, String var3) {
		String var4;
		if(var3.equals("00001")) {
			var4 = "insert into SFIS_LABLE_1901FOX(flownum_" + var1 + ",fn_time) values(\'" + var3 + "\',to_date(\'" + var2 + "\',\'YYYY-MM-DD\'))";
		} else {
			var4 = "update SFIS_LABLE_1901FOX set flownum_" + var1 + " = flownum_" + var1 + " + 1 where to_char(fn_time,\'YYYY-MM-DD\') = \'" + var2 + "\'";
		}

		try {
			this.dh.updateData(var4, "jdbc/MultiCompanyCurrentDB");
		} catch (Exception var6) {
			var6.printStackTrace();
		}

	}

	public void initFont() {
		try {
			BaseFont var1 = BaseFont.createFont("C:/WINDOWS/Fonts/Verdana.TTF", "Identity-H", false);
			BaseFont var2 = BaseFont.createFont("C:/WINDOWS/Fonts/SIMHEI.TTF", "Identity-H", false);
			this.f15 = new Font(var1, 8.0F, 1);
			this.f15c = new Font(var2, 8.0F, 1);
			this.f14b = new Font(var1, 8.0F, 0);
			this.f14 = new Font(var1, 6.0F, 0);
			this.f14c = new Font(var2, 8.0F, 0);
		} catch (DocumentException var3) {
			var3.printStackTrace();
		} catch (IOException var4) {
			var4.printStackTrace();
		}

	}

	public void create() {
	}

	public void delete() {
	}

	public void email() {
	}

	public void exit() {
	}

	public void export() {
	}

	public void help() {
	}

	public void importData() {
	}

	public void modify() {
	}

	public void multiQuery() {
	}

	public void refresh() {
	}

	public void save() {
	}

	public Hashtable<String, P_Component_Meta> needValidateComponents() {
		return null;
	}

	public void setReportOid(String var1) {
	}
}
