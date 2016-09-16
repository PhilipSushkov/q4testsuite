package pageobjects.user.reportBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-10.
 */

public class ReportBuilderPage extends AbstractPageObject {

    private final By reportList = By.id("ext-report-list-1");

    public ReportBuilderPage(WebDriver driver) {
        super(driver);
    }

    public String getReportList() {
        pause(500L);
        return findElement(reportList).getText();
    }
}
