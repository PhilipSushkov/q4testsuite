package pageobjects.user.targeting;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.institutionPage.InstitutionPage;

import java.util.List;
import java.util.Random;

/**
 * Created by jasons on 2016-10-03.
 */
public class NewSearchPage extends AbstractPageObject{

    private final By locationFilter = By.cssSelector("[placeholder=City]");
    private final By locationListItem = By.id("ext-simplelistitem-1");
    private final By searchTypeSelectors = By.cssSelector("[name=target_type] + div");

    private final By draggableSliders = By.cssSelector("div.x-draggable");

    private final By filterDropdownSelectors = By.cssSelector(".targeting-filters-inner .dropdown-field");
    private final By dropdownOptionTitleSelectors = By.cssSelector(".dropdown-field.open .dropdown-content .x-field span");
    private final By dropdownOptionCheckboxSelectors = By.cssSelector(".targeting-filters .dropdown-field.open .dropdown-content .x-field input + div");

    private final By ownershipStockSelectors = By.cssSelector("[name=ownership_stock] + div");
    private final By sectorActivitySelectors = By.cssSelector("[name=sector_activity] + div");
    private final By peerActivitySelectors = By.cssSelector("[name=peer_activity] + div");

    private final By excludeActivistsCheckbox = By.cssSelector("[name=exclude_activists] + div");
    private final By loggedActivityCheckbox = By.cssSelector("[name=logged_activity] + div");

    private final By saveSearchButton = By.xpath("//div[span/text()='Save Search']");
    private final By searchNameField = By.cssSelector("[name=name]");
    private final By saveButton = By.xpath("//div[span/text()='Save']");

    private final By searchButton = By.xpath("//div[span/text()='Search']");
    private final By saveTargetButton = By.className("target-toggle");
    private final By resultName = By.cssSelector(".row div:first-child .content .value");

    private final By contactToggle = By.className("contacts-toggle");
    private final By contactName = By.cssSelector(".contact .column.name");
    private final By contactSaveTargetButton = By.cssSelector(".targeting-result .column.target .target-toggle");
    private final By personContactTargetButton = By.xpath("//div[@class='x-dataview-item contact']/div/span");

    private final By showMoreButton = By.cssSelector(".load-more .x-button");
    private final By nameColumnHeader = By.cssSelector(".list-header .column.name");
    private final By locationColumnHeader = By.cssSelector(".list-header .column.locations");
    private final By PPColumnHeader = By.cssSelector(".list-header .column.purchase-power");
    private final By AUMColumnHeader = By.cssSelector(".list-header .column.aum");
    private final By turnoverColumnHeader = By.cssSelector(".list-header .column.turnover");
    private final By styleColumnHeader = By.cssSelector(".list-header .column.style");
    private final By QRColumnHeader = By.cssSelector(".list-header .column.qr");

    private final By resultLocation = By.cssSelector(".row .location .value");
    private final By resultPP = By.cssSelector(".row .purchase-power");
    private final By resultAUM = By.cssSelector(".row .aum");
    private final By resultTurnover = By.cssSelector(".row .turnover");
    private final By resultStyle = By.cssSelector(".row .style");
    private final By resultQRValue = By.cssSelector(".row .qr-value");

    private final By numLocations = By.className("location-counter");
    private final By multipleLocationSelector = By.className("location-multiple");
    private final By locationPopup = By.className("target-search-result-locations-modal");
    private final By address = By.className("address");

    private final By numResultsClaimed = By.className("result-count");
    private final By yellowArrow = By.className("collapse-button");
    private final By yellowArrowIcon = By.cssSelector(".collapse-button .x-button-icon");
    private final By filtersArea = By.className("targeting-filters");

    Actions actions = new Actions(driver);
    Random random = new Random();

    public NewSearchPage(WebDriver driver) {
        super(driver);
    }


    public TargetingPage createBlankSearch(String searchName){
        waitForElement(saveSearchButton);
        findElement(saveSearchButton).click();
        waitForElement(searchNameField);
        findElement(searchNameField).sendKeys(searchName);
        findElement(saveButton).click();
        waitForLoadingScreen();
        return new TargetingPage(getDriver());
    }

