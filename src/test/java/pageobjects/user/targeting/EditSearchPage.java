package pageobjects.user.targeting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.util.List;

/**
 * Created by jasons on 2016-10-24.
 */
public class EditSearchPage extends AbstractPageObject {

    private final By hideResultsButton = By.cssSelector(".collapse-button");

    private final By cityName = By.cssSelector(".location-item span");
    private final By searchTypeSelectors = By.cssSelector("[name=target_type]");
    private final By minQR = By.cssSelector(".targeting-filters-inner div:nth-child(3) .value.min");
    private final By maxQR = By.cssSelector(".targeting-filters-inner div:nth-child(3) .value.max");
    private final By minPP = By.cssSelector(".targeting-filters-inner div:nth-child(4) .value.min");
    private final By maxPP = By.cssSelector(".targeting-filters-inner div:nth-child(4) .value.max");
    private final By minAUM = By.cssSelector(".targeting-filters-inner div:nth-child(5) .value.min");
    private final By maxAUM = By.cssSelector(".targeting-filters-inner div:nth-child(5) .value.max");

    private final By filterDropdownSelectors = By.cssSelector(".targeting-filters-inner .dropdown-field");
    private final By dropdownOptionTitleSelectors = By.cssSelector(".dropdown-field.open .dropdown-content .x-field span");
    private final By dropdownOptionCheckboxSelectors = By.cssSelector(".dropdown-field.open .dropdown-content .x-field input");

    private final By ownershipFilter = By.cssSelector(".dropdown-field.last .toggle-field .x-form-label span");
    private final By ownershipStockSelectors = By.cssSelector("[name=ownership_stock]");
    private final By sectorActivitySelectors = By.cssSelector("[name=sector_activity]");
    private final By peerActivitySelectors = By.cssSelector("[name=peer_activity]");
    private final By excludeActivistsCheckbox = By.cssSelector("[name=exclude_activists]");
    private final By loggedActivityCheckbox = By.cssSelector("[name=logged_activity]");

    private final By deleteSearchButton = By.cssSelector(".action-button.x-iconalign-center");


    public EditSearchPage(WebDriver driver) {
        super(driver);
    }


    public TargetingPage deleteSearch(){
        waitForElement(deleteSearchButton);
        findElement(deleteSearchButton).click();

        return new TargetingPage(getDriver());
    }

