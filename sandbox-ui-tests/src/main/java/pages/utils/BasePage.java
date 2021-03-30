package pages.utils;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.ITestContext;
import utils.Statics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static utils.Statics.waitingTimeInSeconds;

public class BasePage {
    protected static WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

//    private static final Logger log = LogManager.getLogger(LoginCredentialsTest.class.getName());

    @FindBy(id = "pageLoading")
    static WebElement pageLoader;

    @FindBy(id = "header-tittle")
    static WebElement headerTitle;

    @FindBy(css = "#content h2")
    static WebElement pageH2Title;

    @FindBy(css = "#content h1")
    protected static WebElement pageH1Title;

    @FindBy(css = "#tenantMenu.tenant-switcher:not(.mobile) span:nth-child(2)")
    static WebElement currentTenant;

    @FindBy(css = ".animated.slide-in-down.js_bubbleNotification.message-success")
    static WebElement successBubble;

    @FindAll(value = {@FindBy(css = "ul.dropdown-menu li")})
    List<WebElement> threeDotsMenu;

    @FindBy(css = "li.dropdown-trigger")
    static WebElement threeDotsDropDownMenu;

    @FindBy(id = "saveConfirmationMessage")
    static
    WebElement ppConfirmation;

    @FindBy(id = "saveConfirmationActionOk")
    static
    WebElement btnConfirmationInPopup;

    @FindBy(id = "checkAll")
    static WebElement cbSelectAll;

    @FindBy(id = "moduleMenu")
    static WebElement ddModule;

    @FindBy(id = "tenantMenu")
    static WebElement ddTenant;

    @FindBy(name = "Save")
    private static WebElement btnSave;

    @FindBy(id = "loadProductPopup")
    static WebElement loadProductsPopup;

    @FindBy(css = "#popupWrapper a.button")
    static WebElement btnConfirmLoadOfTheProduct;

    @FindBy(css = "#popupWrapper a.button-cancel")
    static WebElement btnCancelLoadOfTheProducts;

    @FindBy(css = ".icon.icon-shopping-1")
    static WebElement selectedModule;

    @FindBy(id = "mainMenu")
    static WebElement mainMenuID;

    @FindBy(css = ".tree-buttons-bottom a.button-cancel")
    static WebElement btnClearFilter;

    @FindBy(css = "li>button.button")
    static WebElement btnApplyFilter;

    @FindBy(id = "OrderNumber")
    WebElement txtOrderNumber;

//    @FindBy(css = "#companyActivitiesViewport>tr>td:nth-child(2)")
//    List<WebElement>

    public static void clickWithJavaScript(WebElement webElement) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", webElement);
    }


    /**
     * dsadasdasdasda
     * @param selector Webelement selector
     * @param text     is actually visible text in HTML
     */
    public static void selectFromDropDownByVisibleText(WebElement selector, String text) {
        Select dropDown = new Select(selector);
        dropDown.selectByVisibleText(text);
    }

    public static void selectFromDropDownByIndex(WebElement selector, int index) {
        Select dropDown = new Select(selector);
        dropDown.selectByIndex(index);
    }

    public static String selectedDropDownValue(WebElement element) {
        Select dropDownList = new Select(element);
//		String selectedValue = dropDownList.getFirstSelectedOption().getText();
        return dropDownList.getFirstSelectedOption().getText();
    }

    public static void selectFromDropDownByValue(WebElement dropDownSelector, String dropDownValue) {
        Select dropDown = new Select(dropDownSelector);
        dropDown.selectByValue(dropDownValue);
    }

    public static void waitForElementToBeClickable(WebElement selector, int waitInterval) {
        new WebDriverWait(driver, waitInterval)
                .until(ExpectedConditions.elementToBeClickable(selector));
    }

    public static void waitForElementToBeClickable(WebElement selector) {
        new WebDriverWait(driver, Statics.waitingTimeInSeconds)
                .until(ExpectedConditions.elementToBeClickable(selector));
    }

    public static void waitForElementToBeVisible(WebElement selector, int waitInterval) {
        (new WebDriverWait(driver, waitInterval)).until(ExpectedConditions.visibilityOf(selector));
    }

    public static void waitForElementToBeVisible(WebElement selector) {
        (new WebDriverWait(driver, Statics.waitingTimeInSeconds)).until(ExpectedConditions.visibilityOf(selector));
    }

    public static void waitForTheElementDisappears(WebElement selector, int waitInterval) {
        (new WebDriverWait(driver, waitInterval)).until(ExpectedConditions.invisibilityOf(selector));
    }


