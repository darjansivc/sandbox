package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.utils.BasePage;
import test_data.PageLink;
import utils.PropertyFile;


import java.util.ArrayList;
import java.util.List;

public class UseCasePage extends BasePage {
    private final String baseUrl = new PropertyFile().get("baseUrl");
    String PREFIX = "This field previously had ";
    String SUFFIX = " characters ";


    public static ArrayList<Integer> useCasesIds = new ArrayList<>();

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

    @FindBy(css = "a.list-group-item.list-group-item-action")
    private WebElement useCasesTitle;

    @FindBy(css = "[data-testid='remove_usecase_btn']")
    private WebElement btnDelete;

    @FindBy(css = "button.btn.btn-lg.btn-danger")
    private WebElement btnConfirmDelete;

    public void goToUseCasesPage() {
        useCases.click();
    }

    public void isUseCasesPage() {
        waitForElementToBeVisible(btnCreateUseCase);
    }

    public void createUseCase(String title, String description, String expectedResult, String stepId) {
        btnCreateUseCase.click();
        txtTitle.sendKeys(title);
        txtDescription.sendKeys(description);
        txtExpectedResult.sendKeys(expectedResult);

        txtStepId.sendKeys(stepId);
        txtExpectedResult.sendKeys(Keys.ENTER);
//        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", cbSwitch);

        clickWithJavaScript(cbSwitch);
        btnSubmit.click();
        waitForElementToBeClickable(btnCreateUseCase);


//        getUseCaseIds(title);
    }

    public void getUseCaseText() {
        for (int i = 0; i < useCasesTitles.size(); i++) {
            System.out.println("da: " + useCasesTitles.get(i).getText());
        }
    }

    private void addUseCaseIdToList(Integer useCaseId) {
        useCasesIds.add(useCaseId);
    }

    public void getUseCaseIds(String useCaseTitle) {
        for (int i = 0; i < useCasesTitles.size(); i++) {
            if (useCasesTitles.get(i).getText().equals(useCaseTitle)) {
                useCasesTitles.get(i).click();
                String currentUrl = driver.getCurrentUrl();
                String[] useCaseId = currentUrl.split(PageLink.USE_CASES);
                System.out.println("Use Case ID: " + useCaseId[1]);
                addUseCaseIdToList(Integer.valueOf(useCaseId[1]));
                driver.navigate().back();
            }
//            System.out.println("da: " + useCasesTitles.get(i).getText());
        }
    }

    public void editUseCase(String useCaseTitle) {
//        for (int i=0;i<useCasesIds.size();i++){
//            driver.navigate().to(PageLink.USE_CASES+useCasesIds.get(i));
//        }
        for (int i = 0; i < useCasesTitles.size(); i++) {
            if (useCasesTitles.get(i).getText().equals(useCaseTitle)) {
                useCasesTitles.get(i).click();
                waitForElementToBeClickable(txtTitle);
                String newTitle = PREFIX + txtTitle.getAttribute("value").length() + SUFFIX;
                txtTitle.clear();
                txtTitle.sendKeys(newTitle);
                btnSubmit.click();
                waitForElementToBeClickable(btnCreateUseCase);
            }
//            System.out.println("da: " + useCasesTitles.get(i).getText());
        }

    }

    public void deleteUseCase() {
//        int useCasesTitlesListSize = useCasesTitles.size();
        for (int i = 0; i <= useCasesTitles.size(); i++) {
            if (useCasesTitles.get(i).getText().startsWith(PREFIX)) {
                useCasesTitles.get(i).click();
                waitForElementToBeClickable(btnDelete);
                btnDelete.click();
                waitForElementToBeClickable(btnConfirmDelete);
                btnConfirmDelete.click();
                waitForElementToBeClickable(btnCreateUseCase);
                waitForElementToBeClickable(useCasesTitle);

            }
//            System.out.println("da: " + useCasesTitles.get(i).getText());
        }

    }

}
