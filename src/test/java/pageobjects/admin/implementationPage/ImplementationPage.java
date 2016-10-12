package pageobjects.admin.implementationPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-10-12.
 */
public class ImplementationPage extends AbstractPageObject {
    private final By loading = By.className("outer-spinner-container");

    private final By addButton = By.cssSelector("body > q4-app > div > div > q4-implementation > header > div > div.action-buttons > button");
    private final By aliasField = By.name("alias");
    private final By sourceField = By.name("sourceURL");
    private final By saveButton = By.cssSelector("body > q4-app > div > div > q4-implementation > div.modal > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-implementation-create > div > button.button.button-yellow");
    private final By implementationList = By.cssSelector(".ui-datatable table");

    public ImplementationPage(WebDriver driver) {
        super(driver);
    }

    public ImplementationPage addNewImplementation(String alias, String sourceUrl) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loading));
        findElement(addButton).click();
        findElement(aliasField).sendKeys(alias);
        findElement(sourceField).sendKeys(sourceUrl);
        findElement(saveButton).click();

        return this;
    }

    public String getImplementationList() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loading));
        return findElement(implementationList).getText();
    }
}
