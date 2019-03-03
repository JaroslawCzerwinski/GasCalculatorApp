package eng.gascalculator.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
	// public static final String LPG_COLUMN = "Lpg";
	public static final String LPG_AMOUNT_COLUMN = "LPG Amount";
	public static final String LPG_PRICE_COLUMN = "LPG Price";
	// public static final String PET_COLUMN = "Petrol";
	public static final String PET_AMOUNT_COLUMN = "Pet Amount";
	public static final String PET_PRICE_COLUMN = "Pet Price";
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

	@FXML
	private Button saveButton;

	@FXML
	private Button openButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configurateButton();
		configurateSliders();
		configurateTable();
		loadRecords();

	}

	private void configurateButton() {
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Operation calculations = new Operation();
				calculations.paidSavingCalculation(lpgAmountTextField, petrolAmountTextField, lpgPriceTextField,
						petrolPriceTextField, gasEfficiencySlider, paidLabel, savingLabel);

			}

		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ObservableList<GasRecords> recordsSelected, allRecords;
				allRecords = contentTable.getItems();
				recordsSelected = contentTable.getSelectionModel().getSelectedItems();
				recordsSelected.forEach(allRecords::remove);

			}
		});

		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Operation calculations = new Operation();
				calculations.addRecord(dateTextField, distanceTextField, lpgAmountTextField, lpgPriceTextField,
						petrolAmountTextField, petrolPriceTextField, paidLabel, savingLabel, gasEfficiencyLabel,
						contentTable);
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
					lGasRecord.loadGasRecord(contentTable, path);
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
	private void configurateTable() {

		TableColumn<GasRecords, String> dateColumn = new TableColumn<GasRecords, String>(DATE_COLUMN);
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

		TableColumn<GasRecords, String> distanceColumn = new TableColumn<GasRecords, String>(DISTANCE_COLUMN);
		distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

		// TableColumn<GasRecords, String> lpgColumn = new
		// TableColumn<GasRecords, String>(LPG_COLUMN);

		TableColumn<GasRecords, String> lpgAmountColumn = new TableColumn<GasRecords, String>(LPG_AMOUNT_COLUMN);
		lpgAmountColumn.setCellValueFactory(new PropertyValueFactory<>("lpgAmount"));

		TableColumn<GasRecords, String> lpgPriceColumn = new TableColumn<GasRecords, String>(LPG_PRICE_COLUMN);
		lpgPriceColumn.setCellValueFactory(new PropertyValueFactory<>("lpgPrice"));

		// TableColumn<GasRecords, String> petColumn = new
		// TableColumn<GasRecords, String>(PET_COLUMN);

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
		// lpgColumn.getColumns().addAll(lpgAmountColumn, lpgPriceColumn);
		// petColumn.getColumns().addAll(petAmountColumn, petPriceColumn);

	}


	private void loadRecords() {

		String fileName = "GasData.xls";

		LoadGasRecord lGasRecord = new LoadGasRecord();
		String path = fileName;
		lGasRecord.loadGasRecord(contentTable, path);

	}

}