    public void blankSearch(){
        waitForElement(searchButton);
        findElement(searchButton).click();
        waitForElement(saveTargetButton);
    }

    public TargetingPage createNewSearch(String searchName, String[] filters){
        waitForLoadingScreen();
        waitForElement(locationFilter);

        // typing in location
        findElement(locationFilter).sendKeys(filters[0]);
        waitForElement(locationListItem);
        findElement(locationFilter).sendKeys(Keys.RETURN);

        // changing selection to search funds if desired
        if (filters[1].equalsIgnoreCase("Fund")){
            List<WebElement> searchTypeDivs = findElements(searchTypeSelectors);
            searchTypeDivs.get(1).click();
        }

        List<WebElement> sliders = findElements(draggableSliders);
        int leftSliderBound = sliders.get(2).getLocation().getX();
        int rightSliderBound = sliders.get(3).getLocation().getX();
        int sliderRange = rightSliderBound - leftSliderBound;
        System.out.println("SLIDER RANGE: "+sliderRange);

        // Filtering by QR
        actions.dragAndDropBy(sliders.get(2), sliderRange*Integer.parseInt(filters[2])/100, 0).perform();
        actions.dragAndDropBy(sliders.get(3), sliderRange*(Integer.parseInt(filters[3])-100)/100, 0).perform();

        // Filtering by purchasing power
        actions.dragAndDropBy(sliders.get(4), sliderRange*Integer.parseInt(filters[4])/10, 0).perform();
        actions.dragAndDropBy(sliders.get(5), sliderRange*(Integer.parseInt(filters[5])-10)/10, 0).perform();

        // Filtering by AUM
        actions.dragAndDropBy(sliders.get(6), sliderRange*Integer.parseInt(filters[6])/1000, 0).perform();
        actions.dragAndDropBy(sliders.get(7), sliderRange*(Integer.parseInt(filters[7])-1000)/1000, 0).perform();

        List<WebElement> filterDropdowns = findElements(filterDropdownSelectors);

        // Selecting turnover
        System.out.println("SELECTING TURNOVER");
        filterDropdowns.get(0).click();
        List<WebElement> dropdownOptionTitles = findElements(dropdownOptionTitleSelectors);
        List<WebElement> dropdownOptionCheckboxes = findElements(dropdownOptionCheckboxSelectors);
        for (int i=0; i<dropdownOptionTitles.size(); i++){
            if (dropdownOptionTitles.get(i).getText().equalsIgnoreCase(filters[8])){
                dropdownOptionCheckboxes.get(i).click();
                System.out.println("SELECTED: "+dropdownOptionTitles.get(i).getText());
            }
            else System.out.println("NOT MATCHED: "+dropdownOptionTitles.get(i).getText());
        }
        filterDropdowns.get(0).click();

        // Selecting type of institution/fund
        System.out.println("SELECTING TYPE");
        filterDropdowns.get(1).click();
        dropdownOptionTitles = findElements(dropdownOptionTitleSelectors);
        dropdownOptionCheckboxes = findElements(dropdownOptionCheckboxSelectors);
        for (int i=0; i<dropdownOptionTitles.size(); i++){
            if (dropdownOptionTitles.get(i).getText().equalsIgnoreCase(filters[9])){
                Actions execute = new Actions(driver);
                execute.click(dropdownOptionCheckboxes.get(i));
                findElement(By.xpath("//div[contains(@class, 'x-container x-field x-field-checkbox x-field-labeled x-label-align-left x-form-label-nowrap')]/div[contains(@class, 'x-form-label')]/*[text()= '" +
                        filters[9] + "']")).click();
                System.out.println("SELECTED: "+dropdownOptionTitles.get(i).getText());
            }
            else System.out.println("NOT MATCHED: "+dropdownOptionTitles.get(i).getText());
        }
        filterDropdowns.get(1).click();

        // Selecting style of institution/fund
        System.out.println("SELECTING STYLE");
        filterDropdowns.get(2).click();
        dropdownOptionTitles = findElements(dropdownOptionTitleSelectors);
        dropdownOptionCheckboxes = findElements(dropdownOptionCheckboxSelectors);
        for (int i=0; i<dropdownOptionTitles.size(); i++){
            if (dropdownOptionTitles.get(i).getText().equalsIgnoreCase(filters[10])){
                Actions execute = new Actions(driver);
                execute.moveToElement(dropdownOptionCheckboxes.get(i)).click();
                findElement(By.xpath("//div[contains(@class, 'x-container x-field x-field-checkbox x-field-labeled x-label-align-left x-form-label-nowrap')]/div[contains(@class, 'x-form-label')]/*[text()= '" +
                        filters[10] + "']")).click();
                System.out.println("SELECTED: "+dropdownOptionTitles.get(i).getText());
            }
            else System.out.println("NOT MATCHED: "+dropdownOptionTitles.get(i).getText());
        }
        filterDropdowns.get(2).click();

        // Selecting ownership filter
        System.out.println("SELECTING OWNERSHIP");
        filterDropdowns.get(3).click();
        dropdownOptionTitles = findElements(dropdownOptionTitleSelectors);
        dropdownOptionCheckboxes = findElements(dropdownOptionCheckboxSelectors);
        for (int i=0; i<dropdownOptionTitles.size(); i++){
            if (dropdownOptionTitles.get(i).getText().equalsIgnoreCase(filters[11])){
                Actions execute = new Actions(driver);
                execute.moveToElement(dropdownOptionCheckboxes.get(i)).click().perform();
                System.out.println("SELECTED: "+dropdownOptionTitles.get(i).getText());
            }
            else System.out.println("NOT MATCHED: "+dropdownOptionTitles.get(i).getText());
        }

        // changing Ownership in My Stock to Underweight if desired
        if (filters[12].equalsIgnoreCase("Underweight")){
            List<WebElement> ownershipStockDivs = findElements(ownershipStockSelectors);
            ownershipStockDivs.get(0).click();
        }

        // changing Ownership in My Stock to Overweight if desired
        if (filters[12].equalsIgnoreCase("Overweight")){
            List<WebElement> ownershipStockDivs = findElements(ownershipStockSelectors);
            ownershipStockDivs.get(1).click();
        }

        // changing Sector Activity to Net Buyer if desired
        if (filters[13].equalsIgnoreCase("Net Buyer")){
            List<WebElement> sectorActivityDivs = findElements(sectorActivitySelectors);
            sectorActivityDivs.get(0).click();
        }

        // changing Sector Activity to Net Seller if desired
        if (filters[13].equalsIgnoreCase("Net Seller")){
            List<WebElement> sectorActivityDivs = findElements(sectorActivitySelectors);
            sectorActivityDivs.get(1).click();
        }

        // changing Peer Activity to Net Buyer if desired
        if (filters[14].equalsIgnoreCase("Net Buyer")){
            List<WebElement> peerActivityDivs = findElements(peerActivitySelectors);
            peerActivityDivs.get(0).click();
        }

        // changing Peer Activity to Net Seller if desired
        if (filters[14].equalsIgnoreCase("Net Seller")){
            List<WebElement> peerActivityDivs = findElements(peerActivitySelectors);
            peerActivityDivs.get(1).click();
        }

        // enable Exclude Activists filter if desired
        if (filters[15].equalsIgnoreCase("Yes")){
            findElement(excludeActivistsCheckbox).click();
        }

        // enable Logged Activity filter if desired
        if (filters[16].equalsIgnoreCase("Yes")){
            findElement(loggedActivityCheckbox).click();
        }

        // Saving search
        findElement(saveSearchButton).click();
        waitForElement(searchNameField);
        findElement(searchNameField).sendKeys(searchName);
        findElement(saveButton).click();

        return new TargetingPage(getDriver());
    }

