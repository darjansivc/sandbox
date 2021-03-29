package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.utils.BasePage;


public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input[name='email']")
    private WebElement txtEmailField;

    @FindBy(css = "input[name='password']")
    private WebElement txtPasswordField;

    public void setUserNameAndPassword(String loginEmail, String loginPassword) {
        txtEmailField.clear();
        txtEmailField.sendKeys(loginEmail);

        txtPasswordField.clear();
        txtPasswordField.sendKeys(loginPassword);
    }

}
//"input[name='email']"
//"input[name='password']"