package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.dashboardPage.Dashboard;

/**
 * Created by patrickp on 2016-08-10.
 */
public class CreateBriefingBookModal extends AbstractPageObject {

    private final By cancelBriefingButton = By.cssSelector(".report-create .cancel-button");
    private final By closeBriefingBookIcon = By.cssSelector(".report-create.x-floating .close-button");
    private final By nameField = By.name("title");
    private final By saveButton = By.cssSelector(".q4-form .form-button.yellow");

    public CreateBriefingBookModal(WebDriver driver) {
        super(driver);
    }

    public Dashboard cancelCreateBriefingBookModal() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelBriefingButton));
        findElement(cancelBriefingButton).click();

        return new Dashboard(getDriver());
    }

    public Dashboard dismissCreateBriefingBookModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeBriefingBookIcon));
        findElement(closeBriefingBookIcon).click();

        return new Dashboard(getDriver());
    }

    public BriefingBookList saveBriefingBook(String briefingBookName) {
        findElement(nameField).sendKeys(briefingBookName);
        findElement(saveButton).click();

        return new BriefingBookList(getDriver());
    }
}
