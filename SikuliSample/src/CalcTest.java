import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

/* Automating Calculator Application. */
public class CalcTest {
	Runtime rs = null;
	Screen screen = null;
	static CalcTest ct = null;
	Map<String, String> calcButtonMap = null;
	final String APPLICATION = "C:\\Windows\\System32\\calc.exe";

	/* Initialization block */
	{
		calcButtonMap = new HashMap<String, String>();

		String imagePath = "C:\\Users\\juyeong\\Desktop\\calcimages\\";

		calcButtonMap.put("0", imagePath + "0.PNG");
		calcButtonMap.put("1", imagePath + "1.PNG");
		calcButtonMap.put("2", imagePath + "2.PNG");
		calcButtonMap.put("3", imagePath + "3.PNG");
		calcButtonMap.put("4", imagePath + "4.PNG");
		calcButtonMap.put("5", imagePath + "5.PNG");
		calcButtonMap.put("6", imagePath + "6.PNG");
		calcButtonMap.put("7", imagePath + "7.PNG");
		calcButtonMap.put("8", imagePath + "8.PNG");
		calcButtonMap.put("9", imagePath + "9.PNG");

		// Operators
		calcButtonMap.put("Add", imagePath + "Add.PNG");
		calcButtonMap.put("Mul", imagePath + "Mul.PNG");
		calcButtonMap.put("Div", imagePath + "Div.PNG");
		calcButtonMap.put("Sub", imagePath + "Sub.PNG");

		calcButtonMap.put("C", imagePath + "C.PNG");
		calcButtonMap.put("Equals", imagePath + "Equals.PNG");
	}

	/* main method. */
	public static void main(String[] args)
			throws IOException, InterruptedException, FindFailed, UnsupportedFlavorException {

		ct = new CalcTest();

		Settings.ClickDelay = 20;

		// disabling the sikuli logs.
		Settings.ActionLogs = false;

		ct.screen = new Screen();
		ct.rs = Runtime.getRuntime();

		ct.launchCalculator();
		ct.calculate();
		ct.closeCalculator();
	}

	/* Perform's calculations and validates the results. */
	public void calculate()
			throws NumberFormatException, FindFailed, InterruptedException, UnsupportedFlavorException, IOException {
		int results = 0;

		// 4 + 5 =9
		List<String> calcListAdd = new ArrayList<String>();
		calcListAdd.add("4");
		calcListAdd.add("Add");
		calcListAdd.add("5");
		calcListAdd.add("Equals");
		results = Integer.parseInt(performOperation(calcListAdd));
		if (results == 9) {
			System.out.println("Results Sucess: 4 + 5 = " + results);
		} else {
			System.out.println("Results Failed: 4 + 5 = " + results);
		}

		// 8 * 2 =16
		List<String> calcListMul = new ArrayList<String>();
		calcListMul.add("8");
		calcListMul.add("Mul");
		calcListMul.add("2");
		calcListMul.add("Equals");
		results = Integer.parseInt(performOperation(calcListMul));
		if (results == 16) {
			System.out.println("Results Sucess: 8 * 2 = " + results);
		} else {
			System.out.println("Results Failed: 8 * 2 = " + results);
		}

		// 71 - 11 = 60
		List<String> calcListSub = new ArrayList<String>();
		calcListSub.add("7");
		calcListSub.add("1");
		calcListSub.add("Sub");
		calcListSub.add("1");
		calcListSub.add("1");
		calcListSub.add("Equals");
		results = Integer.parseInt(performOperation(calcListSub));
		if (results == 60) {
			System.out.println("Results Sucess: 71 - 11 = " + results);
		} else {
			System.out.println("Results Failed: 71 - 11 = " + results);
		}

		// 18 / 2 = 9
		List<String> calcListDiv = new ArrayList<String>();
		calcListDiv.add("1");
		calcListDiv.add("8");
		calcListDiv.add("Div");
		calcListDiv.add("2");
		calcListDiv.add("Equals");
		results = Integer.parseInt(performOperation(calcListDiv));
		if (results == 9) {
			System.out.println("Results Sucess: 18 / 2 = " + results);
		} else {
			System.out.println("Results Failed: 18 / 2 = " + results);
		}

		// 4 + 5 + 1 + 10 = 20
		List<String> calcListAddMoreNums = new ArrayList<String>();
		calcListAddMoreNums.add("4");
		calcListAddMoreNums.add("Add");
		calcListAddMoreNums.add("5");
		calcListAddMoreNums.add("Add");
		calcListAddMoreNums.add("1");
		calcListAddMoreNums.add("Add");
		calcListAddMoreNums.add("1");
		calcListAddMoreNums.add("0");
		calcListAddMoreNums.add("Equals");
		results = Integer.parseInt(performOperation(calcListAddMoreNums));
		if (results == 20) {
			System.out.println("Results Sucess: 4 + 5 + 1 + 10 = " + results);
		} else {
			System.out.println("Results Failed: 4 + 5 + 1 + 10 = " + results);
		}
	}

	/* Perform operations on calculator application. */
	public String performOperation(List<String> calcList)
			throws FindFailed, InterruptedException, UnsupportedFlavorException, IOException {
		screen.click(calcButtonMap.get("C"));
		Thread.sleep(100);

		for (String calElement : calcList) {
			screen.click(calcButtonMap.get(calElement));
		}

		return ct.getResults();
	}

	/* Fetch the results after the operations are done. */
	public String getResults() throws FindFailed, UnsupportedFlavorException, IOException, InterruptedException {
		Thread.sleep(100);
		screen.click("C:\\Users\\juyeong\\Desktop\\calcimages\\ResultPanel.PNG");
		screen.type("c", Key.CTRL);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(DataFlavor.stringFlavor);
		String results = (String) contents.getTransferData(DataFlavor.stringFlavor);

		System.out.println("results >> " + results);
		return results;
	}

	/* Launch the calculator application.@throws InterruptedException */
	public void launchCalculator() throws IOException, InterruptedException {
		closeCalculator();
		Thread.sleep(500);
		App.open(APPLICATION);
		Thread.sleep(500);
	}

	/* Quit the calculator application. */
	public void closeCalculator() throws IOException {
		App.close(APPLICATION);

		rs.exec("taskkill /F /IM " + "Calculator.exe");
		System.out.println("Calculator Application is closed.");
	}
}
