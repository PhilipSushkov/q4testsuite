package pageobjects.reportBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-10.
 */

public class ReportBuilderPage extends AbstractPageObject {

    private final By pageTitle = By.cssSelector(".page-header");

    public ReportBuilderPage(WebDriver driver) {
        super(driver);
    }

    public String getReportBuilderPageTitle() {
        return findElement(pageTitle).getText();
    }
}
