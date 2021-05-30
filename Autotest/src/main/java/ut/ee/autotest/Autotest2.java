package ut.ee.autotest;

import java.io.FileNotFoundException;

import org.sikuli.script.Env;
import org.sikuli.script.Element;

import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.reporting.ColumnDataTypesReport;

public class Autotest2 implements Runnable {

	private SikuliSteps sikuli;
	private DRDataSource reportFile;
	private int iteration;
	private int jarVersion;

	public Autotest2(int iteration, int jarVersion) {
		this.iteration = iteration;
		this.jarVersion = jarVersion;
	}


	public void run() {
		
		reportFile = new DRDataSource("result", "tab", "comment"); // Create report file
		sikuli = new SikuliSteps(); // Create Example class object
		
		// Open the software that opens the software+jarVersion.jar located in src/main/resources folder
		openSoftware();
		
		
		if (iteration == 1) {
			
			// YOUR CODE GOES HERE
			
			// Running this example now that is not related to any of the specification points
			// exampleTestCase(); // delete it
			
			// tab1();	
			// tab2();
			// ...
		}
		
		if (iteration == 2) {
			
			tab1();	
			tab2();
			tab3();
			tab4();
			
		}
		
		if (iteration == 3) {
			// ...
			// tabX();
			// ...
		}
		
		// Create the report
		ColumnDataTypesReport report = new ColumnDataTypesReport(reportFile, "Report" + iteration + ".pdf");
		try {
			report.build();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void tab1() {
		boolean succeeded = false;
		
		if (sikuli.verifyIfExists("koala.png")) {
			System.out.println("Koala exists");
			
			sikuli.click("TextEditorTab.png"); // Navigate to another tab
			sikuli.click("KoalaTab.png"); // Return to koala tab
			succeeded = sikuli.verifyIfExists("koala.png");
		} else {
			System.out.println("Koala not exists");
		}
		
		if (succeeded) {
			reportFile.add("PASSED", "Tab 1", "Koala picture is the same");
		} else {
			reportFile.add("FAILED", "Tab 1", "Koala picture is not the same");
		}
		
	}
	
	
	private void tab2() {
		// Test case that clicks on the text editor tab and finally compares the text that was entered by copying
		// it to the clipboard and then comparing it
		sikuli.click("TextEditorTab.png");
		
		boolean editorSucceeded = false;
		if (sikuli.click("editorSample.png", 0.99)) {
			System.out.println("HTML editor is same");
		} else {
			System.out.println("HTML editor is different");
		}
		
		if(sikuli.write("writeSomething.png", "I am writing here")) {
			System.out.println("Using resolution 1920 x 1080 picture");
		} else {
			// 1366x768 resolution picture
			System.out.println("Using resolution 1366 x 768 picture");
			sikuli.write("writeSomethingLower.png", "I am writing here");
		}
		
		sikuli.click("LoremIpsumTab.png"); // Navigate to another tab
		sikuli.click("TextEditorTab.png"); // Return to text editor tab
		sikuli.click("writeSomethingText.png");
		
		boolean textRemainSucceeded = sikuli.compareTextToClipboards("I am writing here");
		boolean formatSucceeded = false;
		if (sikuli.click("textFormat2.png", 0.99)) {
			formatSucceeded = true;
			System.out.println("Text formatting setting remaining");
		} else {
			System.out.println("Text formatting setting not remaining");
		}
		
		// Add report
		if (editorSucceeded) {
			reportFile.add("PASSED", "Tab 2", "HTML editor had all the elements");
		} else {
			reportFile.add("FAILED", "Tab 2", "HTML editor did not have all the elements");
		}
		
		if (textRemainSucceeded) {
			reportFile.add("PASSED", "Tab 2", "Entered text format was the same");
		} else {
			reportFile.add("FAILED", "Tab 2", "Entered text format was not the same");
		}
		
		if (formatSucceeded) {
			reportFile.add("PASSED", "Tab 2", "Text format setting was the same");
		} else {
			reportFile.add("FAILED", "Tab 2", "Text format setting was not the same");
		}
		
	}
	
	
	private void tab3() {
		sikuli.click("LoremIpsumTab.png");
		
		sikuli.click("loremIpsumText.png");
		sikuli.rightClick(); // Right click to check context menu
		if (sikuli.verifyIfExists("contextMenu.png")) {
			System.out.println("Context menu exists");
		} else {
			System.out.println("Context menu not exists");
		}
		
		sikuli.click("selectAll.png");
		sikuli.rightClick();
		boolean textSelectSucceeded = sikuli.verifyIfExists("loremIpsumSelected.png");
		
		sikuli.click("copy.png");
		String initialText = Env.getClipboard();
		sikuli.click("loremIpsumSelected.png");
		
		sikuli.click("ColorPickerTab.png"); // Navigate to another tab
		sikuli.click("LoremIpsumTab.png"); // Return to lorem ipsum tab
		sikuli.click("loremIpsumText.png");
		boolean textSucceeded = sikuli.compareTextToClipboards(initialText);
		
		// Add report
		if (textSucceeded) {
			reportFile.add("PASSED", "Tab 3", "Text length was the same");
		} else {
			reportFile.add("FAILED", "Tab 3", "Text length was not the same");
		}
		
		if (textSelectSucceeded) {
			reportFile.add("PASSED", "Tab 3", "Text is selectable by right click");
		} else {
			reportFile.add("FAILED", "Tab 3", "Text is not selectable by right click");
		}
		
	}
	
	
	private void tab4() {
		sikuli.click("ColorPickerTab.png");
		
		boolean collapseSucceeded = false;
		boolean colorChangeSucceeded = false;
		
		sikuli.click("collapsedTab.png"); // Open tab
		if (sikuli.verifyIfExists("colorDefault.png")) {
			System.out.println("Color picker default: #fcffff");
			
			sikuli.click("colorDefault.png");
			sikuli.click("black.png"); // Change color to black
			if (sikuli.verifyIfExists("colorBlack.png")) {
				System.out.println("Color picker: black");
			} else {
				System.out.println("Could not change color to black");
			}
		} else {
			System.out.println("Color picker default is not #fcffff");
		}
		
		if (sikuli.click("collapsedTab.png")) { // Collapse tab
			collapseSucceeded = true;
		}
		
		sikuli.click("ProgressTab.png"); // Navigate to another tab
		sikuli.click("ColorPickerTab.png"); // Return to color picker tab
		sikuli.click("collapsedTab.png"); // Open collapsed tab
		
		if (sikuli.verifyIfExists("colorBlack.png")) {
			colorChangeSucceeded = true;
			System.out.println("Selected color code is same");
		} else {
			System.out.println("Selected color code is not same");
		}
		
		// Add report
		if (collapseSucceeded) {
			reportFile.add("PASSED", "Tab 4", "Tab was collapsible");
		} else {
			reportFile.add("FAILED", "Tab 4", "Tab was not collapsible");
		}
		
		if (colorChangeSucceeded) {
			reportFile.add("PASSED", "Tab 4", "Selected color stays the same");
		} else {
			reportFile.add("FAILED", "Tab 4", "Selected color stays not the same");
		}
		
	}
	

	private void exampleTestCase() {
		
		/**
		 * 
		 */
		// Example test case that clicks on the text editor tab and finally compares the text that was entered by copying
		// it to the clipboard and then comparing it
		sikuli.click("TextEditorTab.png");
		if(sikuli.write("writeSomething.png", "I am writing here")) {
			System.out.println("Using resolution 1920 x 1080 picture");
			// Only needed for this example, you don't have to take this into consideration
		} else {
			// 1366x768 resolution picture
			System.out.println("Using resolution 1366 x 768 picture");
			sikuli.write("writeSomethingLower.png", "I am writing here");
		}
		
		// Navigate away and back from the tab if spec requires it
		
		boolean succeeded = sikuli.compareTextToClipboards("I am writing here");
		if (succeeded) {
			reportFile.add("PASSED", "Tab 1", "Entered text was the same");
		} else {
			reportFile.add("FAILED", "Tab 1", "Entered text was not the same");
		}
		// and 1 bullet point can be similarly covered like this.
		/**
		 * 
		 */
		// Another test case goes here
		
		// ends after reporting
		
	}
	
	private void openSoftware() {
		try {
			sikuli.openJavaJar(jarVersion);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
