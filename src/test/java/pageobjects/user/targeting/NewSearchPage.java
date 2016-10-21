package pageobjects.user.targeting;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.FindElements;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.util.List;

/**
 * Created by jasons on 2016-10-03.
 */
public class NewSearchPage extends AbstractPageObject{

    private final By locationFilter = By.cssSelector("[placeholder=City]");
    private final By locationListItem = By.id("ext-simplelistitem-1");
    private final By searchTypeSelectors = By.cssSelector("[name=target_type]");
    private final By searchTypeSelectorDivs = By.cssSelector("[name=target_type] + div");

    private final By draggableSliders = By.cssSelector("div.x-draggable");

    private final By filterDropdownSelectors = By.cssSelector(".targeting-filters-inner .dropdown-field");
    private final By dropdownOptionTitleSelectors = By.cssSelector(".dropdown-field.open .dropdown-content .x-field span");
    private final By dropdownOptionCheckboxSelectors = By.cssSelector(".dropdown-field.open .dropdown-content .x-field input + div");

    private final By saveSearchButton = By.xpath("//div[span/text()='Save Search']");
    private final By searchNameField = By.cssSelector("[name=name]");
    private final By saveButton = By.xpath("//div[span/text()='Save']");

    Actions actions = new Actions(driver);

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
        if (filters[1]=="Fund" || filters[1]=="fund"){
            List<WebElement> searchTypes = findElements(searchTypeSelectors);
            List<WebElement> searchTypeDivs = findElements(searchTypeSelectorDivs);
            if (searchTypes.get(0).isSelected()){
                //searchTypes.get(1).click();
                searchTypeDivs.get(1).click();
            }
            else {
                searchTypes.get(0).click();
            }
        }

        List<WebElement> sliders = findElements(draggableSliders);
        int leftSliderBound = sliders.get(2).getLocation().getX();
        int rightSliderBound = sliders.get(3).getLocation().getX();
        int sliderRange = rightSliderBound - leftSliderBound;
        System.out.println("SLIDER RANGE: "+sliderRange);

        // Filtering by QR
        actions.dragAndDropBy(sliders.get(2), sliderRange*Integer.parseInt(filters[2])/100, 0).perform();
        //System.out.println("Moved left slider with offset of: "+sliderRange*Integer.parseInt(filters[2])/100);
        actions.dragAndDropBy(sliders.get(3), sliderRange*(Integer.parseInt(filters[3])-100)/100, 0).perform();
        //System.out.println("Moved right slider with offset of: "+sliderRange*(Integer.parseInt(filters[3])-100)/100);

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
            if (dropdownOptionTitles.get(i).getText().equals(filters[8])){
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
            if (dropdownOptionTitles.get(i).getText().equals(filters[9])){
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
            if (dropdownOptionTitles.get(i).getText().equals(filters[10])){
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
            if (dropdownOptionTitles.get(i).getText().equals(filters[11])){
                dropdownOptionCheckboxes.get(i).click();
                System.out.println("SELECTED: "+dropdownOptionTitles.get(i).getText());
            }
            else System.out.println("NOT MATCHED: "+dropdownOptionTitles.get(i).getText());
        }
        filterDropdowns.get(3).click();

        //TO DO HERE: Filter by ownership in my stock, sector activity, peer activity, activists, logged activity

        // Saving search
        findElement(saveSearchButton).click();
        waitForElement(searchNameField);
        findElement(searchNameField).sendKeys(searchName);
        findElement(saveButton).click();

        return new TargetingPage(getDriver());
    }
}
