package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.FileInputStream;
import java.util.Properties;

public class LoginTest {
    private WebDriver driver;
    private Properties props;

    @BeforeClass
    public void loadCredentials() throws Exception {
        props = new Properties();
        props.load(new FileInputStream("src/test/resources/credentials.properties"));
    }

    @BeforeMethod
    public void openHomePageAndMaximize() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @Test
    public void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(props.getProperty("hm.email.valid"), props.getProperty("hm.password.valid"));
        assert loginPage.isLoggedInSuccessfully();
    }
}
