package specs.user.Security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * Created by kelvint on 11/2/16.
 */
public class Overview extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectSecurityFromSideNav();
    }

    @Test
    public void checkStockPrice() throws IOException {
        SecurityOverviewPage finish = new SecurityOverviewPage(driver);

        Stock stock = YahooFinance.get("SYY");
        float price = stock.getQuote().getPrice().floatValue();
        System.out.println(price);

        Assert.assertEquals(price, finish.getStockPrice(), 0.5);
    }

    @Test
    /**   Combines Test Cases C490 -> C492    */
    public void companyDataCorrect() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        String[] companyData = {"Sysco Corp", "SYY", "Food Distributors\nNYSE"};

        Assert.assertEquals(securityOverviewPage.getCompanyName(), companyData[0]);
        Assert.assertEquals(securityOverviewPage.getCompanyTicker(), companyData[1]);
        Assert.assertEquals(securityOverviewPage.getIndustry_Exchange(), companyData[2]);
    }

    @Test
    /**    Test Case C493    */
    public void correctStockFormat() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        String stockQuote = securityOverviewPage.getStockQuote();

        //To make it into a pure integer. If this fails, a number format exception would appear.
        Double.parseDouble(stockQuote.replace(".", ""));

        //Checking if it is indeed two decimal places
        Assert.assertEquals(stockQuote.substring((stockQuote.length() - 3), (stockQuote.length() - 2)), ".");


        String stockChange = securityOverviewPage.getStockChange();
        String firstChange = "";
        String secondChange = "";

        //See the chart below the if statements to understand the logic a bit better.
        if (stockChange.length() == 13 && stockChange.substring(0, 1).equals("-")) //The -> " " one
        {
            firstChange = stockChange.substring(0, 5);
            secondChange = stockChange.substring(6, 14);
        } else if (12 <= stockChange.length() && stockChange.length() <= 14) //The -> "(" ones
        {
            firstChange = stockChange.substring(0, stockChange.length() / 2 - 2);
            secondChange = stockChange.substring(stockChange.length() / 2 - 1, stockChange.length());
        } else if (stockChange.length() < 12 || 14 < stockChange.length()) //All other invalid cases
        {
            Assert.assertTrue(false); //tends to fail, check
        }

        //12 - 14 chars, depending if negative or positive.
        // -0.80 (-1.16%) /2 = 7  -> "("
        // -0.80 (1.16%) /2 = 6  -> " "
        // 0.80 (-1.16%) /2 = 6 -> "("
        // 0.80 (1.16%)  /2 = 6 -> "("

        Double.parseDouble(firstChange);
        double secondChangeNumber = Double.parseDouble(secondChange.replaceAll("[()%]", ""));
        //replace the chars within [ ]

        Assert.assertEquals(secondChange.substring(0, 1), "(");
        Assert.assertEquals(secondChange.substring(secondChange.length() - 1, secondChange.length()), ")");
        Assert.assertEquals(secondChange.substring(secondChange.length() - 2, secondChange.length() - 1), "%");

        //Checking change icon color (not arrow)
        if (secondChangeNumber > 0) {
            Assert.assertEquals(securityOverviewPage.getChangeIconColor(), "462041131"); //These numbers are rgba in a single int val
        } else if (secondChangeNumber < 0) {
            Assert.assertEquals(securityOverviewPage.getChangeIconColor(), "23175601"); //Formatted in getChangeIconColor method
        }
    }

    @Test
    /**Test Case C494*/
    public void correctVolumeFormat() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        String volume = securityOverviewPage.getVolume();
        String avgVolume = securityOverviewPage.getAvgVolume();

        int volumeValue = Integer.parseInt(volume.replaceAll(",", ""));
        int avgVolumeValue = Integer.parseInt(avgVolume.replaceAll(",", ""));

        int volumeSize = volume.length();
        int avgVolumeSize = avgVolume.length();


        for (Double x = new Double(0); x < volumeSize; x++) //Checks if there is a comma for every 3 numbers for Volume a
        {
            if (x%4 == 0 && x != 0) { //Checking the double is a whole number
                Assert.assertEquals(volume.substring(volumeSize - x.intValue(), volumeSize - (x.intValue() - 1)), ",");
            }
            else if (volume.substring(volumeSize - (x.intValue() + 1),volumeSize - x.intValue()) == ",") {
                Assert.assertFalse(true);
            }
        }

        for (Double x = new Double(0); x < avgVolumeSize; x++) //Checks if there is a comma for every 3 numbers for avg Volume
        {
            if (x%4 == 0 && x != 0) { //Checking the double is a whole number
                Assert.assertEquals(avgVolume.substring(avgVolumeSize - x.intValue(), avgVolumeSize - (x.intValue() - 1)), ",");
            }
            else if (avgVolume.substring(avgVolumeSize - (x.intValue() + 1),avgVolumeSize - x.intValue()) == ",") {
               Assert.assertFalse(true);
            }
        }

        if (volumeValue < 0  || avgVolumeValue < 0 ) { //Checking for negatives
            Assert.assertTrue(false);
        }
    }

    @Ignore
    @Test
    /**Test Case C495*/
    public void navigationDropdownMenu() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver).clickDropdownLeftArrowOverview()
                .clickViewDropdownMenu();

        Assert.assertTrue(securityOverviewPage.dropdownMenuExists());

        securityOverviewPage.clickDropdownOwnership();//Taken to same page, so nothing happens

        Assert.assertTrue(securityOverviewPage.ownershipPageExists());

        securityOverviewPage.goBackPage();

        Assert.assertTrue(securityOverviewPage.OverviewPageExists());

        securityOverviewPage.clickViewDropdownMenu();

        Assert.assertTrue(securityOverviewPage.dropdownMenuExists());

        securityOverviewPage.offsetCompanyNameClick(457, 45); //537, 151

        Assert.assertFalse(securityOverviewPage.dropdownMenuExists());
    }
}
