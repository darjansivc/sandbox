package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.utils.BasePage;

import java.util.List;

public class UseCasePage extends BasePage {
    public UseCasePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[data-testid='use_cases_card_id']")
    private WebElement useCases;

    @FindBy(css = "[data-testid='create_use_case_btn']")
    private WebElement btnCreateUseCase;

    @FindBy(name = "title")
    private WebElement txtTitle;

    @FindBy(name = "description")
    private WebElement txtDescription;

    @FindBy(name = "expected_result")
    private WebElement txtExpectedResult;

    @FindBy(id = "switch")
    private WebElement cbSwitch;

    @FindBy(id = "stepId")
    private WebElement txtStepId;

    @FindBy(css = "[data-testid='add_step_btn']")
    private WebElement btnAddStep;

    @FindBy(css = "[data-testid='submit_btn']")
    private WebElement btnSubmit;

    @FindBy(css = "a.list-group-item.list-group-item-action")
    private List<WebElement> useCasesTitles;

    public void goToUseCasesPage() {
        useCases.click();
    }

    public void isUseCasesPage() {
        waitForElementToBeVisible(btnCreateUseCase);
    }

    public void createUseCase(String title, String description, String expectedResult, String stepId) {
        waitForElementToBeClickable(btnCreateUseCase);
        btnCreateUseCase.click();
        txtTitle.sendKeys(title);
        txtDescription.sendKeys(description);
        txtExpectedResult.sendKeys(expectedResult);

        txtStepId.sendKeys(stepId);
        txtExpectedResult.sendKeys(Keys.ENTER);
//        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", cbSwitch);

        clickWithJavaScript(cbSwitch);
        btnSubmit.click();
    }

    public void getUseCaseText() {
        for (int i = 0; i < useCasesTitles.size(); i++) {
            System.out.println("da: " + useCasesTitles.get(i).getText());
        }
    }

}
