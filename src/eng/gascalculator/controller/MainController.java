package eng.gascalculator.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;

import eng.gascalculator.gas.GasRecords;
import eng.gascalculator.operations.Operation;
import eng.gascalculator.operations.LoadGasRecord;
import eng.gascalculator.operations.SaveGasRecord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController implements Initializable {

	public static final String DATE_COLUMN = "Date";
	public static final String DISTANCE_COLUMN = "Distance";
	public static final String LPG_AMOUNT_COLUMN = "LPG Amount";
	public static final String LPG_PRICE_COLUMN = "LPG Price";
	public static final String PET_AMOUNT_COLUMN = "Pet Amount";
	public static final String PET_PRICE_COLUMN = "Pet Price";
	public static final String PAID_COLUMN = "Paid";
	public static final String SAVING_COLUMN = "Saving";
	public static final String GAS_EFFICIENCY_COLUM = "Gas Efficiency";

	@FXML
	private TextField dateTextField, distanceTextField, lpgAmountTextField, lpgPriceTextField, petrolAmountTextField,
			petrolPriceTextField;

	@FXML
	private Label savingLabel, paidLabel, gasEfficiencyLabel, averageCostLabel, averageConsumptionLabel, totalCostLabel,
			totalSavingsLabel;

	@FXML
	private Button calculateButton, deleteButton, addButton, saveButton, openButton, chartsButton;

	@FXML
	private TableView<GasRecords> contentTable;

	@FXML
	private Slider gasEfficiencySlider;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Operation operation = new Operation();
		ArrayList<GasRecords> gasRecordList = new ArrayList<GasRecords>();

		configurateButton(gasRecordList);
		configurateSliders();
		configurateTable();
		loadRecords(gasRecordList);

		if (contentTable.getItems().isEmpty()) {

		} else {
			operation.totalPaidSaving(contentTable, totalCostLabel, totalSavingsLabel);
			operation.averageCost(contentTable, averageCostLabel);
			operation.averageConsumption(contentTable, averageConsumptionLabel);
		}

		operation.numericInputFormat(distanceTextField);
		operation.numericInputFormat(lpgAmountTextField);
		operation.numericInputFormat(lpgPriceTextField);
		operation.numericInputFormat(petrolAmountTextField);
		operation.numericInputFormat(petrolPriceTextField);

	}

	private void configurateButton(ArrayList<GasRecords> gasRecordList) {

		calculateButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Operation operation = new Operation();
				operation.paidSavingCalculation(lpgAmountTextField, petrolAmountTextField, lpgPriceTextField,
						petrolPriceTextField, gasEfficiencySlider, paidLabel, savingLabel);

				operation.totalPaidSaving(contentTable, totalCostLabel, totalSavingsLabel);
				operation.averageCost(contentTable, averageCostLabel);
				operation.averageConsumption(contentTable, averageConsumptionLabel);

			}

		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Operation operation = new Operation();
				ObservableList<GasRecords> recordsSelected, allRecords;
				allRecords = contentTable.getItems();
				recordsSelected = contentTable.getSelectionModel().getSelectedItems();
				recordsSelected.forEach(allRecords::remove);

				operation.totalPaidSaving(contentTable, totalCostLabel, totalSavingsLabel);
				operation.averageCost(contentTable, averageCostLabel);
				operation.averageConsumption(contentTable, averageConsumptionLabel);

			}
		});

		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Operation operation = new Operation();

				boolean dateInputFormat = operation.dateInputFormat(dateTextField);

				if (dateInputFormat) {
					operation.addRecord(dateTextField, distanceTextField, lpgAmountTextField, lpgPriceTextField,
							petrolAmountTextField, petrolPriceTextField, paidLabel, savingLabel, gasEfficiencyLabel,
							contentTable, totalSavingsLabel, gasRecordList);

					operation.totalPaidSaving(contentTable, totalCostLabel, totalSavingsLabel);
					operation.averageCost(contentTable, averageCostLabel);
					operation.averageConsumption(contentTable, averageConsumptionLabel);
				}
			}

		});

		chartsButton.setOnAction(new EventHandler<ActionEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handle(ActionEvent event) {

				try {
					Stage chartsWindow = new Stage();
					FXMLLoader loader = new FXMLLoader(
							getClass().getResource("/eng/gascalculator/view/ChartsPane.fxml"));

					Parent chartParent = (Parent) loader.load();
					chartsWindow.setTitle("Charts");
					chartsWindow.setScene(new Scene(chartParent, 1800, 600));
					chartsWindow.show();

					ChartController chartControl = loader.getController();

					chartControl.configurateCheckBox(gasRecordList);

					chartControl.plotChart(gasRecordList);

				} catch (IOException e1) {
					
					e1.printStackTrace();
				}

			}

		});

		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SaveGasRecord sGasRecord = new SaveGasRecord();
				sGasRecord.saveRecords(contentTable);
			}

		});

		openButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				JFileChooser fs = new JFileChooser(new File("c://"));
				fs.setDialogTitle("Open a File");
				int result = fs.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {

					LoadGasRecord lGasRecord = new LoadGasRecord();
					String path = fs.getSelectedFile().getPath();
					lGasRecord.loadGasRecord(contentTable, path, gasRecordList);
				}
			}

		});

	}

	private void configurateSliders() {
		gasEfficiencySlider.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				gasEfficiencyLabel.setText(String.format("%.2f", new_val));

			}
		});
	}

	@SuppressWarnings("unchecked")
	public void configurateTable() {

		TableColumn<GasRecords, String> dateColumn = new TableColumn<GasRecords, String>(DATE_COLUMN);
		dateColumn.setCellValueFactory(new PropertyValueFactory<GasRecords, String>("date"));

		TableColumn<GasRecords, String> distanceColumn = new TableColumn<GasRecords, String>(DISTANCE_COLUMN);
		distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

		TableColumn<GasRecords, String> lpgAmountColumn = new TableColumn<GasRecords, String>(LPG_AMOUNT_COLUMN);
		lpgAmountColumn.setCellValueFactory(new PropertyValueFactory<>("lpgAmount"));

		TableColumn<GasRecords, String> lpgPriceColumn = new TableColumn<GasRecords, String>(LPG_PRICE_COLUMN);
		lpgPriceColumn.setCellValueFactory(new PropertyValueFactory<>("lpgPrice"));

		TableColumn<GasRecords, String> petAmountColumn = new TableColumn<GasRecords, String>(PET_AMOUNT_COLUMN);
		petAmountColumn.setCellValueFactory(new PropertyValueFactory<>("petAmount"));

		TableColumn<GasRecords, String> petPriceColumn = new TableColumn<GasRecords, String>(PET_PRICE_COLUMN);
		petPriceColumn.setCellValueFactory(new PropertyValueFactory<>("petPrice"));

		TableColumn<GasRecords, String> paidColumn = new TableColumn<GasRecords, String>(PAID_COLUMN);
		paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));

		TableColumn<GasRecords, String> savingColumn = new TableColumn<GasRecords, String>(SAVING_COLUMN);
		savingColumn.setCellValueFactory(new PropertyValueFactory<>("saving"));

		TableColumn<GasRecords, String> gasEfficiencyColumn = new TableColumn<GasRecords, String>(GAS_EFFICIENCY_COLUM);
		gasEfficiencyColumn.setCellValueFactory(new PropertyValueFactory<>("gasEfficiency"));

		contentTable.getColumns().addAll(dateColumn, distanceColumn, lpgAmountColumn, lpgPriceColumn, petAmountColumn,
				petPriceColumn, paidColumn, savingColumn, gasEfficiencyColumn);

	}

	private void loadRecords(ArrayList<GasRecords> gasRecordList) {

		String fileName = "GasData.xls";

		LoadGasRecord lGasRecord = new LoadGasRecord();
		String path = fileName;
		lGasRecord.loadGasRecord(contentTable, path, gasRecordList);

	}

}
