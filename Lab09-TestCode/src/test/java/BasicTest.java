import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class BasicTest extends TestHelper {


    private String username = "";
    private String password = "";

    @Test
    public void titleExistsTest() {
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }

    /**
     * 관리자 테스트 1. 계정등록
     * 관리자 테스트 4. 계정삭제
     */
    @Test
    public void registerDeleteTest() {
        username = "juyeong";
        password = "aaabbb";

        // Register
        register(username, password);

        WebElement registerNotice = driver.findElement(By.id("notice"));
        assertEquals("User " + username + " was successfully created.", registerNotice.getText());

        // Delete
        deleteAccount(username);

        WebElement deleteNotice = driver.findElement(By.id("notice"));
        assertEquals("User was successfully deleted.", deleteNotice.getText());
    }

    /**
     * Negative test
     * 관리자 테스트 1. 계정등록
     * 입력 칸을 모두 공백으로 둔 후 계정 생성 버튼을 누른 경우
     */
    @Test
    public void registerNegativeTest() {
        register(username, password);

        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"error_explanation\"]/h2"));
        assertEquals("2 errors prohibited this user from being saved:", errorMessage.getText());
    }

    /**
     * Negative test
     * 관리자 테스트 1. 계정등록
     * 비밀번호와 비밀번호 확인이 일치하지 않는 경우
     */
    @Test
    public void registerPasswordConfirm() {
        username = "juyeong";
        password = "aaabbb";
        String falsePassword = "cccddd";

        driver.get(baseUrlAdmin);

        driver.findElement(By.linkText("Register")).click();

        driver.findElement(By.id("user_name")).sendKeys(username);
        driver.findElement(By.id("user_password")).sendKeys(password);
        driver.findElement(By.id("user_password_confirmation")).sendKeys(falsePassword);

        By loginButtonXpath = By.xpath("//input[@value='Create User']");
        driver.findElement(loginButtonXpath).click();

        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"error_explanation\"]/ul/li"));
        assertEquals("Password confirmation doesn't match Password", errorMessage.getText());
    }

    /**
     * Bug
     * 관리자 테스트 4. 계정삭제
     */
    @Test
    public void deleteTest() {
        username = "admin";
        password = "password";

        // Login
        login(username, password);

        // Delete
        deleteAccount(username);

        WebElement deleteNotice = driver.findElement(By.id("notice"));
        assertEquals("User was successfully deleted.", deleteNotice.getText());
    }

    /**
     * 관리자 테스트 2. 시스템에 로그인
     * 관리자 테스트 3. 시스템에서 로그아웃
     */
    @Test
    public void loginLogoutTest() {
        username = "admin";
        password = "password";

        login(username, password);

        // assert that correct page appeared
        assertEquals("https://mysterious-hollows-44808.herokuapp.com/products", driver.getCurrentUrl());

        driver.get(baseUrlAdmin);
        WebElement loginInfo = driver.findElement(By.xpath("//*[@id=\"" + username + "\"]"));
        assertEquals("Logged in user: " + username + " Edit Delete", loginInfo.getText());

        logout();
        WebElement adminHeader = driver.findElement(By.id("Admin"));
        assertEquals("Welcome to Admin Site", adminHeader.getText());
    }

    /**
     * Negative test
     * 관리자 테스트 2. 시스템에 로그인
     * 비밀번호가 올바르지 않은 경우
     */
    @Test
    public void loginFalsePassword() {
        username = "admin";
        password = "aaabbb";

        login(username, password);

        WebElement notice = driver.findElement(By.id("notice"));

        assertEquals("Invalid user/password combination", notice.getText());
    }

    /**
     * 관리자 테스트 5. 제품추가
     * 관리자 테스트 7. 제품 삭제
     */
    @Test
    public void addRemoveProduct() {
        username = "admin";
        password = "password";

        login(username, password);

        // Create
        String title = "Apple Watch Series 6";
        String description = "Apple Watch Series 6 keeps the people and things you care about right there with you, no matter where life takes you.";
        String type = "Other";
        String price = "429";

        createProduct(title, description, type, price);

        WebElement titleElement = driver.findElement(By.xpath("//*[@id=\"" + title + "\"]/td[2]/a"));
        assertEquals(title, titleElement.getText());
        WebElement typeElement = driver.findElement(By.xpath("//*[@id=\"" + title + "\"]/td[2]/div/span"));
        assertEquals(type, typeElement.getText());

        // Delete
        By deleteButtonXpath = By.xpath("//*[@id=\"" + title + "\"]/td[4]/a");
        driver.findElement(deleteButtonXpath).click();

        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("Product was successfully destroyed.", notice.getText());

        logout();
    }

    /**
     * 관리자 테스트 6. 제품 편집
     */
    @Test
    public void editProduct() {
        username = "admin";
        password = "password";

        login(username, password);

        // Create
        String title = "Apple Watch Series 6";
        String description = "Apple Watch Series 6 keeps the people and things you care about right there with you, no matter where life takes you.";
        String type = "Other";
        String price = "429";

        createProduct(title, description, type, price);

        // Edit
        By editButtonXpath = By.xpath("//*[@id=\"" + title + "\"]/td[3]/a");
        driver.findElement(editButtonXpath).click();

        String newDescription = "The future of health is on your wrist.";
        driver.findElement(By.id("product_description")).clear();
        driver.findElement(By.id("product_description")).sendKeys(newDescription);
        By updateButtonXpath = By.xpath("//input[@value='Update Product']");
        driver.findElement(updateButtonXpath).click();

        WebElement updateNotice = driver.findElement(By.id("notice"));
        assertEquals("Product was successfully updated.", updateNotice.getText());

        WebElement updatedDescription = driver.findElement(By.xpath("//*[@id=\"main\"]/div/p[3]"));
        assertEquals("Description: " + newDescription, updatedDescription.getText());

        // Go back to products page
        By backButtonXpath = By.xpath("//*[@id=\"main\"]/div/div/a[2]");
        driver.findElement(backButtonXpath).click();

        // Delete
        By deleteButtonXpath = By.xpath("//*[@id=\"" + title + "\"]/td[4]/a");
        driver.findElement(deleteButtonXpath).click();

        WebElement deleteNotice = driver.findElement(By.id("notice"));
        assertEquals("Product was successfully destroyed.", deleteNotice.getText());

        logout();
    }

    /**
     * Negative test
     * 관리자 테스트 6. 제품 편집
     * 가격 칸에 문자를 넣은 경우
     */
    @Test
    public void editProductNegative() {
        username = "admin";
        password = "password";

        login(username, password);

        By editButtonXpath = By.xpath("//*[@id=\"Web Application Testing Book\"]/td[3]/a");
        driver.findElement(editButtonXpath).click();

        driver.findElement(By.id("product_price")).sendKeys("abcd");

        By createButtonXpath = By.xpath("//input[@value='Update Product']");
        driver.findElement(createButtonXpath).click();

        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"error_explanation\"]/ul/li"));
        assertEquals("Price is not a number", errorMessage.getText());

        logout();
    }

    /**
     * 사용자 테스트 1. 카트에 제품 추가
     * 사용자 테스트 3. 카트에서 항목 하나씩 삭제
     */
    @Test
    public void addRemoveProductToCart() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        By sunglassXpath = By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]");
        By bookXpath = By.xpath("//*[@id=\"Web Application Testing Book_entry\"]/div[2]/form/input[1]");
        driver.findElement(sunglassXpath).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement sunglassQuantity = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[1]"));
        WebElement sunglassName = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[2]"));
        WebElement sunglassPrice = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[3]"));

        assertEquals("1×", sunglassQuantity.getText());
        assertEquals("B45593 Sunglasses", sunglassName.getText());
        assertEquals("€26.00", sunglassPrice.getText());

        driver.findElement(bookXpath).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement bookQuantity = driver.findElement(By.xpath("//*[@id=\"current_item\"]/td[1]"));
        WebElement bookName = driver.findElement(By.xpath("//*[@id=\"current_item\"]/td[2]"));
        WebElement bookPrice = driver.findElement(By.xpath("//*[@id=\"current_item\"]/td[3]"));

        assertEquals("1×", bookQuantity.getText());
        assertEquals("Web Application Testing Book", bookName.getText());
        assertEquals("€29.99", bookPrice.getText());

        By deleteButtonXpath = By.xpath("//*[@id=\"delete_button\"]/a");

        driver.findElement(deleteButtonXpath).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement deleteNotice = driver.findElement(By.id("notice"));
        assertEquals("Item successfully deleted from cart.", deleteNotice.getText());

        driver.findElement(deleteButtonXpath).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        deleteNotice = driver.findElement(By.id("notice"));
        assertEquals("Item successfully deleted from cart.", deleteNotice.getText());
    }

    /**
     * 사용자 테스트 2. 카트의 제품 수량 증가 / 감소
     */
    @Test
    public void cartProductQuantity() {
        By sunglassXpath = By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]");
        driver.findElement(sunglassXpath).click();

        WebElement sunglassQuantity = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[1]"));
        WebElement sunglassName = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[2]"));
        WebElement sunglassPrice = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[3]"));

        assertEquals("1×", sunglassQuantity.getText());
        assertEquals("B45593 Sunglasses", sunglassName.getText());
        assertEquals("€26.00", sunglassPrice.getText());

        driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[5]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[5]/a")).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sunglassQuantity = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[1]"));
        sunglassPrice = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[3]"));

        assertEquals("3×", sunglassQuantity.getText());
        assertEquals("€78.00", sunglassPrice.getText());

        driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[4]/a")).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sunglassQuantity = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[1]"));
        sunglassPrice = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[3]"));

        assertEquals("2×", sunglassQuantity.getText());
        assertEquals("€52.00", sunglassPrice.getText());

        By emptyButtonXpath = By.xpath("//input[@value='Empty cart']");
        driver.findElement(emptyButtonXpath).click();
    }

    /**
     * 사용자 테스트 2. 카트의 제품 수량 증가 / 감소 - 감소해서 삭제
     */
    @Test
    public void removeCartByDecrease() {
        By sunglassXpath = By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]");
        driver.findElement(sunglassXpath).click();

        WebElement sunglassQuantity = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[1]"));
        WebElement sunglassName = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[2]"));
        WebElement sunglassPrice = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[3]"));

        assertEquals("1×", sunglassQuantity.getText());
        assertEquals("B45593 Sunglasses", sunglassName.getText());
        assertEquals("€26.00", sunglassPrice.getText());

        driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[4]/a")).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean cartExists = isElementPresent(By.id("cart_title"));
        assertEquals(false, cartExists);
    }

    /**
     * 사용자 테스트 4. 카트 물품 전체 삭제
     */
    @Test
    public void removeAllProductInCart() {
        By sunglassXpath = By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]");
        driver.findElement(sunglassXpath).click();

        WebElement sunglassQuantity = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[1]"));
        WebElement sunglassName = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[2]"));
        WebElement sunglassPrice = driver.findElement(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[3]"));

        assertEquals("1×", sunglassQuantity.getText());
        assertEquals("B45593 Sunglasses", sunglassName.getText());
        assertEquals("€26.00", sunglassPrice.getText());

        By emptyButtonXpath = By.xpath("//input[@value='Empty cart']");
        driver.findElement(emptyButtonXpath).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement deleteNotice = driver.findElement(By.id("notice"));
        assertEquals("Cart successfully deleted.", deleteNotice.getText());
    }

    /**
     * 사용자 테스트 5. 홈페이지에서 이름별로 제품 검색
     */
    @Test
    public void searchProduct() {
        driver.findElement(By.id("search_input")).sendKeys("book");
        WebElement bookElement = driver.findElement(By.id("Web Application Testing Book_entry"));
        assertEquals(true, bookElement.isDisplayed());
    }

    /**
     * Negative test
     * 사용자 테스트 5. 홈페이지에서 이름별로 제품 검색
     */
    @Test
    public void searchProductNegative() {
        driver.findElement(By.id("search_input")).sendKeys("Apple");

        WebElement sunglass1Element = driver.findElement(By.id("B45593 Sunglasses_entry"));
        WebElement sunglass2Element = driver.findElement(By.id("Sunglasses 2AR_entry"));
        WebElement sunglass3Element = driver.findElement(By.id("Sunglasses B45593_entry"));
        WebElement bookElement = driver.findElement(By.id("Web Application Testing Book_entry"));

        assertEquals(false, sunglass1Element.isDisplayed());
        assertEquals(false, sunglass2Element.isDisplayed());
        assertEquals(false, sunglass3Element.isDisplayed());
        assertEquals(false, bookElement.isDisplayed());
    }

    /**
     * 사용자 테스트 5. 홈페이지에서 제품 범주별로 필터링 - Sunglasses
     */
    @Test
    public void filterSunglasses() {
        By sunglassXpath = By.xpath("//*[@id=\"menu\"]/ul/li[2]/a");
        driver.findElement(sunglassXpath).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("Sunglasses B45593_entry"))));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean sunglass1Present = isElementPresent(By.id("B45593 Sunglasses_entry"));
        boolean sunglass2Present = isElementPresent(By.id("Sunglasses 2AR_entry"));
        boolean sunglass3Present = isElementPresent(By.id("Sunglasses B45593_entry"));
        boolean bookPresent = isElementPresent(By.id("Web Application Testing Book_entry"));

        assertEquals(true, sunglass1Present);
        assertEquals(true, sunglass2Present);
        assertEquals(true, sunglass3Present);
        assertEquals(false, bookPresent);
    }

    /**
     * 사용자 테스트 5. 홈페이지에서 제품 범주별로 필터링 - Books
     */
    @Test
    public void filterBooks() {
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[3]/a")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("Web Application Testing Book_entry"))));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean sunglass1Present = isElementPresent(By.id("B45593 Sunglasses_entry"));
        boolean sunglass2Present = isElementPresent(By.id("Sunglasses 2AR_entry"));
        boolean sunglass3Present = isElementPresent(By.id("Sunglasses B45593_entry"));
        boolean bookPresent = isElementPresent(By.id("Web Application Testing Book_entry"));

        assertEquals(false, sunglass1Present);
        assertEquals(false, sunglass2Present);
        assertEquals(false, sunglass3Present);
        assertEquals(true, bookPresent);
    }

    /**
     * Bug
     * 사용자 테스트 5. 홈페이지에서 제품 범주별로 필터링 - Other
     */
    @Test
    public void filterProductBug() {
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[4]/a")).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean sunglass1Present = isElementPresent(By.id("B45593 Sunglasses_entry"));
        boolean sunglass2Present = isElementPresent(By.id("Sunglasses 2AR_entry"));
        boolean sunglass3Present = isElementPresent(By.id("Sunglasses B45593_entry"));
        boolean bookPresent = isElementPresent(By.id("Web Application Testing Book_entry"));

        assertEquals(false, sunglass1Present);
        assertEquals(false, sunglass2Present);
        assertEquals(false, sunglass3Present);
        assertEquals(false, bookPresent);
    }

    /**
     * 사용자 테스트 6. 물품 결제
     */
    @Test
    public void placeOrder() {
        By sunglassXpath = By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]");
        driver.findElement(sunglassXpath).click();

        By checkoutButtonXpath = By.xpath("//input[@value='Checkout']");
        driver.findElement(checkoutButtonXpath).click();

        String name = "Juyeong Lee";
        String address = "Seoul, South Korea";
        String email = "juyeonglee0413@gmail.com";
        String type = "Check";

        driver.findElement(By.id("order_name")).sendKeys(name);
        driver.findElement(By.id("order_address")).sendKeys(address);
        driver.findElement(By.id("order_email")).sendKeys(email);
        Select payType = new Select(driver.findElement(By.id("order_pay_type")));
        payType.selectByValue(type);

        By orderButtonXpath = By.xpath("//input[@value='Place Order']");
        driver.findElement(orderButtonXpath).click();

        WebElement receiptHeader = driver.findElement(By.xpath("//*[@id=\"order_receipt\"]/h3"));
        WebElement orderName = driver.findElement(By.xpath("//*[@id=\"order_receipt\"]/p[1]"));
        WebElement orderAddress = driver.findElement(By.xpath("//*[@id=\"order_receipt\"]/p[2]"));
        WebElement orderEmail = driver.findElement(By.xpath("//*[@id=\"order_receipt\"]/p[3]"));
        WebElement orderPayType = driver.findElement(By.xpath("//*[@id=\"order_receipt\"]/p[4]"));

        WebElement quantity = driver.findElement(By.xpath("//*[@id=\"check_out\"]/tbody/tr[1]/td[1]"));
        WebElement productName = driver.findElement(By.xpath("//*[@id=\"check_out\"]/tbody/tr[1]/td[2]"));
        WebElement price = driver.findElement(By.xpath("//*[@id=\"check_out\"]/tbody/tr[1]/td[3]"));
        WebElement total = driver.findElement(By.xpath("//*[@id=\"check_out\"]/tbody/tr[2]/td[2]/strong"));

        assertEquals("Thank you for your order", receiptHeader.getText());
        assertEquals("Name: " + name, orderName.getText());
        assertEquals("Address: " + address, orderAddress.getText());
        assertEquals("Email: " + email, orderEmail.getText());
        assertEquals("Pay type: " + type, orderPayType.getText());

        assertEquals("1×", quantity.getText());
        assertEquals("B45593 Sunglasses", productName.getText());
        assertEquals("€26.00", price.getText());
        assertEquals("€26.00", total.getText());
    }

    /**
     * Negative test
     * 사용자 테스트 6. 물품 결제
     * 입력 칸을 모두 공백으로 둔 후 주문 버튼을 누른 경우
     */
    @Test
    public void placeOrderNegative() {
        By sunglassXpath = By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]");
        driver.findElement(sunglassXpath).click();

        By checkoutButtonXpath = By.xpath("//input[@value='Checkout']");
        driver.findElement(checkoutButtonXpath).click();

        By orderButtonXpath = By.xpath("//input[@value='Place Order']");
        driver.findElement(orderButtonXpath).click();

        WebElement nameError = driver.findElement(By.xpath("//*[@id=\"error_explanation\"]/ul/li[1]"));
        WebElement addressError = driver.findElement(By.xpath("//*[@id=\"error_explanation\"]/ul/li[2]"));
        WebElement emailError = driver.findElement(By.xpath("//*[@id=\"error_explanation\"]/ul/li[3]"));
        WebElement payTypeError = driver.findElement(By.xpath("//*[@id=\"error_explanation\"]/ul/li[4]"));

        assertEquals("Name can't be blank", nameError.getText());
        assertEquals("Address can't be blank", addressError.getText());
        assertEquals("Email can't be blank", emailError.getText());
        assertEquals("Pay type is not included in the list", payTypeError.getText());
    }
}
