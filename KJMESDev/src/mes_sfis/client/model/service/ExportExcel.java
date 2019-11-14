package mes_sfis.client.model.service;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import java.util.Date;
import base.util.WebServiceUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

/**
 * @author      <a>jieyu_fu</a>
 */
 public class ExportExcel<T> {
	public void exportExcel(Collection<T> dataset, OutputStream out) {
		exportExcel("Excel", null, dataset, out, "yyyy-MM-dd");
	}
	public void exportExcel(String[] headers, Collection<T> dataset,
		OutputStream out) {
		exportExcel("Excel", headers, dataset, out, "yyyy-MM-dd");
	}
	public void exportExcel(String[] headers, Collection<T> dataset,
		OutputStream out, String pattern) {
		exportExcel("Excel", headers, dataset, out, pattern);
	}
	@SuppressWarnings("unchecked")
	public void exportExcel(String title, String[] headers,
		Collection<T> dataset, OutputStream out, String pattern) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth((short) 15);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style2.setFont(font2);
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		comment.setString(new HSSFRichTextString("可以在POI中添加注?！"));
		comment.setAuthor("Jie");
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		HSSFFont font3 = workbook.createFont();
		font3.setColor(HSSFColor.BLACK.index);
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				/*HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);*/
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
				   + fieldName.substring(0, 1).toUpperCase()
				   + fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
						new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					String textValue = null;
					if(value instanceof Vector) {
						if(((Vector)value).size() > 0){
							for(int x = 0;x < ((Vector)value).size();x++){
								Vector rowdata = (Vector)(((Vector)value).get(x));
								row = sheet.createRow(x + 1);
								if(rowdata.size() > 0){
									for(int y = 0;y < rowdata.size();y++){
										
										HSSFCell cell = row.createCell(y);
										cell.setCellStyle(style2);
										Object objdata = rowdata.get(y);
										if (objdata instanceof Boolean) {
											textValue = objdata.toString();
										} else if (objdata instanceof Date) {
											Date date = (Date) objdata;
											SimpleDateFormat sdf = new SimpleDateFormat(pattern);
											textValue = sdf.format(date);
										} else if (objdata instanceof byte[]) {
											row.setHeightInPoints(60);
											sheet.setColumnWidth(i, (short) (35.7 * 80));
											byte[] bsValue = (byte[]) objdata;
											HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
											 1023, 255, (short) 10, y + 1, (short) 10, y + 1);
											patriarch.createPicture(anchor, workbook.addPicture(
											 bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
										} else{
											textValue = objdata.toString();
										}
										if(textValue!=null){
											Pattern p = Pattern.compile("^//d+(//.//d+)?$");   
											Matcher matcher = p.matcher(textValue);
											if(matcher.matches()){
												cell.setCellValue(Double.parseDouble(textValue));
											}else{
												HSSFRichTextString richString = new HSSFRichTextString(textValue);
												/*HSSFFont font3 = workbook.createFont();
												font3.setColor(HSSFColor.BLACK.index);*/
												richString.applyFont(font3);
												cell.setCellValue(richString);
											}
										}
									}
								}
							}
						}
					} 
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 

	public void groupExcel(String fileNameExcel,Vector rowdata,Vector column) {
		ExportExcel<FillingTableData> ex = new ExportExcel<FillingTableData>();
		java.util.List<FillingTableData> dataset = new ArrayList<FillingTableData>();
		try{
			String[] headers = new String[column.size()];
			for(int i = 0;i < headers.length;i++){
				headers[i] = base.client.util.CloneArray_ChangeStr.NulltoSpace(column.get(i));
			}
			dataset.add(new FillingTableData(rowdata));
			OutputStream out = new FileOutputStream(fileNameExcel);
			ex.exportExcel(headers, dataset, out);
			out.close();
			JOptionPane.showMessageDialog(null, "匯出成功!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class FillingTableData {
	private Vector rowdata;
	public FillingTableData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FillingTableData(Vector rowdata) {
		super();
		this.rowdata = rowdata;
	}
	public Vector getRowdata() {
		return rowdata;
	}
	public void setRowdata(Vector rowdata) {
		this.rowdata = rowdata;
	}
}
