package builder;

public class AccountsBuilder {

	private int accountNumber;
	private String accountHolder;
	private int amountInAccount;
	private int pin;
	private int overdraftAmount;
	
	private AccountsBuilder(AccountBuilderClass accountsBuilderClass)
	{
		this.accountHolder = accountsBuilderClass.accountHolder;
		this.accountNumber = accountsBuilderClass.accountNumber;
		this.amountInAccount = accountsBuilderClass.amountInAccount;
		this.pin  = accountsBuilderClass.pin;
		this.overdraftAmount = accountsBuilderClass.overdraftAmount;
	}
	
	public int getAccountNumber()
	{
		return accountNumber;
	}
	
	public String getAccountHolderName()
	{
		return accountHolder;
	}
	
	public int getAmountInAccount() 
	{
		return amountInAccount;
	}
	
	public int getPin()
	{
		return pin;
	}
	
	public int getOverdraftAmount()
	{
		return overdraftAmount;
	}
	
	public static class AccountBuilderClass
	{
		private int accountNumber;
		private String accountHolder;
		private int amountInAccount;
		private int pin;
		private int overdraftAmount;
		
		public AccountBuilderClass accountNumber(int accountNumber)
		{
			this.accountNumber  = accountNumber;
			return this;
		}
		
		public AccountBuilderClass accountHolder(String accountHolder)
		{
			this.accountHolder  = accountHolder;
			return this;
		}
		
		public AccountBuilderClass amountInAccount(int amountInAccount)
		{
			this.amountInAccount  = amountInAccount;
			return this;
		}
		
		public AccountBuilderClass pin(int pin)
		{
			this.pin  = pin;
			return this;
		}
		
		public AccountBuilderClass overdraftAmount(int overdraftAmount)
		{
			this.overdraftAmount  = overdraftAmount;
			return this;
		}
		
		public AccountsBuilder build()
		{
			return new AccountsBuilder(this);
		}
	}
}
