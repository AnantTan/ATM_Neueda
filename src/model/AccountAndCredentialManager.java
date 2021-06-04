package model;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import builder.AccountsBuilder;
import builder.TransactionBuilder;

// this class implements the singleton pattern
// in production this type of class might be the back end connecting to a database

public enum AccountAndCredentialManager {

	// there can be only instance of the enum class
	// this adds to security of the program
	AccountAndCredentialManagerInstance; // instance of this enum class

	private HashMap<Integer, AccountsBuilder> accountsMap = new HashMap<Integer, AccountsBuilder>(); // accounts
	private HashMap<Integer, TransactionBuilder> transactionsMap = new HashMap<Integer, TransactionBuilder>(); // transactions
	private AccountsBuilder currentUser; // current logged in user
	private Date date; // date and time

	// this method will return the instance of this class
	public static AccountAndCredentialManager getInstance() {
		return AccountAndCredentialManagerInstance;
	}

	public void generatingDefaultAccounts() {

		AccountsBuilder defaultAccount_1, defaultAccount_2; // default accounts
		TransactionBuilder defaultTransaction_1, defaultTransaction_2; // default transactions

		// creating default users using the builder pattern
		defaultAccount_1 = new AccountsBuilder.AccountBuilderClass().accountNumber(123456789).accountHolder("Default 1")
				.amountInAccount(800).pin(1234).overdraftAmount(200).build();

		defaultAccount_2 = new AccountsBuilder.AccountBuilderClass().accountNumber(987654321).accountHolder("Default 2")
				.amountInAccount(1230).pin(4321).overdraftAmount(150).build();

		// initializing the date
		date = new Date();
		// creating the first transactions which is the opening balance
		defaultTransaction_1 = new TransactionBuilder.TransactionBuilderClass().timeOfAccountOpening(date)
				.transactionOf("+800").build();
		defaultTransaction_2 = new TransactionBuilder.TransactionBuilderClass().timeOfAccountOpening(date)
				.transactionOf("+1230").build();

		// adding the accounts created to the map
		accountsMap.put(123456789, defaultAccount_1);
		accountsMap.put(987654321, defaultAccount_2);

		// adding the transactions to the map
		transactionsMap.put(123456789, defaultTransaction_1);
		transactionsMap.put(987654321, defaultTransaction_2);

		// similarly new accounts can be created using their details
		// but that functionality is not added as ATM's do not create accounts
	}

	public boolean checkLoginInfo(String accountNumber, int pin) {

		// if the map contains the account number which is the account number
		// then check for its pin
		// if the pin matches return true else false
		try {
			if (accountsMap.containsKey(Integer.parseInt(accountNumber))) {
				if (accountsMap.get(Integer.parseInt(accountNumber)).getPin() == pin) {
					currentUser = accountsMap.get(Integer.parseInt(accountNumber)); // setting the current user for the
																					// program it logs out
					return true;
				}
			}
		} catch (Exception e) {
			// if the integer exceeds its capacity
			// escaping number format exception
			// could be changed to Long if a length is defined for the account no.
			return false;
		}
		return false;
	}

	// this method updates user's balance after a transaction
	public void updateUserBalance(int amountWithdrawn) {

		// one way to update the users balance would be to go through the whole builder
		// class again
		// this would create a new object of the current user which is not correct but
		// it can be done
		// however if we do this we also need to update the current user variable as
		// that object is used else where in the program
		// this is shown in the code below

		/*
		 * accountsMap.put(getCurrentUser().getAccountNumber(), new
		 * AccountsBuilder.AccountBuilderClass(getCurrentUser())
		 * .amountInAccount(getCurrentUser().getAmountInAccount() -
		 * amountWithdrawn).build());
		 */
		/*
		 * currentUser =accountsMap.get(currentUser.getAccountNumber());
		 */

		// the other way is to simply create a hole in our builder class
		// it will be used to set the new balance for the current user
		// this sacrifices the builder pattern a little bit
		// but it makes the code shorter and simpler
		// it is shown below

		// subtracting amount withdrawn from the current balance to get new balance
		currentUser.updateBankBalance(currentUser.getAmountInAccount() - amountWithdrawn);

		// adding the withdrawal in the transaction history
		date = new Date(); // get current date and time
		transactionsMap.get(currentUser.getAccountNumber()).addTransactionEntry(date,
				"-".concat(String.valueOf(amountWithdrawn)));
	}

	public void amountDeposited(int depositedAmount) {

		// adding amount deposited to the current balance to get new balance
		currentUser.updateBankBalance(currentUser.getAmountInAccount() + depositedAmount);

		// adding the deposit in the transaction history
		date = new Date(); // get current date and time
		transactionsMap.get(currentUser.getAccountNumber()).addTransactionEntry(date,
				"+".concat(String.valueOf(depositedAmount)));
	}

	public LinkedHashMap<String, String> getTransactionMap() {

		// return the transaction map of the current from the transaction builder class
		return transactionsMap.get(currentUser.getAccountNumber()).getTransactionMap();
	}

	// return the current user that is logged in
	public AccountsBuilder getCurrentUser() {
		return currentUser;
	}
}