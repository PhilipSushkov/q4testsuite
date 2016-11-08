package specs.user.Security;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;

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

    /**   Combines Test Cases C490 -> C492    */

    public void companyDataCorrect()
    {

        SecurityOverviewPage test = new SecurityOverviewPage(driver);
        String[] companyData = {"Sysco Corp", "SYY", "Food Distributors\nNYSE"};

        Assert.assertEquals("Company Name '" + companyData[0] + "' Not Found:",companyData[0]
                ,test.getCompanyName());
        Assert.assertEquals("Company Ticker '" + companyData[1] + "' Not Found:",companyData[1]
                ,test.getCompanyTicker());
        Assert.assertEquals("Company Industry and Exchange Not Found: \n" + companyData[2],companyData[2]
                ,test.getIndustry_Exchange());

    }

    @Test

    /**    Test Case C493    */

    public void correctStockFormat() {

        SecurityOverviewPage test = new SecurityOverviewPage(driver);
        String stockQuote = test.getStockQuote();

        //To make it into a pure integer. If this fails, a number format exception would appear.
        Double.parseDouble(stockQuote.replace(".", ""));

        //Checking if it is indeed two decimal places
        Assert.assertEquals("Stock Quote does not have a decimal point where it should be (2 decimal places):" , "."
                ,stockQuote.substring((stockQuote.length() - 3), (stockQuote.length() - 2)));

        String stockChange = test.getStockChange();
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
            Assert.assertTrue(false);
        }

        //12 - 14 chars, depending if negative or positive.
        // -0.80 (-1.16%) /2 = 7  -> "("
        // -0.80 (1.16%) /2 = 6  -> " "
        // 0.80 (-1.16%) /2 = 6 -> "("
        // 0.80 (1.16%)  /2 = 6 -> "("

        Double.parseDouble(firstChange);
        double secondChangeNumber = Double.parseDouble(secondChange.replaceAll("[()%]", ""));
        //replace the chars within [ ]

        //All tests regarding format
        Assert.assertEquals(secondChange.substring(0, 1), "(");
        Assert.assertEquals(secondChange.substring(secondChange.length() - 1, secondChange.length()), ")");
        Assert.assertEquals(secondChange.substring(secondChange.length() - 2, secondChange.length() - 1), "%");

        //Checking change icon color (not arrow)
        if (secondChangeNumber > 0) {
            Assert.assertEquals(test.getChangeIconColor(), "462041131"); //These numbers are rgba in a single int val
        } else if (secondChangeNumber < 0) {
            Assert.assertEquals(test.getChangeIconColor(), "23175601"); //Formatted in getChangeIconColor method
        }

    }



    @Test

    /**     Test Case C494      */

    public void correctVolumeFormat()
    {
        SecurityOverviewPage test = new SecurityOverviewPage(driver);
        String volume = test.getVolume();
        String avgVolume = test.getAvgVolume();
        int volumeValue = Integer.parseInt(volume.replaceAll(",", ""));
        int avgVolumeValue = Integer.parseInt(avgVolume.replaceAll(",", ""));
        int volumeSize = volume.length();
        int avgVolumeSize = avgVolume.length();

        for (Double x = new Double(0); x < volumeSize; x++) //Checks if there is a comma for every 3 numbers for Volume
        {
            if (x%4 == 0 && x != 0) { //Checking the double is a whole number
                Assert.assertEquals(volume.substring(volumeSize - x.intValue(), volumeSize - (x.intValue() - 1)), ",");
            }
            else if (volume.substring(volumeSize - (x.intValue() + 1),volumeSize - x.intValue()) == ",") {
                Assert.assertFalse(true);
            }
        }

        for (Double x = new Double(0); x < avgVolumeSize; x++) //Same as above for avg Volume
        {
            if (x%4 == 0 && x != 0) { //Checking the double is a whole number
                Assert.assertEquals(avgVolume.substring(avgVolumeSize - x.intValue()
                        ,avgVolumeSize - (x.intValue() - 1)), ",");
            }
            else if (avgVolume.substring(avgVolumeSize - (x.intValue() + 1),avgVolumeSize - x.intValue()) == ",") {
               Assert.assertFalse(true);
            }
        }

        if (volumeValue < 0  || avgVolumeValue < 0 ) { //Checking for negatives
            Assert.assertTrue(false);
        }
    }

    @Test //Think of a better manner to execute tests that regard going to new pages.

    /**     Test Case C495 -> C496      */

    public void navigationFromOverview() {

        SecurityOverviewPage test = new SecurityOverviewPage(driver);

        test.clickDropdownLeftArrowOverview(); //Taken to same page, i.e:Nothing happens. If something does, test fails
        //The above doesnt seem ot work as expected, it doesnt clikc intended element

        test.clickDropdownMenu_Overview();

        Assert.assertTrue(test.dropdownMenuExists());

        test.clickDropdownOwnership();//Taken to same page, so nothing happens

        Assert.assertTrue(test.ownershipPageExists());

        test.goBackPages(1);

        Assert.assertTrue(test.overviewPageExists());

        test.clickDropdownMenu_Overview();

        Assert.assertTrue(test.dropdownMenuExists());

        test.offsetCompanyNameClick(457, 45); //537, 151 are the clicked coordinates, used to close the dropdown modal

        Assert.assertFalse(test.dropdownMenuExists());

    }





    @After
    public void disableDriver()
    {
        driver.close();
        driver.quit();
    }


}
