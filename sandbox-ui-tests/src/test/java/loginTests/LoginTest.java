package loginTests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseTest;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @BeforeClass
    public void beforeClass() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void login() {
        loginPage.setUserNameAndPassword("darjan.sivc@gmail.com", "qahtec");
    }
}
