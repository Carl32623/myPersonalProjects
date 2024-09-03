import java.util.ArrayList;


public class Account {
	//name of account and account ID number
	private String name, uuid;
	
	//user object that owns this account
	private User holder;
	
	//list of transactions for this account
	private ArrayList<Transaction> transactions;
	
	/**
	 * Create a new Account
	 * @param name		the name of the account
	 * @param holder	the user object that holds this account
	 * @param theBank	the bank that issues the account
	 */
	public Account(String name, User holder, Bank theBank) {
		
		//set the account name and holder
		this.name = name;
		this.holder = holder;
		
		//get new account uuid
		this.uuid = theBank.getNewAccountUUID();
		
		//initialize transactions
		this.transactions = new ArrayList<Transaction>();
		
		//add to holder and bank lists
		holder.addAccount(this);
		theBank.addAccount(this);
		
	}
	
	/**
	 * returns the account uuid
	 * @return	the uuid
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * Get summary line for account
	 * @return	the string summary
	 */
	public String getSummaryLine() {
		//get the accounts balance
		double balance = this.getBalance();
		
		//format the summary line, depending on the whether the balance is negative
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		}
		else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
	}
	
	/**
	 * Get the balance of this account by adding transactions
	 * @return	the balance value
	 */
	public double getBalance() {
		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}
	
	/**
	 * Print the transaction history of the account
	 */
	public void printTransHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size() - 1; t >= 0; t--) {
			System.out.printf(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}
	
	/**
	 * Add new transaction in this account
	 * @param amount	amount transacted
	 * @param memo		transaction memo
	 */
	public void addTransaction(double amount, String memo) {
		//create new transaction and add to list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}
}
