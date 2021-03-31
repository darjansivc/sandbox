package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.utils.BasePage;
import test_data.PageLink;

import java.util.ArrayList;
import java.util.List;

public class UseCasePage extends BasePage {
    private static final String PREFIX = "This field previously had ";
    private static final String SUFFIX = " characters ";
    private List<Integer> createdUseCasesIds = new ArrayList<>();
    private List<String> foundedUseCases = new ArrayList<>();
    private List<String> newTitlesList = new ArrayList<>();
    private int numberOfIterations;


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

    public boolean isUseCasesPage() {
        waitForElementToBeVisible(btnCreateUseCase);
        return btnCreateUseCase.isDisplayed();
    }

    public void createUseCase(String title, String description, String expectedResult, String stepId) {
        btnCreateUseCase.click();
        txtTitle.sendKeys(title);
        foundedUseCases.add(title); //dodato
        txtDescription.sendKeys(description);
        txtExpectedResult.sendKeys(expectedResult);

        txtStepId.sendKeys(stepId);
        txtExpectedResult.sendKeys(Keys.ENTER);
        clickWithJavaScript(cbSwitch);
        btnSubmit.click();
        waitForElementToBeClickable(btnCreateUseCase);

        numberOfIterations++;
    }

//    public void getAddedUseCasesTitles(String useCaseTitle) {
//        for (int i = 0; i < useCasesTitles.size(); i++) {
//            if (useCasesTitles.get(i).getText().equals(useCaseTitle)) {
//                foundedUseCases.add(useCasesTitles.get(i).getText());
//            }
//        }
//    }

    public boolean verifyIfUseCasesAreAdded() {
        if (foundedUseCases.size() == numberOfIterations) {
            return true;
        }
        return false;
    }


    public void editUseCase(String useCaseTitle) {
        for (int i = 0; i < useCasesTitles.size(); i++) {
            if (useCasesTitles.get(i).getText().equals(useCaseTitle)) {
                useCasesTitles.get(i).click();
                waitForElementToBeClickable(txtTitle);
                String[] currentUseCasesId = driver.getCurrentUrl().split(PageLink.USE_CASES);

                createdUseCasesIds.add(Integer.valueOf(currentUseCasesId[1]));

                String newTitle = PREFIX + txtTitle.getAttribute("value").length() + SUFFIX;
                newTitlesList.add(newTitle);
                txtTitle.clear();
                txtTitle.sendKeys(newTitle);
                btnSubmit.click();
                waitForElementToBeClickable(btnCreateUseCase);
            }
        }

    }

    public boolean verifyIfUseCasesAreEdited() {
        if (newTitlesList.size() == numberOfIterations) {
            return true;
        }
        return false;
    }

    public boolean verifyIfUseCaseTitlesAreChanged() {
        for (int i = 0; i < newTitlesList.size(); i++) {
            for (int j = 0; j < useCasesTitles.size(); j++) {
                if (!newTitlesList.get(i).equals(useCasesTitles.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void deleteUseCase() {
        for (int i = 0; i < createdUseCasesIds.size(); i++) {
            driver.navigate().to(PageLink.USE_CASES + createdUseCasesIds.get(i));
            waitForElementToBeClickable(btnDelete);
            btnDelete.click();
            waitForElementToBeClickable(btnConfirmDelete);
            btnConfirmDelete.click();
            waitForElementToBeClickable(btnCreateUseCase);

        }

    }

}
