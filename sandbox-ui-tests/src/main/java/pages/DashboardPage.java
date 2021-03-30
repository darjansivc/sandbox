package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.utils.BasePage;

public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getUseCasesCard() {
        return useCasesCard;
    }

    @FindBy(css ="[data-testid='use_cases_card_id'")
    private WebElement useCasesCard;

    public boolean isInitialized(){
        return useCasesCard.isDisplayed();
    }

}
