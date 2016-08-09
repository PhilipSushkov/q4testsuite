package pageobjects.watchlist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-05.
 */
public class Watchlist extends AbstractPageObject{

    private final By pageTitle = By.cssSelector(".watchlist-header .page-title");

    public Watchlist(WebDriver driver) {
        super(driver);
    }

    public String getWatchlistPageTitle() {

        return findElement(pageTitle).getText();
    }
}