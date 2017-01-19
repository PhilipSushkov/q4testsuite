package specs.admin.morningCoffee;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.morningCoffeePage.Market;
import pageobjects.admin.morningCoffeePage.MorningCoffeePage;
import pageobjects.admin.morningCoffeePage.Sector;
import specs.AdminAbstractSpec;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        Assert.assertTrue("Record not found",morningCoffeePage.recentReportExists(symbol)); //current Date is used to check that is the report from today, no other way to identify a unique report.

    }

    @Test
    public void canCancelAReportGeneration(){
        String symbol ="NFLX";
        Date currentDate = new Date();
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCancelReport();
        Assert.assertFalse("Record not found",morningCoffeePage.recentReportExists(symbol));
    }

    @Test
    public void canPreviewNewlyCreatedReport(){
        String symbol ="NFLX";
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCreateReport().clickRecentReport(symbol);
    }

    @Test
    public void ownerCanBeSortedAscending() {
        String symbol ="YUM";
        DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy hh:mm:ss");
        Date dateOfDeletedReport = new Date();
        dateFormat.format(dateOfDeletedReport);

        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
       dateOfDeletedReport= morningCoffeePage.recentReportDate(symbol);
      System.out.print(morningCoffeePage.confirmReportDelete(symbol,dateOfDeletedReport));
       //morningCoffeePage.clickRecentReport(symbol).confirmDelete().confirmReportDelete(symbol,dateOfDeletedReport);
    }






}
