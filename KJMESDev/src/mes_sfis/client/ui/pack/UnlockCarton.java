package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.excel.EXCEL_1721;
import mes_sfis.client.ui.barcode.MyTableModel;
import mes_sfis.client.util.DataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by Srx_Zhu on 2018/4/24 0024.
 */
public class UnlockCarton extends BasePanel{
    public static final String VERSION = "$Name:  $, $Id: UnlockCarton.java,v 1 2018/04/16 09:28:13 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

    JPanel jpanel = new JPanel();
    JLabel jLabel1 = new JLabel("請輸入箱號");//0, 0, 100, 30
    JLabel jLabel2 = new JLabel("請輸入ISN");//0, 0, 100, 30
    JTextField jText1 = new JTextField();//110, 0, 150, 30
    JTextField jText2 = new JTextField();//110, 0, 150, 30
    JButton jBtn1 = new JButton("查詢");//280, 0, 100, 30
    JButton jBtn2 = new JButton("解鎖");//390, 0, 100, 30
	private JButton reworkBut = new JButton("Rework解鎖");
    JButton jBtn11 = new JButton("查詢");//280, 0, 100, 30
    JButton jBtn22 = new JButton("解鎖");//390, 0, 100, 30
    JButton jBtn3 = new JButton("導出");
    private Font font = new Font("宋體", Font.BOLD, 14);
    private Color color = new Color(106, 106, 255);
    private MyTableModel TM;
    private Object[][] ExcelData;
    DataHandler dh;


    public UnlockCarton(UI_InitVO uiVO) {
        super(uiVO);
        dh = new DataHandler(uiVO);
        init();
    }



