package pageobjects.user.reportBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.dashboardPage.Dashboard;

/**
 * Created by patrickp on 2016-08-10.
 */
public class BuildReportModal extends AbstractPageObject {

    private final By cancelReportButton = By.cssSelector(".report-create .cancel-button");
    private final By closeReportIcon = By.cssSelector(".report-create.x-floating .close-button");

    public BuildReportModal(WebDriver driver) {
        super(driver);
    }

    public Dashboard cancelBuildReportModal() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelReportButton));
        findElement(cancelReportButton).click();

        return new Dashboard(getDriver());
    }

    public Dashboard dismissBuildReportModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeReportIcon));
        findElement(closeReportIcon).click();

        return new Dashboard(getDriver());
    }
}
