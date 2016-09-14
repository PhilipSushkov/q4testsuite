package pageobjects.reportBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-13.
 */
public class ReportDetailsPage extends AbstractPageObject{
    public ReportDetailsPage(WebDriver driver) {
        super(driver);
    }
    private final By generateButton = By.xpath("//*[contains(text(), 'Generate')]");

    public ReportDetailsPage generateTearSheet() {
        findElement(generateButton).click();

        return this;
    }
}
