package eng.gascalculator.operations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import eng.gascalculator.gas.GasRecords;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Operation {

	public void paidSavingCalculation(TextField lpgATF, TextField petATF, TextField lpgPTF, TextField petPTF,
			Slider gasES, Label paidL, Label savingL) {

		Boolean lpgA = lpgATF.getText().isEmpty() && lpgATF.getText() != null;
		Boolean petA = petATF.getText().isEmpty() && lpgATF.getText() != null;
		Boolean lpgP = lpgPTF.getText().isEmpty() && lpgATF.getText() != null;
		Boolean petP = petPTF.getText().isEmpty() && lpgATF.getText() != null;

		if (lpgA || petA || lpgP || petP) {
			if (lpgA) {
				JOptionPane.showMessageDialog(null, "Correct the value of lpg amount");
			}
			if (petA) {
				JOptionPane.showMessageDialog(null, "Correct the value of petrol amount");
			}
			if (lpgP) {
				JOptionPane.showMessageDialog(null, "Correct the value of lpg price");
			}
			if (petP) {
				JOptionPane.showMessageDialog(null, "Correct the value of petrol price");
			}
		} else {

			double lpgAmo = Double.parseDouble(lpgATF.getText().trim().replace(",", "."));
			double petAmo = Double.parseDouble(petATF.getText().trim().replace(",", "."));
			double lpgPrice = Double.parseDouble(lpgPTF.getText().trim().replace(",", "."));
			double petPrice = Double.parseDouble(petPTF.getText().trim().replace(",", "."));
			double gasEfficiency = Double.parseDouble(gasES.valueProperty().getValue().toString()) / 100;

			double paidValue = lpgAmo * lpgPrice + petAmo * petPrice;
			double savingValue = gasEfficiency * lpgAmo * petPrice - lpgAmo * lpgPrice;

			paidL.setText(String.format("%.2f zł", paidValue));
			savingL.setText(String.format("%.2f zł", savingValue));

		}
	}

	public void addRecord(TextField dateTF, TextField disTF, TextField lpgATF, TextField lpgPTF, TextField petATF,
			TextField petPTF, Label paidL, Label savingL, Label gasEL, TableView<GasRecords> cT, Label savingsTotalL,
			ArrayList<GasRecords> gasRecordList) {

		double paidValue = Double.parseDouble(paidL.getText().replace(",", ".").replace(" zł", ""));

		boolean dateOk = checkDate(dateTF);

		if (paidValue > 0.00 && dateOk) {
			GasRecords gasRecords = new GasRecords();

			gasRecords.setDate(dateTF.getText());
			gasRecords.setDistance(String.format("%.7s km", disTF.getText().replace(".", ",")));
			gasRecords.setLpgAmount(lpgATF.getText().replace(".", ","));
			gasRecords.setLpgPrice(String.format("%.5s zł", lpgPTF.getText().replace(".", ",")));
			gasRecords.setPetAmount(petATF.getText().replace(".", ","));
			gasRecords.setPetPrice(String.format("%.5s zł", petPTF.getText().replace(".", ",")));
			gasRecords.setPaid(paidL.getText());
			gasRecords.setSaving(savingL.getText());
			gasRecords.setGasEfficiency(String.format("%2$s%1$s", "%", gasEL.getText()));

			gasRecordList.add(gasRecords);

			cT.getItems().add(gasRecords);

			dateTF.clear();
			disTF.clear();
			lpgATF.clear();
			lpgPTF.clear();
			petATF.clear();
			petPTF.clear();
			paidL.setText(String.valueOf("0,00 zł"));
			savingL.setText(String.valueOf("0,00 zł"));

		}
		if (!dateOk){
			dateTF.clear();
			JOptionPane.showMessageDialog(null, "Date not exist, please correct");
		}
	}

	private boolean checkDate(TextField dateTF) {

		String[] divide = dateTF.getText().split("-");
		String year = divide[0];
		String month = divide[1];
		String day = divide[2];
		String result = day + "-" + month + "-" + year;

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		try {
			Date date = (Date) formatter.parse(result);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
			dateTF.clear();
			
			return false;
		}

		return true;

	}

	public void totalPaidSaving(TableView<GasRecords> cT, Label tCL, Label tSL) {

		Double totalCostValue = 0.00;
		Double totalSavingValue = 0.00;

		for (int i = 0; i < cT.getItems().size(); i++) {

			totalCostValue = totalCostValue
					+ Double.valueOf(String.valueOf(cT.getColumns().get(6).getCellObservableValue(i).getValue())
							.replace(" zł", "").replace(",", "."));

		}
		for (int i = 0; i < cT.getItems().size(); i++) {

			totalSavingValue = totalSavingValue
					+ Double.valueOf(String.valueOf(cT.getColumns().get(7).getCellObservableValue(i).getValue())
							.replace(" zł", "").replace(",", "."));

		}

		tCL.setText(String.format("%.2f zł", totalCostValue));
		tSL.setText(String.format("%.2f zł", totalSavingValue));
	}

	public void averageCost(TableView<GasRecords> cT, Label cL) {

		Double firstDistans = Double.valueOf(
				String.valueOf(cT.getColumns().get(1).getCellObservableValue(0).getValue()).replace(" km", ""));

		Double lastDistans = Double.valueOf(
				String.valueOf(cT.getColumns().get(1).getCellObservableValue(cT.getItems().size() - 1).getValue())
						.replace(" km", ""));

		Double totalCostValue = 0.00;
		for (int i = 0; i < cT.getItems().size(); i++) {

			totalCostValue = totalCostValue
					+ Double.valueOf(String.valueOf(cT.getColumns().get(6).getCellObservableValue(i).getValue())
							.replace(" zł", "").replace(",", "."));

		}

		Double averageCost = 100 * totalCostValue / (lastDistans - firstDistans);

		cL.setText(String.format("%.2f zł", averageCost));

	}

	public void averageConsumption(TableView<GasRecords> cT, Label aCL) {
		Double sumOfLPG = 0.00;

		for (int i = 0; i < cT.getItems().size(); i++) {

			sumOfLPG = sumOfLPG
					+ Double.valueOf(String.valueOf(cT.getColumns().get(2).getCellObservableValue(i).getValue())
							.replace(" zł", "").replace(",", "."));

		}
		Double sumOfPetrol = 0.00;

		for (int i = 0; i < cT.getItems().size(); i++) {

			sumOfPetrol = sumOfPetrol
					+ Double.valueOf(String.valueOf(cT.getColumns().get(4).getCellObservableValue(i).getValue())
							.replace(" zł", "").replace(",", "."));

		}
		Double firstDistans = Double.valueOf(
				String.valueOf(cT.getColumns().get(1).getCellObservableValue(0).getValue()).replace(" km", ""));

		Double lastDistans = Double.valueOf(
				String.valueOf(cT.getColumns().get(1).getCellObservableValue(cT.getItems().size() - 1).getValue())
						.replace(" km", ""));

		Double averageConsumption = ((sumOfLPG + sumOfPetrol) / (lastDistans - firstDistans)) * 100;

		aCL.setText(String.format("%.2f l", averageConsumption));
	}

	public void numericInputFormat(TextField t) {
		t.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.,]\\d{0,2})?")) {
					t.setText(oldValue);
				}
			}
		});

	}

	public boolean dateInputFormat(TextField t) {

		String date = t.getText();

		if (date.matches("\\d{0,2}([\\-,]\\d{0,2})([\\-,]\\d{0,4})")) {
			return true;
		} else
			JOptionPane.showMessageDialog(null, "Wrong date format. Use dd-mm-yyyy");
		return false;

	}

}