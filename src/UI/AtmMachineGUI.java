package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.ActionOfButtons;
import model.AccountAndCredentialManager;

public class AtmMachineGUI {

	private ActionOfButtons actionOfButtons = new ActionOfButtons();
	public static JFrame loginFrame = new JFrame();
	public static JFrame userAccountFrame = new JFrame();
	private JPanel panelOne, panelTwo;
	private JButton loginButton, resetButton, withdrawButton;
	private JLabel accountNumberLabel, pinLabel, withdrawalAmountLabel;
	public static JTextField accountNumberTextField, withdrawalAmountText;
	public static JPasswordField pinField;
	private JMenuBar menuBar;
	private JMenu money, myAccount, userName;
	private JMenuItem cashWithdraw, balance, deposit, details, transactions, logoutUser;

	public AtmMachineGUI() {

	}

	@SuppressWarnings("static-access")
	public void loginWindow() {
		panelOne = new JPanel(new GridLayout(2, 2));
		panelOne.setBorder(BorderFactory.createEmptyBorder(6, 8, 3, 1));
		panelTwo = new JPanel();

		accountNumberLabel = new JLabel("Account No.");
		pinLabel = new JLabel("Pin");

		accountNumberTextField = new JTextField();
		accountNumberTextField.grabFocus();
		// first field to take in input should be this

		pinField = new JPasswordField();

		loginButton = new JButton("Login");
		loginButton.addActionListener(actionOfButtons);
		loginButton.setActionCommand("login");
		loginFrame.getRootPane().setDefaultButton(loginButton); // pressing enter will trigger the login button

		resetButton = new JButton("Reset");
		resetButton.addActionListener(actionOfButtons);
		resetButton.setActionCommand("reset");

		panelOne.add(accountNumberLabel);
		panelOne.add(accountNumberTextField);
		panelOne.add(pinLabel);
		panelOne.add(pinField);

		panelTwo.setLayout(new BoxLayout(panelTwo, BoxLayout.LINE_AXIS));
		panelTwo.setBorder(BorderFactory.createEmptyBorder(6, 8, 3, 1));
		panelTwo.add(Box.createHorizontalGlue());
		panelTwo.add(resetButton);
		panelTwo.add(Box.createRigidArea(new Dimension(3, 0)));
		panelTwo.add(loginButton);

		loginFrame.getContentPane().add(panelOne, BorderLayout.CENTER);
		loginFrame.getContentPane().add(panelTwo, BorderLayout.SOUTH);

		loginFrame.setVisible(true);
		loginFrame.setTitle("Neueda");
		loginFrame.setSize(300, 150);
		loginFrame.setResizable(false);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setDefaultCloseOperation(loginFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	public void userAccountGui() {
		loginFrame.disable();; // close the login frame once the user is on its dash board
		resetDetails(); // remove the credentials

		menuBar = new JMenuBar();

		money = new JMenu("Money");
		myAccount = new JMenu("My Account");
		// getting current user name to be shown on the dash board
		userName = new JMenu(AccountAndCredentialManager.getInstance().getCurrentUser().getAccountHolderName());

		cashWithdraw = new JMenuItem("Cash Withdrwal");
		cashWithdraw.addActionListener(actionOfButtons);
		cashWithdraw.setActionCommand("cashWithdrawl");

		balance = new JMenuItem("Check Balance");
		balance.addActionListener(actionOfButtons);
		balance.setActionCommand("check balance");

		deposit = new JMenuItem("Deposit");
		details = new JMenuItem("Details");
		transactions = new JMenuItem("Transactions");

		logoutUser = new JMenuItem("Logout");
		logoutUser.addActionListener(actionOfButtons);
		logoutUser.setActionCommand("logout");

		money.add(cashWithdraw);

		money.add(balance);
		money.add(deposit);

		myAccount.add(details);
		myAccount.add(transactions);

		userName.add(logoutUser);

		menuBar.add(money);
		menuBar.add(myAccount);
		menuBar.add(Box.createGlue());
		menuBar.add(userName);

		menuBar.setBackground(Color.cyan);
		userAccountFrame.setJMenuBar(menuBar);

		userAccountFrame.setVisible(true);
		userAccountFrame.setTitle("Neueda Bank");
		userAccountFrame.setSize(400, 250);
		userAccountFrame.setResizable(false);
		userAccountFrame.setLocationRelativeTo(null);
		userAccountFrame.setDefaultCloseOperation(userAccountFrame.EXIT_ON_CLOSE);
	}

	public void cashWtihdrawlGui() {

		userAccountFrame.getContentPane().removeAll(); // remove any tiles that were here before this

		panelOne = new JPanel(new GridLayout(1, 1));
		panelOne.setBorder(BorderFactory.createTitledBorder("Please enter the below and press withdraw"));
		panelTwo = new JPanel();

		withdrawalAmountLabel = new JLabel("Enter the amount");
		withdrawalAmountText = new JTextField();

		withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(actionOfButtons);
		withdrawButton.setActionCommand("withdraw");
		userAccountFrame.getRootPane().setDefaultButton(withdrawButton); // pressing enter will trigger login button

		panelOne.add(withdrawalAmountLabel);
		panelOne.add(withdrawalAmountText);
		panelTwo.add(withdrawButton);

		userAccountFrame.getContentPane().add(panelOne);
		userAccountFrame.getContentPane().add(panelTwo, BorderLayout.SOUTH);
		userAccountFrame.revalidate(); // refresh the content pane after adding these tiles
	}

	@SuppressWarnings("deprecation")
	public void dispensedNotesGui(HashMap<Integer, Integer> dispensedNotes, int dispensingAmount) {

		panelOne = new JPanel();
		panelOne.setBorder(BorderFactory.createTitledBorder("Notes and their numbers that you should recieve"));

		JTable table = new JTable(toTableModel(dispensedNotes));
		table.disable(); // disable the columns of the table

		userAccountFrame.getContentPane().removeAll();
		userAccountFrame.getContentPane().add(panelOne, BorderLayout.NORTH);
		userAccountFrame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		userAccountFrame.getContentPane().revalidate();
		JOptionPane.showMessageDialog(null, "Please collect your cash !", "Dispensing " + dispensingAmount + "€",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void checkBalanceGui() {
		JOptionPane.showMessageDialog(null,
				"You current balance is "
						+ AccountAndCredentialManager.getInstance().getCurrentUser().getAmountInAccount() + "€",
				"Total balance ", JOptionPane.INFORMATION_MESSAGE);
	}

	private TableModel toTableModel(HashMap<Integer, Integer> dispensedNotes) {

		DefaultTableModel model = new DefaultTableModel(new Object[] { "Notes", "Number" }, 0);
		for (HashMap.Entry<?, ?> entry : dispensedNotes.entrySet()) {
			model.addRow(new Object[] { entry.getKey(), entry.getValue() });
		}
		return model;
	}

	public static void resetDetails() {

		accountNumberTextField.setText("");
		pinField.setText("");
	}

	public void notEnoughMoneyInTheMachine(int moneyInTheMachine) {
		JOptionPane.showMessageDialog(null,
				"Not enough cash in the machine ! (Max witdrawal amount = " + moneyInTheMachine + "€",
				"Cannot Dispense", JOptionPane.ERROR_MESSAGE);
	}

	public void notEnoughMoneyInTheAccount(int moneyInTheAccount) {
		JOptionPane.showMessageDialog(null, "Not enough money in your account ! (Balance = " + moneyInTheAccount + "€",
				"Cannot Dispense", JOptionPane.ERROR_MESSAGE);
	}
}