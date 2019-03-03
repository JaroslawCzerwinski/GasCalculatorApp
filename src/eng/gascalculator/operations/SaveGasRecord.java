package eng.gascalculator.operations;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import eng.gascalculator.gas.GasRecords;
import javafx.scene.control.TableView;

public class SaveGasRecord{

	public void saveRecords (TableView<GasRecords> cT){


		Workbook workbook = new HSSFWorkbook();
		Sheet spreadsheet = workbook.createSheet("GasRecords");
		Row row = spreadsheet.createRow(0);
		for (int j = 0; j < cT.getColumns().size(); j++) {
			row.createCell(j).setCellValue(cT.getColumns().get(j).getText());
		}

		for (int i = 0; i < cT.getItems().size(); i++) {
			row = spreadsheet.createRow(i + 1);
			for (int j = 0; j < cT.getColumns().size(); j++) {
				if (cT.getColumns().get(j).getCellData(i) != null) {
					row.createCell(j).setCellValue(cT.getColumns().get(j).getCellData(i).toString());
				} else {
					row.createCell(j).setCellValue("");
				}
			}
		}

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("GasData.xls");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