    public void performBasicLocationSearch(String location){
        waitForElement(locationFilter);
        findElement(locationFilter).sendKeys(location);
        waitForElement(locationListItem);
        findElement(locationFilter).sendKeys(Keys.RETURN);
        waitForElement(searchButton);
        findElement(searchButton).click();
        waitForElement(saveTargetButton);
    }

    public String targetRandomInstitution(){
        int count =0;
        // performing filterless institution search
        waitForElement(searchButton);
        findElement(searchButton).click();
        waitForElement(saveTargetButton);
        waitForLoadingScreen();
        // randomly selecting entry in result (among first 20) that is not already targeted
        int index = random.nextInt(20);
        List<WebElement> saveTargetButtons = findElements(saveTargetButton);
        List<WebElement> resultNames = findElements(resultName);
        while (!saveTargetButtons.get(index).getAttribute("class").contains("unsaved")){
            if(count>20){
                findElement(showMoreButton).click();
                waitForLoadingScreen();
                count = 0;
                saveTargetButtons = findElements(saveTargetButton);
            }
            index = random.nextInt(20);
            count++;

        }

        // targeting selected institution and returning its name
        String targetedInstitution = resultNames.get(index).getText();
        saveTargetButtons.get(index).click();
        return targetedInstitution;
    }

