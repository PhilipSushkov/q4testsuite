package pageobjects.admin.companyPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-16.
 */
public class CompanyPage extends AbstractPageObject {
    private final By addCompanyButton = By.cssSelector(".page-header .action-buttons .add");
    private final By companyField = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-autocomplete.auto-complete-search .ui-inputtext");
    private final By saveButton = By.cssSelector(".button-yellow");

    public CompanyPage(WebDriver driver) {
        super(driver);
    }

    public CompanyPage addNewCompany() {
        findElement(addCompanyButton).click();
        findElement(companyField).sendKeys("Microsoft");
        findElement(saveButton).click();

        return this;
    }
}
