import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class frontend {
	static int loggedIn = 0;
	static int type = 0; // if type = 1 its retail, if type=2 its agent
	static ArrayList<String> transactionFile = new ArrayList<String>();
	public static void main(String[] args) {
		login();
	}
	public static void login(){
		//Runs on start up
		
				//Prompts User for log in
				if(loggedIn == 0){
					//Not logged in
					while(loggedIn == 0){
						System.out.print("Please log in: Command>");
						String logInCom = input();
						//System.out.println(logInCom);
						/*
						int total = 0;
						for (char ch : logInCom.toCharArray()){
					        total += (int)ch;
					    }
						if(total == 537){
						*/
						if(logInCom.equals("login")){//this doesnt work. 
							loggedIn = 1;
							System.out.println("Logged In");	
						}else{
							System.out.println("Invalid log in.");
						}
					}
						
					
					/*
				    String logInCom = input();
				    System.out.println(logInCom);//Test
				    
				    if(logInCom != "login"){
				    	System.out.println("Invalid command, please log in");
				    	System.exit(1);//Shouldnt exit, simply reset the command process
				    
				    }else{
				    */
				    if(loggedIn == 1){
				    	//Looking for type of user.
				    	System.out.println("Please enter a type of user. (Retail or Agent): Command>");
				    	String logInTypeCom = input();
				    	System.out.println(logInTypeCom);
				    	
				    	if(logInTypeCom.equals("retail")){
				    		loggedIn = 1;
				    		type= 1;
				    		takeCommand();
				    	}else if(logInTypeCom.equals("agent")){
				    		loggedIn = 1;
				    		type= 2;
				    		takeCommand();
				    	}else{
				    		System.out.println("Invalid type, please log in");
					    	System.exit(1);//Shouldnt exit, simply reset the command process
				    	}
				    }
				}else{
					//Logged In
					takeCommand();
				}
	}
	public static String input(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
	    String command = null;
	 
	    //  read the username from the command-line; need to use try/catch with the
	    //  readLine() method
	    try {
	    	command = br.readLine();
	    } catch (IOException ioe) {
	    	//Set up valid error message
	    	System.out.println("IO error trying to read your name!");
	        System.exit(1);//Shouldnt exit, simply reset the command process
	    }
	    command = command.toLowerCase();
		return command;
	}
	public static void takeCommand(){
		System.out.print("Command>");
		String command = input();
		if(command.equals("login")){
			error("You are already logged in.");
		}else if(command.equals("logout")){
			front_logout();
		}else if(command.equals("create")){
			if(type == 2){
				front_create();
			}else{
				error("Not logged into the proper account.");
			}
		}else if(command.equals("delete")){
			if(type == 2){
				front_delete();
			}else{
				error("Not logged into the proper account.");
			}
		}else if(command.equals("deposit")){
			front_deposit();
		}else if(command.equals("withdraw")){
			front_withdraw();
		}else if(command.equals("transfer")){
			front_transfer();
		}else{
			error("Some error statement.");
		}
	}
	public static void error(String errorMessage){
		System.out.println(errorMessage);
		takeCommand();
	}
	public static void front_create() {
		// get the account number
		// get the account name
		// update the transaction summary file
		System.out.print("Account Number:");
		String accountNumber = frontend.input();
		if(accountNumber(accountNumber)==false){
			frontend.error("Error: Invalid account number.");
		}else{
			//validate the account number 
			System.out.print("Account Name:");
			String accountName = frontend.input();
			if(accountName(accountName)==false){
				frontend.error("Error: Invalid account name.");
			}else{
			//validate the account name 

			
				accountNumber = addZeros(accountNumber, 6);//adds zeros in front if size is less than 6

				String str = "04_" + accountNumber + "_" + accountName; 
				frontend.transactionFile.add(str);
				//write.writeToSummaryFile(str);
				System.out.println("Account created.");
			}
		}
	}
	public static void front_logout() {
		frontend.loggedIn = 0;
		//run main function on frontend.java
		System.out.println("Logged out.");
		
		for (String s : frontend.transactionFile){
			writeToSummaryFile(s);	
		}
    	frontend.login();
	}
	public static void front_delete() {
		System.out.print("Please enter an account number:");
		String accountNumber = frontend.input();
		if(accountNumber(accountNumber)==false){
			frontend.error("Error: Invalid account number.");
		}else{
			System.out.print("Please enter an acount name:");
			String accountName = frontend.input();
			if(accountName(accountName)==false){
				frontend.error("Error: Invalid account name.");
			}else{

				accountNumber = addZeros(accountNumber, 6);//adds zeros in front if size is less than 6
				
				String str = "05_" + accountNumber + "_" + accountName;
				frontend.transactionFile.add(str);
				//write.writeToSummaryFile(str);
				System.out.println("Account deleted.");
			}
		}
	}
	public static void front_deposit() {
		//get account number
		//get amount
		System.out.print("Account Number:");
		String accountNumber = frontend.input();
		if(accountNumber(accountNumber)==false){
			frontend.error("Error: Invalid account number.");
		}else{
			if(accountExists(accountNumber)==false){
				frontend.error("Error: Account doesnt exist.");
			}else{
				System.out.print("Account Amount:");
				String accountAmount = frontend.input();
				if(accountAmount(accountAmount)==false){
					frontend.error("Error: Invalid account amount.");
				}else{

					if(lowerLimit(accountAmount)==false){
						frontend.error("Error: Invalid account amount. Must be greater than 0.");
					}else{
						if(upperLimit(accountAmount)==false){
							frontend.error("Error: Invalid account amount. The amount is too high for the type of login.");
						}else{
							//convert amountStr to int well more validate
							//make sure the account number is long enough
							//Does there have to be an account name?
							//make sure its a valid account
							
							accountAmount = addZeros(accountAmount, 8);//adds zeros in front if size is less than 8
							accountNumber = addZeros(accountNumber, 6);//adds zeros in front if size is less than 6
							
							String str = "01_" + accountNumber + "_" + accountAmount; 
							frontend.transactionFile.add(str);
							//write.writeToSummaryFile(str);
							System.out.println("Successful Deposit.");
						}
					}
				}
			}
		}
	}
	public static void front_transfer() {
		//get account number
		//get amount
		System.out.print("To Account Number:");
		String toAccountNumber = frontend.input();
		if(accountNumber(toAccountNumber)==false){
			frontend.error("Error: Invalid 'to' account.");
		}else{
			System.out.print("From Account Number:");
			String fromAccountNumber = frontend.input();
			if(accountNumber(fromAccountNumber)==false){
				frontend.error("Error: Invalid 'From' account.");
			}else{
				System.out.print("Account Amount:");
				String accountAmount = frontend.input();
				if(accountAmount(accountAmount)==false){
					frontend.error("Error: Invalid amount.");
				}else{

					if(lowerLimit(accountAmount)==false){
						frontend.error("Error: Invalid account amount. Must be greater than 0.");
					}else{
						if(upperLimit(accountAmount)==false){
							frontend.error("Error: Invalid account amount. The amount is too high for the type of login.");
						}else{
							//convert amountStr to int well more validate
							//make sure the account number is long enough
							//Does there have to be an account name?
							//make sure they are both valid accounts
								
							accountAmount = addZeros(accountAmount, 8);//adds zeros in front if size is less than 8
							fromAccountNumber = addZeros(fromAccountNumber, 6);//adds zeros in front if size is less than 6
							toAccountNumber = addZeros(toAccountNumber, 6);//adds zeros in front if size is less than 6
							
							
							String str = "03_" + toAccountNumber + "_" + fromAccountNumber + "_" + accountAmount; 
							frontend.transactionFile.add(str);
							//write.writeToSummaryFile(str);
							System.out.println("Successful Transfer.");
						}
					}
				}
			}
		}
	}
	public static boolean accountName(String name){
		//name
		//Make sure the whole name is letters.
		boolean valid = true;
		for (char ch : name.toCharArray()){
	        int asciiVal = (int)ch;	
			//if((65 <= asciiVal <= 90) || (97 <= asciiVal <= 122) || (asciiVal == 32)){
			if((asciiVal >= 65) || (asciiVal <= 90) || (asciiVal >= 97) || (asciiVal <=122)){
				// a - z
				// space
				// A - Z
				valid=true;
			}else{
				valid=false;
			}
	    }
		return valid;
	}
	public static boolean accountNumber(String number){
		//covert the string to integers
		boolean valid = true;
		try{
			int x =Integer.parseInt(number);
		}catch(NumberFormatException e){
			valid = false;
		}
		//check length make sure the account is the proper length
		int lengthOfAccount = number.length();
		if(lengthOfAccount != 8){
			if(lengthOfAccount > 8){
				valid = false;
			}
		}
		return valid;
	}
	public static boolean accountExists(String number){
		//Check Account File for the account.
		
		//Check length, if its less or equal to 8. 
		//if less add zeros.
		if(number.length() == 8){
			BufferedReader br = null;
			try {
	 
				String sCurrentLine;
	 
				br = new BufferedReader(new FileReader("./src/valid_accounts.txt"));
	 
				while ((sCurrentLine = br.readLine()) != null) {
					if(sCurrentLine == number){
						return true;
					}
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(number.length() > 8){
			return false;
		}else{
			String newAccountNumber = addZeros(number, 8);
			//check the newAccountNumber
			BufferedReader br = null;
			try {
	 
				String sCurrentLine;
	 
				br = new BufferedReader(new FileReader("./src/valid_accounts.txt"));
	 
				while ((sCurrentLine = br.readLine()) != null) {
					if(sCurrentLine == newAccountNumber){
						return true;
					}
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public static boolean accountAmount(String number){
		try{
			int x =Integer.parseInt(number);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	public static String addZeros(String number, int size){
		//get the number and subtrack it by 8 and add that many zeros
		int length = number.length();
		int additionalZeros = size - length;
		for( int i = 0 ; i < additionalZeros ; i++ ){
			number = "0" + number;
		}
		return number;
	}
	public static boolean lowerLimit(String amount){
		boolean valid = false;
		try{
			int amountInt = Integer.parseInt(amount);
			if(amountInt > 0){
				valid = true;
			}
		}catch(NumberFormatException e){
			//should not reach here because we validate before.
			System.out.println("Error 0001");
			System.exit(1);
		}
		return valid;
	}
	public static boolean upperLimit(String amount){
		boolean valid = false;
		try{
			int amountInt = Integer.parseInt(amount);
			if(frontend.type == 1){
				//retail mode
				if(amountInt > 100000){//above 1,000.00
					valid = false;
				}else{
					valid = true;
				}
			}else if(frontend.type == 2){
				//agent
				if(amountInt > 99999999){//above 999,999.99
					valid = false;
				}else{
					valid = true;
				}
			}else{
				//error
				//Should never get here b/c in order to perform an action they must be logged in. 
				System.out.println("Error 0003");
				System.exit(1);
			}
		}catch(NumberFormatException e){
			//should not reach here because we validate before.
			System.out.println("Error 0002");
			System.exit(1);
		}
		return valid;
	}
	
	public static boolean withdrawLimit(String accountNumber,
			String accountAmount) {
		
		int totalAmount = Integer.parseInt(accountAmount);
		for (String transactionStr : frontend.transactionFile){
			int itemNum = 0;
			boolean userAccount = false;
		    for (String str: transactionStr.split("_", 3)){
		        //loops 3 items
		    	if(itemNum == 0 ){
		    		itemNum++;
		    		userAccount = false;
		    	}else if(itemNum == 1){
		    		//account number
		    		if( str == accountNumber ){
		    			userAccount = true;
		    		}
		    		itemNum++;
		    	}else{
		    		//account amount
		    		if(userAccount == true ){
		    			int amount = Integer.parseInt(str);
		    			totalAmount += amount;
		    			if((totalAmount) > 1000){
		    				return false;
		    			}
		    			userAccount = false;
		    		}
		    		itemNum++;
		    	}
		    }
		}
		return true;
	}
	public static void front_withdraw() {
		//get account number
		//get amount
		System.out.print("Account Number:");
		String accountNumber = frontend.input();
		if(accountNumber(accountNumber)==false){
			frontend.error("Error: Invalid account number.");
		}else{
			if(accountExists(accountNumber)==false){
				frontend.error("Error: Invalid account number.");
			}else{
				System.out.print("Account Amount:");
				String accountAmount = frontend.input();
				if(accountAmount(accountAmount)==false){
					frontend.error("Error: Invalid account amount.");
				}else{

					if(lowerLimit(accountAmount)==false){
						frontend.error("Error: Invalid account amount. Must be greater than 0.");
					}else{
						if(upperLimit(accountAmount)==false){
							frontend.error("Error: Invalid account amount. The amount is too high for the type of login.");
						}else{
							if(withdrawLimit(accountNumber, accountAmount)==false){
								frontend.error("Error: Invalid account amount. The amount is too high for the type of login.");
							}else{
								//convert amountStr to int well more validate
								//make sure the account number is long enough
								//Does there have to be an account name?
								//make sure they are accounts
								
								//Pad Zeros on account number, account amount
								accountAmount = addZeros(accountAmount, 8);//adds zeros in front if size is less than 8
								accountNumber = addZeros(accountNumber, 6);//adds zeros in front if size is less than 6

										
								String str = "02_" + accountNumber + "_" + accountAmount; 
								frontend.transactionFile.add(str);
								//write.writeToSummaryFile(str);
								System.out.println("Successful Withdraw.");
							}
						}
					}
				}
			}
		}
	}
	public static void writeToAccountsFile(String content){
		try {
			 
			File file = new File("./src/valid_accounts.txt");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			System.out.println("Written to the accounts file.");
 
		} catch (IOException e) {
			//e.printStackTrace();
			frontend.error("Error: Unable to write to accounts file.");
		}
	}
	public static void writeToSummaryFile(String content){
		try {
			 
			File file = new File("./src/transaction_summary.txt");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			System.out.println("Written to the accounts file.");
 
		} catch (IOException e) {
			//e.printStackTrace();
			frontend.error("Error: Unable to write to accounts file.");
		}
	}
}
