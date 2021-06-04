package builder;

// this class is responsible for creating and updating the user objects

public class AccountsBuilder {

	// user details
	private int accountNumber;
	private String accountHolder;
	private int amountInAccount;
	private int pin;
	private int overdraftAmount;

	// private constructor to create builder pattern
	private AccountsBuilder(AccountBuilderClass accountsBuilderClass) {
		this.accountHolder = accountsBuilderClass.accountHolder;
		this.accountNumber = accountsBuilderClass.accountNumber;
		this.amountInAccount = accountsBuilderClass.amountInAccount;
		this.pin = accountsBuilderClass.pin;
		this.overdraftAmount = accountsBuilderClass.overdraftAmount;
	}

	// getters

	public int getAccountNumber() {
		return accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolder;
	}

	public int getAmountInAccount() {
		return amountInAccount;
	}

	public int getPin() {
		return pin;
	}

	public int getOverdraftAmount() {
		return overdraftAmount;
	}

	// a little hole to update the user's balance easily
	// (please read AccountAndCredentialManager's method updateUserBalance for
	// related info)

	public void updateBankBalance(int remainingBalance) {
		this.amountInAccount = remainingBalance;
	}

	// static class for creating the objects
	public static class AccountBuilderClass {

		// user details
		private int accountNumber;
		private String accountHolder;
		private int amountInAccount;
		private int pin;
		private int overdraftAmount;

		// if we take the long way to update balance
		// we need initialize these 2 constructors
		// this one for creating new users
		// the other one for updating it
		// (please read AccountAndCredentialManager's updateUserBalance method for
		// related info)

		/*
		 * public AccountBuilderClass() { }
		 */

		// this also needs to be initialized if the long way is taken to update
		// this allows us to update the user's balance keeping builder pattern intact
		// but forces us to create a new object

		/*
		 * public AccountBuilderClass(AccountsBuilder accountsBuilder) {
		 * 
		 * this.accountHolder = accountsBuilder.getAccountHolderName();
		 * this.accountNumber = accountsBuilder.getAccountNumber(); this.amountInAccount
		 * = accountsBuilder.getAmountInAccount(); this.pin = accountsBuilder.getPin();
		 * this.overdraftAmount = accountsBuilder.getOverdraftAmount(); }
		 */

		// setting up the variables when the creating the object
		public AccountBuilderClass accountNumber(int accountNumber) {
			this.accountNumber = accountNumber;
			return this;
		}

		public AccountBuilderClass accountHolder(String accountHolder) {
			this.accountHolder = accountHolder;
			return this;
		}

		public AccountBuilderClass amountInAccount(int amountInAccount) {
			this.amountInAccount = amountInAccount;
			return this;
		}

		public AccountBuilderClass pin(int pin) {
			this.pin = pin;
			return this;
		}

		public AccountBuilderClass overdraftAmount(int overdraftAmount) {
			this.overdraftAmount = overdraftAmount;
			return this;
		}

		// return the object of parent class
		public AccountsBuilder build() {
			return new AccountsBuilder(this);
		}
	}
}
