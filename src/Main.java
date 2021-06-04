import UI.AtmMachineGUI;
import model.AccountAndCredentialManager;
import model.MoneyManager;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccountAndCredentialManager.getInstance().generatingDefaultAccounts();
		new MoneyManager(0,0).setUpMoneyInAtm();
		new AtmMachineGUI().loginWindow();;
	}
}
