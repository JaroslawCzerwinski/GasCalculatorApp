package eng.gascalculator.calculations;

public class Calculations {

	private Double paidValue;
	private Double savingValue;

	public Double paidCalculations(double lpgAmo, double lpgPrice, double petAmo, double petPrice) {
		double paidValue = lpgAmo * lpgPrice + petAmo * petPrice;
		return paidValue;

	}

	public Double savingCalculations(double lpgAmo, double lpgPrice, double petPrice, double gasEfficiency) {
		double savingValue = gasEfficiency * lpgAmo * petPrice - lpgAmo * lpgPrice;
		return savingValue;

	}

}
