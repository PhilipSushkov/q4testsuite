package specs.user.securityDetails;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOwnershipPage;
import specs.AbstractSpec;
import static org.hamcrest.CoreMatchers.containsString;

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
        // checking that the date tabs indicate the end dates of the last four quarters
        Assert.assertTrue("The displayed date options are incorrect.", securityOwnershipPage.dateOptionsAreValid());
        // checking that the "As of" text is correct when each date option is selected
        Assert.assertTrue("As of statement is incorrect when switching through date options.", securityOwnershipPage.asOfDateIsValid());
    }

    @Test
    public void topBuyersAndSellersListIsValid(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // checking top buyers and sellers list while Surveillance tab is selected
        Assert.assertTrue("Top buyers list contains negative numbers.", securityOwnershipPage.topBuyersListIsPositive());
        Assert.assertTrue("Top sellers list contains positive numbers.", securityOwnershipPage.topSellersListIsNegative());
        Assert.assertTrue("Top buyers list is not in descending order.", securityOwnershipPage.topBuyersListIsDescending());
        Assert.assertTrue("Top sellers list is not in ascending order.", securityOwnershipPage.topSellersListIsAscending());
        Assert.assertTrue("Top buyers and sellers list contains duplicates.", securityOwnershipPage.topBuyersAndSellersAreUnique());
        // checking top buyers and sellers list while each date option is selected
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
        // checking that 10 holders are initially shown and that the table can be sorted
        Assert.assertEquals("Initial number of holders displayed is incorrect", 10, securityOwnershipPage.getNumOfHoldersDisplayed());
        Assert.assertTrue("Sorting the holders table does not work.", securityOwnershipPage.canSortHoldersTable());
        // clicking show more and checking that 20 holders are now shown and that the table can still be sorted
        Assert.assertEquals("Number of holders displayed after clicking 'Show more' is incorrect", 20, securityOwnershipPage.showMoreHolders().getNumOfHoldersDisplayed());
        Assert.assertTrue("Sorting the holders table does not work after showing more holders.", securityOwnershipPage.canSortHoldersTable());
    }

    @Test
    public void holderTableColouringIsCorrect(){
        // Checks that all values in the 1Q CHG and MKT VAL CHG columns are green if positive, red if negative, or dark grey if zero
        Assert.assertTrue("One or more change values in the Holders table is not coloured correctly",
                new SecurityOwnershipPage(driver).holderTableChangeValueColouringIsCorrect());
    }

    @Test
    public void canSeeActivistHolders(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        //store holders
        String[] holders = securityOwnershipPage.getHolderNames();
        //select activist filter and check that all have red flag
        securityOwnershipPage.showOnlyActivists();
        Assert.assertEquals("Known issue - DESKTOP-7285 - Not all filtered entries have activist icon", securityOwnershipPage.getNumOfHoldersDisplayed(), securityOwnershipPage.getNumOfActivistsDisplayed());
        //unselect activist filter
        securityOwnershipPage.doNotShowOnlyActivists();
        //check that original list is displayed
        Assert.assertArrayEquals("Original list is not displayed after reverting activist filter", holders, securityOwnershipPage.getHolderNames());
    }

    @Test
    public void allFilteredActivistsAreActivists(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // store list of filtered holders
        String[] institutions = securityOwnershipPage.showOnlyActivists().getHolderNames();
        // for each holder, go to institution page and check that activist icon appears
        for (int i=0; i<institutions.length; i++){
            InstitutionPage institutionPage = securityOwnershipPage.goToInstitutionalHolder(i);
            Assert.assertThat("ERROR: incorrect institution page opened", institutionPage.getInstitutionName(), containsString(institutions[i]));
            Assert.assertTrue("Known issue - DESKTOP-7281 - Institution "+institutions[i]+" is not an activist.", institutionPage.isActivist());
            institutionPage.accessSideNavFromPage().selectOwnershipFromSideNav().showOnlyActivists();
        }
    }

}
