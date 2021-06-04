package interfaces;

// this interface gives the core operations an ATM must have

public interface Manager {

	public void setUpMoneyInAtm(); // this will be initialize money in the ATM

	public void processWithdrawal(); // this will be used to process the cash withdrawal

	public void processCashDeposit(); // this will be used to process cash deposit
}