    public String targetRandomContact(){
        // performing filterless fund search
        waitForElement(searchButton);
        List<WebElement> searchTypeDivs = findElements(searchTypeSelectors);
        searchTypeDivs.get(1).click();
        findElement(searchButton).click();
        waitForElementToAppear(contactToggle);

        // randomly selecting fund in result (among first 10 that contain contacts)
        int index = random.nextInt(10);
        List<WebElement> contactToggles = findElements(contactToggle);

        // targeting first contact and returning its name
        contactToggles.get(index).click();
        waitForElementToAppear(personContactTargetButton);
        String targetedContact = findElement(contactName).getText();
        System.out.print(targetedContact);
        findElement(personContactTargetButton).click();
        return targetedContact;
    }

    public InstitutionPage goToInstitution(String name){
        pause (2000);
        List<WebElement> resultNames = findElements(resultName);
        for (int i=0; i<resultNames.size(); i++){
            if (resultNames.get(i).getText().equalsIgnoreCase(name)){
                resultNames.get(i).click();
                return new InstitutionPage(getDriver());
            }
        }
        return null;
    }

    public ContactDetailsPage goToContact(String name){
        pause (1000);
        findElement(contactName).click();
        return new ContactDetailsPage(getDriver());
    }



    /** Returns number of results currently displayed on the page. */
    public int numResultsDisplayed(){
        return findElements(resultName).size();
    }

    /** Returns number n displayed as "Results (n)" at top of results*/
    public int numResultsClaimed(){
        waitForElementToAppear(numResultsClaimed);
        String numResultsText = findElement(numResultsClaimed).getText();
        return Integer.parseInt(numResultsText.substring(1,numResultsText.indexOf(')')));
    }

    public boolean resultsCanBeSorted(){
        // sorting by name ascending
        findVisibleElement(nameColumnHeader).click();
        pause(500);
        if (!elementsAreAlphaUpSortedIgnoreCase(findElements(resultName))){
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }

        // sorting by name descending
        findVisibleElement(nameColumnHeader).click();
        pause(500);
        if (!elementsAreAlphaDownSortedIgnoreCase(findElements(resultName))){
            System.out.println("SORT ERROR: Names are not in descending order.");
            return false;
        }

        // sorting by location ascending
        findVisibleElement(locationColumnHeader).click();
        pause(500);
        if (!elementsAreAlphaUpSorted(findElements(resultLocation))){
            System.out.println("SORT ERROR: Locations are not in ascending order.");
            return false;
        }

        // sorting by location descending
        findVisibleElement(locationColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findElements(resultLocation))){
            System.out.println("SORT ERROR: Locations are not in descending order.");
            return false;
        }

        // sorting by PP ascending
        findVisibleElement(PPColumnHeader).click();
        pause(300);
        if (!elementsAreNumUpSorted(findElements(resultPP))){
            System.out.println("SORT ERROR: PP values are not in ascending order.");
            return false;
        }

        // sorting by PP descending
        findVisibleElement(PPColumnHeader).click();
        pause(300);
        if (!elementsAreNumDownSorted(findElements(resultPP))){
            System.out.println("SORT ERROR: PP values are not in descending order.");
            return false;
        }