    public boolean verifyFilters(String[] filters){
        boolean filtersMatch = true;
        waitForElement(cityName);
        findElement(hideResultsButton).click();

        // verifying location
        if (!findElement(cityName).getText().equalsIgnoreCase(filters[0])){
            filtersMatch = false;
            System.out.println("Location "+findElement(cityName).getText()+" does not equal desired value "+filters[0]);
        }

        // verifying institution/fund option
        List<WebElement> searchTypes = findElements(searchTypeSelectors);
        if (filters[1].equalsIgnoreCase("Fund")){
            if (!searchTypes.get(1).isSelected()){
                filtersMatch = false;
                System.out.println("Desired search type 'Fund' is not selected.");
            }
        }
        else {
            if (!searchTypes.get(0).isSelected()){
                filtersMatch = false;
                System.out.println("Desired search type 'Institution' is not selected.");
            }
        }

        // verifying QR filter
        if (!findElement(minQR).getText().equals(filters[2])){
            filtersMatch = false;
            System.out.println("Min QR "+findElement(minQR).getText()+" does not equal desired value "+filters[2]);
        }
        if (!findElement(maxQR).getText().equals(filters[3])){
            filtersMatch = false;
            System.out.println("Max QR "+findElement(maxQR).getText()+" does not equal desired value "+filters[3]);
        }

        // verifying purchasing power filter
        if (!findElement(minPP).getText().equals(filters[4])){
            filtersMatch = false;
            System.out.println("Min PP "+findElement(minPP).getText()+" does not equal desired value "+filters[4]);
        }
        if (!findElement(maxPP).getText().equals(filters[5])){
            filtersMatch = false;
            System.out.println("Max PP "+findElement(maxPP).getText()+" does not equal desired value "+filters[5]);
        }

        // verifying AUM filter
        if (!findElement(minAUM).getText().equals(filters[6])){
            filtersMatch = false;
            System.out.println("Min AUM "+findElement(minAUM).getText()+" does not equal desired value "+filters[6]);
        }
        if (!findElement(maxAUM).getText().equals(filters[7])) {
            filtersMatch = false;
            System.out.println("Max AUM " + findElement(maxAUM).getText() + " does not equal desired value " + filters[7]);
        }

        List<WebElement> filterDropdowns = findElements(filterDropdownSelectors);
        boolean filterOptionFound = false;

        // verifying turnover filter
        filterDropdowns.get(0).click();
        List<WebElement> dropdownOptionTitles = findElements(dropdownOptionTitleSelectors);
        List<WebElement> dropdownOptionCheckboxes = findElements(dropdownOptionCheckboxSelectors);
        for (int i=0; i<dropdownOptionTitles.size() && !filterOptionFound; i++){
            if (dropdownOptionTitles.get(i).getText().equalsIgnoreCase(filters[8])){
                filterOptionFound = true;
                if (!dropdownOptionCheckboxes.get(i).isSelected()){
                    filtersMatch = false;
                    System.out.println("Desired turnover filter " + filters[8] + " is not selected.");
                }
            }
        }
        if (!filterOptionFound){
            filtersMatch = false;
            System.out.println("Desired turnover filter " + filters[8] + " is not listed in the dropdown.");
        }
        filterDropdowns.get(0).click();

        // verifying type filter
        filterOptionFound = false;
        filterDropdowns.get(1).click();
        dropdownOptionTitles = findElements(dropdownOptionTitleSelectors);
        dropdownOptionCheckboxes = findElements(dropdownOptionCheckboxSelectors);
        for (int i=0; i<dropdownOptionTitles.size() && !filterOptionFound; i++){
            if (dropdownOptionTitles.get(i).getText().equalsIgnoreCase(filters[9])){
                filterOptionFound = true;
                if (!dropdownOptionCheckboxes.get(i).isSelected()){
                    filtersMatch = false;
                    System.out.println("Desired type filter " + filters[9] + " is not selected.");
                }
            }
        }
        if (!filterOptionFound){
            filtersMatch = false;
            System.out.println("Desired type filter " + filters[9] + " is not listed in the dropdown.");
        }
        filterDropdowns.get(1).click();

        // verifying style filter
        filterOptionFound = false;
        filterDropdowns.get(2).click();
        dropdownOptionTitles = findElements(dropdownOptionTitleSelectors);
        dropdownOptionCheckboxes = findElements(dropdownOptionCheckboxSelectors);
        for (int i=0; i<dropdownOptionTitles.size() && !filterOptionFound; i++){
            if (dropdownOptionTitles.get(i).getText().equalsIgnoreCase(filters[10])){
                filterOptionFound = true;
                if (!dropdownOptionCheckboxes.get(i).isSelected()){
                    filtersMatch = false;
                    System.out.println("Desired style filter " + filters[10] + " is not selected.");
                }
            }
        }
        if (!filterOptionFound){
            filtersMatch = false;
            System.out.println("Desired style filter " + filters[10] + " is not listed in the dropdown.");
        }
        filterDropdowns.get(2).click();

        // verifying ownership filter
        if (!findElement(ownershipFilter).getText().equalsIgnoreCase(filters[11])){
            filtersMatch = false;
            System.out.println("Ownership filter "+findElement(ownershipFilter).getText()+" does not equal desired value "+filters[11]);
        }

        // verifying ownership in my stock filter
        List<WebElement> ownershipStockFilters = findElements(ownershipStockSelectors);
        if (filters[12].equalsIgnoreCase("Underweight")){
            if (!ownershipStockFilters.get(0).isSelected()){
                filtersMatch = false;
                System.out.println("Desired Ownership in My Stock filter 'Underweight' is not selected.");
            }
        }
        else if (filters[12].equalsIgnoreCase("Overweight")){
            if (!ownershipStockFilters.get(1).isSelected()){
                filtersMatch = false;
                System.out.println("Desired Ownership in My Stock filter 'Overweight' is not selected.");
            }
        }
        else {
            if (!ownershipStockFilters.get(2).isSelected()){
                filtersMatch = false;
                System.out.println("Desired Ownership in My Stock filter 'All' is not selected.");
            }
        }

        // verifying sector activity filter
        List<WebElement> sectorActivityFilters = findElements(sectorActivitySelectors);
        if (filters[13].equalsIgnoreCase("Net Buyer")){
            if (!sectorActivityFilters.get(0).isSelected()){
                filtersMatch = false;
                System.out.println("Desired Sector Activity filter 'Net Buyer' is not selected.");
            }
        }
        else if (filters[13].equalsIgnoreCase("Net Seller")){
            if (!sectorActivityFilters.get(1).isSelected()){
                filtersMatch = false;
                System.out.println("Desired Sector Activity filter 'Net Seller' is not selected.");
            }
        }
        else {
            if (!sectorActivityFilters.get(2).isSelected()){
                filtersMatch = false;
                System.out.println("Desired Sector Activity filter 'All' is not selected.");
            }
        }

        // verifying peer activity filter
        List<WebElement> peerActivityFilters = findElements(peerActivitySelectors);
        if (filters[14].equalsIgnoreCase("Net Buyer")){
            if (!peerActivityFilters.get(0).isSelected()){
                filtersMatch = false;
                System.out.println("Desired Peer Activity filter 'Net Buyer' is not selected.");
            }
        }
        else if (filters[14].equalsIgnoreCase("Net Seller")){
            if (!peerActivityFilters.get(1).isSelected()){
                filtersMatch = false;
                System.out.println("Desired Peer Activity filter 'Net Seller' is not selected.");
            }
        }
        else {
            if (!peerActivityFilters.get(2).isSelected()){
                filtersMatch = false;
                System.out.println("Desired Peer Activity filter 'All' is not selected.");
            }
        }

        // verifying exclude activists filter
        if (filters[15].equalsIgnoreCase("Yes")){
            if (!findElement(excludeActivistsCheckbox).isSelected()){
                filtersMatch = false;
                System.out.println("Desired filter 'Exclude Activists' is not selected.");
            }
        }
        else {
            if (findElement(excludeActivistsCheckbox).isSelected()){
                filtersMatch = false;
                System.out.println("Undesired filter 'Exclude Activists' is selected.");
            }
        }

        // verifying logged activity filter
        if (filters[16].equalsIgnoreCase("Yes")){
            if (!findElement(loggedActivityCheckbox).isSelected()){
                filtersMatch = false;
                System.out.println("Desired filter 'Logged Activity' is not selected.");
            }
        }
        else {
            if (findElement(loggedActivityCheckbox).isSelected()){
                filtersMatch = false;
                System.out.println("Undesired filter 'Logged Activity' is selected.");
            }
        }

        return filtersMatch;
    }

}
