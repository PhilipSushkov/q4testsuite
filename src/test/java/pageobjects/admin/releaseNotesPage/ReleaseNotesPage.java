package pageobjects.admin.releaseNotesPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import pageobjects.AbstractPageObject;

/**
 * Created by dannyl on 2017-08-03.
 */

// This pageobject includes the details page of release notes
public class ReleaseNotesPage extends AbstractPageObject{

    private final By addButton = By.xpath("//button[contains(@class, 'square-button add')]");
    private final By editButton = By.xpath("//button[contains(text(), 'Edit')]");
    private final By firstReleaseNote = By.xpath("//tr[contains(@class, 'ui-widget-content ui-datatable-even')][1]");
    private final By searchField = By.xpath("//input[contains(@type, 'search')]");
    private final By deleteButton = By.xpath("//button[contains(@class, 'remove')]");
    private final By editReleaseNoteTitle = By.xpath("//div[contains(@class, 'page-title')]");
    private final By releaseNoteTitle = By.xpath("//div[contains(@class, 'detail-column')]/span");

    public ReleaseNotesPage (WebDriver driver) {super(driver);}

    public CreateReleaseNotesPage goToCreateReleaseNotePage(){

        waitForElementToBeClickable(addButton).click();
        return new CreateReleaseNotesPage(driver);
    }

    public ReleaseNotesPage openReleaseNoteEdit (String title){

        searchForNote(title);
        waitForLoadingScreen();
        waitForElementToBeClickable(editButton).click();
        return this;
    }

    public String checkCanDeleteReleaseNote (String title){

        try {
            searchForNote(title);
        }
        catch (Exception e)
        {
            return null;
        }
        return getTitleOnDetailsPage();
    }

    public String getTitleOnDetailsPage (){
        waitForLoadingScreen();
        return waitForElementToAppear(editReleaseNoteTitle).getText();
    }

    public String getTitle(){
        waitForLoadingScreen();
        return waitForElementToBeClickable(releaseNoteTitle).getText();
    }

    public ReleaseNotesPage searchForNote (String title){
        waitForElementToBeClickable(searchField).sendKeys(title);
        waitForLoadingScreen();
        waitForElementToBeClickable(firstReleaseNote).click();

        return this;
    }

}
