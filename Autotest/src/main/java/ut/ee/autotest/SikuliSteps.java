package ut.ee.autotest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.sikuli.basics.Settings;
import org.sikuli.script.Env;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

/**
 * Created pre-defined methods based on Sikuli API
 */

@SuppressWarnings("deprecation")
public class SikuliSteps {

	public Process p;
	public Screen screen = new Screen();

	/**
	 * Clicks on a image if you provide the picture location, similarity and how
	 * many times you want to look for it. Useful when the element on the screen
	 * is moving
	 * 
	 * @param pictureUrl
	 * @param similarity
	 * @param retires
	 * @return
	 */
	public boolean click(String pictureUrl, Double similarity, int retires) {
		boolean found = false;
		int i = 0;
		while (!click(pictureUrl, similarity) && i < retires) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("Could not find " + pictureUrl);
				found = false;
			}
			System.out.println("Could not find the image. Checking for " + i
					+ " time");
			found = true;
		}
		return found;
	}

	/**
	 * Clicks on a image if you provide the picture location and similarity of
	 * the picture
	 * 
	 * @param pictureUrl
	 * @param similarity
	 * @return
	 */
	public boolean click(String pictureUrl, Double similarity) {
		Settings.MinSimilarity = similarity;

		try {
			screen.exists(("C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\Autotest\\src\\main\\resources\\" + pictureUrl)).click();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Could not find " + pictureUrl);
			return false;
		}
		System.out.println("Clicked on " + pictureUrl);
		return true;
	}

	/**
	 * Calls out the click(String pictureUrl, Double similarity) with default
	 * similarity 0.85
	 * 
	 * @param pictureUrl
	 * @return
	 */
	public boolean click(String pictureUrl) {
		return click(pictureUrl, 0.85);
	}

	/**
	 * Kills the java process
	 */

	public void exitJava() {
		p.destroyForcibly();
	}

	/**
	 * Right clicks where the mouse is at and moves the mouse out of the way
	 */
	public void rightClick() {
		screen.rightClick();
		screen.keyUp();
	}

	/**
	 * Opens java file. Provide version to launch software1 or software2
	 * 
	 * @param version
	 *            - 1 or 2, depending on which software you want to open
	 * @throws InterruptedException
	 */
	public void openJavaJar(int version) throws InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("java", "-jar",
				"src/main/resources/software" + version + ".jar");
		System.out.println("Trying to open the jar...");
		try {
			p = pb.start();
		} catch (IOException e) {
			System.err.println("Could not open the process");
			e.printStackTrace();
		}
		Thread.sleep(TimeUnit.SECONDS.toMillis(5));
		if (p.isAlive()) {
			System.out.println("Process has been launched");
		} else {
			System.err.println("Could not open the process! Check the path");
		}

	}

	/**
	 * Selects all (if possible) and then copies it to textFromSikuli variable
	 * 
	 * @param textToCompare
	 *            - String against which the text is compared to
	 * @return
	 */
	public boolean compareTextToClipboards(String textToCompare) {
		screen.type("a", Key.CTRL);
		screen.type("c", Key.CTRL);
		String textFromSikuli = Env.getClipboard();
		if (textFromSikuli.equals(textToCompare)) {
			System.out.println("Text is the same");
			return true;
		} else {
			System.out.println("Text is not the same :\n'" + textToCompare
					+ "' " + "\nvs");
			System.out.println("'" + textFromSikuli + "'");
			return false;
		}
	}

	/**
	 * Clicks on the provided image and tries to write text into the location
	 * (into a text area for example)
	 * 
	 * @param pictureUrl
	 * @param text
	 */
	public boolean write(String pictureUrl, String text) {
		if (click(pictureUrl)) {
			screen.doubleClick();
			screen.type(text);
			return true;
		} else {
			System.out.println("Could not type text");
			return false;
		}

	}

	/**
	 * Verify if the picture exists. Can be used in a loop until true (or fail
	 * test after 10 tries)
	 * 
	 * @param pictureUrl
	 * @return
	 * @throws FindFailed
	 */
	public boolean verifyIfExists(String pictureUrl){
		screen.mouseUp();
		if (screen.exists("C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\Autotest\\src\\main\\resources\\" + pictureUrl) != null) {
			// click(pictureUrl);
			System.out.println(pictureUrl + " exists");
			return true;
		} else {
			System.out.println("Could not find : " + pictureUrl);
			return false;
		}

	}

	public boolean verifyIfExistsReTried(String pictureUrl, int retries) {
		screen.mouseUp();
		boolean found = false;
		int i = 0;
		while (i <= retries && found == false) {
			if (screen.exists("C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\Autotest\\src\\main\\resources\\" + pictureUrl) != null) {
				System.out.println(pictureUrl + " exists");
				found = true;
			} else {
				System.out.println("Could not find : " + pictureUrl);
				found = false;
			}
			i++;

		}
		return found;
	}

}
