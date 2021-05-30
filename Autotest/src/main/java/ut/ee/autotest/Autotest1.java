package ut.ee.autotest;

import java.io.FileNotFoundException;

import org.sikuli.script.Env;

import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.reporting.ColumnDataTypesReport;

public class Autotest1 implements Runnable {

	private SikuliSteps sikuli;
	private DRDataSource reportFile;
	private int iteration;
	private int jarVersion;

	public Autotest1(int iteration, int jarVersion) {
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
			
			tab1();
			tab2();
			tab3();
			tab4();
		}
		
		if (iteration == 2) {
			
			// tab1();
			// tab2();
			// ...
			
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
		
		boolean editorSucceeded = sikuli.verifyIfExists("editorSample.png");
		
		if(sikuli.verifyIfExists("writeSomething.png")) {
			sikuli.write("writeSomething.png", "I am writing here");
			System.out.println("Using resolution 1920 x 1080 picture");
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// 1366x768 resolution picture
			System.out.println("Using resolution 1366 x 768 picture");
			sikuli.write("writeSomethingLower.png", "I am writing here");
		}
		
		sikuli.click("LoremIpsumTab.png"); // Navigate to another tab
		sikuli.click("TextEditorTab.png"); // Return to text editor tab
		sikuli.click("writeSomethingLowerText.png");
		
		boolean textRemainSucceeded = sikuli.compareTextToClipboards("I am writing here");
		boolean formatSucceeded = sikuli.verifyIfExists("textFormat.png");
		
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
			reportFile.add("PASSED", "Tab 2", "Text format selection was the same");
		} else {
			reportFile.add("FAILED", "Tab 2", "Text format selection was not the same");
		}
		
	}
	
	
	private void tab3() {
		sikuli.click("LoremIpsumTab.png");
		
		String initialText = "";
		boolean contextMenuSucceeded = false;
		
		// Check context menu
		sikuli.click("loremIpsumText.png");
		sikuli.rightClick(); // Right click
		if (sikuli.verifyIfExists("contextMenu.png")) {
			contextMenuSucceeded = true;
			System.out.println("Context menu exists");
		} else {
			System.out.println("Context menu not exists");
		}
		
		// Check text length
		sikuli.click("selectAll.png");
		sikuli.rightClick();
		sikuli.click("copy.png");
		initialText = Env.getClipboard();
		sikuli.click("loremIpsumSelected.png");
		
		sikuli.click("ColorPickerTab.png"); // Navigate to another tab
		sikuli.click("LoremIpsumTab.png"); // Return to lorem ipsum tab
		sikuli.click("loremIpsumTextEnd.png");
		boolean textSucceeded = sikuli.compareTextToClipboards(initialText);
		
		// Add report
		if (textSucceeded) {
			reportFile.add("PASSED", "Tab 3", "Text length was the same");
		} else {
			reportFile.add("FAILED", "Tab 3", "Text length was not the same");
		}
		
		if (contextMenuSucceeded) {
			reportFile.add("PASSED", "Tab 3", "Context menu is present");
		} else {
			reportFile.add("FAILED", "Tab 3", "Context menu is not present");
		}
		
	}
	
	
	private void tab4() {
		sikuli.click("ColorPickerTab.png");
		
		boolean collapseSucceeded = false;
		boolean defaultColorWhite = false;
		boolean colorChangeSucceeded = false;
		
		sikuli.click("collapsedTab.png"); // Open tab
		if (sikuli.verifyIfExists("colorWhite.png")) {
			defaultColorWhite = true;
			System.out.println("Color picker default: white");
			
			sikuli.click("colorWhite.png");
			sikuli.click("purple.png"); // Change color to purple
			if (sikuli.verifyIfExists("colorPurple.png")) {
				System.out.println("Color picker: purple");
			} else {
				System.out.println("Could not change color to purple");
			}
		} else {
			System.out.println("Color picker default is not white");
		}
		
		if (sikuli.click("collapsedTab.png")) { // Collapse tab
			collapseSucceeded = true;
		}
		
		sikuli.click("KoalaTab.png"); // Navigate to another tab
		sikuli.click("ColorPickerTab.png"); // Return to color picker tab
		sikuli.click("collapsedTab.png"); // Open collapsed tab
		if (sikuli.verifyIfExists("colorPurple.png")) {
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
		
		if (defaultColorWhite && colorChangeSucceeded) {
			reportFile.add("PASSED", "Tab 4", "Selected color stays the same, Default color was white");
		} else if (defaultColorWhite && !colorChangeSucceeded) {
			reportFile.add("FAILED", "Tab 4", "Selected color stays not the same");
		} else if (!defaultColorWhite && colorChangeSucceeded) {
			reportFile.add("FAILED", "Tab 4", "Default color was not white");
		} else {
			reportFile.add("FAILED", "Tab 4", "Selected color stays not the same, Default color was not white");
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
