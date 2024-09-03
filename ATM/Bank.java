import java.util.ArrayList;
import java.util.Random;



public class Bank {

	//
	private String name;
	
	//
	private ArrayList<User> users;
	
	//
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new bank object with empty lists of users and accounts
	 * @param name	the name of the bank
	 */
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	/**
	 * generate a new unique id for a user
	 * @return
	 */
	public String getNewUserUUID() {
		// initialize
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		//continue looping until we get a unique id
		do {
			//generate the number
			uuid = "";
			for(int c = 0; c < len; c++){
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			//check to make sure it is unique
			nonUnique = false;
			for(User u : this.users) {
				if(uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while(nonUnique);
		
		return uuid;
	}
	
	/**
	 * generate a new unique id for an account
	 * @return	the uuid
	 */
	public String getNewAccountUUID() {
		// initialize
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique;
		
		//continue looping until we get a unique id
		do {
			//generate the number
			uuid = "";
			for(int c = 0; c < len; c++){
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			//check to make sure it is unique
			nonUnique = false;
			for(Account a : this.accounts) {
				if(uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while(nonUnique);
		
		return uuid;
	}
	
	/**
	 * Add an account
	 * @param anAcct	the account to add
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	
	/**
	 * Create a new user of the bank
	 * @param firstName	the user's first name
	 * @param lastName	the user's last name
	 * @param pin		the user's pin number
	 * @return			the new user object
	 */
	public User addUser(String firstName, String lastName, String pin) {
		
		//create a new user object and add to list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		//create a savings account for the new user
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
	}
	
	/**
	 * Get the user object associated with userID and pin
	 * @param userID	the UUID of the user to login
	 * @param pin		the pin of the user
	 * @return			the user object if the login is successful, or null
	 * 					if it is not
	 */
	public User userLogin(String userID, String pin) {
		// search through a list of users
		for (User u : this.users) {
			
			// check if user ID is correct
			if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		//if we haven't found the user or the pin is invalid
		return null;
	}
	
	/**
	 * Get the name of the bank
	 * @return	then name of the bank
	 */
	public String getName() {
		return this.name;
	}
}
