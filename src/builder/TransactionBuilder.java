package builder;

import java.util.Date;
import java.util.LinkedHashMap;

// this class is responsible for managing the transaction history of the user

public class TransactionBuilder {

	private Date dataAndTimeOfTransaction; // date and time of transaction
	private String transaction; // credit or debit amount
	// linked hashmap to maintain the order of the transactions
	private LinkedHashMap<String, String> transactionsMap = new LinkedHashMap<String, String>();

	// private constructor to create builder pattern
	private TransactionBuilder(TransactionBuilderClass builderClass) {
		this.dataAndTimeOfTransaction = builderClass.dataAndTimeOfTransaction;
		this.transaction = builderClass.transaction;
		transactionsMap.put(String.valueOf(dataAndTimeOfTransaction), transaction);
	}

	// putting in the entries of the transaction
	public void addTransactionEntry(Date dataAndTimeOfTransaction, String transaction) {

		// putting unique data and time as key
		// money as the value
		transactionsMap.put(String.valueOf(dataAndTimeOfTransaction), transaction);
	}

	// getter
	public LinkedHashMap<String, String> getTransactionMap() {
		return transactionsMap;
	}

	// static class to build the objects
	public static class TransactionBuilderClass {
		private Date dataAndTimeOfTransaction;
		private String transaction;

		// setting up the variables when creating the object
		public TransactionBuilderClass timeOfAccountOpening(Date date) {
			this.dataAndTimeOfTransaction = date;
			return this;
		}

		public TransactionBuilderClass transactionOf(String transaction) {
			this.transaction = transaction;
			return this;
		}

		// return the object of the parent class
		public TransactionBuilder build() {
			return new TransactionBuilder(this);
		}
	}
}
