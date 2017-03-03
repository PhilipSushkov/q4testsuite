package specs.admin.morningCoffee;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.morningCoffeePage.MorningCoffeePage;
import specs.AdminAbstractSpec;

import java.util.Date;

/**
 * Created by noelc on 2017-01-16.
 */
public class morningCoffeeReport extends AdminAbstractSpec {


    @Before
    public void setUp() {
        new AdminLoginPage(driver).loginAdmin()
                .navigateToMorningCoffeePage();
    }

    @Test
    public void canCreateReport(){
        String symbol ="AAPL";
        Date currentDate = new Date();
       MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
       morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCreateReport();
        Assert.assertTrue("Record not found",morningCoffeePage.recentReportExists(symbol,currentDate)); //current Date is used to check that is the report from today, no other way to identify a unique report.

    }

    @Test
    public void canCancelAReportGeneration(){
        String symbol ="NFLX";
        Date currentDate = new Date();
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCancelReport();
        Assert.assertFalse("Record was found",morningCoffeePage.recentReportExists(symbol,currentDate));
    }

    @Test
    public void canPreviewNewlyCreatedReport(){
        String symbol ="YUM";
        Date currentDate = new Date();
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCreateReport().clickRecentReport(symbol, currentDate);
    }

    @Test
    public void ownerCanBeSortedAscending(){
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
       Assert.assertTrue("Not sorted", morningCoffeePage.clickOwnerHeader().isOwnerSortedAscending());
    }





}
