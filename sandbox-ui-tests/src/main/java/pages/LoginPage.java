package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.utils.BasePage;
import utils.PropertyFile;


public class LoginPage extends BasePage {
    private final String loginEmail = new PropertyFile().get("loginEmail");
    private final String loginPassword = new PropertyFile().get("loginPassword");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "email")
    private WebElement txtEmailField;

    @FindBy(name = "password")
    private WebElement txtPasswordField;

    @FindBy(css = "button[data-testid='submit_btn']")
    private WebElement btnSubmit;

    public void setUserNameAndPassword() {
        txtEmailField.clear();
        txtEmailField.sendKeys(loginEmail);

        txtPasswordField.clear();
        txtPasswordField.sendKeys(loginPassword);

        btnSubmit.click();
    }

}
//"input[name='email']"
//"input[name='password']"