package eng.gascalculator.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import eng.gascalculator.gas.GasRecords;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;

public class ChartController implements Initializable {

	final ArrayList<CheckBox> cBList = new ArrayList<CheckBox>();

	XYChart.Series<String, Double> paidSeries = new XYChart.Series<String, Double>();
	XYChart.Series<String, Double> savingSeries = new XYChart.Series<String, Double>();
	XYChart.Series<String, Double> lpgPriceSeries = new XYChart.Series<String, Double>();
	XYChart.Series<String, Double> lpgAmountSeries = new XYChart.Series<String, Double>();
	XYChart.Series<String, Double> petPriceSeries = new XYChart.Series<String, Double>();
	XYChart.Series<String, Double> petAmountSeries = new XYChart.Series<String, Double>();
	XYChart.Series<String, Double> distanceSeries = new XYChart.Series<String, Double>();

	@FXML
	private LineChart<String, Double> lineChart;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;

	@FXML
	private CheckBox distanceCB, lpgAmountCB, lpgPriceCB, petAmountCB, petPriceCB, paidCB, savingCB;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@SuppressWarnings("unchecked")
	public void configurateCheckBox(ArrayList<GasRecords> gasRecordList) {

		xAxis.setLabel("Time");
		
		paidSeries.setName("Paid"); 
		savingSeries.setName("Saving"); 
		lpgPriceSeries.setName("Lpg price"); 
		lpgAmountSeries.setName("Lpg amount"); 
		petPriceSeries.setName("Petrol price"); 
		petAmountSeries.setName("Petrol amount"); 
		distanceSeries.setName("Car distance"); 
		
		cBList.add(distanceCB);
		cBList.add(lpgAmountCB);
		cBList.add(lpgPriceCB);
		cBList.add(petAmountCB);
		cBList.add(petPriceCB);
		cBList.add(paidCB);
		cBList.add(savingCB);

		EventHandler<ActionEvent> eventCB = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (event.getSource() instanceof CheckBox) {

					plotChart(gasRecordList);

				}
			}
		};
		for (CheckBox ch : cBList) {
			ch.setOnAction(eventCB);
		}
	}

	@SuppressWarnings("unchecked")
	public void plotChart(ArrayList<GasRecords> gasRecordList) {

		if (!paidCB.isSelected()) {
			paidSeries.getData().clear();
		}
		if (!savingCB.isSelected()) {
			savingSeries.getData().clear();
		}
		if (!lpgPriceCB.isSelected()) {
			lpgPriceSeries.getData().clear();
		}
		if (!petPriceCB.isSelected()) {
			petPriceSeries.getData().clear();
		}
		if (!lpgAmountCB.isSelected()) {
			lpgAmountSeries.getData().clear();
		}
		if (!petAmountCB.isSelected()) {
			petAmountSeries.getData().clear();
		}

		if (!distanceCB.isSelected()) {
			distanceSeries.getData().clear();
		}
		lineChart.getData().clear();

		if (paidCB.isSelected()) {
			for (int i = 0; i < gasRecordList.size(); i++) {
				GasRecords gasRecord = gasRecordList.get(i);

				String date = gasRecord.getDate().toString();
				Double paid = Double.parseDouble(gasRecord.getPaid().toString().replace(" z³", "").replace(",", "."));
				paidSeries.getData().add(new XYChart.Data<String, Double>(date, paid));

			}
			lineChart.getData().addAll(paidSeries);

		}

		if (savingCB.isSelected()) {
			for (int i = 0; i < gasRecordList.size(); i++) {
				GasRecords gasRecord = gasRecordList.get(i);

				String date = gasRecord.getDate().toString();
				Double saving = Double
						.parseDouble(gasRecord.getSaving().toString().replace(" z³", "").replace(",", "."));
				savingSeries.getData().add(new XYChart.Data<String, Double>(date, saving));

			}
			lineChart.getData().addAll(savingSeries);

		}

		if (lpgPriceCB.isSelected()) {
			for (int i = 0; i < gasRecordList.size(); i++) {
				GasRecords gasRecord = gasRecordList.get(i);

				String date = gasRecord.getDate().toString();
				Double lpgPrice = Double
						.parseDouble(gasRecord.getLpgPrice().toString().replace(" z³", "").replace(",", "."));
				lpgPriceSeries.getData().add(new XYChart.Data<String, Double>(date, lpgPrice));
			}
			lineChart.getData().addAll(lpgPriceSeries);

		}

		if (petPriceCB.isSelected()) {
			for (int i = 0; i < gasRecordList.size(); i++) {
				GasRecords gasRecord = gasRecordList.get(i);

				String date = gasRecord.getDate().toString();
				Double petPrice = Double
						.parseDouble(gasRecord.getPetPrice().toString().replace(" z³", "").replace(",", "."));
				petPriceSeries.getData().add(new XYChart.Data<String, Double>(date, petPrice));
			}
			lineChart.getData().addAll(petPriceSeries);

		}

		if (lpgAmountCB.isSelected()) {
			for (int i = 0; i < gasRecordList.size(); i++) {
				GasRecords gasRecord = gasRecordList.get(i);

				String date = gasRecord.getDate().toString();
				Double lpgAmount = Double.parseDouble(gasRecord.getLpgAmount().toString().replace(",", "."));
				lpgAmountSeries.getData().add(new XYChart.Data<String, Double>(date, lpgAmount));
			}
			lineChart.getData().addAll(lpgAmountSeries);

		}

		if (petAmountCB.isSelected()) {
			for (int i = 0; i < gasRecordList.size(); i++) {
				GasRecords gasRecord = gasRecordList.get(i);

				String date = gasRecord.getDate().toString();
				Double petAmount = Double.parseDouble(gasRecord.getPetAmount().toString().replace(",", "."));
				petAmountSeries.getData().add(new XYChart.Data<String, Double>(date, petAmount));
			}
			lineChart.getData().addAll(petAmountSeries);

		}
		if (distanceCB.isSelected()) {
			for (int i = 0; i < gasRecordList.size(); i++) {
				GasRecords gasRecord = gasRecordList.get(i);

				String date = gasRecord.getDate().toString();
				Double distance = Double.parseDouble(gasRecord.getDistance().toString().replace("km", ""));
				distanceSeries.getData().add(new XYChart.Data<String, Double>(date, distance));
			}
			lineChart.getData().addAll(distanceSeries);

		}

	}
}
