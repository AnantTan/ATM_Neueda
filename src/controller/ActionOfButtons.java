package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import UI.AtmMachineGUI;
import model.AccountAndCredentialManager;
import model.MoneyManager;

// this class is control center of the ATM
// this class looks after the UI 

public class ActionOfButtons implements ActionListener {

	private AtmMachineGUI atmMachineGui;
	// having the same manager throughout the course
	private static UserInputManager inputManager = new UserInputManager();

	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		// go to reset method
		if (e.getActionCommand().equals("reset")) {
			AtmMachineGUI.resetDetails();
		} else if (e.getActionCommand().equals("login")) {

			// checking input validity
			if (inputManager.isNumber(AtmMachineGUI.accountNumberTextField.getText())
					&& inputManager.isNumber(AtmMachineGUI.pinField.getText())) {

				// checking the user login info
				if (AccountAndCredentialManager.getInstance().checkLoginInfo(
						AtmMachineGUI.accountNumberTextField.getText(),
						Integer.parseInt(AtmMachineGUI.pinField.getText()))) {

					// if the login details match the database open the dash board
					atmMachineGui = new AtmMachineGUI();
					atmMachineGui.userAccountGui();

				} else
					// if the login details do not match show a message
					JOptionPane.showMessageDialog(null, "You account number and pin do not match", "Inalid details",
							JOptionPane.ERROR_MESSAGE);
			} else
				// if the input is invalid show a message
				showInputErrorMessage();

		} else if (e.getActionCommand().equals("logout")) {

			AtmMachineGUI.userAccountFrame.dispose(); // close the current user
			AtmMachineGUI.userAccountFrame.getContentPane().removeAll(); // remove whatever was open by the user
			AtmMachineGUI.loginFrame.enable(); // enable the login window again

			// could also use 'system.exit(0)' if the program is supposed to stop here

		} else if (e.getActionCommand().equals("cashWithdrawl")) {
			MoneyManager manager = new MoneyManager(0, 0); // instance of money manager class

			// if there is no money left in the ATM
			// then do not open the withdraw window
			// show a message
			if (manager.getTotalMoneyInAtm() < 5) {

				// message
				JOptionPane.showMessageDialog(null, "There is no money left in the MACHINE", "No money",
						JOptionPane.ERROR_MESSAGE);
			} else {

				atmMachineGui = new AtmMachineGUI();
				atmMachineGui.cashWtihdrawlGui(); // open cash withdraw window
			}
		} else if (e.getActionCommand().equals("withdraw")) {

			// checking the user input
			if (inputManager.isNumber(AtmMachineGUI.withdrawalAmountTextField.getText())) {

				// check if the amount is divisible by 5
				// there are no notes of '1' in the machine
				// so the amount should be completely divisible by 5
				if (inputManager.isAFactorOf5(AtmMachineGUI.withdrawalAmountTextField.getText())) {

					// getting the withdrawal amount requested from the text field
					// converting it into integer then passing it to money exchanger
					new MoneyManager(Integer.parseInt(AtmMachineGUI.withdrawalAmountTextField.getText()), 0)
							.processWithdrawal();
				} else
					JOptionPane.showMessageDialog(null, "The withdrawal amount should be divisible by 5",
							"No '1€' notes", JOptionPane.ERROR_MESSAGE);
			} else
				showInputErrorMessage();

		} else if (e.getActionCommand().equals("check balance")) {

			atmMachineGui = new AtmMachineGUI();
			atmMachineGui.checkBalanceGui();

		} else if (e.getActionCommand().equals("details")) {

			atmMachineGui = new AtmMachineGUI();
			atmMachineGui.showAccountDetails();

		} else if (e.getActionCommand().equals("depositCash")) {

			atmMachineGui = new AtmMachineGUI();
			atmMachineGui.cashDepositGui();
		} else if (e.getActionCommand().equals("deposit")) {

			// checking user input
			if (inputManager.isNumber(AtmMachineGUI.depositAmountTextField.getText())) {

				// check if the amount is divisible by 5
				// there are no notes of '1' in the machine
				// so the amount should be completely divisible by 5
				// otherwise the number of notes cannot be increased in the machine
				if (inputManager.isAFactorOf5(AtmMachineGUI.depositAmountTextField.getText())) {

					// getting the amount to be deposited
					// converting it to integer
					// passing it to money manager
					new MoneyManager(0, Integer.parseInt(AtmMachineGUI.depositAmountTextField.getText()))
							.processCashDeposit();
				} else
					JOptionPane.showMessageDialog(null, "There deposit amount should be divisible by 5", "Error",
							JOptionPane.ERROR_MESSAGE);
			} else
				showInputErrorMessage();
		} else if (e.getActionCommand().equals("transactions")) {
			atmMachineGui = new AtmMachineGUI();

			// get the transaction map from the credential manager class
			atmMachineGui.transactionsGui(AccountAndCredentialManager.getInstance().getTransactionMap());
		}
	}

	// show a generic error message
	private void showInputErrorMessage() {
		JOptionPane.showMessageDialog(null, "Please provide a valid input (ATM takes only numbers!)", "Inalid input",
				JOptionPane.ERROR_MESSAGE);
	}

}
