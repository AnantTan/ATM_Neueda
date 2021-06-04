package controller;

import interfaces.Validator;

public class UserInputManager implements Validator {

	@Override
	public boolean isNumber(String input) {
		return input.matches("^\\d+$");
	}

	@Override
	public boolean isAFactorOf5(String input) {

		if (Integer.parseInt(input) % 5 == 0)
			return true;
		return false;
	}
}
