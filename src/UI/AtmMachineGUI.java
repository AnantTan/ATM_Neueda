package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

import builder.AccountsBuilder;
import controller.ActionOfButtons;
import model.AccountAndCredentialManager;

// this class is the UI of the ATM where the user will interact

public class AtmMachineGUI {

	private ActionOfButtons actionOfButtons = new ActionOfButtons();
	public static JFrame loginFrame = new JFrame();
	public static JFrame userAccountFrame = new JFrame();
	private JPanel panelOne, panelTwo;
	private JButton loginButton, resetButton, withdrawButton, depositButton;
	private JLabel accountNumberLabel, pinLabel, withdrawalAmountLabel, accountHolderLabel, currentBalanceLabel,
			overdraftAmountLabel, depositAmountLabel;
	public static JTextField accountNumberTextField, withdrawalAmountTextField, depositAmountTextField;
	private JTextField accountHolderField, currentBalanceField, overdraftAmountField, accountNumberDetailTextField;
	public static JPasswordField pinField;
	private JMenuBar menuBar;
	private JMenu money, myAccount, userName;
	private JMenuItem cashWithdraw, balance, deposit, details, transactions, logoutUser;

	public AtmMachineGUI() {

		// constructor
	}

	// this method is the login page
	@SuppressWarnings("static-access")
	public void loginWindow() {
		panelOne = new JPanel(new GridLayout(2, 2)); // panel with a grid of 2X2
		panelOne.setBorder(BorderFactory.createEmptyBorder(6, 8, 3, 1)); // creating a border
		panelTwo = new JPanel();

		// initializing the variables
		accountNumberLabel = new JLabel("Account No.");
		pinLabel = new JLabel("Pin");

		// initializing the variables
		accountNumberTextField = new JTextField();
		accountNumberTextField.grabFocus();
		// first field to take in input should be this

		// initializing the variables
		pinField = new JPasswordField();

		// initializing the variables
		loginButton = new JButton("Login");
		loginButton.addActionListener(actionOfButtons); // adding the action listener
		loginButton.setActionCommand("login"); // setting the action command
		loginFrame.getRootPane().setDefaultButton(loginButton); // pressing enter will trigger the login button

		// initializing the variables
		resetButton = new JButton("Reset");
		resetButton.addActionListener(actionOfButtons);
		resetButton.setActionCommand("reset");

		// adding the tiles to the panel
		panelOne.add(accountNumberLabel);
		panelOne.add(accountNumberTextField);
		panelOne.add(pinLabel);
		panelOne.add(pinField);

		// adding the tiles to the panel
		panelTwo.setLayout(new BoxLayout(panelTwo, BoxLayout.LINE_AXIS));
		panelTwo.setBorder(BorderFactory.createEmptyBorder(6, 8, 3, 1));
		panelTwo.add(Box.createHorizontalGlue());
		panelTwo.add(resetButton);
		panelTwo.add(Box.createRigidArea(new Dimension(3, 0)));
		panelTwo.add(loginButton);

		// adding the panel to the frame
		loginFrame.getContentPane().add(panelOne, BorderLayout.CENTER);
		loginFrame.getContentPane().add(panelTwo, BorderLayout.SOUTH);

		// setting up the frame
		loginFrame.setVisible(true);
		loginFrame.setTitle("Neueda"); // title of the frame
		loginFrame.setSize(300, 150); // size of the frame
		loginFrame.setResizable(false); // not resizable
		loginFrame.setLocationRelativeTo(null); // appear in the center of the screen
		loginFrame.setDefaultCloseOperation(loginFrame.EXIT_ON_CLOSE); // terminate on close
	}

	// this method is main dashboard of the user
	@SuppressWarnings({ "static-access", "deprecation" })
	public void userAccountGui() {
		loginFrame.disable();
		// close the login frame once the user is on its dash board
		resetDetails(); // remove the credentials

		// initializing the variables
		menuBar = new JMenuBar();

		// initializing the variables
		money = new JMenu("Money");
		myAccount = new JMenu("My Account");
		// getting current user name to be shown on the dash board
		userName = new JMenu(AccountAndCredentialManager.getInstance().getCurrentUser().getAccountHolderName());

		// initializing the variables
		cashWithdraw = new JMenuItem("Cash Withdrwal");
		cashWithdraw.addActionListener(actionOfButtons); // adding the action listener
		cashWithdraw.setActionCommand("cashWithdrawl"); // setting up the action command

		// initializing the variables
		balance = new JMenuItem("Check Balance");
		balance.addActionListener(actionOfButtons);
		balance.setActionCommand("check balance");

		// initializing the variables
		deposit = new JMenuItem("Cash Deposit");
		deposit.addActionListener(actionOfButtons);
		deposit.setActionCommand("depositCash");

		// initializing the variables
		details = new JMenuItem("Details");
		details.addActionListener(actionOfButtons);
		details.setActionCommand("details");

		// initializing the variables
		transactions = new JMenuItem("Transactions");
		transactions.addActionListener(actionOfButtons); // adding the action listener
		transactions.setActionCommand("transactions"); // setting the action command

		// initializing the variables
		logoutUser = new JMenuItem("Logout");
		logoutUser.addActionListener(actionOfButtons);
		logoutUser.setActionCommand("logout");

		// adding the menu items to the menu
		money.add(cashWithdraw);
		money.add(balance);
		money.add(deposit);

		// adding the menu items to the menu
		myAccount.add(details);
		myAccount.add(transactions);

		// adding the menu item to the menu
		userName.add(logoutUser);

		// adding the menu to the menu bar
		menuBar.add(money);
		menuBar.add(myAccount);
		menuBar.add(Box.createGlue()); // stick the user name to right on the menu bar
		menuBar.add(userName);

		menuBar.setBackground(Color.cyan); // color of the menu bar
		userAccountFrame.setJMenuBar(menuBar);

		userAccountFrame.setVisible(true);
		userAccountFrame.setTitle("Neueda Bank");
		userAccountFrame.setSize(400, 250); // size of the frame
		userAccountFrame.setResizable(false); // not resizable
		userAccountFrame.setLocationRelativeTo(null); // appear in the center
		userAccountFrame.setDefaultCloseOperation(userAccountFrame.EXIT_ON_CLOSE); // terminate on close
	}