        // sorting by AUM ascending
        findVisibleElement(AUMColumnHeader).click();
        pause(300);
        if (!elementsAreNumUpSorted(findElements(resultAUM))){
            System.out.println("SORT ERROR: AUM values are not in ascending order.");
            return false;
        }

        // sorting by AUM descending
        findVisibleElement(AUMColumnHeader).click();
        pause(300);
        if (!elementsAreNumDownSorted(findElements(resultAUM))){
            System.out.println("SORT ERROR: AUM values are not in descending order.");
            return false;
        }

        // sorting by turnover ascending
        findVisibleElement(turnoverColumnHeader).click();
        pause(300);
        if (!elementsAreTurnoverUpSorted(findElements(resultTurnover))){
            System.out.println("SORT ERROR: Turnover values are not in ascending order.");
            return false;
        }

        // sorting by turnover descending
        findVisibleElement(turnoverColumnHeader).click();
        pause(300);
        if (!elementsAreTurnoverDownSorted(findElements(resultTurnover))){
            System.out.println("SORT ERROR: Turnover values are not in descending order.");
            return false;
        }

        // sorting by style ascending
        findVisibleElement(styleColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findElements(resultStyle))){
            System.out.println("SORT ERROR: Styles are not in ascending order.");
            return false;
        }

        // sorting by style descending
        findVisibleElement(styleColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findElements(resultStyle))){
            System.out.println("SORT ERROR: Styles are not in descending order.");
            return false;
        }

        // sorting by QR ascending
        findVisibleElement(QRColumnHeader).click();
        pause(300);
        if (!elementsAreNumUpSorted(findElements(resultQRValue))){
            System.out.println("SORT ERROR: QR scores are not in ascending order.");
            return false;
        }

        // sorting by QR descending
        findVisibleElement(QRColumnHeader).click();
        pause(300);
        if (!elementsAreNumDownSorted(findElements(resultQRValue))){
            System.out.println("SORT ERROR: QR scores are not in descending order.");
            return false;
        }

        return true;
    }

    public void showMoreResults(){
        int numResults = numResultsDisplayed();
        findVisibleElement(showMoreButton).click();
        for (int i=0; i<100; i++){ // waiting up to 10 seconds for the number of displayed results to increase
            if (numResultsDisplayed()>numResults){
                return;
            }
            pause(100);
        }
        System.out.println("TIMEOUT: More results haven't been loaded after 10 seconds");
    }

    public boolean showMoreAppears(){
        return findVisibleElements(showMoreButton).size() > 0;
    }

    /** Returns the number in the circle for the first result with "Multiple" locations. */
    public int numLocationsFirst(){
        return Integer.parseInt(findElement(numLocations).getText());
    }

    public void openFirstLocationPopup(){
        findVisibleElement(multipleLocationSelector).click();
        waitForElementToAppear(locationPopup);
    }

    public boolean locationPopupIsOpen(){
        return doesElementExist(locationPopup);
    }

    public int numLocationsDisplayedInPopup(){
        return findElements(address).size();
    }

    public void closeLocationPopup(){
        actions.moveToElement(findElement(locationPopup), -1, -1).click().build().perform(); //clicking outside of popup
        waitForElementToDissapear(locationPopup);
    }

    public boolean filtersAreaIsCollapsed(){
        String filterAreaHeightCSS = findElement(filtersArea).getCssValue("height");
        int filterAreaHeight = Integer.parseInt(filterAreaHeightCSS.substring(0,filterAreaHeightCSS.indexOf('p'))); //trims "px" and parses to int
        System.out.println("Filter area has height of "+filterAreaHeight);
        return filterAreaHeight<600; //filter area is considered collapsed if it's height is less than 600px
    }

    public void clickInFiltersArea(){
        actions.moveToElement(findElement(locationFilter), 1, 1).click().build().perform(); //clicks just inside the top left corner of filter area
        pause(1000);
    }

    public void clickSearchButton(){
        findElement(searchButton).click();
        pause(1000);
    }

    public boolean yellowArrowIsUp(){
        return findElement(yellowArrowIcon).getAttribute("class").contains("q4i-arrow-sm-up-4pt");
    }

    public void clickYellowArrow(){
        findElement(yellowArrow).click();
        pause(1000);
    }
}