    public void init(){
        jpanel.setLayout(null);
        jpanel.setBounds(20,100, 800, 600);
        jLabel1.setBounds(10, 10, 100, 30);
        jLabel2.setBounds(10, 50, 100, 30);
        jText1.setBounds(110, 10, 200, 30);
        jText2.setBounds(110, 50, 200, 30);
        jBtn1.setBounds(330, 10, 100, 30);
        jBtn2.setBounds(430, 10, 100, 30);
/*        jBtn11.setBounds(330, 50, 100, 30);
        jBtn22.setBounds(430,50, 100, 30);*/
        jBtn3.setBounds(530, 10, 100, 30);
		reworkBut.setBounds(430,50,150,30);


        String[] ss = new String[]{"鎧嘉料號", "ISN","箱號"};
        Object[][][] ooo = {new Object[][]{{"","",""}}};
        final JTable table = MyTable(ooo[0], ss);
        JScrollPane jsp = new JScrollPane(table);
        jsp.getViewport().setBackground(Color.white);
        jsp.setBounds(0,100,800,500);

        jpanel.add(jsp);
        jpanel.setVisible(true);
        this.add(jpanel);
        this.add(jText1);
        this.add(jText2);
        this.add(jBtn1);
        this.add(jBtn2);
/*        this.add(jBtn11);
        this.add(jBtn22);*/
        this.add(jBtn3);
        this.add(jLabel1);
        this.add(jLabel2);
		add(reworkBut);



        jBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cartonNo = jText1.getText().toString().trim().replace(" ", "");
                queryCartonData(cartonNo, table);
            }
        });
        jBtn11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ISN = jText2.getText().toString().trim().replace(" ", "");
                queryCartonISNData(ISN, table);
            }
        });

        jBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carton_no = "";
                carton_no = jText1.getText().trim().replace(" ", "");
                if (carton_no == null || carton_no.equals("")) {
                    JOptionPane.showConfirmDialog(null, "請輸入箱號！", "提示", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                int ti = JOptionPane.showConfirmDialog(null, "確認解鎖？", "提示", JOptionPane.YES_NO_OPTION);
//0：確定 否清除數據
                if (ti == 0) {
                    int tj = JOptionPane.showConfirmDialog(null, "請再次確認解鎖！", "提示", JOptionPane.YES_NO_OPTION);
//0：確定 否清除數據
                    if (tj == 0) {
                        trueUnlock(carton_no,false);
                    } else {

                    }
                } else {

                }

            }
        });
		
		reworkBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carton_no = "";
                carton_no = jText1.getText().trim().replace(" ", "");
                if (carton_no == null || carton_no.equals("")) {
                    JOptionPane.showConfirmDialog(null, "請輸入箱號！", "提示", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                int ti = JOptionPane.showConfirmDialog(null, "確認解鎖？", "提示", JOptionPane.YES_NO_OPTION);
//0：確定 否清除數據
                if (ti == 0) {
                    int tj = JOptionPane.showConfirmDialog(null, "請再次確認解鎖！", "提示", JOptionPane.YES_NO_OPTION);
//0：確定 否清除數據
                    if (tj == 0) {
                        trueUnlock(carton_no,true);
                    } else {

                    }
                } else {

                }

            }
        });
		
       jBtn22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ISN = "";
                ISN = jText2.getText().trim().replace(" ", "");
                if (ISN == null || ISN.equals("")) {
                    JOptionPane.showConfirmDialog(null, "請輸入箱號！", "提示", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                int ti = JOptionPane.showConfirmDialog(null, "確認解鎖？", "提示", JOptionPane.YES_NO_OPTION);
//0：確定 否清除數據
                if (ti == 0) {
                    int tj = JOptionPane.showConfirmDialog(null, "請再次確認解鎖！", "提示", JOptionPane.YES_NO_OPTION);
//0：確定 否清除數據
                    if (tj == 0) {
                        trueUnlockISN(ISN);
                    } else {

                    }
                } else {

                }

            }
        });
        jBtn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ExcelData!=null){
                    ExportExcel();
                }else{
                    JOptionPane.showConfirmDialog(null, "當前無查詢數據！", "提示", JOptionPane.PLAIN_MESSAGE);
                }

            }
        });
    }


    public void trueUnlock(String carton_no,boolean isRework) {
		if(isRework){
			sfisRework(carton_no);
			return;
		}
		doUnLock(carton_no);
    }
	
	private void doUnLock(String carton_no){
		String sql = "update mes_pack_carton set is_break=1,break_systemdate=sysdate where carton_no='" + carton_no + "'";
        try {
            dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showConfirmDialog(null, "解鎖箱成功", "提示", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void sfisRework(String carton_no){
		String sql = "select t.m_sn from MES_PACK_CARTON_ISN t,MES_PACK_CARTON c where t.carton_oid = c.carton_oid and c.carton_no = '" + carton_no + "'";
        try {
            Vector vISN = dh.getDataVector(sql, DataSourceType._MultiCompanyCurrentDB);
			if(vISN != null){
				selectReworkFrame(((Hashtable)vISN.get(1)).get("M_SN").toString(),vISN,carton_no);
				/*for(int i = 1;i < vISN.size();i++){
					Hashtable htISN = (Hashtable)vISN.get(i);
					String isn = htISN.get("M_SN").toString();
					sql = "select step from (select rownum li,t.step from route_step t where t.rtype3 = 'A'"
					+ " and t.route = (select t.route from MO_D t where t.isn = '"
					+ isn + "') and"
					+ " t.ridx < (select ridx from route_step where step = (select t.step from MO_D t where t.isn = '"
					+ isn + "') and route = (select t.route from MO_D t where t.isn = '"
					+ isn + "'))) mv where mv.li ="
					+ " (select count(route) from route_step t where t.rtype3 = 'A' and t.route = (select t.route from mo t where t.mo ="
					+ " (select t.mo from MO_D t where t.isn = '" + isn + "')) and t.ridx < (select ridx from route_step where step = (select t.step from MO_D t where t.isn = '"
					+ isn + "') and route = (select t.route from MO_D t where t.isn = '"
					+ isn + "')))";
					sql = "update MO_D t set t.step = (" + sql + ") where t.isn = '" + isn + "'";
					dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
				}*/
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private Hashtable fillItems(JComboBox jc,String isn){
		Hashtable vStep_Name = new Hashtable();
		String sql = "select step,stepnm from (select stepnm,t.step from route_step t where t.rtype3 = 'A'"
					+ " and t.route = (select t.route from MO_D t where t.isn = '"
					+ isn + "') and"
					+ " t.ridx < (select ridx from route_step where step = (select t.step from MO_D t where t.isn = '"
					+ isn + "') and route = (select t.route from MO_D t where t.isn = '"
					+ isn + "')) and stepnm is not null) mv";
		try {
			Vector vStep = dh.getDataVector(sql, DataSourceType._SFIS_KAIJIA_STD);
			if(vStep != null){
				for(int i = 1;i < vStep.size();i++){
					Hashtable tab1 = (Hashtable)vStep.get(i);
					String name = tab1.get("STEPNM").toString();
					vStep_Name.put(name,tab1.get("STEP").toString());
					jc.addItem(name);
					jc.setSelectedIndex(i - 1);
				}
			}
		} catch (Exception e) {
            e.printStackTrace();
        }
		return vStep_Name;
	}
	
	private void selectReworkFrame(String isn,final Vector vISN,final String carton_no){
		final JFrame frame = new JFrame("選擇回退站點");
		frame.setBounds(400,300,350, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);
		JLabel accessLab = new JLabel("退回到:");
		accessLab.setBounds(10, 0, 80, 25);
		final JComboBox jc = new JComboBox();
		jc.setBounds(100,0,225,25);
		JButton sureBut = new JButton("確定");
		sureBut.setBounds(50,30,75,25);
		JButton cancelBut = new JButton("取消");
		cancelBut.setBounds(135,30,75,25);
		panel.add(accessLab);
		panel.add(jc);
		panel.add(sureBut);
		panel.add(cancelBut);
		final Hashtable vStep_Name = fillItems(jc,isn);
		sureBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String stepnm = jc.getSelectedItem().toString();
				if(stepnm != null && !"".equals(stepnm)){
					String stepOfSql = vStep_Name.get(stepnm).toString();
					for(int i = 1;i < vISN.size();i++){
						Hashtable htISN = (Hashtable)vISN.get(i);
						String isn = htISN.get("M_SN").toString();
						String sql = "update MO_D t set t.step = '" + stepOfSql + "' where t.isn = '" + isn + "'";
						try {
							dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					doUnLock(carton_no);
					frame.dispose();
				}else{
					JOptionPane.showConfirmDialog(null, "請選擇退回站點", "警告", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		cancelBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		frame.setVisible(true);
	}
	
    public void trueUnlockISN(String ISN) {
        String sql = "update mes_pack_carton_isn set SN3='1' where M_SN='" + ISN + "'";
        try {
            dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showConfirmDialog(null, "解鎖單個ISN成功", "提示", JOptionPane.PLAIN_MESSAGE);
    }

    public void ExportExcel() {
        EXCEL_1721 excel = new EXCEL_1721();
        try {
            excel.ExportNoResponse(excel.sheetName5, excel.titleName5, excel.fileName5, excel.columnNumber5, excel.columnWidth5, excel.tableTitleName5, ExcelData);
            JOptionPane.showConfirmDialog(null, "導出EXCEL至D盤成功", "提示", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JTable MyTable(Object[][] data, String[] tableHead) {
        try {
            TM = new MyTableModel(data, tableHead);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JTable table = new JTable(TM);

        table.setFont(font);
        table.setForeground(color);
        table.setRowHeight(20);
        table.setBackground(Color.white);
        TM.removeRow(0);
        TM.fireTableDataChanged();
        table.setEnabled(false);
        return table;
    }


    public Object[][] queryCartonData(String cartonNo, JTable tbale) {
        Object[][] tableData = null;
        if ("".equals(cartonNo)) {
            JOptionPane.showConfirmDialog(null, "請輸入箱號", "警告", JOptionPane.PLAIN_MESSAGE);
            return tableData;
        }
        String sql="";
        sql = "select c.kj_pn,c.carton_no,i.m_sn as ISN  from mes_pack_carton c  join mes_pack_carton_isn i on c.carton_oid = i.carton_oid    where c.carton_no='" + cartonNo + "'";

        try {
            List<Hashtable> result = null;
            result = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
            if (result == null) {
                JOptionPane.showConfirmDialog(null, "該箱為空，請核對后重新輸入", "警告", JOptionPane.PLAIN_MESSAGE);
            } else {
                tableData = new Object[result.size()][3];
                for (int i = 0; i < result.size(); i++) {
                    tableData[i][0] = (String) result.get(i).get("KJ_PN");
                    tableData[i][1] = (String) result.get(i).get("ISN");
                    tableData[i][2] = (String) result.get(i).get("CARTON_NO");
                }
                MyTableModel MTM = (MyTableModel) tbale.getModel();
                MTM.Clearcontents();
                MTM.refreshContents(tableData);
                MTM.fireTableDataChanged();
                ExcelData = tableData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return tableData;
    }
    public Object[][] queryCartonISNData(String ISN, JTable tbale) {
        Object[][] tableData = null;
        if ("".equals(ISN)) {
            JOptionPane.showConfirmDialog(null, "請輸入ISN", "警告", JOptionPane.PLAIN_MESSAGE);
            return tableData;
        }
        String sql="";
        sql = "select c.kj_pn,c.carton_no,i.m_sn as ISN  from mes_pack_carton c  join mes_pack_carton_isn i on c.carton_oid = i.carton_oid    where i.m_sn='" + ISN + "'";

        try {
            List<Hashtable> result = null;
            result = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
            if (result == null) {
                JOptionPane.showConfirmDialog(null, "該箱為空，請核對后重新輸入", "警告", JOptionPane.PLAIN_MESSAGE);
            } else {
                tableData = new Object[result.size()][3];
                for (int i = 0; i < result.size(); i++) {
                    tableData[i][0] = (String) result.get(i).get("KJ_PN");
                    tableData[i][1] = (String) result.get(i).get("ISN");
                    tableData[i][2] = (String) result.get(i).get("CARTON_NO");
                }
                MyTableModel MTM = (MyTableModel) tbale.getModel();
                MTM.Clearcontents();
                MTM.refreshContents(tableData);
                MTM.fireTableDataChanged();
                ExcelData = tableData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return tableData;
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
}
