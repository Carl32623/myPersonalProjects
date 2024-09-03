
import java.util.Date;

public class Transaction {
	//amount of transaction
	private double amount;
	
	//time and date of transaction
	private Date timeStamp;
	
	//notes on transaction
	private String memo;
	
	//account for which transaction was performed
	private Account inAccount;
	
	/**
	 * Create a new transaction
	 * @param amount		the amount transacted
	 * @param inAccount		the account the transaction belongs to
	 */
	public Transaction(double amount, Account inAccount) {
		this.amount = amount;
		this.inAccount = inAccount;
		this.timeStamp = new Date();
		this.memo = "";
	}
	
	/**
	 * Create a new transaction
	 * @param amount		the amount transacted
	 * @param inAccount		the account the transaction belongs to
	 * @param memo			the memo for the transaction
	 */
	public Transaction(double amount, String memo, Account inAccount) {
		//call two argument constructor
		this(amount, inAccount);
		
		// set the memo
		this.memo = memo;
	}
	
	/**
	 * Get the amount of the transaction
	 * @return	the amount
	 */
	public double getAmount() {
		return this.amount;
	}
	
	/**
	 * Get a string summarizing the transaction
	 * @return	the summary string
	 */
	public String getSummaryLine() {
		if (this.amount >= 0) {
			return String.format("%s : $%s.02f : %s", this.timeStamp.toString(), this.amount,
					this.memo);
		}
		else {
			return String.format("%s : $(%s.02f) : %s", this.timeStamp.toString(), this.amount,
					this.memo);
		}
	}
	
	
}
