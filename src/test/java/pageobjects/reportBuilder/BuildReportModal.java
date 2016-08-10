package pageobjects.reportBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.dashboardPage.Dashboard;

/**
 * Created by patrickp on 2016-08-10.
 */
public class BuildReportModal extends AbstractPageObject {

    private final By cancelReportButton = By.cssSelector(".report-create .cancel-button");

    public BuildReportModal(WebDriver driver) {
        super(driver);
    }

    public Dashboard dismissBuildReportModal() {
        pause(2000L);
        wait.until(ExpectedConditions.elementToBeClickable(cancelReportButton));
        findElement(cancelReportButton).click();

        return new Dashboard(getDriver());
    }
}
