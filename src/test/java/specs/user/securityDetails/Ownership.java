package specs.user.securityDetails;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOwnershipPage;
import specs.AbstractSpec;

/**
 * Created by kelvint on 11/2/16.
 */
public class Ownership extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectOwnershipFromSideNav();
    }

    @Test
    public void dateOptionsWorkCorrectly(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("The displayed date options are incorrect.", securityOwnershipPage.dateOptionsAreValid());
        Assert.assertTrue("As of statement is incorrect when switching through date options.", securityOwnershipPage.asOfDateIsValid());
    }

    @Test
    public void topBuyersAndSellersListIsValid(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Top buyers list contains negative numbers.", securityOwnershipPage.topBuyersListIsPositive());
        Assert.assertTrue("Top sellers list contains positive numbers.", securityOwnershipPage.topSellersListIsNegative());
        Assert.assertTrue("Top buyers list is not in descending order.", securityOwnershipPage.topBuyersListIsDescending());
        Assert.assertTrue("Top sellers list is not in ascending order.", securityOwnershipPage.topSellersListIsAscending());
        Assert.assertTrue("Top buyers and sellers list contains duplicates.", securityOwnershipPage.topBuyersAndSellersAreUnique());
        for (int i=0; i<4; i++){
            securityOwnershipPage.selectDate(i);
            Assert.assertTrue("Top buyers list contains negative numbers while in date option"+(i+1), securityOwnershipPage.topBuyersListIsPositive());
            Assert.assertTrue("Top sellers list contains positive numbers while in date option"+(i+1), securityOwnershipPage.topSellersListIsNegative());
            Assert.assertTrue("Top buyers list is not in descending order while in date option"+(i+1), securityOwnershipPage.topBuyersListIsDescending());
            Assert.assertTrue("Top sellers list is not in ascending order while in date option"+(i+1), securityOwnershipPage.topSellersListIsAscending());
            Assert.assertTrue("Top buyers and sellers list contains duplicates while in date option"+(i+1), securityOwnershipPage.topBuyersAndSellersAreUnique());
        }
    }

    @Test
    public void canSortAndShowMoreHolders(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertEquals("Initial number of holders displayed is incorrect", 10, securityOwnershipPage.getNumOfHoldersDisplayed());
        Assert.assertTrue("Sorting the holders table does not work.", securityOwnershipPage.canSortHoldersTable());
        Assert.assertEquals("Number of holders displayed after clicking 'Show more' is incorrect", 20, securityOwnershipPage.showMoreHolders().getNumOfHoldersDisplayed());
        Assert.assertTrue("Sorting the holders table does not work after showing more holders.", securityOwnershipPage.canSortHoldersTable());
    }

    @Test
    public void holderTableColouringIsCorrect(){
        // Checks that all values in the 1Q CHG and MKT VAL CHG columns are green if positive, red if negative, or dark grey if zero
        Assert.assertTrue("One or more change values in the Holders table is not coloured correctly",
                new SecurityOwnershipPage(driver).holderTableChangeValueColouringIsCorrect());
    }

}
