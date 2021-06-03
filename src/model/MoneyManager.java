package model;

import java.util.HashMap;
import java.util.LinkedHashMap;

import UI.AtmMachineGUI;
import builder.AccountsBuilder;

public class MoneyManager {

	private static int totalMoneyInTheAtm;
	// using a linked hash map to maintain the order in which the money is inserted
	// it helps in iteration later on
	private static LinkedHashMap<Integer, Integer> moneyDenominationsMap = new LinkedHashMap<Integer, Integer>();
	private int withdrawalAmount;
	private AccountsBuilder currentUser;
	private AtmMachineGUI atmMachineGUI;

	public MoneyManager(int withdrawalAmount) {

		this.withdrawalAmount = withdrawalAmount;
		this.currentUser = AccountAndCredentialManager.getInstance().getCurrentUser(); // current user
	}

	// setting up the total money in ATM
	// setting up the denominations the total amount is made up of
	public void setUpMoneyInAtm() {
		totalMoneyInTheAtm = 1500;
		moneyDenominationsMap.put(50, 10);
		moneyDenominationsMap.put(20, 30);
		moneyDenominationsMap.put(10, 30);
		moneyDenominationsMap.put(5, 20);
//		moneyDenominationsMap.put("20", moneyDenominationsMap.get("20")-6);
	}

	// processing cash withdrawal
	public void processWithdrawal() {
		// checking if the money requested is more than the ATM holds
		if (withdrawalAmount > totalMoneyInTheAtm) {
			atmMachineGUI = new AtmMachineGUI();
			atmMachineGUI.notEnoughMoneyInTheMachine(totalMoneyInTheAtm);
		}
		// checking if the money requested is more than the customer has in its account
		else if (withdrawalAmount > currentUser.getAmountInAccount()) {
			atmMachineGUI = new AtmMachineGUI();
			atmMachineGUI.notEnoughMoneyInTheAccount(currentUser.getAmountInAccount());
		} else
			calculateDenominationsAndUpdateBalance();
	}

	// this method calculates the minimum number of notes to be dispensed
	private void calculateDenominationsAndUpdateBalance() {

		HashMap<Integer, Integer> dispensedNotesMap = new HashMap<Integer, Integer>();
		// getting the entry set of the linked hash map which is all the notes that can
		// be dispensed
		for (HashMap.Entry<Integer, Integer> entry : moneyDenominationsMap.entrySet()) {

			if (withdrawalAmount != 0) {
				// if the amount is higher than the current note
				if (withdrawalAmount >= entry.getKey())
					// getting the number of notes that will be dispensed for this note
					System.out.println("No of " + entry.getKey() + "'s" + " :" + withdrawalAmount / entry.getKey());
				dispensedNotesMap.put(entry.getKey(), withdrawalAmount / entry.getKey());
				withdrawalAmount = withdrawalAmount % entry.getKey(); // the withdrawal amount that is left
			}
		}
		atmMachineGUI = new AtmMachineGUI();
		atmMachineGUI.dispensedNotesGui(dispensedNotesMap);
	}
}
