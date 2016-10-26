package pageobjects.user.targeting;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;

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
    private final By dropdownOptionCheckboxSelectors = By.cssSelector(".dropdown-field.open .dropdown-content .x-field input + div");

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
    private final By contactSaveTargetButton = By.cssSelector(".contact .target-toggle");

    Actions actions = new Actions(driver);
    Random random = new Random();

    public NewSearchPage(WebDriver driver) {
        super(driver);
    }



    public TargetingPage createNewSearch(String searchName, String[] filters){
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
                dropdownOptionCheckboxes.get(i).click();
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
                dropdownOptionCheckboxes.get(i).click();
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
                dropdownOptionCheckboxes.get(i).click();
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

    public String targetRandomInstitution(){
        // performing filterless institution search
        waitForElement(searchButton);
        findElement(searchButton).click();
        waitForElement(saveTargetButton);

        // randomly selecting entry in result (among first 20) that is not already targeted
        int index = random.nextInt(20);
        List<WebElement> saveTargetButtons = findElements(saveTargetButton);
        List<WebElement> resultNames = findElements(resultName);
        while (!saveTargetButtons.get(index).getAttribute("class").contains("unsaved")){
            index = random.nextInt(20);
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

        // randomly selecting fund in result (among first 10 that contain contacts)
        int index = random.nextInt(10);
        List<WebElement> contactToggles = findElements(contactToggle);

        // targeting first contact and returning its name
        contactToggles.get(index).click();
        String targetedContact = findElement(contactName).getText();
        findElement(contactSaveTargetButton).click();
        return targetedContact;
    }
}
