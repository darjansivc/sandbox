package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.utils.BasePage;
import test_data.PageLink;

public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".dashboard .row")
    private WebElement cardsSection;

    @FindBy(css = ".dashboard b")
    private WebElement dashboardTitle;

    @FindBy(css = ".progress.mb-4")
    private WebElement progressBar;

    @FindBy(css = "[data-testid='profile_card_id']")
    private WebElement profileCard;

    @FindBy(css = "[data-testid='use_cases_card_id']")
    private WebElement useCasesCard;

    @FindBy(css = "[data-testid='playground_card_id']")
    private WebElement playgroundCard;

    @FindBy(css = "[data-testid='reports_card_id']")
    private WebElement reportsCard;


    public boolean isDashboardSection() {
        waitForElementToBeVisible(cardsSection);
        return dashboardTitle.getText().equals("Dashboard") && progressBar.isDisplayed() && isExpectedUrl(PageLink.DASHBOARD);
    }

    private boolean isCardSection() {
        return profileCard.isDisplayed() && useCasesCard.isDisplayed() && playgroundCard.isDisplayed() && reportsCard.isDisplayed();
    }

    public boolean isDashboardPage() {
        return isDashboardSection() && isCardSection();
    }

}
