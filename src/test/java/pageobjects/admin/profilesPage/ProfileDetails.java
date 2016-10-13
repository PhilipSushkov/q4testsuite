package pageobjects.admin.profilesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-10-11.
 */
public class ProfileDetails extends AbstractPageObject{
    private final By editProfileButton = By.cssSelector(".page-header .action-buttons .edit");
    private final By firstNameField = By.name("firstName");
    private final By saveButton = By.cssSelector(".button.button-yellow");
    private final By loadingSpinner = By.className("outer-spinner-container");
    private final By estimatesToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[1]/td[2]/span/p-inputswitch");
    private final By expectedRangesToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[2]/td[2]/span/p-inputswitch");
    private final By sentimentToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[3]/td[2]/span/p-inputswitch");
    private final By volatilityToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[4]/td[2]/span/p-inputswitch");
    private final By activismToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[5]/td[2]/span/p-inputswitch");
    private final By eventsToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[6]/td[2]/span/p-inputswitch");
    private final By webcastToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[7]/td[2]/span/p-inputswitch");
    private final By websiteToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[8]/td[2]/span/p-inputswitch");
    private final By tradingAnalyticsToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[9]/td[2]/span/p-inputswitch");
    private final By relativePerformaceToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[10]/td[2]/span/p-inputswitch");
    private final By surveillanceToggle = By.xpath("/html/body/q4-app/div/div/q2-profile-details/q4-subscription/q4-subscription-services/p-datatable/div/div/table/tbody/tr[11]/td[2]/span/p-inputswitch");

    public ProfileDetails(WebDriver driver) {
        super(driver);
    }

    public ProfileDetails editProfileFirstName(String firstName) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        findElement(editProfileButton).click();
        findElement(firstNameField).clear();
        findElement(firstNameField).sendKeys(firstName);
        findElement(saveButton).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));

        return this;
    }

    public ProfileDetails disableEstimates() {
        toggleOff(estimatesToggle);

        return this;
    }

    public ProfileDetails enabledExpectedRanges() {
        toggleOn(expectedRangesToggle);

        return this;
    }

    public ProfileDetails enableSentiment() {
        toggleOn(sentimentToggle);

        return this;
    }

    public ProfileDetails enabledVolatility() {
        toggleOn(volatilityToggle);

        return this;
    }

    public ProfileDetails enabledActivism() {
        toggleOn(activismToggle);

        return this;
    }

    public ProfileDetails enabledEvents() {
        toggleOn(eventsToggle);

        return this;
    }

    public ProfileDetails enableWebcast() {
        toggleOn(webcastToggle);

        return this;
    }

    public ProfileDetails enabledWebsite() {
        toggleOn(websiteToggle);

        return this;
    }

    public ProfileDetails enabledTradingAnalytics() {
        toggleOn(tradingAnalyticsToggle);

        return this;
    }

    public ProfileDetails enableRelativePerformance() {
        toggleOn(relativePerformaceToggle);

        return this;
    }

    public ProfileDetails enableSurveillange() {
        toggleOn(surveillanceToggle);

        return this;
    }
}
