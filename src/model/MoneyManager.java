package model;

import java.util.HashMap;
import java.util.LinkedHashMap;

import UI.AtmMachineGUI;
import builder.AccountsBuilder;
import interfaces.Manager;

public class MoneyManager implements Manager {

	private static int totalMoneyInTheAtm;
	// using a linked hash map to maintain the order in which the money is inserted
	// it helps in iteration later on
	private static LinkedHashMap<Integer, Integer> moneyDenominationsMap = new LinkedHashMap<Integer, Integer>();
	private int withdrawalAmount, depositAmount;
	private final int dispensingAmount, depositedAmount;
	private AccountsBuilder currentUser;
	private AtmMachineGUI atmMachineGUI;

	public MoneyManager(int withdrawalAmount, int depositAmount) {

		this.withdrawalAmount = withdrawalAmount; // to be used for calculations during withdrawal
		this.dispensingAmount = withdrawalAmount; // to be used later in a message
		this.depositAmount = depositAmount; // this will be used for calculation
		this.depositedAmount = depositAmount; // this will be added to the machine
		this.currentUser = AccountAndCredentialManager.getInstance().getCurrentUser(); // current user
	}

	// setting up the total money in ATM
	// setting up the denominations the total amount is made up of
	@Override
	public void setUpMoneyInAtm() {
		totalMoneyInTheAtm = 1500;
		moneyDenominationsMap.put(50, 10);
		moneyDenominationsMap.put(20, 30);
		moneyDenominationsMap.put(10, 30);
		moneyDenominationsMap.put(5, 20);
	}

	// processing cash withdrawal
	@Override
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
		int numberOfNotes = 0;
		int notesToBeDispensedOfAnotherDenomination = 0;
		// getting the entry set of the linked hash map which is all the notes that can
		// be dispensed
		for (HashMap.Entry<Integer, Integer> entry : moneyDenominationsMap.entrySet()) {

			if (withdrawalAmount != 0) {

				// if the amount is higher than the current note
				if (withdrawalAmount >= entry.getKey())

					// getting the number of notes that will be dispensed for this note
					// number of notes that will be dispensed for this note
					numberOfNotes = withdrawalAmount / entry.getKey();

				// if the notes of this denomination are not left in the machine
				// if the numberOfNotes is more than the notes present of this denomination
				// dispense the maximum number of notes present
				// and calculate the notes left of this denomination
				// this prevents the the number of notes to go in negative logically
				if (numberOfNotes > entry.getValue()) {
					// the maximum number of these notes will
					numberOfNotes = entry.getValue();

					// calculating the notes left of this denomination
					notesToBeDispensedOfAnotherDenomination = (withdrawalAmount / entry.getKey()) - entry.getValue();
				}

				dispensedNotesMap.put(entry.getKey(), numberOfNotes); // putting the note and its number in the map

				// removing the notes from the map (ATM) that have been dispensed
				moneyDenominationsMap.put(entry.getKey(), moneyDenominationsMap.get(entry.getKey()) - numberOfNotes);

				// getting the carried over notes of the denomination and adding it to the left
				// over amount
				withdrawalAmount = (withdrawalAmount % entry.getKey())
						+ notesToBeDispensedOfAnotherDenomination * entry.getKey(); // the withdrawal amount that is
																					// left

				numberOfNotes = 0; // resetting the counter to prevent any value carry over
				notesToBeDispensedOfAnotherDenomination = 0; // resetting the counter to prevent any value carry over
			}
		}
		// after whole operation is done
		// remove this amount from the ATM
		totalMoneyInTheAtm -= dispensingAmount;

		// if you want to check the number of notes being changed after a transaction
		// uncomment the following method
		 //showTheLeftNotes();

		// update the user balance after the money has been dispensed
		AccountAndCredentialManager.getInstance().updateUserBalance(dispensingAmount);
		
		// show the dispensed cash format and go back
		atmMachineGUI = new AtmMachineGUI();
		atmMachineGUI.dispensedNotesGui(dispensedNotesMap, dispensingAmount);
	}

	// show the number of notes left in the machine after transaction

	/*private void showTheLeftNotes() {
		for (HashMap.Entry<Integer, Integer> entry : moneyDenominationsMap.entrySet()) {
			System.out.println("No of  notes left of " + entry.getKey() + " : " + entry.getValue());
		}
	}*/

	@Override
	public void processCashDeposit() {

		int numberOfNotes = 0;
		for (HashMap.Entry<Integer, Integer> entry : moneyDenominationsMap.entrySet()) {

			if (depositAmount != 0) {

				// getting the number of notes that will be deposited of this note
				// number of notes that will be added for this note
				numberOfNotes = depositAmount / entry.getKey();

				// adding the notes to the map (ATM) that have been deposited
				moneyDenominationsMap.put(entry.getKey(), moneyDenominationsMap.get(entry.getKey()) + numberOfNotes);

				// left over amount to be deposited
				depositAmount = depositAmount % entry.getKey();

				numberOfNotes = 0; // resetting the counter to prevent any value carry over
			}
		}
		totalMoneyInTheAtm += depositedAmount;

		// update the user balance after the money has been deposited
		AccountAndCredentialManager.getInstance().amountDeposited(depositedAmount);
		
		// go back and show some message
		atmMachineGUI = new AtmMachineGUI();
		atmMachineGUI.depositedAmountMessage(depositedAmount);
	}

	public int getTotalMoneyInAtm() {
		return totalMoneyInTheAtm;
	}
}
