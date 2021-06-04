import UI.AtmMachineGUI;
import model.AccountAndCredentialManager;
import model.MoneyManager;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// setting up the ATM
		AccountAndCredentialManager.getInstance().generatingDefaultAccounts(); // creating users
		new MoneyManager(0, 0).setUpMoneyInAtm(); // setting up the money
		new AtmMachineGUI().loginWindow(); // user interaction
	}
}