//    FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
//            .withTimeout(Duration.ofSeconds(30))
//            .pollingEvery(Duration.ofSeconds(5))
//            .ignoring(NoSuchElementException.class);
//
//
//    WebElement element = fluentWait.until(new Function<WebDriver, WebElement>() {
//        public WebElement apply(WebDriver driver) {
//            WebElement element = driver.findElement(By.xpath("//*[@id='softwareTestingMaterial']"));
//            String getTextOnPage = element.getText();
//            if(getTextOnPage.equals("Software Testing Material - DEMO PAGE")){
//                System.out.println(getTextOnPage);
//                return element;
//            }else{
//                System.out.println("FluentWait Failed");
//                return null;
//            }
//        }
//    });

    public static void waitForElementValueBecomeDifferentThenEmpty(WebElement selector){
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);


        fluentWait.until(driver -> {
            String getTextOnPage = selector.getText();
            if(!getTextOnPage.equals("")){
                System.out.println("Current element text is: " + getTextOnPage);
                return selector;
            }else{
                System.out.println("FluentWait...");
                return null;
            }
        });
    }

    public static boolean waitForElementToAppear(WebElement element) {
        boolean webElementPresence = false;
        try {
            Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(5))
                    .ignoring(NoSuchElementException.class);
            fluentWait.until(ExpectedConditions.visibilityOf(element));
            if (element.isDisplayed()) {
                webElementPresence= true;
            }
        } catch (TimeoutException toe) {
            System.out.println("TimeoutException:" + Arrays.toString(toe.getStackTrace()));
        } catch (Exception e) {
            System.out.println("TimeoutException_line2:" + Arrays.toString(e.getStackTrace()));
        }
        return webElementPresence;
    }



    public static void waitForTheElementDisappears(WebElement selector) {
        (new WebDriverWait(driver, Statics.waitingTimeInSeconds)).until(ExpectedConditions.invisibilityOf(selector));
    }

    public static void waitForTheLoaderToDisappear(int waitInterval) {
        (new WebDriverWait(driver, waitInterval)).until(ExpectedConditions.invisibilityOf(pageLoader));
    }

    public static void waitForTheLoaderToDisappear() {
        (new WebDriverWait(driver, Statics.waitingTimeInSeconds)).until(ExpectedConditions.invisibilityOf(pageLoader));
    }

