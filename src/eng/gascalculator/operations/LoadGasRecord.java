package eng.gascalculator.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import eng.gascalculator.gas.GasRecords;
import javafx.scene.control.TableView;

public class LoadGasRecord {

	public void loadGasRecord(TableView<GasRecords> cT, String path) {

		Workbook wb = null;

		File filePath = new File(path);
		if (filePath.isFile()) {

			try {
				wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(path)));

			} catch (IOException e) {

				e.printStackTrace();
			}

			cT.getItems().clear();

			Sheet sheet = wb.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				GasRecords gasRecords = new GasRecords();
				for (int j = 0; j <= 8; j++) {

					if (sheet.getRow(i).getCell(j).getColumnIndex() == 0) {
						gasRecords.setDate(sheet.getRow(i).getCell(j).getStringCellValue());
						j++;
					}
					if (sheet.getRow(i).getCell(j).getColumnIndex() == 1) {
						gasRecords.setDistance(sheet.getRow(i).getCell(j).getStringCellValue());
						j++;
					}
					if (sheet.getRow(i).getCell(j).getColumnIndex() == 2) {
						gasRecords.setLpgAmount(sheet.getRow(i).getCell(j).getStringCellValue());
						j++;
					}
					if (sheet.getRow(i).getCell(j).getColumnIndex() == 3) {
						gasRecords.setLpgPrice(sheet.getRow(i).getCell(j).getStringCellValue());
						j++;
					}
					if (sheet.getRow(i).getCell(j).getColumnIndex() == 4) {
						gasRecords.setPetAmount(sheet.getRow(i).getCell(j).getStringCellValue());
						j++;
					}
					if (sheet.getRow(i).getCell(j).getColumnIndex() == 5) {
						gasRecords.setPetPrice(sheet.getRow(i).getCell(j).getStringCellValue());
						j++;
					}
					if (sheet.getRow(i).getCell(j).getColumnIndex() == 6) {
						gasRecords.setPaid(sheet.getRow(i).getCell(j).getStringCellValue());
						j++;
					}
					if (sheet.getRow(i).getCell(j).getColumnIndex() == 7) {
						gasRecords.setSaving(sheet.getRow(i).getCell(j).getStringCellValue());
						j++;
					}
					if (sheet.getRow(i).getCell(j).getColumnIndex() == 8) {
						gasRecords.setGasEfficiency(sheet.getRow(i).getCell(j).getStringCellValue());
						j++;
					}

					cT.getItems().add(gasRecords);

				}
			}
		}
	}
}
