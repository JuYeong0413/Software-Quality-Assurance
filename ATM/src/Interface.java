import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.Color;
import java.text.DateFormat;
import java.lang.Runtime.*;



public class Interface extends Account {

//Default Constructor 
public Interface () {
		super(); // Calling Parent Class Constructor i.e., Account
		}

//fastCashMenu for Customer's Transaction
public void fastCashMenu(CustomerInfo cust) {
		try  
			{		
				Account ac = new Account();
				AccountTransaction aT = new AccountTransaction();
				BufferedReader br = new BufferedReader (new InputStreamReader (System.in) );
				CustomerInfo cust2 = new CustomerInfo();
				int ch , i = 0;
				Integer withdrawAmount = 0;
				Integer originalBalance = Integer.parseInt(cust.startingBalance) ;
				String str ;
				System.out.println("1) 500");
				System.out.println("2) 1000");
				System.out.println("3) 2000");
				System.out.println("4) 5000");
				System.out.println("5) 10,000");
				System.out.println("6) 15,000");
				System.out.println("7) 20,000");
				System.out.println("8) To Quit From This Menu");
				System.out.println("Select  one  of  the  denominations  of  money : ");
				str = br.readLine();
				ch = Integer.parseInt(str);
				switch(ch) {
					case 1:
						System.out.print("Are you sure you want to withdraw Rs. 500 ( Y / N ) ");
						str = br.readLine();
					if (str.equalsIgnoreCase("y")) {
						withdrawAmount = 500;
						aT.withdrawCash(cust, withdrawAmount);
					} 
						break;
					
						
					case 2:
						System.out.print("Are you sure you want to withdraw Rs. 1000 ( Y / N ) ");
						str = br.readLine();
					if (str.equalsIgnoreCase("y")) {
						withdrawAmount = 1000;
						aT.withdrawCash(cust, withdrawAmount);
						}
						
						break;
				
					
					case 3:
						System.out.print("Are you sure you want to withdraw Rs. 2000 ( Y / N ) ");
						str = br.readLine();
						if (str.equalsIgnoreCase("y")) {
						withdrawAmount = 2000;
						aT.withdrawCash(cust, withdrawAmount);
						}
						break;
				
					
					case 4:
						System.out.print("Are you sure you want to withdraw Rs. 5000 ( Y / N ) ");
						str = br.readLine();
					if (str.equalsIgnoreCase("y")) {
							withdrawAmount = 5000;
							aT.withdrawCash(cust, withdrawAmount);
						}
						
						break;
					
					case 5:
						System.out.print("Are you sure you want to withdraw Rs. 10,000 ( Y / N ) ");
						str = br.readLine();
					if (str.equalsIgnoreCase("y")) {
						withdrawAmount = 10000;
						aT.withdrawCash(cust, withdrawAmount);
						}
						break;
					
					case 6:
						System.out.print("Are you sure you want to withdraw Rs. 15,000 ( Y / N ) ");
						str = br.readLine();
					if (str.equalsIgnoreCase("y")) {
						withdrawAmount = 15000;
						aT.withdrawCash(cust, withdrawAmount);
						}
						break;
					
					case 7:
						System.out.print("Are you sure you want to withdraw Rs. 20,000 ( Y / N ) ");
						str = br.readLine();
					if (str.equalsIgnoreCase("y")) {
						withdrawAmount = 20000;
						aT.withdrawCash(cust, withdrawAmount);
						}
						break;
						
					
					case 8:
						System.exit(0);
					}
			} catch (IOException ioEx) {
				System.out.println(ioEx);
	        }
	}

//normalCash for Customer's Transaction	
public void normalCash(CustomerInfo cust) {
	try 
		{
			AccountTransaction aT = new AccountTransaction();
			Integer withdrawAmount = 0;
			Date now = new Date();
			String str , todaysDate;
			todaysDate = now.toString();
			BufferedReader br = new BufferedReader (new InputStreamReader(System.in) );
			System.out.print("Enter The Withdawal Amount : ");
			str = br.readLine();
			withdrawAmount = Integer.parseInt(str);
			
			if (withdrawAmount < 0) {
				
				System.out.println("Amount Cant Be In Negative");
			
			} else if (withdrawAmount> Integer.parseInt(cust.startingBalance)) {
				
				System.out.println("Dont Have Enough Money To Withdraw");
			
			} else {
			
				aT.withdrawCash(cust,withdrawAmount);
			
			}
		
		} catch (IOException ioEx) {
				System.out.println(ioEx);
	        }
}	

//printReceiptWithdraw
public void printReceipt(CustomerInfo c , String todaysDate , Integer Amount , String receiptType) {
	String choice = null;
	try 
		{
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in) );
		System.out.print("Do you want to print Receipt ( Y / N )");
		choice = br.readLine();
		if (choice.equalsIgnoreCase("y")) {
			System.out.println("Account #  : " + c.accountNumber);
			System.out.println("Date  : " + todaysDate);
			System.out.println(receiptType + " : " + Amount);
			System.out.println("Balance : " + c.startingBalance);
			
		}
		
		} catch (IOException ioEx) {
				System.out.println(ioEx);
	        }
			
}


