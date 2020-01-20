package org.SNXa.poi;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class PoiSS {
	public void saveToSheet(List<Map> rows) throws Throwable {

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Data");

		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(1);

		// and when done write
		OutputStream fileOut = new FileOutputStream("Data.xls");
		wb.write(fileOut);
		wb.close();
	}// ()

}
