import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestHelper {

    static WebDriver driver;
    final int waitForResponseTime = 4;
	
	// here write a link to your admin website (e.g. http://my-app.herokuapp.com/admin)
    String baseUrlAdmin = "https://mysterious-hollows-44808.herokuapp.com/admin";
	
	// here write a link to your website (e.g. http://my-app.herokuapp.com/)
    String baseUrl = "https://mysterious-hollows-44808.herokuapp.com/";

    @Before
    public void setUp() {

        // if you use Chrome:
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\juyeong\\Downloads\\chromedriver.exe");
        driver = new ChromeDriver();

        // if you use Firefox:
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\...\\geckodriver.exe");
        //driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);

    }

    void goToPage(String page) {
        WebElement elem = driver.findElement(By.linkText(page));
        elem.click();
        waitForElementById(page);
    }

    void waitForElementById(String id) {
        new WebDriverWait(driver, waitForResponseTime).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    void login(String username, String password) {

        driver.get(baseUrlAdmin);

        driver.findElement(By.linkText("Login")).click();

        driver.findElement(By.id("name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

        By loginButtonXpath = By.xpath("//input[@value='Login']");
        // click on the button
        driver.findElement(loginButtonXpath).click();
    }

    void logout() {
        WebElement logout = driver.findElement(By.linkText("Logout"));
        logout.click();

        waitForElementById("Admin");
    }

    void register(String username, String password) {

        driver.get(baseUrlAdmin);

        driver.findElement(By.linkText("Register")).click();

        driver.findElement(By.id("user_name")).sendKeys(username);
        driver.findElement(By.id("user_password")).sendKeys(password);
        driver.findElement(By.id("user_password_confirmation")).sendKeys(password);

        By loginButtonXpath = By.xpath("//input[@value='Create User']");
        driver.findElement(loginButtonXpath).click();
    }

    void deleteAccount(String username) {
        driver.get(baseUrlAdmin);

        By deleteXpath = By.xpath("//*[@id=\"" + username + "\"]/a[2]");
        driver.findElement(deleteXpath).click();
    }

    void createProduct(String title, String description, String type, String price) {
        By addProductXpath = By.xpath("//*[@id=\"new_product_div\"]/a");
        driver.findElement(addProductXpath).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        driver.findElement(By.id("product_title")).sendKeys(title);
        driver.findElement(By.id("product_description")).sendKeys(description);
        Select prodType = new Select(driver.findElement(By.id("product_prod_type")));
        prodType.selectByValue(type);
        driver.findElement(By.id("product_price")).sendKeys(price);

        By createButtonXpath = By.xpath("//input[@value='Create Product']");
        driver.findElement(createButtonXpath).click();
    }

    @After
    public void tearDown() {
        driver.close();
    }

}