	// this method is the UI for cash withdrawal
	public void cashWtihdrawlGui() {

		userAccountFrame.getContentPane().removeAll(); // remove any tiles that were here before this

		panelOne = new JPanel(new GridLayout(1, 1));
		panelOne.setBorder(BorderFactory.createTitledBorder("Please enter the amount below and press withdraw"));
		panelTwo = new JPanel();

		withdrawalAmountLabel = new JLabel("Enter the amount");
		withdrawalAmountTextField = new JTextField();

		withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(actionOfButtons);
		withdrawButton.setActionCommand("withdraw");
		userAccountFrame.getRootPane().setDefaultButton(withdrawButton); // pressing enter will trigger withdraw button

		panelOne.add(withdrawalAmountLabel);
		panelOne.add(withdrawalAmountTextField);
		panelTwo.add(withdrawButton);

		userAccountFrame.getContentPane().add(panelOne);
		userAccountFrame.getContentPane().add(panelTwo, BorderLayout.SOUTH);
		userAccountFrame.revalidate(); // refresh the content pane after adding these tiles
	}

	// this method is the UI for after the cash withdrawal is completed
	@SuppressWarnings("deprecation")
	public void dispensedNotesGui(HashMap<Integer, Integer> dispensedNotes, int dispensingAmount) {

		panelOne = new JPanel();
		panelOne.setBorder(BorderFactory.createTitledBorder("Notes and their numbers that you should recieve"));

		// creating the table to show breakdown of the cash
		JTable table = new JTable(toDispenseTableModel(dispensedNotes));
		table.disable(); // disable the columns of the table

		userAccountFrame.getContentPane().removeAll(); // remove everything from the previous frame
		userAccountFrame.getContentPane().add(panelOne, BorderLayout.NORTH); // add the panel
		userAccountFrame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER); // add the table
		userAccountFrame.getContentPane().revalidate(); // refresh the frame