//    public static void waitForElementToBePresent(WebElement selector) {
//        (new WebDriverWait(driver, waitingTimeInSeconds)).until(ExpectedConditions.presenceOfElementLocated(By(selector)));
//    }

    public boolean isElementPresent(WebElement webElement){
        try {
            webElement.getTagName();
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public static List<String> getOrdersNumbersSubList(int endValue, List<WebElement> rowsContent) {
        List<WebElement> subList = rowsContent.subList(0, endValue);

        List<String> returnValue = new ArrayList<String>();

        for (WebElement webEelement : subList) {
            String orderNumber = webEelement.getText();
            System.out.println(orderNumber);

            returnValue.add(orderNumber);
        }

        return returnValue;
    }

    public static List<String> clickOnAllCheckBoxesFromList(int endValue, List<WebElement> rowsContent,
                                                            WebElement ordersTable) {
        List<WebElement> subList = rowsContent.subList(0, endValue);

        List<String> returnValue = new ArrayList<String>();

        for (WebElement webEelement : subList) {
            String orderNumber = webEelement.getText();
            System.out.println(orderNumber);

            returnValue.add(orderNumber);

            List<WebElement> rowsList = ordersTable.findElements(By.cssSelector("tr a"));

            for (WebElement cell : rowsList) {
                if (orderNumber.equals(cell.getText())) {
                    WebElement parent = cell.findElement(By.xpath(".."));
                    WebElement parent2 = parent.findElement(By.xpath(".."));
                    WebElement parent3 = parent2.findElement(By.tagName("td"));
                    WebElement checkbox = parent3.findElement(By.cssSelector("input.hidden-text.block-element"));
                    checkbox.click();
                    break;
                }
            }
        }

        return returnValue;
    }

    public static void clickOnSelectAllCheckBox(WebElement selectAllCheckBox) {
        selectAllCheckBox.click();

    }

    public static void clickOnChoosenRadioButtonByValue(String desiredRadioButtonValue, List<WebElement> wrapperOfCheckBoxElement) {
        for (WebElement webelement : wrapperOfCheckBoxElement) {
            String getWebElementValue = webelement.getAttribute("value");
            if (getWebElementValue.equals(desiredRadioButtonValue)) {
                webelement.click();
            }
        }
    }

//    public static void selectRadioButtonByVisibleText()

    public static String getH1PageTitle() {
        waitForElementToAppear(pageH1Title);
        return pageH1Title.getText();
    }

    public static String getPageTitle() {
        return pageH2Title.getText();
    }

    public String getHeaderTitle() {
        return headerTitle.getText();
    }


    public static String getCurrentTimeAndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmssddMMyyyy");
        Date date = new Date();
        String currentDateAndTime = formatter.format(date);
        return currentDateAndTime;
    }

//    public static String getCurrentTenantName() {
//        return currentTenant.getText();
//
//    }

    public void areAllElementsEnabled(List<WebElement> elementsList) {
        for (WebElement menuPoint : elementsList) {
            Assert.assertTrue(menuPoint.isEnabled());
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public static boolean isSuccesBubbleShown() {
        waitForElementToBeVisible(successBubble, 15);
        return true;
//        if (successBubble.isDisplayed()){
//            return true;
//        }
//        return false;
//        assertTrue(successBubble.isDisplayed());
    }

    public static void waitForSuccesBubbleIsGone() {
        waitForTheElementDisappears(successBubble, 15);
        Assert.assertFalse(successBubble.isDisplayed());
    }

    public static void clickOnSaveButton() {
        waitForElementToBeClickable(btnSave);
        btnSave.click();
        waitForTheLoaderToDisappear();
    }

    private WebElement findMenuItemInThreeDotsDropDownMenuByMenuItemTitle(String menuItemTitle) {
        //		WebElement returnThreeDotsMenuItem = null;
        WebElement returnValue = null;

        for (WebElement threeDotsMenuItem : threeDotsMenu) {
            String returnText = threeDotsMenuItem.getText();
//            System.out.println(returnText);
            if (menuItemTitle.equals(threeDotsMenuItem.getText())) {
                returnValue = threeDotsMenuItem;
                break;
            }
        }

        return returnValue;
    }

    public void chooseFromThreeDotsMenu(String menuItemTitle) {
        waitForElementToBeVisible(threeDotsDropDownMenu);
        Actions action = new Actions(driver);

        action.moveToElement(threeDotsDropDownMenu).perform();
        WebElement menuItemElement = findMenuItemInThreeDotsDropDownMenuByMenuItemTitle(menuItemTitle);
        waitForElementToBeClickable(menuItemElement);
        menuItemElement.click();
        waitForTheLoaderToDisappear();
    }

    public static void chooseFromHoverMenu(WebElement menuItemLocator) {

        Actions action = new Actions(driver);
        waitForElementToBeVisible(threeDotsDropDownMenu);
        action.moveToElement(threeDotsDropDownMenu).perform();
        waitForElementToBeClickable(menuItemLocator);
        menuItemLocator.click();
        waitForTheLoaderToDisappear();
    }

    public static void changeModule(String moduleName, WebElement moduleLink) {
        if (!moduleName.equals(selectedModule.getText())){
            Actions action = new Actions(driver);
            action.moveToElement(ddModule).perform();
            moduleLink.click();
            waitForTheLoaderToDisappear();
        }
    }

    public static void chooseTenantFromTenantList(WebElement tenantName) {
        Actions action = new Actions(driver);
        action.moveToElement(ddTenant).perform();
        tenantName.click();
    }


    public static void confirmInWarningPopUp() {
        if (ppConfirmation.isEnabled() && ppConfirmation.isDisplayed()) {
            btnConfirmationInPopup.click();
            waitForTheLoaderToDisappear();
        }
    }

    public static void selectCheckBoxWithLabelName(List<WebElement> listWithCheckBoxes, String labelName) {
        for (WebElement n : listWithCheckBoxes) {
            if (n.findElement(By.tagName("label")).getText().equals(labelName)) {
                if (!n.findElement(By.cssSelector("span[class='mask-checkbox']>input")).isSelected()) {
                    n.findElement(By.cssSelector("span[class='mask-checkbox']>input")).click();
                    break;
                }
                break;
            }
        }
    }

    public static void selectCheckBoxWithLabelNameTree(List<WebElement> listWithCheckBoxes, String labelName) {
        for (WebElement n : listWithCheckBoxes) {
            String aaa = n.findElement(By.cssSelector("span.tree-node-name")).getText();
            if (aaa.equals(labelName)) {
                if (!n.findElement(By.cssSelector("span[class='mask-checkbox']>input")).isSelected()) {
                    n.findElement(By.cssSelector("span[class='mask-checkbox']>input")).click();
                    break;
                }
                break;
            }
        }
    }


    public static void selectCheckBoxWithLabelNameInTreeWithoutDefault(List<WebElement> listWithCheckBoxes, String labelName) {
        for (WebElement n : listWithCheckBoxes) {
            String aaa = n.findElement(By.cssSelector("label.checkbox-text")).getText();
            if (aaa.equals(labelName)) {
                if (!n.findElement(By.cssSelector("span[class='mask-checkbox']>input")).isSelected()) {
                    n.findElement(By.cssSelector("span[class='mask-checkbox']>input")).click();
                    waitForTheLoaderToDisappear();
                    break;
                }
                break;
            }
        }
    }


    public static void clickOnSelectAllCheckBox() {
        cbSelectAll.click();
        waitForTheLoaderToDisappear();
    }

    public static String getSuiteName(ITestContext context) {
        return context.getCurrentXmlTest().getSuite().getName();
    }

    public static boolean isItemInTableTdElement(WebElement tableID, String tableChildrenAsString, int tableColumnNumber, String cellValue){
        List<WebElement> listOfElements = tableID.findElements(By.cssSelector(tableChildrenAsString + "(" + tableColumnNumber + ")"));
        for (WebElement elementInList : listOfElements){
            if (elementInList.getText().equals(cellValue)){
                return true;
            }
        }
        return false;
    }

    public static boolean isItemAddedToRightSideMenu(List <WebElement> listWithItems, String itemName){
        for (WebElement itemInList : listWithItems){
            if (itemInList.getText().equals(itemName)){
                return true;
            }
        }
        return false;
    }


    public static void takeSnapShot(WebDriver webdriver,String fileWithPath) {

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file

        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

        File DestFile=new File(fileWithPath);

        //Copy file at destination

        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void cancelLoadProductScreen() {
        try {
            if (loadProductsPopup.isEnabled() && loadProductsPopup.isDisplayed()) {
                btnCancelLoadOfTheProducts.click();
                waitForTheLoaderToDisappear();
            }
        } catch (NoSuchElementException e) {

        }
    }


    public static void goToPage(WebElement itemLevel1){
        waitForElementToBeVisible(mainMenuID);
        waitForElementToBeClickable(itemLevel1);
        itemLevel1.click();
        waitForTheLoaderToDisappear();

        confirmInWarningPopUp();
        cancelLoadProductScreen();
    }

    public static void goToPage(WebElement itemLevel1, WebElement itemsListLevel2, WebElement itemLevel2){
        waitForTheLoaderToDisappear();
        waitForElementToBeVisible(mainMenuID);
        waitForElementToBeClickable(itemLevel1);
        itemLevel1.click();
        waitForElementToBeVisible(itemsListLevel2);
        waitForElementToBeClickable(itemLevel2,60);
        itemLevel2.click();
        waitForTheLoaderToDisappear();

        confirmInWarningPopUp();
        cancelLoadProductScreen();
    }

    public static void goToPage(WebElement itemLevel1, WebElement itemsListLevel2, WebElement itemLevel2, WebElement itemsListLevel3, WebElement itemLevel3){
        waitForElementToBeVisible(mainMenuID);
        waitForElementToBeClickable(itemLevel1);
        itemLevel1.click();
        waitForElementToBeVisible(itemsListLevel2);
        waitForElementToBeClickable(itemLevel2);
        itemLevel2.click();
        waitForElementToBeVisible(itemsListLevel3);
        waitForElementToBeClickable(itemLevel3);
        itemLevel3.click();
        waitForTheLoaderToDisappear();
    }


//    public static ArrayList<String> listOfDistributionOrders = new ArrayList<>();
//
//    public static List<String> listOfCustomerOrders = new ArrayList<>();
//
//    public void fillListWithOrderNumbers(String orderNumber){
//        listOfDistributionOrders.add(orderNumber);
//    }
//
//    public static void fillListWithCustomerOrderNumbers(String orderNumber){
//        listOfCustomerOrders.add(orderNumber);
//    }

    public static void clearFilter(){
        waitForTheLoaderToDisappear();
        waitForElementToBeClickable(btnClearFilter);

        btnClearFilter.click();
        waitForTheLoaderToDisappear();
        waitForElementToBeClickable(btnClearFilter);
    }

    public static void applyFilter(){
        waitForTheLoaderToDisappear();
        waitForElementToBeClickable(btnApplyFilter);
        btnApplyFilter.click();
        waitForTheLoaderToDisappear();
        waitForElementToBeClickable(btnApplyFilter);
    }

    public void searchByOrderNumber(String orderNumber){
        clearFilter();
        txtOrderNumber.clear();
        txtOrderNumber.sendKeys(orderNumber);
        applyFilter();

    }



}
