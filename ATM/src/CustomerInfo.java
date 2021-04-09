public class CustomerInfo 
{
	int accountNumber = 0;
	String loginName;
	String pinCode;
	String holderName;
	String accountType;
	String startingBalance;
	String status;
	public static int maxAccountNumber = 0;
	
	public CustomerInfo() { // Default Constructor
		//accountNumber += 1;
	}
	
	public CustomerInfo(int aNum , String lN, String pC, String hN, String aT ,String sB , String st ) //Parameterized Constructor
	{
		maxAccountNumber ++ ;
		accountNumber = aNum;
		loginName = lN;
		pinCode = pC;
		holderName = hN;
		accountType = aT;
		startingBalance = sB;
		status = st;
	}
	
//method for displaying Customer Record on Console
public void print() 	{
		System.out.println( "AccountNumber : " + accountNumber + "\n LoginName : " + loginName + "\n pinCode : " + pinCode + "\n holderName : " + holderName + "\n accountType : " + accountType + "\n startingBalance : " + startingBalance + "\n Status : " + status);
	}
}//end of class