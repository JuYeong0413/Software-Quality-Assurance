import java.util.*;
import java.io.*;
import java.awt.*;

public class AccountTransaction extends Account { 
		ArrayList transactionArrayList;
		
//Default Constructor
 public AccountTransaction () {
	super();
	transactionArrayList = new ArrayList();
}


// Saving Transactions Of Customers To file transaction.txt
 public void saveTransactionToFile (String type ,CustomerInfo c, String todaysDate, Integer Amount) { 
	try
        {	
		Date now = new Date();
		FileWriter fw = new FileWriter("transaction.txt");
		PrintWriter pw = new PrintWriter(fw);
		String line = null , date = null;
		date = now.toString();
		line = "<" + type + ">;<" + c.accountNumber + "> ; " + "<" + Amount  + "> ; " + "<" + c.startingBalance + " > ; " + todaysDate ;
		pw.println(line);
		pw.flush(); // this will flush from Stream and write all data to filename transaction.txt
		pw.close();
		fw.close();
	} catch (IOException ioEx) {
		System.out.println(ioEx);
	}
}// end saveTransactionToFile
 
 // withdrawCash	
public void withdrawCash (CustomerInfo cust, Integer withdrawAmount ) {
	//try { 
			Interface in = new Interface();
			Account ac = new Account();
			CustomerInfo cust2 = new CustomerInfo();
			Date now = new Date();
			//String todaysDate = currDate.toString() + "/" + currMonth.toString() + "/" + currYear.toString();
			//String todaysDate = DateFormat.getInstance().format(now);
			String todaysDate = null;//DateFormat.getDateTimeInstance((DateFormat.SHORT, DateFormat.SHORT).format(now)).toString();
			todaysDate = now.toString();
			Integer originalBalance = Integer.parseInt(cust.startingBalance) ;
			if (withdrawAmount > originalBalance) {
			System.out.println("You Dont Have Such Amount To Withdraw");
			} else {
			originalBalance = originalBalance - withdrawAmount;
			cust.startingBalance = originalBalance.toString();
				for ( int i = 0 ; i < ac.customerArrayList.size() ; i++)  {
					cust2 = (CustomerInfo)ac.customerArrayList.get(i);
							if (cust2.accountNumber == (cust.accountNumber) ) {
								cust2.startingBalance = originalBalance.toString();
								ac.saveCustomerToFile();
								System.out.println("Withdraw Cash Successfully");
								System.out.println("Your Balance Is " + cust2.startingBalance);
								in.printReceipt(cust2,todaysDate,withdrawAmount,"WithDraw Amount");
								saveTransactionToFile("Withdraw Cash",cust2,todaysDate,withdrawAmount);
								//transactionArrayList.add(cust2);
							} //end Nested if		
						}// end for
					} // end Upper If
				}

// DepositCash
public  void depositCash (CustomerInfo cust, Integer depositAmount) {
			Account ac = new Account();
			CustomerInfo cust2 = new CustomerInfo();
			Interface in = new Interface();
			Date now = new Date();
			String todaysDate =  now.toString();//currDate.toString() + "/" + currMonth.toString() + "/" + currYear.toString();
			Integer originalBalance = Integer.parseInt(cust.startingBalance) ;
			originalBalance = originalBalance + depositAmount;
			cust.startingBalance = originalBalance.toString();
				for ( int i = 0 ; i < ac.customerArrayList.size() ; i++)  {
					cust2 = (CustomerInfo)ac.customerArrayList.get(i);
						if (cust2.accountNumber == (cust.accountNumber) ) {
							cust2.startingBalance = originalBalance.toString();
							ac.saveCustomerToFile();
							System.out.println("Deposit Cash Successfully");
							System.out.println("Your Balance Is " + cust2.startingBalance);
							in.printReceipt(cust2,todaysDate,depositAmount,"Deposit");
							saveTransactionToFile("Deposited Cash",cust2,todaysDate,depositAmount);
						} //end if		
				}// end for
	}// End depositCash() 

//TransferCash
public void transferCash (CustomerInfo sendingCustomer, CustomerInfo receivingCustomer,  Integer transferAmount) {
				Account ac = new Account();
				Interface in = new Interface();
				String aNum = null;
				Date now = new Date();
				String todaysDate = now.toString();
				CustomerInfo sC = null;
				CustomerInfo rC = null;
				Integer senderBalance = Integer.parseInt(sendingCustomer.startingBalance) ;
				senderBalance = senderBalance - transferAmount;
				Integer receiverBalance = Integer.parseInt(receivingCustomer.startingBalance) ;
				receiverBalance = receiverBalance + transferAmount ;
				
				for ( int i = 0 ; i < ac.customerArrayList.size() ; i++)  {
					sC = (CustomerInfo)ac.customerArrayList.get(i);
					if (sC.accountNumber == (sendingCustomer.accountNumber) ) {
							sC.startingBalance = senderBalance.toString();
							ac.saveCustomerToFile();
					}  // end If
				}// end For
				
				
				for ( int i = 0 ; i < ac.customerArrayList.size() ; i++)  {
					rC = (CustomerInfo)ac.customerArrayList.get(i);
					if (rC.accountNumber == (receivingCustomer.accountNumber) ) {
							rC.startingBalance = receiverBalance.toString();
							ac.saveCustomerToFile();
				} //end if 
		  }// End For
				
				System.out.println("You wish to deposit Rs " + transferAmount + " in account held by " + receivingCustomer.holderName );
				System.out.println("Transaction Confirmed");
				in.printReceipt(sendingCustomer,todaysDate,transferAmount,"Transferred Amount");
				saveTransactionToFile("Transferred Cash", sendingCustomer , todaysDate , transferAmount);
} // End transferCash()

// Returning Balance Of Customer
public Integer displayBalance ( CustomerInfo c ) { 
						return Integer.parseInt(c.startingBalance);
	}
} // end AccountTransaction