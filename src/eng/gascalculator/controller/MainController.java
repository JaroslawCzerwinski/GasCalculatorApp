package eng.gascalculator.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class MainController implements Initializable {

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
	private Button clearButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configurateButton();
		configurateSliders();

	}

	public Double getGasEfficiency() {
		return Double.parseDouble(gasEfficiencySlider.valueProperty().getValue().toString()) / 100;
	}

	private void configurateButton() {
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				double lpgAmo = Double.parseDouble(new String(lpgAmountTextField.getText()));
				double petAmo = Double.parseDouble(new String(petrolAmountTextField.getText()));
				double lpgPrice = Double.parseDouble(new String(lpgPriceTextField.getText()));
				double petPrice = Double.parseDouble(new String(petrolPriceTextField.getText()));

				double paidValue = lpgAmo * lpgPrice + petAmo * petPrice;
				double saivingValue = getGasEfficiency() * lpgAmo * petPrice - lpgAmo * lpgPrice;

				paidLabel.setText(String.format("%.2f", paidValue));
				savingLabel.setText(String.format("%.2f", saivingValue));

			}
		});

		clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
            	dateTextField.clear();
            	distanceTextField.clear();
            	lpgAmountTextField.clear();
            	petrolAmountTextField.clear();
            	lpgPriceTextField.clear();
            	petrolPriceTextField.clear();
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
}