		// message for the user
		JOptionPane.showMessageDialog(null, "Please collect your cash !", "Dispensing " + dispensingAmount + "€",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// this method displays the current balance in the account
	public void checkBalanceGui() {
		JOptionPane.showMessageDialog(null,
				"You current balance is "
						+ AccountAndCredentialManager.getInstance().getCurrentUser().getAmountInAccount() + "€",
				"Total balance ", JOptionPane.INFORMATION_MESSAGE);
	}

	// this method show the details of the account
	public void showAccountDetails() {
		panelOne = new JPanel(new GridLayout(4, 4));

		// initializing the variables
		accountNumberLabel = new JLabel("Account No.");
		accountNumberDetailTextField = new JTextField();
		accountNumberDetailTextField.setEditable(false); // the text box should not be editable

		// initializing the variables
		accountHolderLabel = new JLabel("Account Holder");
		accountHolderField = new JTextField();
		accountHolderField.setEditable(false);

		// initializing the variables
		currentBalanceLabel = new JLabel("Current Bal.");
		currentBalanceField = new JTextField();
		currentBalanceField.setEditable(false); // the box should not editable

		// initializing the variables
		overdraftAmountLabel = new JLabel("Overdraft Amt.");
		overdraftAmountField = new JTextField();
		overdraftAmountField.setEditable(false);

		// adding the tiles to the panel
		panelOne.add(accountNumberLabel);
		panelOne.add(accountNumberDetailTextField);

		panelOne.add(accountHolderLabel);
		panelOne.add(accountHolderField);

		panelOne.add(currentBalanceLabel);
		panelOne.add(currentBalanceField);

		panelOne.add(overdraftAmountLabel);
		panelOne.add(overdraftAmountField);

		// fill the data in the boxes of this user
		setUpTheDetails(AccountAndCredentialManager.getInstance().getCurrentUser());

		userAccountFrame.getContentPane().removeAll();
		userAccountFrame.getContentPane().add(panelOne, BorderLayout.CENTER);
		userAccountFrame.getContentPane().revalidate();
	}

	// this method is the UI for cash deposit
	public void cashDepositGui() {

		userAccountFrame.getContentPane().removeAll(); // remove any tiles that were here before this

		panelOne = new JPanel(new GridLayout(1, 1));
		panelOne.setBorder(BorderFactory.createTitledBorder("Please enter the deposit amount below"));
		panelTwo = new JPanel();

		depositAmountLabel = new JLabel("Deposit amount");
		depositAmountTextField = new JTextField();

		depositButton = new JButton("Deposit");
		depositButton.addActionListener(actionOfButtons);
		depositButton.setActionCommand("deposit");
		userAccountFrame.getRootPane().setDefaultButton(depositButton); // pressing enter will trigger deposit button

		panelOne.add(depositAmountLabel);
		panelOne.add(depositAmountTextField);
		panelTwo.add(depositButton);

		userAccountFrame.getContentPane().add(panelOne);
		userAccountFrame.getContentPane().add(panelTwo, BorderLayout.SOUTH);
		userAccountFrame.revalidate(); // refresh the content pane after adding these tiles
	}

	// this method will show a message after cash deposit
	public void depositedAmountMessage(int depositedAmount) {

		showAccountDetails(); // show the account details

		// message including the current balance and the amount deposited
		JOptionPane.showMessageDialog(null,
				"You current balance is "
						+ AccountAndCredentialManager.getInstance().getCurrentUser().getAmountInAccount() + "€",
				"Deposited " + depositedAmount + "€", JOptionPane.INFORMATION_MESSAGE);
	}

	// this method is UI for transaction history
	@SuppressWarnings("deprecation")
	public void transactionsGui(LinkedHashMap<String, String> transactionHistory) {

		panelOne = new JPanel();
		panelOne.setBorder(BorderFactory.createTitledBorder("This is your transaction history")); // titled border

		// initialzing the table for the transaction
		JTable table = new JTable(toTransactionTableModel(transactionHistory));
		table.disable(); // disable the columns of the table

		userAccountFrame.getContentPane().removeAll();
		userAccountFrame.getContentPane().add(panelOne, BorderLayout.NORTH); // adding the panel
		userAccountFrame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER); // adding the table to the
																							// frame
		userAccountFrame.getContentPane().revalidate();
	}

	// set up user details
	private void setUpTheDetails(AccountsBuilder currentUser) {

		// get details and set them in the boxes
		accountNumberDetailTextField.setText(String.valueOf(currentUser.getAccountNumber()));
		accountHolderField.setText(currentUser.getAccountHolderName());
		currentBalanceField.setText(String.valueOf(currentUser.getAmountInAccount()));
		overdraftAmountField.setText(String.valueOf(currentUser.getOverdraftAmount()));
	}

	// this method creates the data for the table
	// it takes the data from the hashmap makes an object of it
	private TableModel toDispenseTableModel(HashMap<Integer, Integer> dispensedNotes) {

		// heading in the table with row count that is 0
		DefaultTableModel model = new DefaultTableModel(new Object[] { "Notes", "Number" }, 0);
		for (HashMap.Entry<?, ?> entry : dispensedNotes.entrySet()) {
			model.addRow(new Object[] { entry.getKey(), entry.getValue() }); // getting how many notes were dispensed
		}
		return model;
	}

	// this method creates the data for the table
	// it takes the data from the hashmap makes an object of it
	private TableModel toTransactionTableModel(LinkedHashMap<String, String> transactionHistory) {

		// heading in the table with row count that is 0
		DefaultTableModel model = new DefaultTableModel(new Object[] { "Data", "Amount" }, 0);
		for (HashMap.Entry<?, ?> entry : transactionHistory.entrySet()) {
			model.addRow(new Object[] { entry.getKey(), entry.getValue() }); // getting data and time and amounts
		}
		return model;
	}

	// reset the boxes
	public static void resetDetails() {

		accountNumberTextField.setText("");
		pinField.setText("");
	}

	// show a message
	public void notEnoughMoneyInTheMachine(int moneyInTheMachine) {

		// message saying not enough money in the MACHINE
		JOptionPane.showMessageDialog(null,
				"Not enough cash in the machine ! (Max witdrawal amount = " + moneyInTheMachine + "€",
				"Cannot Dispense", JOptionPane.ERROR_MESSAGE);
	}

	// show a message
	public void notEnoughMoneyInTheAccount(int moneyInTheAccount) {

		// message saying not enough money in the ACCOUNT
		JOptionPane.showMessageDialog(null, "Not enough money in your account ! (Balance = " + moneyInTheAccount + "€",
				"Cannot Dispense", JOptionPane.ERROR_MESSAGE);
	}
}