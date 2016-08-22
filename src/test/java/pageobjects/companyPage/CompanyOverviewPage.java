package pageobjects.companyPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.dashboardPage.Dashboard;
import pageobjects.watchlist.WatchlistPage;

/**
 * Created by patrickp on 2016-08-04.
 */
public class CompanyOverviewPage extends WatchlistPage {
    private final By companyName = By.className("company-name");

    public CompanyOverviewPage(WebDriver driver) {
        super(driver);
    }

    public String getCompanyName() {
        return findElement(companyName).getText();
    }
}
