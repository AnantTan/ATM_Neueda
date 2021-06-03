package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import UI.AtmMachineGUI;
import model.AccountAndCredentialManager;
import model.MoneyManager;

public class ActionOfButtons implements ActionListener {

	private AtmMachineGUI atmMachineGui;

	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getActionCommand().equals("reset")) {
			System.out.println("yesssss");
			AtmMachineGUI.resetDetails();
		} else if (e.getActionCommand().equals("login")) {

			// checking login info
			if (AccountAndCredentialManager.getInstance().checkLoginInfo(AtmMachineGUI.accountNumberTextField.getText(),
					Integer.parseInt(AtmMachineGUI.pinField.getText()))) {
				atmMachineGui = new AtmMachineGUI();
				atmMachineGui.userAccountGui();
			} else
				JOptionPane.showMessageDialog(null, "You account number and pin do not match", "Inalid details",
						JOptionPane.ERROR_MESSAGE);
		} else if (e.getActionCommand().equals("logout")) {

			AtmMachineGUI.userAccountFrame.dispose(); // close the current user
			AtmMachineGUI.userAccountFrame.getContentPane().removeAll(); // remove whatever was open by the user
			AtmMachineGUI.loginFrame.enable(); // enable the login window again

			// could also use 'system.exit(0)' if the program is supposed to stop here
		} else if (e.getActionCommand().equals("cashWithdrawl")) {
			MoneyManager manager = new MoneyManager(0);
			
			// if there is no money left in the ATM
			// then do  not open the withdraw window
			// show a message
			if(manager.getTotalMoneyInAtm()<5)
			{
				// message
				JOptionPane.showMessageDialog(null, "There is no money left in the MACHINE", "No money",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
			atmMachineGui = new AtmMachineGUI();
			atmMachineGui.cashWtihdrawlGui(); // open cash withdraw window
			}
		} else if (e.getActionCommand().equals("withdraw")) {
			// getting the withdrawal amount requested from the text field
			// converting it into integer then passing it to money exchanger
			new MoneyManager(Integer.parseInt(AtmMachineGUI.withdrawalAmountText.getText())).processWithdrawal();
		} else if (e.getActionCommand().equals("check balance")) {
			atmMachineGui = new AtmMachineGUI();
			atmMachineGui.checkBalanceGui();
		}
	}

}
