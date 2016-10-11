package com.green.APITesting.datautils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import com.green.utils.Utils;

/**
 * excel data driver
 * 
 * @author Dieson Zuo
 */
public class ExcelUtils implements Iterator<Object[]> {
	private static final String path = System.getProperty("user.dir");

	private static String filePath;
	private static String sheetName;
	private static String excelVersion;
	private XSSFWorkbook book07 = null;
	private XSSFSheet sheet07 = null;
	private int rowNum = 0; // All of the row
	private int colNum = 0; // All of the column
	private int curRowNo = 0; // The current row
	private int curWrite = 0;
	private String[] columnnName; // title

	public ExcelUtils(String excelName, String sheet) {
		filePath = path + excelName;
		sheetName = sheet;

		switch (Utils.getOs()) {
		case "WINDOWS":
			filePath = path + "\\test_data\\" + excelName;
			break;
		case "LINUX":
			filePath = path + "/test_data/" + excelName;
			break;
		default:
			break;
		}

		try {
			read(filePath, sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void read(String excelPath, String sheetName) throws Exception {
		try {
			book07 = new XSSFWorkbook(OPCPackage.open(new File(excelPath)));
			sheet07 = book07.getSheet(sheetName);
			rowNum = sheet07.getPhysicalNumberOfRows();

			Row row = sheet07.getRow(0);
			colNum = row.getPhysicalNumberOfCells();
			Iterator<Cell> heads = row.cellIterator();

			columnnName = new String[colNum];
			for (int count = 0; heads.hasNext(); count++) {
				Cell cell = heads.next();
				columnnName[count] = cell.getRichStringCellValue().toString();
			}
			this.curRowNo++;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean hasNext() {
		if (this.rowNum == 0 || this.curRowNo >= this.rowNum) {
			curWrite = 1;
			return false;
		} else
			return true;
	}

	public Object[] next() {
		Map<String, Object> s = new HashMap<String, Object>();
		Object temp = "";

		Row row = sheet07.getRow(curRowNo);
		for (int i = 0; i < colNum; i++) {
			if (row.getCell(i) != null) {
				switch (row.getCell(i).getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					temp = "";
					break;
				case Cell.CELL_TYPE_NUMERIC:
					temp = row.getCell(i).getNumericCellValue();
					break;
				case Cell.CELL_TYPE_STRING:
					temp = row.getCell(i).getRichStringCellValue().toString();
					break;
				default:
					break;
				}
				s.put(this.columnnName[i], temp);
			} else {
				s.put(this.columnnName[i], "");
			}
		}

		Object r[] = new Object[1];
		r[0] = s;
		this.curRowNo++;
		this.curWrite++;
		return r;
	}

	/**
	 * write to excel
	 * 
	 * @param titleName
	 * @param value
	 */
	public void write(String titleName, String value) {
		int rowNo = getTitleNo(titleName);

		if (rowNo == -1) {
			Assert.fail("Excel have no title name!");
			return;
		}

		File file = new File(filePath);
		FileInputStream fis;
		FileOutputStream fos;
		try {

			fis = new FileInputStream(filePath);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheet(sheetName);
			XSSFRow row = sheet.getRow(curWrite);
			XSSFCell cell;

			// If the cell exist, modify. Does not exist, create.
			if (row.getCell(rowNo) != null) {
				cell = row.getCell(rowNo);
			} else {
				cell = row.createCell(rowNo);
			}

			// write
			cell.setCellValue(value);

			fos = new FileOutputStream(file);
			wb.write(fos);
			fos.flush();
			fos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the title number
	 * 
	 * @param titleName
	 * @return
	 */
	private int getTitleNo(String titleName) {
		int col = -1;
		File excelPath = new File(filePath);
		try {
			if (excelVersion.equals("07")) {
				OPCPackage pkg = OPCPackage.open(excelPath);
				XSSFWorkbook book = new XSSFWorkbook(pkg);
				Sheet sheet = book.getSheet(sheetName);
				Row titles = sheet.getRow(0);

				for (int i = 0; i < titles.getPhysicalNumberOfCells(); i++) {
					String title = titles.getCell(i).toString();
					if (title.equals(titleName)) {
						col = i;
						break;
					}
				}
			} else {
				HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(excelPath));
				HSSFSheet sheet = book.getSheet(sheetName);
				Row titles = sheet.getRow(0);

				for (int i = 0; i < titles.getPhysicalNumberOfCells(); i++) {
					String title = titles.getCell(i).toString();
					if (title.equals(titleName)) {
						col = i;
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return col;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
	}

}
