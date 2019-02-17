package eng.gascalculator.gas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GasRecords {

	private StringProperty date;
	private StringProperty distance;
	private StringProperty lpgAmount;
	private StringProperty lpgPrice;
	private StringProperty petAmount;
	private StringProperty petPrice;
	private StringProperty paid;
	private StringProperty saving;
	private StringProperty gasEfficiency;

	public String getDate() {
		return date.get();
	}

	public void setDate(String date) {
		this.date.setValue(date);
	}

	public StringProperty dateProperty() {
		return date;
	}

	public String getDistance() {
		return distance.get();
	}

	public void setDistance(String distance) {
		this.distance.setValue(distance);
	}

	public StringProperty distanceProperty() {
		return distance;
	}

	public String getLpgAmount() {
		return lpgAmount.get();
	}

	public void setLpgAmount(String lpgAmount) {
		this.lpgAmount.setValue(lpgAmount);
	}

	public StringProperty lpgAmountProperty() {
		return lpgAmount;
	}

	public String getLpgPrice() {
		return lpgPrice.get();
	}

	public void setLpgPrice(String lpgPrice) {
		this.lpgPrice.setValue(lpgPrice);
	}

	public StringProperty lpgPriceProperty() {
		return lpgPrice;
	}

	public String getPetAmount() {
		return petAmount.get();
	}

	public void setPetAmount(String petAmount) {
		this.petAmount.setValue(petAmount);
	}

	public StringProperty petAmountProperty() {
		return petAmount;
	}

	public String getPetPrice() {
		return petPrice.get();
	}

	public void setPetPrice(String petPrice) {
		this.petPrice.setValue(petPrice);
	}

	public StringProperty petPriceProperty() {
		return petPrice;
	}

	public String getPaid() {
		return paid.get();
	}

	public void setPaid(String paid) {
		this.paid.setValue(paid);
	}

	public StringProperty paidProperty() {
		return paid;
	}

	public String getSaving() {
		return saving.get();
	}

	public void setSaving(String saving) {
		this.saving.setValue(saving);
	}

	public StringProperty savingProperty() {
		return saving;
	}

	public String getGasEfficiency() {
		return gasEfficiency.get();
	}

	public void setGasEfficiency(String gasEfficiency) {
		this.gasEfficiency.setValue(gasEfficiency);
	}

	public StringProperty gasEfficiencyProperty() {
		return gasEfficiency;
	}

	public GasRecords() {
		this.date = new SimpleStringProperty();
		this.distance = new SimpleStringProperty();
		this.lpgAmount = new SimpleStringProperty();
		this.lpgPrice = new SimpleStringProperty();
		this.petAmount = new SimpleStringProperty();
		this.petPrice = new SimpleStringProperty();
		this.paid = new SimpleStringProperty();
		this.saving = new SimpleStringProperty();
		this.gasEfficiency = new SimpleStringProperty();
	}
}