//customerMenu For Customer Transaction Options
public void customerMenu( CustomerInfo cust ) {
	try 
		{	
			AccountTransaction aT = new AccountTransaction();
			CustomerInfo cust2 = new CustomerInfo();
			Account ac = new Account();
			BufferedReader br = new BufferedReader ( new InputStreamReader (System.in) );
			int choice = 0;
			Integer balance = 0;
			String str, option = "y";
		do 
			{	
			System.out.println("1 ---- Withdraw Cash");
			System.out.println("2 ---- Cash Transfer");
			System.out.println("3 ---- Deposit Cash");
			System.out.println("4 ---- Display Balance");
			System.out.println("5 ---- Exit");
			System.out.println("Select One Of The Above Options");
			str = br.readLine();
			choice = Integer.parseInt(str);
				switch (choice) {
					case 1: // Withdraw Cash
							String c = "a" ;
							System.out.println("a) Fast Cash");
							System.out.println("b) Normal Cash");
							System.out.println("Please Select A Mode Of Withdrawal");
							c = br.readLine();	
								if (c.equalsIgnoreCase("a")){
									fastCashMenu(cust);	
								} else if (c.equalsIgnoreCase("b")) {
									normalCash(cust);
								}
							break;
					
					case 2: //Cash Transfer
						String transferAmount = null ;
						String aNum = null;
						System.out.print("Enter Amount In Multiples Of 500 : ");
						transferAmount = br.readLine();
						balance = Integer.parseInt(transferAmount);
						System.out.print("Enter The Account Number To Which You Want To Transfer : ");
						aNum = br.readLine();
						cust2 = ac.checkAccountNumber(Integer.parseInt(aNum));
						if (cust2 != null) {
						aT.transferCash(cust , cust2 , balance);
						}
						break;
					
					case 3: //Deposit Cash
						String depositAmount = null;
						System.out.print("Enter Amount To Be Deposit ");
						depositAmount = br.readLine();
						Integer amount = Integer.parseInt(depositAmount);
						if ( amount < 0 ) {
							System.out.println("Amount Cant Be In Negative");
						} else {
						aT.depositCash(cust,amount);
						}
						break;
					
					case 4: //Display Balance
						balance = aT.displayBalance(cust);
						System.out.println ("Your Balance Is ::: " + balance);
						break;
					
					case 5:
							System.exit(0);
				}// end Switch
					System.out.print("Do You Want To Go To Main Menu  ( Y / N ) : ");
					option = br.readLine();
				} while (option.equalsIgnoreCase("y"));
		} catch (IOException ioEx) {
				System.out.println(ioEx);
	        }
   }

//adminMenu For creating, searching, deleting , updating CustomerRecords   
public void adminMenu() {
	try 
			{		
			Account ac = new Account();
			CustomerInfo c = new CustomerInfo();
			BufferedReader br = new BufferedReader (new InputStreamReader(System.in) );
			int choice = 0 ;
			int accountNumber = 0;
			String str,option = "y";
		do
			{
			System.out.println("1 ---- Create New Account");
			System.out.println("2 ---- Delete Existing Account");
			System.out.println("3 ---- Update Account Information");
			System.out.println("4 ---- Search For Account");
			System.out.println("5 ---- View Reports");
			System.out.println("6 ---- Exit");
			System.out.println("Select One Of The Above Options");
			str = br.readLine();
			choice = Integer.parseInt(str);
				switch (choice) {
					case 1: // For Adding New Customer
						do {
						ac.addCustomer();
						System.out.print("Do You Want To Add More Customers  ( Y / N ): ");
						option = br.readLine();
						} while (option.equalsIgnoreCase("y"));
						break;
					
					case 2: //For Deleting Customer Info
						System.out.print("Enter the Account Number , which you want to Delete : "); 
						str = br.readLine();
						accountNumber = Integer.parseInt(str);
						ac.deleteCustomer(accountNumber);
						break;
					
					case 3: //For Updating Customer Info
						System.out.print("Enter The Account Number : "); 
						str = br.readLine();
						accountNumber = Integer.parseInt(str);
						ac.updateCustomer(accountNumber);
						break;
					
					case 4: // For Searching Customer Records
						ac.searchCustomer();
						break;
					
					case 5:
						//View Reports
					break;
					
					case 6:
						System.exit(0);
				}// end of Switch
					System.out.print("Do You Want To Go To Main Menu  ( Y / N ) : ");
					option = br.readLine();
			} while(option.equalsIgnoreCase("y")); //end do while
		} catch (IOException ioEx)  {
					System.out.println(ioEx);
				}
	}

//Taking input From Customer And Match Its LoginName And PinCode In File And Returning That Whole Object If matches right
public CustomerInfo enterLoginPinCode () {
	try 
		{		
		CustomerInfo c = new CustomerInfo();
		Account ac = new Account();
		String loginName = null , pCode = null;
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in) );
		System.out.print("Enter Your LoginName ");
		loginName = br.readLine();
		System.out.println();
		System.out.print("Enter Your Pin Code ");
		pCode = br.readLine();
		System.out.println();
		c = checkLoginPinCode(loginName,pCode);
		return c;
		} catch (IOException ioEx)  {
					System.out.println(ioEx);
		}
	return null;
}

//Start Main ()
public static void main (String[] args) { 
		//public native void cls();
		//final static String ESC = "\033[";
		//Runtime.getRuntime().exec("cls");
		//System.out.flush();
		String[] input = {"osama", "maverick"};
		args = input;
		AccountTransaction aT = new AccountTransaction();
		Account ac = new Account();
		Interface in = new Interface();
		CustomerInfo c = new CustomerInfo();
		String loginName = args[0];
		String pCode = args[1];
		c = ac.checkLoginPinCode(args[0],args[1]);
		if ( loginName.equals("osama") && pCode.equals("maverick") )  {
		System.out.println("Welcome Administrator");
		in.adminMenu();
		} else  if ( c != null ) {
		c = ac.checkLoginPinCode(args[0],args[1]);
		System.out.println("Welcome , " + c.holderName.toUpperCase());
		in.customerMenu(c);
		} else {
		System.out.println ("Your UserName & PinCode Is Incorrect");
		//System.out.println("Enter Again : ");
		}
	}// End Main

}// end class Interface