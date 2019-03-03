package eng.gascalculator.operations;

import eng.gascalculator.gas.GasRecords;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Operation {

	public void paidSavingCalculation(TextField lpgATF, TextField petATF, TextField lpgPTF, TextField petPTF,
			Slider gasES, Label paidL, Label savingL) {

		double lpgAmo = Double.parseDouble(lpgATF.getText().replace(",", "."));
		double petAmo = Double.parseDouble(petATF.getText().replace(",", "."));
		double lpgPrice = Double.parseDouble(lpgPTF.getText().replace(",", "."));
		double petPrice = Double.parseDouble(petPTF.getText().replace(",", "."));
		double gasEfficiency = Double.parseDouble(gasES.valueProperty().getValue().toString()) / 100;

		double paidValue = lpgAmo * lpgPrice + petAmo * petPrice;
		double savingValue = gasEfficiency * lpgAmo * petPrice - lpgAmo * lpgPrice;

		paidL.setText(String.format("%.2f z³", paidValue));
		savingL.setText(String.format("%.2f z³", savingValue));

	}

	public void addRecord(TextField dateTF, TextField disTF, TextField lpgATF, TextField lpgPTF, TextField petATF,
			TextField petPTF, Label paidL, Label savingL, Label gasEL, TableView<GasRecords> cT) {

		double paidValue = Double.parseDouble(paidL.getText().replace(",", ".").replace(" z³", ""));

		if (paidValue > 0.00) {
			GasRecords gasRecords = new GasRecords();

			gasRecords.setDate(dateTF.getText());
			gasRecords.setDistance(String.format("%.7s km", disTF.getText().replace(".", ",")));
			gasRecords.setLpgAmount(lpgATF.getText().replace(".", ","));
			gasRecords.setLpgPrice(String.format("%.5s z³", lpgPTF.getText().replace(".", ",")));
			gasRecords.setPetAmount(petATF.getText().replace(".", ","));
			gasRecords.setPetPrice(String.format("%.5s z³", petPTF.getText().replace(".", ",")));
			gasRecords.setPaid(paidL.getText());
			gasRecords.setSaving(savingL.getText());
			gasRecords.setGasEfficiency(String.format("%2$s%1$s", "%", gasEL.getText()));

			cT.getItems().add(gasRecords);

			dateTF.clear();
			disTF.clear();
			lpgATF.clear();
			lpgPTF.clear();
			petATF.clear();
			petPTF.clear();
			paidL.setText(String.valueOf("0,00 z³"));
			savingL.setText(String.valueOf("0,00 z³"));
		}
	}

}
