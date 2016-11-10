package pageobjects.admin.intelligencePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-11-09.
 */
public class ReportDetailsPage extends AbstractPageObject {
    private final By reportHeader = By.cssSelector(".page-header .page-title .details");

    public ReportDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getReportHeader() {
        waitForLoadingScreen();
        return findElement(reportHeader).getText();
    }
}
