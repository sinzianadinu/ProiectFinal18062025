package pages;

import org.openqa.selenium.*;

public class LoginPage extends BasePage {
    private final By emailInput = By.xpath("//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input");
    private final By passwordInput = By.cssSelector("input.oxd-input.oxd-input--active[type='password'][name='password']");
    private final By submitBtn = By.cssSelector("button.oxd-button.oxd-button--medium.oxd-button--main.orangehrm-login-button[type='submit']");
    private final By errorMessage = By.cssSelector("div.z-alert__text");
    private final By userMenuAfterLogin = By.cssSelector("a[data-testid='user-menu-button']");
    private final By logoutBtn = By.cssSelector("a[data-zta='account-overview-logout-link']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        elements.waitForElement(emailInput);
        elements.fillElement(emailInput, email);
        WebElement emailElement = driver.findElement(emailInput);
        String emailJs = "arguments[0].value = '" + email + "';" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));";
        ((JavascriptExecutor) driver).executeScript(emailJs, emailElement);

        // PAS 2: Simulează ENTER după ce a fost completat emailul

        emailElement.sendKeys(Keys.ENTER); // trimite tasta Enter

        // PAS 3: Așteaptă input-ul de parolă să apară
        elements.waitForElement(passwordInput);

        // PAS 4: Completează parola și click pe butonul de login
        elements.fillElement(passwordInput, password);
        elements.clickElement(submitBtn);
    }

    public boolean isLoginErrorDisplayed() {
        return elements.getElements(errorMessage).size() > 0;
    }

    public boolean isLoggedInSuccessfully() {
        By greetingText = By.xpath("//*[contains(text(), 'Salut Eugen')]");
        try {
            elements.waitForElement(greetingText);
            return true;
        } catch (Exception e) {
            System.out.println("Textul 'Salut Eugen' nu a fost găsit după login.");
            return false;
        }
    }

    public void logout() {
        elements.clickElement(logoutBtn);
    }

    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("feedback_logout");
    }
}