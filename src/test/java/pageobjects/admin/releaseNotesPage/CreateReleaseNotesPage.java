package pageobjects.admin.releaseNotesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by dannyl on 2017-08-03.
 */
public class CreateReleaseNotesPage extends AbstractPageObject {

    private final By versionInput = By.xpath("//input[contains(@id, 'version')]");
    private final By titleInput = By.xpath("//input[contains(@id, 'title')]");
    private final By releaseDateInput = By.xpath("//span[contains(@class, 'ui-calendar')]");
    private final By overviewInput = By.xpath("//div[contains(@class,'ql-editor ql-blank')]");
    private final By confirmationMessage = By.xpath("//h2[contains(text(), 'Created')]");
    private final By closeConfirmationMessageButton = By.xpath("//div[contains(@class, 'ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-top')]/a[contains(@class, 'ui-dialog-titlebar-icon ui-dialog-titlebar-close ui-corner-all')]");
    private final By publishButton = By.xpath("//button[contains(text(), 'Publish')]");
    private final By saveButton = By.xpath("//button[contains(text(), 'Save')]");
    private final By deleteButton = By.xpath("//button[contains(@class, 'remove')]");
    private final By modalDeleteButton = By.xpath("//button[contains(text(), 'Confirm')]");
    private final By modalCancelButton = By.xpath("//button[contains(@class, 'button button-no-background')]");

    public CreateReleaseNotesPage(WebDriver driver){super(driver);}

    public CreateReleaseNotesPage createReleaseNote (String version, String title, String releaseDay, String overview){
        waitForElementToAppear(versionInput).sendKeys(version);
        waitForElementToAppear(titleInput).sendKeys(title);
        waitForElementToAppear(releaseDateInput).click();
        waitForElementToAppear(By.xpath("//a[contains(@class, 'ui-state-default')][contains(text(), '"+ releaseDay +"')]")).click();
        waitForElementToAppear(overviewInput).sendKeys(overview);
        waitForElementToBeClickable(saveButton).click();
        try
        {
        waitForElementToAppear(closeConfirmationMessageButton).click();
        }
        catch (TimeoutException e){
            System.out.println("No confirmation modal appeared.");
        }
        return this;
    }


    public ReleaseNotesPage deleteReleaseNote (String title){
        new ReleaseNotesPage(driver)
                .navigateToReleaseNotesPage()
                .openReleaseNoteEdit(title);
        waitForElementToBeClickable(deleteButton).click();
        waitForElementToBeClickable(modalDeleteButton).click();

        return new ReleaseNotesPage(driver);
    }

    public ReleaseNotesPage cancelDeleteReleaseNote (String title){
        new ReleaseNotesPage(driver)
                .navigateToReleaseNotesPage()
                .openReleaseNoteEdit(title);
        waitForElementToBeClickable(deleteButton).click();
        waitForElementToBeClickable(modalCancelButton).click();

        return new ReleaseNotesPage(driver);
    }

    public boolean checkCanSaveReleaseNote (String title){
        new ReleaseNotesPage(driver).navigateToReleaseNotesPage().openReleaseNoteEdit(title);
        try
        {
        waitForElementToAppear(publishButton);
        }
        catch (ElementNotFoundException e){
            return false;
        }
        return true;
    }
}
