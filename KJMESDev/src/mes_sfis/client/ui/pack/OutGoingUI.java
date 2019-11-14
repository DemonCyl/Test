package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.bean.MesPackConfig;
import mes_sfis.client.model.bean.OutGoingConfig;
import mes_sfis.client.model.bean.PackCarton;
import mes_sfis.client.model.bean.Product;
import mes_sfis.client.model.service.OutGoingService;
import mes_sfis.client.model.service.PackageService;
import mes_sfis.client.model.service.MailService;
import mes_sfis.client.model.service.ExportExcel;
import mes_sfis.client.pdf.OutGoingPdf;
import mes_sfis.client.pdf.PDFCreater;
import mes_sfis.client.pdf.PDFCreater1606;
import mes_sfis.client.sfis.LoginOutClient;
import mes_sfis.client.sfis.PassDeviceClient;
import mes_sfis.client.util.ConfigJSONUtil;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.SoundUtil;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.sfis.PassForProcedure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Srx_Zhu on 2018/4/11 0011.
 */
public class OutGoingUI extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: OutGoingUI.java,v 1.4 2018/04/17 07:53:17 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

    JPanel contentPanel = new JPanel();
    JButton jPrintButton = new JButton("打印");
    JLabel shipmentLabel = new JLabel("出貨通知單");
    JLabel titleLabel = new JLabel("1721出貨作業單");
    JTextField shipmentText = new JTextField();
    JTextArea showOgfConfig = new JTextArea();
	JButton exportExcel = new JButton("匯出Excel");
	List<Hashtable> listexcel1 = new ArrayList<Hashtable>();
	List<Hashtable> cartonList = new ArrayList<Hashtable>();
	String noisyno = "";
	String shipOidExc = "";
    OutGoingService outGoingService;
    List<Hashtable> cartonConfigList = new ArrayList<>();//零散箱列表
    List<Hashtable> palletConfigList = new ArrayList<>();//棧板列表
    Hashtable ogaConfig;
    Font titleFont = new Font("宋体", Font.PLAIN, 40);
    String shipment = "";
    OutGoingPdf ogp = null;
    OutGoingPdf ogp2 = null;
    String SHIP_OID = "";
    String OUT_GOING_NO = "";
    DataHandler dh;
    boolean palletEmpty;
    boolean cartonEmpty;

    public OutGoingUI(UI_InitVO uiVO) {
        super(uiVO);
        outGoingService = new OutGoingService(uiVO);

        dh = new DataHandler(uiVO);

        init();
    }

    @Override
    public void create() {

    }

    @Override
    public void save() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void singleQuery() {

    }

    @Override
    public void multiQuery() {

    }

    @Override
    public void print() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void help() {

    }

    @Override
    public void email() {

    }

    @Override
    public void export() {

    }

    @Override
    public void importData() {

    }

    @Override
    public Hashtable<String, P_Component_Meta> needValidateComponents() {
        return null;
    }

    @Override
    public void setReportOid(String s) {

    }

    public void init() {
        contentPanel.setLayout(null);

        titleLabel.setForeground(Color.blue);

        shipmentText.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        shipmentText.setEditable(true);
        showOgfConfig.setEditable(false);
        JScrollPane scrolljs = new JScrollPane(showOgfConfig);
        scrolljs.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//設置縱向滾動捲軸總是顯示
        jPrintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				Vector<OutGoingConfig> vtOut = new Vector<OutGoingConfig>();
                //OutGoingConfig outGoingConfig = new OutGoingConfig();
                String ship_oid = "";
                String erp_ship_oid = "";
                String erp_out_oid = "";
                List<Hashtable> titleList = new ArrayList<Hashtable>();
                int cartonNum = 0;
                shipment = shipmentText.getText().toString().replace(" ", "");
                List<Hashtable> cartonList3 = null;
                try {
                    ogaConfig = outGoingService.ERPIsTrue(shipment);
                    if (ogaConfig == null) {
                        JOptionPane.showConfirmDialog(null, "該單號無效", "", JOptionPane.PLAIN_MESSAGE);
						return;
                    }
                    if (!"Y".equals(ogaConfig.get("CONFIRM_YN"))) {
                        return;
                    }
                    if (!"1".equals(ogaConfig.get("STATUS"))) {
                        return;
                    }
                    erp_ship_oid = (String) ogaConfig.get("ERP_SHIP_OID");
                    erp_out_oid = (String) ogaConfig.get("OUT_GOING_NO");

                    ship_oid = (String) outGoingService.getShipOidByERP(erp_ship_oid);
                    vtOut = outGoingService.getShipConfig(ship_oid);
                    titleList = outGoingService.getALLConfigByShipOid(ship_oid);
                    ogp = new OutGoingPdf("ShipMent.pdf");
                    ogp2 = new OutGoingPdf("ShipMent_2.pdf");
                    cartonList3 = outGoingService.getCartonListByShipOid2(ship_oid);
                    cartonNum = cartonList3.size();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (palletEmpty == true) {
                    int ti = JOptionPane.showConfirmDialog(null, "本次出貨無棧板信息，確認打印？", "提示", JOptionPane.YES_NO_OPTION);
                    if (ti == 0) {
                    } else {
                        return;
                    }
                    ogp.PalletPdfGenerate(titleList, vtOut, cartonNum,erp_out_oid);
                    ogp.close();
                    ogp.printPdf("ShipMent.pdf");
                } else if(cartonEmpty == true){
                    int ti = JOptionPane.showConfirmDialog(null, "本次出貨無零散箱信息，確認打印？", "提示", JOptionPane.YES_NO_OPTION);
                    if (ti == 0) {
                    } else {
                        return;
                    }
                    ogp2.CartonPdfGenerate(titleList, cartonList3, vtOut);
                    ogp2.close();
                    ogp2.printPdf("ShipMent_2.pdf");
                }else{
                    ogp.PalletPdfGenerate(titleList, vtOut, cartonNum,erp_out_oid);
                    ogp.close();
                    ogp.printPdf("ShipMent.pdf");
                    ogp2.CartonPdfGenerate(titleList, cartonList3, vtOut);
                    ogp2.close();
                    ogp2.printPdf("ShipMent_2.pdf");
                }
                outGoingService.Setinfo(erp_out_oid, erp_ship_oid);
				callGoRoute();
				try {
					MailService.testSendEmail(new String[]{"%CH_SHIPMENT_NOTICE@ch.corpnet"},new String[]{"%CH_MIS_MES_SFIS@ch.corpnet"},"出貨通知","",null);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });


        shipmentText.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                int key = arg0.getKeyCode();
                if (key == '\n') {
                    Clear();

                    shipment = shipmentText.getText().toString().replace(" ", "");
                    try {
                        ogaConfig = outGoingService.ERPIsTrue(shipment);
						if(ogaConfig == null || ogaConfig.get("ERP_SHIP_OID") == null || "".equals(ogaConfig.get("ERP_SHIP_OID").toString())){
							JOptionPane.showConfirmDialog(null, "無此單據", "", JOptionPane.PLAIN_MESSAGE);
                            return;
						}

                        if ("Y".equals(ogaConfig.get("CONFIRM_YN")) && "1".equals(ogaConfig.get("STATUS"))) {
                            OUT_GOING_NO = (String) ogaConfig.get("OUT_GOING_NO");
                            SHIP_OID = (String) ogaConfig.get("SHIP_OID");
                        } else if ("X".equals(ogaConfig.get("CONFIRM_YN")) && "0".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：開立", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } else if ("X".equals(ogaConfig.get("CONFIRM_YN")) && "9".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：作廢", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } else if ("X".equals(ogaConfig.get("CONFIRM_YN")) && "R".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：送簽退回", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } else if ("X".equals(ogaConfig.get("CONFIRM_YN")) && "S".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：送簽中", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } else if ("X".equals(ogaConfig.get("CONFIRM_YN")) && "W".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：抽單", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } else if ("Y".equals(ogaConfig.get("CONFIRM_YN")) && "0".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：開立", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } else if ("Y".equals(ogaConfig.get("CONFIRM_YN")) && "9".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：作廢", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } else if ("Y".equals(ogaConfig.get("CONFIRM_YN")) && "R".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：送簽退回", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } else if ("Y".equals(ogaConfig.get("CONFIRM_YN")) && "S".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：送簽中", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        } else if ("Y".equals(ogaConfig.get("CONFIRM_YN")) && "W".equals(ogaConfig.get("STATUS"))) {
                            JOptionPane.showConfirmDialog(null, "該單據已作廢；狀態：抽單", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e1) {
                int key = e1.getKeyCode();
                if (key == '\n') {
                    if (ogaConfig == null) {
                        showOgfConfig.append("單據無效，請核對后再輸入");
                        return;
                    }
                    if (!"Y".equals(ogaConfig.get("CONFIRM_YN"))) {
                        return;
                    }
                    if (!"1".equals(ogaConfig.get("STATUS"))) {
                        return;
                    }
                    try {
                        String erpShipOid = (String) ogaConfig.get("ERP_SHIP_OID");
                        if (erpShipOid == null) {
                            JOptionPane.showConfirmDialog(null, "未查詢到ERP出貨單號", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        String ShipOid = outGoingService.getShipOidByERP(erpShipOid);
						noisyno = erpShipOid;
						shipOidExc = ShipOid;
                        if (ShipOid == null) {
                            JOptionPane.showConfirmDialog(null, "尚未生成ERP出貨單號", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        List<Hashtable> allConfigList = new ArrayList<Hashtable>();
                        //List<Hashtable> cartonList = new ArrayList<Hashtable>();
                        List<Hashtable> cartonList2 = new ArrayList<Hashtable>();
                        allConfigList = outGoingService.getALLConfigByShipOid(ShipOid);
						listexcel1 = allConfigList;
                        String KJPN = "";
                        String KJPN2 = "";
                        String palletOid = "";
                        if (allConfigList == null || ((Hashtable)allConfigList.get(0)).get("PALLET_NO") == null || "".equals(((Hashtable)allConfigList.get(0)).get("PALLET_NO").toString())) {
                            showOgfConfig.append("棧板信息為空");
                            palletEmpty = true;
                        } else {
                            for (int i = 0; i < allConfigList.size(); i++) {
                                if (!KJPN.equals((String) allConfigList.get(i).get("KJ_PN"))) {
                                    KJPN = (String) allConfigList.get(i).get("KJ_PN");
                                    showOgfConfig.append("\n");
                                    showOgfConfig.append("鎧嘉料號：" + KJPN + "\n");
                                    showOgfConfig.append("棧板信息：" + "\n");
                                    showOgfConfig.append("棧板號：" + allConfigList.get(i).get("PALLET_NO").toString() + "        " + "棧板內箱數:" + allConfigList.get(i).get("CARTON_QTY").toString() + "\n");
                                    if (!palletOid.equals((String) allConfigList.get(i).get("PALLET_OID"))) {
                                        cartonList2 = outGoingService.getCartonListByPalletOid((String) allConfigList.get(i).get("PALLET_OID"));
                                        palletOid = (String) allConfigList.get(i).get("PALLET_OID");
                                        for (int j = 0; j < cartonList2.size(); j++) {
                                            showOgfConfig.append("          箱號：" + cartonList2.get(j).get("CARTON_NO").toString() + "        " + "箱內產品數:" + cartonList2.get(j).get("QTY").toString() + "\n");
                                        }
                                    }

                                } else {
                                    if (!palletOid.equals((String) allConfigList.get(i).get("PALLET_OID"))) {
                                        cartonList2 = outGoingService.getCartonListByPalletOid((String) allConfigList.get(i).get("PALLET_OID"));
                                        palletOid = (String) allConfigList.get(i).get("PALLET_OID");
                                        showOgfConfig.append("棧板號：" + allConfigList.get(i).get("PALLET_NO").toString() + "        " + "棧板內箱數:" + allConfigList.get(i).get("CARTON_QTY").toString() + "\n");
                                        for (int j = 0; j < cartonList2.size(); j++) {
                                            showOgfConfig.append("          箱號：" + cartonList2.get(j).get("CARTON_NO").toString() + "        " + "箱內產品數:" + cartonList2.get(j).get("QTY").toString() + "\n");
                                        }
                                    }

                                }

                            }
                        }
                        cartonList = outGoingService.getCartonListByShipOid2(ShipOid);
						//listexcel1 = cartonList;
                        if (cartonList == null) {
                            cartonEmpty = true;
                            showOgfConfig.append("零散箱信息為空");
                        } else {
                            for (int i = 0; i < cartonList.size(); i++) {
                                System.out.println("123");
                                if (!KJPN2.equals((String) cartonList.get(i).get("KJ_PN"))) {
                                    KJPN2 = (String) cartonList.get(i).get("KJ_PN");
                                    showOgfConfig.append("\n");
                                    showOgfConfig.append("鎧嘉料號：" + KJPN2 + "\n");

                                    showOgfConfig.append("零散箱信息：" + "\n");
                                    for (int j = 0; j < cartonList.size(); j++) {
										if(!KJPN2.equals(cartonList.get(j).get("KJ_PN").toString()))
											continue;
                                        showOgfConfig.append("零散箱號：" + cartonList.get(j).get("CARTON_NO").toString() + "        " + "箱內產品數:" + cartonList.get(j).get("QTY").toString() + "\n");
                                    }
                                }
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
					setExcelData(0);
                }

            }

            @Override
            public void keyTyped(KeyEvent e2) {
                int key = e2.getKeyCode();
                if (key == '\n') {
                    JOptionPane.showMessageDialog(null, "undefined");
                }
            }
        });


        this.add(contentPanel);
        this.add(jPrintButton);
        this.add(titleLabel);
        this.add(scrolljs);
        this.add(shipmentLabel);
        this.add(shipmentText);
        /*this.add(showOgfConfig);
*/
		this.add(exportExcel);

        jPrintButton.setBounds(630, 50, 80, 40);
        titleLabel.setBounds(30, 10, 100, 20);
        shipmentLabel.setBounds(30, 60, 80, 30);
        shipmentText.setBounds(100, 62, 500, 30);
        scrolljs.setBounds(20, 100, 700, 500);
        /*showOgfConfig.setBounds(200,150,400,800);*/
		exportExcel.setBounds(140, 10, 130, 40);
		
		exportExcel.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setExcelData(1);
				}
			}
		);

    }
	
	private void callGoRoute(){
		if(cartonList != null){
			String device = "";
			try{
				ReadConfig2 a = new ReadConfig2();
				a.hashINI();
				device = a.iniHash.get("epega.exe.device").toString();
			}catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "配置文件異常，請聯繫 OA     注： 配置文件格式為 UNICODE", "警告", JOptionPane.PLAIN_MESSAGE);
			}
			for(int m = 0;m < cartonList.size();m++){
				String carton = cartonList.get(m).get("CARTON_NO").toString();
				Vector vtISN = outGoingService.getIsn(carton);
				if(vtISN != null && vtISN.size() > 1){
					for(int i = 1;i < vtISN.size();i++){
						Hashtable htISN = new Hashtable();
						htISN = (Hashtable)vtISN.get(i);
						procedureFun(device,carton,(String)htISN.get("M_SN"));
					}
				}
			}
		}
	}
	
	private void procedureFun(String device,String cartonNo,String isn){
		PassForProcedure passForProcedure = new PassForProcedure();
		try {
			passForProcedure.open();
			HashMap map = new HashMap();
			map.put("DEVICE", device);
			map.put("OP", uiVO.getLogin_id());
			map.put("STATUS", "2");
			passForProcedure.procedureLogin(map);
			map.put("STATUS", "1");
			passForProcedure.procedureLogin(map);
			map.put("ISN", cartonNo);
			passForProcedure.procedurePass(map);
			map.put("ISN", isn);
			passForProcedure.procedurePass(map);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "過站呼叫異常", "警告", JOptionPane.PLAIN_MESSAGE);
        } finally {
			passForProcedure.close();
		}
	}
	
	private void setExcelData(int excelsource){
		Vector rowdata = new Vector();
		String KJPN2 = "";
		try {
			Vector row0 = new Vector();
			row0.add(noisyno);
			row0.add(OUT_GOING_NO);
			rowdata.add(row0);
			Vector rowth = new Vector();
			rowth.add("鎧嘉料號");
			rowth.add("客戶料號");
			rowth.add("ISN");
			rowth.add("SSN");
			rowdata.add(rowth);
			Vector<OutGoingConfig> vtOC = outGoingService.getShipConfig(shipOidExc);
			OutGoingConfig outGoingConfigExc = new OutGoingConfig();
			int allCount = 0;
			if(vtOC != null && vtOC.size() > 0){
				for(int x = 0;x < vtOC.size();x++){
					outGoingConfigExc = vtOC.get(x);
					allCount += outGoingConfigExc.getPlanQty();
					String KJPN = "";
					for (int i = 0; i < listexcel1.size(); i++) {
						if(!((String) listexcel1.get(i).get("KJ_PN")).equals(outGoingConfigExc.getKjPn()) || !outGoingConfigExc.getCsPn().equals((String) listexcel1.get(i).get("OEMPN")))
							continue;
						if (!KJPN.equals((String) listexcel1.get(i).get("KJ_PN"))) {
							KJPN = (String) listexcel1.get(i).get("KJ_PN");
							List<Hashtable> list = outGoingService.getIsnSsn(listexcel1.get(i).get("CARTON_NO").toString());
							Vector row = new Vector();
							row.add("箱號");
							row.add(listexcel1.get(i).get("CARTON_NO").toString());
							rowdata.add(row);
							int catnum = 0;
							if(list != null){
								for(int m = 0;m < list.size();m++){
									Hashtable ht = list.get(m);
									Vector row2 = new Vector();
									row2.add((String)listexcel1.get(i).get("KJ_PN"));
									row2.add(outGoingConfigExc.getCsPn());
									row2.add((String)ht.get("M_SN"));
									row2.add((String)ht.get("S_SN"));
									rowdata.add(row2);
									catnum ++;
								}
							}
							row.add("箱內產品數");
							row.add(catnum);
							row.add("D.C");
							row.add(listexcel1.get(i).get("DATE_CODE").toString());
						}else{
							List<Hashtable> list = outGoingService.getIsnSsn(listexcel1.get(i).get("CARTON_NO").toString());
							Vector row = new Vector();
							row.add("箱號");
							row.add(listexcel1.get(i).get("CARTON_NO").toString());
							rowdata.add(row);
							int catnum = 0;
							if(list != null){
								for(int m = 0;m < list.size();m++){
									Hashtable ht = list.get(m);
									Vector row2 = new Vector();
									row2.add((String)listexcel1.get(i).get("KJ_PN"));
									row2.add(outGoingConfigExc.getCsPn());
									row2.add((String)ht.get("M_SN"));
									row2.add((String)ht.get("S_SN"));
									rowdata.add(row2);
									catnum ++;
								}
							}
							row.add("箱內產品數");
							row.add(catnum);
							row.add("D.C");
							row.add(listexcel1.get(i).get("DATE_CODE").toString());
						}
					}
				}
			}
			row0.add("產品總數");
			row0.add(allCount);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		saveFile(rowdata,excelsource);
	}
	
	private void saveFile(Vector rowdata,int excelsource) {
		if(excelsource == 0){
			ExportExcel EE = new ExportExcel();
			Vector column = new Vector();
			column.add("出貨通知單號");
			column.add("通知單號");
			EE.groupExcel("D://mes_data/pdf/ShipMent.xls",rowdata,column);
		}else{
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Excel文件(*.xls)", "xls");
			chooser.setFileFilter(filter);
			int option = chooser.showSaveDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				String fname = chooser.getName(file);
				if (fname.indexOf(".xls") == -1) {
					file = new File(chooser.getCurrentDirectory(), fname + ".xls");
				}
				String fileNameExcel = file.getAbsolutePath();
				ExportExcel EE = new ExportExcel();
				Vector column = new Vector();
				column.add("出貨通知單號");
				column.add("通知單號");
				EE.groupExcel(fileNameExcel,rowdata,column);
			}
		}
	}


    public void Clear() {
        showOgfConfig.setText("");
        cartonEmpty = false;
        palletEmpty = false;
    }

}