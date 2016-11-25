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

}
