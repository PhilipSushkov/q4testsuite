package pageobjects.myQ4TeamPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-16.
 */
public class MyQ4TeamPage extends AbstractPageObject{

    private final By pageTitle = By.cssSelector(".page-header");

    public MyQ4TeamPage(WebDriver driver) {
        super(driver);
    }

    public String getMyQ4TeamPageTitle() {
        return findElement(pageTitle).getText();
    }
}
