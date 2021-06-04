package controller;

import interfaces.Validator;

// this class validates user inputs

public class UserInputManager implements Validator {

	// this method checks if the input is a number
	// using a regex
	@Override
	public boolean isNumber(String input) {
		return input.matches("^\\d+$");
	}

	// this method checks if the amount entered is divisible by 5 or not
	// since there are no '1' notes the amount needs to be a factor of 5
	@Override
	public boolean isAFactorOf5(String input) {

		if (Integer.parseInt(input) % 5 == 0)
			return true;
		return false;
	}
}
