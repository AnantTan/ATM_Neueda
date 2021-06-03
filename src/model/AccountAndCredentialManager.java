package model;
import java.util.HashMap;
import builder.AccountsBuilder;

// this class implements the singleton pattern
// in production this type of class might be the back end connecting to a database

public enum AccountAndCredentialManager {

	AccountAndCredentialManagerInstance; 	// instance of this enum class
	private HashMap<Integer,AccountsBuilder> accountsMap = new HashMap<Integer, AccountsBuilder>();
	private AccountsBuilder currentUser;

	// this method will return the instance of this class
	public static AccountAndCredentialManager getInstance()
	{
		return AccountAndCredentialManagerInstance;
	}

	public void printTheDetails() {
		System.out.println(accountsMap.get(123456789).getAmountInAccount());
	}

	public void generatingDefaultAccounts() {

		AccountsBuilder defaultAccount_1,  defaultAccount_2;
		
		// creating default users using the builder pattern
		defaultAccount_1 = new AccountsBuilder.AccountBuilderClass().accountNumber(123456789).accountHolder("Default 1")
				.amountInAccount(800).pin(1234).overdraftAmount(200).build();
		
		 defaultAccount_2 = new AccountsBuilder.AccountBuilderClass().accountNumber(987654321).accountHolder("Default 2")
				.amountInAccount(1230).pin(4321).overdraftAmount(150).build();
		 
		 // adding the accounts created to the array list
		 accountsMap.put(123456789, defaultAccount_1);
		 accountsMap.put(987654321,defaultAccount_2);
	}
	
	public boolean checkLoginInfo(String accountNumber,int pin)
	{
		// if the map contains the account number which is the account number
		// then check for its pin
		// if the pin matches return true else false
		
		if(accountsMap.containsKey(Integer.parseInt(accountNumber))) {
			if(accountsMap.get(Integer.parseInt(accountNumber)).getPin()==pin) {
				currentUser = accountsMap.get(Integer.parseInt(accountNumber)); // setting the current user that will run through until it logs out
				return true;
			}
		}
			return false;
	}
	
	// return the current user that is logged in
	public AccountsBuilder getCurrentUser()
	{
		return currentUser;
	}
}