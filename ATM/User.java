import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	// users first name, last name, and ID number
	private String firstName, lastName, uuid;
	
	//the MD5 hash of the user's pin number
	private byte pinHash[];
	
	//list of accounts for this user
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new user
	 * @param firstName  the users first name
	 * @param lastName   the users last name
	 * @param pin		 the users pin number
	 * @param theBank	 the users bank
	 */
	public User(String firstName, String lastName, String pin, Bank theBank) {
		
		//Set users name
		this.firstName = firstName;
		this.lastName = lastName;
		
		//store the pins MD5 hash, rather than the original value
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		//get new unique, universal ID for the user
		this.uuid = theBank.getNewUserUUID();
		
		//create empty list of accounts
		this.accounts = new ArrayList<Account>();
		//print log message
		System.out.printf("New user %s, %s with ID %s created.\n", lastName,
				firstName, this.uuid);
	}
	
	/**
	 * Add an account for the user
	 * @param anAcct	the account to add
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	
	/**
	 * returns the users uuid
	 * @return	the uuid
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * Check whether a given pin matches the user pin
	 * @param aPin		the pin to check
	 * @return			whether the pin is valid or not
	 */
	public boolean validatePin(String aPin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()),
					this.pinHash);
		}
		catch (NoSuchAlgorithmException e){
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	
	/**
	 * Return the users first name
	 * @return	the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Print summaries for the accounts for the user
	 */
	public void printAccountsSummary() {
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	
	/**
	 * Get the number of accounts of the user
	 * @return	the number of accounts
	 */
	public int numAccounts() {
		return this.accounts.size();
	}
	
	/**
	 * print transaction history for a particular account
	 * @param acctIdx	the index of the account to use
	 */
	public void printTransactionHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
	}
	
	/**
	 * Get the balance of a partial account
	 * @param acctIdx	the index of the account to use
	 * @return			the balance of the account
	 */
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}
	
	/**
	 * Get the UUID of a particular account
	 * @param acctIdx	the index of the account
	 * @return			the UUID of the account
	 */
	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}
	
	/**
	 * Add a transaction to an account
	 * @param acctIdx	index of the account
	 * @param amount	amount of the transaction
	 * @param memo		memo of the transaction
	 */
	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
		
	}
}
