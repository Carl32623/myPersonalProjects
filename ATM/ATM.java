
import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		// initialize scanner
		Scanner sc = new Scanner(System.in);
		
		//initialize bank
		Bank theBank = new Bank("Bank of LaLonde");
		
		//add a user, which also creates a savings account
		User aUser = theBank.addUser("John", "Doe", "1234");
		
		//add checking account for user
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User currUser;
		while (true) {
			//stay in the login prompt until successful login
			currUser = ATM.mainMenuPrompt(theBank, sc);
			
			//stay in main menu until user quits
			ATM.printUserMenu(currUser, sc);
		}

	}
	
	/**
	 * Print the ATM's login menu
	 * @param theBank	the bank object of account to use
	 * @param sc		used for input
	 * @return			authenticated user
	 */
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		//initialize
		String userID;
		String pin;
		User authUser;
		
		//prompt user for userID and pin until correct
		do {
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.println("Enter user ID: ");
			userID = sc.nextLine();
			System.out.println("Enter pin: ");
			pin = sc.nextLine();
			
			//try to get user object corresponding to ID and pin
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println("Incorrect User/pin combination.  " + 
						"Please try again.");
			}
			
		}while(authUser == null);  //continue looping
		
		return authUser;
	}
	
	public static void printUserMenu(User theUser, Scanner sc) {
		//print a summary of users accounts
		theUser.printAccountsSummary();
		
		//initialize 
		int choice;
		
		//user menu
		do {
			System.out.printf("Welcome %s, what would you like to do?\n", 
					theUser.getFirstName());
			System.out.println("  1: Show account transaction history");
			System.out.println("  2: Withdraw");
			System.out.println("  3: Deposit");
			System.out.println("  4: Transfer");
			System.out.println("  5: Exit");
			System.out.println("  Enter choice: ");
			
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose 1 - 5");
				
			}
			
		}while(choice < 1 || choice > 5);
		
		//process the choice
		switch (choice) {
		
		case 1:
			ATM.showTransactionHistory(theUser, sc);
			break;
			
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
			
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		}
		
		//re-display this menu unless user wants to quit
		if (choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
	}
	
	/**
	 * Show the transaction history for the account
	 * @param theUser	the logged in user
	 * @param sc		for input
	 */
	public static void showTransactionHistory(User theUser, Scanner sc) {
		//initialize 
		int theAcct;
		
		// get the account for which transaction to show
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"whose transactions you want to view: ", theUser.numAccounts());
			
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(theAcct < 0 || theAcct >= theUser.numAccounts());
		
		//print transaction history
		theUser.printTransactionHistory(theAcct);
	}
	
	/**
	 * Process transferring funds from one account to another
	 * @param theUser	the logged in user
	 * @param sc		input
	 */
	public static void transferFunds(User theUser, Scanner sc) {
		//initialize
		int fromAcct;
		int toAcct;
		double amount;
		double acctBalance;
		
		//get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ",
					theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBalance = theUser.getAcctBalance(fromAcct);
		
		//get the account to transfer to
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer to: ",
					theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			}
			else if(amount > acctBalance) {
				System.out.printf("Amount must not be greater than\n" +
						"balance of $%.02f.\n", acctBalance);
			}
		}while (amount < 0 || amount > acctBalance);
		
		//finally, do the transfer
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(fromAcct)));
	}
	
	/**
	 * Process a fund withdraw from an account
	 * @param theUser	the logged in user
	 * @param sc		input
	 */
	public static void withdrawFunds(User theUser, Scanner sc) {
		//initialize
		int fromAcct;
		String memo;
		double amount;
		double acctBalance;
		
		//get the account to withdraw from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to withdraw from: ",
					theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBalance = theUser.getAcctBalance(fromAcct);
		
		//get the amount to withdraw
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			}
			else if(amount > acctBalance) {
				System.out.printf("Amount must not be greater than\n" +
						"balance of $%.02f.\n", acctBalance);
			}
		}while (amount < 0 || amount > acctBalance);
		
		sc.nextLine();
		
		//get the memo
		System.out.println("Eneter a mem: ");
		memo = sc.nextLine();
		
		//do the withdraw
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
		
	}
	
	/**
	 * deposit a fund to an account
	 * @param theUser	the logged in user
	 * @param sc		input
	 */
	public static void depositFunds(User theUser, Scanner sc) {
		//initialize
		int toAcct;
		String memo;
		double amount;
		double acctBalance;
		
		//get the account to deposit to
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to deposit to: ",
					theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBalance = theUser.getAcctBalance(toAcct);
		
		//get the amount to deposit
		do {
			System.out.printf("Enter the amount to transfer (min $%.02f): $", acctBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			}
			
		}while (amount < 0);
		
		sc.nextLine();
		
		//get the memo
		System.out.println("Eneter a mem: ");
		memo = sc.nextLine();
		
		//do the deposit
		theUser.addAcctTransaction(toAcct, amount, memo);
	}
}
