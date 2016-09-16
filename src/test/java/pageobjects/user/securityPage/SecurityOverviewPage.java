package pageobjects.user.securityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.user.watchlist.WatchlistPage;

/**
 * Created by patrickp on 2016-08-04.
 */
public class SecurityOverviewPage extends WatchlistPage {
    private final By companyName = By.className("company-name");

    public SecurityOverviewPage(WebDriver driver) {
        super(driver);
    }

    public String getCompanyName() {
        return findElement(companyName).getText().replaceAll("\\p{P}", "");
    }
}
