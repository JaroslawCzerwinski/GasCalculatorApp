package eng.gascalculator.controller;

import java.net.URL;
import java.util.ResourceBundle;

import eng.gascalculator.calculations.Calculations;
import eng.gascalculator.gas.GasRecords;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable {

	public static final String DATE_COLUMN = "Date";
	public static final String DISTANCE_COLUMN = "Distance";
	public static final String LPG_COLUMN = "Lpg";
	public static final String LPG_AMOUNT_COLUMN = "Amount";
	public static final String LPG_PRICE_COLUMN = "Price";
	public static final String PET_COLUMN = "Petrol";
	public static final String PET_AMOUNT_COLUMN = "Amount";
	public static final String PET_PRICE_COLUMN = "Price";
	public static final String PAID_COLUMN = "Paid";
	public static final String SAVING_COLUMN = "Saving";
	public static final String GAS_EFFICIENCY_COLUM = "Gas Efficiency";

	@FXML
	private TextField dateTextField;

	@FXML
	private TextField distanceTextField;

	@FXML
	private TextField lpgAmountTextField;

	@FXML
	private TextField lpgPriceTextField;

	@FXML
	private TextField petrolAmountTextField;

	@FXML
	private TextField petrolPriceTextField;

	@FXML
	private TableView<GasRecords> contentTable;

	@FXML
	private Button calculateButton;

	@FXML
	private Label savingLabel;

	@FXML
	private Label paidLabel;

	@FXML
	private Slider gasEfficiencySlider;

	@FXML
	private Label gasEfficiencyLabel;

	@FXML
    private Button deleteButton;

	@FXML
	private Button addButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configurateButton();
		configurateSliders();
		configurateTable();

	}

	private void configurateButton() {
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				paidSavingCalculation();

			}

		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				deleteButtonClicked();

			}
		});

		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addRecord();
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

	private void configurateTable() {

		TableColumn<GasRecords, String> dateColumn = new TableColumn<GasRecords, String>(DATE_COLUMN);
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

		TableColumn<GasRecords, String> distanceColumn = new TableColumn<GasRecords, String>(DISTANCE_COLUMN);
		distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

		TableColumn<GasRecords, String> lpgColumn = new TableColumn<GasRecords, String>(LPG_COLUMN);

		TableColumn<GasRecords, String> lpgAmountColumn = new TableColumn<GasRecords, String>(LPG_AMOUNT_COLUMN);
		lpgAmountColumn.setCellValueFactory(new PropertyValueFactory<>("lpgAmount"));

		TableColumn<GasRecords, String> lpgPriceColumn = new TableColumn<GasRecords, String>(LPG_PRICE_COLUMN);
		lpgPriceColumn.setCellValueFactory(new PropertyValueFactory<>("lpgPrice"));

		TableColumn<GasRecords, String> petColumn = new TableColumn<GasRecords, String>(PET_COLUMN);

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

		contentTable.getColumns().addAll(dateColumn,distanceColumn,lpgColumn, petColumn, paidColumn,savingColumn ,gasEfficiencyColumn );
		lpgColumn.getColumns().addAll(lpgAmountColumn,lpgPriceColumn);
		petColumn.getColumns().addAll(petAmountColumn, petPriceColumn);


	}

	private void paidSavingCalculation() {
		Calculations calculations = new Calculations();

		double lpgAmo = Double.parseDouble(new String(lpgAmountTextField.getText()));
		double petAmo = Double.parseDouble(new String(petrolAmountTextField.getText()));
		double lpgPrice = Double.parseDouble(new String(lpgPriceTextField.getText()));
		double petPrice = Double.parseDouble(new String(petrolPriceTextField.getText()));
		double gasEfficiency = Double.parseDouble(gasEfficiencySlider.valueProperty().getValue().toString()) / 100;

		double paidValue = calculations.paidCalculations(lpgAmo, lpgPrice, petAmo, petPrice);
		double saivingValue = calculations.savingCalculations(lpgAmo, lpgPrice, petPrice, gasEfficiency);

		paidLabel.setText(String.format("%.2f", paidValue));
		savingLabel.setText(String.format("%.2f", saivingValue));

	}

	private void addRecord() {
		GasRecords gasRecords = new GasRecords();
		gasRecords.setDate(dateTextField.getText());
		gasRecords.setDistance(distanceTextField.getText());
		gasRecords.setLpgAmount(lpgAmountTextField.getText());
		gasRecords.setLpgPrice(lpgPriceTextField.getText());
		gasRecords.setPetAmount(petrolAmountTextField.getText());
		gasRecords.setPetPrice(petrolPriceTextField.getText());
		gasRecords.setPaid(paidLabel.getText());
		gasRecords.setSaving(savingLabel.getText());
		gasRecords.setGasEfficiency(gasEfficiencyLabel.getText());

		contentTable.getItems().add(gasRecords);

		dateTextField.clear();
		distanceTextField.clear();
		lpgAmountTextField.clear();
		lpgPriceTextField.clear();
		petrolAmountTextField.clear();
		petrolPriceTextField.clear();
		paidLabel.setText(String.valueOf(0.00));
		savingLabel.setText(String.valueOf(0.00));

	}

	public void deleteButtonClicked() {
		ObservableList<GasRecords> recordsSelected, allRecords;
		allRecords = contentTable.getItems();
		recordsSelected = contentTable.getSelectionModel().getSelectedItems();

		recordsSelected.forEach(allRecords::remove);

	}
}
