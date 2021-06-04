package interfaces;

// this interface gives the validation methods

public interface Validator {

	public boolean isNumber(String input); // check if the input is a number

	public boolean isAFactorOf5(String input); // check if the amount is a factor of 5
}
