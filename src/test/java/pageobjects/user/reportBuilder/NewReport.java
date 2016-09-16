package pageobjects.user.reportBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-14.
 */
public class NewReport extends AbstractPageObject{
    private final By reportPageTitle = By.cssSelector(".report-page .report-title");

    public NewReport(WebDriver driver) {
        super(driver);
    }

    public String getReportPageTitle() {
        pause(5000L);
        return findElement(reportPageTitle).getText();
    }
}
