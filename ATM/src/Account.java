import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.Color;

public class Account extends CustomerInfo 
	{
		ArrayList customerArrayList;

// Default Constructor
public Account () {
			customerArrayList = new ArrayList();
			loadCustomerFromFile();
		}
	
//Taking Input in object and returning
public CustomerInfo customerRecord() {  
			String login , pCode , hName , aType , startBal , status;
			BufferedReader br = new BufferedReader ( new InputStreamReader (System.in) ) ;
			String ch = null;
			CustomerInfo c = null;
			int aNum = 1;
		try  
			{
			    System.out.print("Login Name : ");
				login = br.readLine();
				System.out.println();
				System.out.print("Pin Code : ");
				pCode = br.readLine();
				System.out.println();
				System.out.print("Holder Name : ");
				hName = br.readLine();
				System.out.println();
				System.out.print("Account Type (Savings , Current) : ");
				aType = br.readLine();
				System.out.println();
				System.out.print("Starting Balance : ");
				startBal = br.readLine();
				System.out.println();
				System.out.print("Status : ");
				status = br.readLine();
				System.out.println();
				aNum = maxAccountNumber;
				c = new CustomerInfo(aNum , login , pCode , hName , aType , startBal , status); //Construct new person object
				return c; //returning Object
			} catch (IOException ioEx) {
					System.out.println(ioEx);
					return null;
		}
}
	
//Writing New CustomerRecord To ArrayList After Taking Input And Save To File
public void addCustomer () {
				CustomerInfo c = new CustomerInfo();
				c = customerRecord(); 
				customerArrayList.add(c); //adding CustomerInfo Object To ArrayList
				System.out.println("Account Successfully Created -- The Account Number Assigned Is : " + maxAccountNumber);
				saveCustomerToFile();
	}

//Search Customer's Record by Name By Iterating Over customerArrayList
public void searchCustomer () { 
		CustomerInfo c = null;
		CustomerInfo c2 = null;
		String login = null , pCode = null , hName = null , aType  = null , startBal = null , status = null;
		System.out.println("Search Menu ");
	try 
		{
		c2 = customerRecord();
		if (c2 != null) {
			System.out.println(" ========================= Search Results ============================");
			System.out.println("Account# || LoginName || HolderName || AccountType || Balance || Status");
			for (int i  = 0 ; i < customerArrayList.size() ; i ++) {
				c = (CustomerInfo)customerArrayList.get(i);
					if (c.loginName.equals((c2.loginName.equals("") ?   c.loginName : c2.loginName )) 
                       && (c.holderName.equals((c2.holderName.equals("") ?   c.holderName : c2.holderName )))
					   && (c.accountType.equals((c2.accountType.equals("") ?   c.accountType : c2.accountType )))
					   && (c.startingBalance.equals((c2.startingBalance.equals("") ?   c.startingBalance : c2.startingBalance )))
					   && (c.status.equals((c2.status.equals("") ?   c.status : c2.status )))) {
					   System.out.println( c.accountNumber + " || " + c.loginName  + " || " + c.holderName + " || " + c.accountType + " || "  + c.startingBalance +" || " +	c.status);
					   }// end Nested if
					}// end For
				}//end Upper If
			} catch (NullPointerException nPx) {
		nPx.printStackTrace();
	}
}//end searchCustomer
	
//Delete person record by name by iterating over customerArrayList And again Save it To File
public void deleteCustomer (int accountNum)	{ // reading accountNumber
		try 
			{
				int aNum = accountNum;
				String str;
				CustomerInfo c = null;
				BufferedReader br = new BufferedReader ( new InputStreamReader(System.in) ) ;
		for (int i = 0 ; i < customerArrayList.size() ; i++)  {
				c = (CustomerInfo)customerArrayList.get(i);
				if(accountNum == c.accountNumber) 
				customerArrayList.remove(i);
			}//end for
				
				System.out.print("You wish to delete the account held by " + c.holderName + " If this information is correct  please re-enter the account number : ");
				accountNum = br.read();
				System.out.println();
				saveCustomerToFile();
				System.out.println("Account Deleted Successfully");
							
		} catch (IOException ioEx) {
				System.out.println(ioEx);
			}
}//end deleteCustomer()
	
//Public CustomerInfo newCustomer
public void updateCustomer (int accountNum) { //updating Customer Information and Saving Back to file
				CustomerInfo c = null;
				CustomerInfo c2 = null;
			for ( int i = 0 ; i < customerArrayList.size() ; i++) {
							c = (CustomerInfo)customerArrayList.get(i);
							if (accountNum == c.accountNumber) {
							c.print();
							System.out.println("Please Enter In The Fields You Wish To Update (Leave Blank Otherwise)");
							c2 = customerRecord(); // this is for taking Input and Update according to your desire
				if (c2 != null ) {
							
							if ( c2.loginName.equals("") ) {
							c.loginName = c.loginName;
							} else {
							c.loginName = c2.loginName;
							}
							
							if ( c2.pinCode.equals("") ) {
							c.pinCode = c.pinCode;
							} else {
							c.pinCode = c2.pinCode;
							}
							
							if ( c2.holderName.equals("") ) {
							c.holderName = c.holderName;
							} else {
							c.holderName = c2.holderName;
							}
							
							if ( c2.accountType.equals("") ) {
							c.accountType = c.accountType;
							} else {
							c.accountType = c2.accountType;
							}
							
							if ( c2.startingBalance.equals("") ) {
							c.startingBalance = c.startingBalance;
							} else {
							c.startingBalance = c2.startingBalance;
							}
							
							if ( c2.status.equals("") ) {
							c.status = c.status;
							} else {
							c.status = c2.status;
							}
							saveCustomerToFile();
							System.out.println("Your  Account  Has  Been  Successfully  Updated");
					}
				}		 
			}//end for 
		} //end updateCustomer()

//Saving Input To A File account.txt
public void saveCustomerToFile ()  {
		try 
			{
				CustomerInfo c = null;
				String line, line2 , reverseLoginName , reversePinCode ;
				FileWriter fw = new FileWriter("account.txt");
				PrintWriter pw = new PrintWriter(fw);
				FileWriter fw2 = new FileWriter("login.txt");
				PrintWriter pw2 = new PrintWriter(fw2);
		
		for (int i = 0 ; i < customerArrayList.size() ; i++) {
				c = (CustomerInfo)customerArrayList.get(i);
				line = c.accountNumber + ";" + c.loginName + ";" + c.pinCode + ";" + c.holderName + ";" + c.accountType + ";" + c.startingBalance + ";" + c.status;
				pw.println(line); //Printing line To Stream only
				reverseLoginName = new StringBuffer (c.loginName).reverse().toString();
				reversePinCode = new StringBuffer (c.pinCode).reverse().toString();
				line2 = c.accountNumber + ";" + reverseLoginName + ";" + reversePinCode;
				pw2.println(line2);//Printing line To Stream only
			}//end For
				pw.flush(); // this will flush from stream and write all data to filename account.txt
				pw.close();
				fw.close();
				pw2.flush(); // this will flush from stream and write all data to filename login.txt
				pw2.close();
				fw2.close();
		    } catch (IOException ioEx) {
			System.out.println(ioEx);
		}
	}//end of saveCustomerToFile()
	
//Reteriving Data That Is Written On File And fill it to customerArrayList
public void loadCustomerFromFile()  {
		try 
			{
				String login , pCode , hName , aType , startBal , status;
				String dataFromFile[] = null;
				int aNum = 0;
				FileReader fr = new FileReader("account.txt");
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
		while ( line != null) {
				dataFromFile = line.split(";");
				maxAccountNumber = Integer.parseInt(dataFromFile[0]);
				login = dataFromFile[1];
				pCode = dataFromFile[2];
				hName = dataFromFile[3];
				aType = dataFromFile[4];
				startBal = dataFromFile[5];
				status = dataFromFile[6];
				CustomerInfo c = new CustomerInfo(maxAccountNumber, login , pCode , hName , aType , startBal , status);
				customerArrayList.add(c);
				line = br.readLine();
			}//end while
			br.close();
			fr.close();
		} catch(IOException ioEx) {
			System.out.println(ioEx);
		}
	}//End of loadCustomerFromFile()

//Checking LoginName And PinCode From Interfcae Class 
public CustomerInfo checkLoginPinCode (String lName , String pCode) { 
			CustomerInfo c = null;
			double balance = 0;
			for ( int i = 0 ; i < customerArrayList.size() ; i++) {
							c = (CustomerInfo)customerArrayList.get(i);
							if (c.loginName.equals(lName) && c.pinCode.equals(pCode)) {
								return c;
							}
						}
				return null;
	} // End OF checkLoginPinCode()
 
// Checking AccountNumber and return its object to further manipulation
public CustomerInfo checkAccountNumber(int accountNum) {
			CustomerInfo c = null;
			for ( int i = 0 ; i < customerArrayList.size() ; i++) {
							c = (CustomerInfo)customerArrayList.get(i);
							if (accountNum == c.accountNumber) {
								return c;
								}// end if
							} //end For
				return null;
	}// End checkAccountNumber()
	
}//End Of Class Account