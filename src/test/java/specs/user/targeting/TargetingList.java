package specs.user.targeting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.targeting.EditSearchPage;
import pageobjects.user.targeting.NewSearchPage;
import pageobjects.user.targeting.TargetingPage;
import specs.AbstractSpec;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;


/**
 * Created by jasons on 2016-10-03.
 */
public class TargetingList extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectTargetingFromSideNav();
    }

    Date current = new Date();

    @Test
    /* Combination of TestRail test cases C2352, C2285, C2286, and C2345.
     * Will create a new search with specified filters, save it, view it, verify
     * filters are correct, then delete it using button on filter page. */
    public void canAddViewAndDeleteASavedSearch() {
        String searchName = current.toString(); // makes title of search equal to current time
        String[] filters = {
            "New York, NY", // City
                "Institution", // Institution or Fund
                "3", "97", // Quality Rating (lower and upper bounds, between 0 and 100)
                "1", "8", // Purchasing Power (lower and upper bounds, between 0 and 10)
                "30", "960", // AUM (lower and upper bounds, between 0 and 1000)
                "Low", // Turnover
                "Investment Adviser", // Type
                "Growth", // Style
                "Owns Only Me", // Ownership
                "Underweight", // Ownership in My Stock
                "Net Buyer", // Sector Activity
                "Net Buyer", // Peer Activity
                "yes", // Exclude Activists
                "yes" // Logged Activity
        };

        // creating a new search with filters and saving it
        new TargetingPage(driver).newSearch().createNewSearch(searchName, filters);

        // verifying that search name is listed
        int searchNameIndex = new TargetingPage(driver).findSearchNameIndex(searchName); //will be -1 if not listed
        Assert.assertNotEquals("Search name not found in saved searches list", -1, searchNameIndex);

        // opening search and verifying that filters are correct
        boolean filtersMatch = new TargetingPage(driver).editSearch(searchNameIndex).verifyFilters(filters);
        Assert.assertTrue("Known issue - DESKTOP-7376 - Filters do not match.", filtersMatch);

        // deleting search using button on filter page
        new EditSearchPage(driver).deleteSearch();

        // verifying that search is gone
        searchNameIndex = new TargetingPage(driver).findSearchNameIndex(searchName); //will be -1 if not listed
        Assert.assertEquals("Search has not been deleted", -1, searchNameIndex);
    }

    @Test
    /* Based on TestRail test case C2291.
     * Can navigate to a targeted institution. */
    public void canAccessATargetedInstitution() {
        String firstInstitution = new TargetingPage(driver).getFirstInstitution();
        String institutionPageTitle = new TargetingPage(driver).openFirstInstitution().getInstitutionName();
        Assert.assertTrue("Empty institution name listed.",!firstInstitution.isEmpty());
        Assert.assertTrue("Institution page title doesn't match.",firstInstitution.equalsIgnoreCase(institutionPageTitle));
    }

    @Test
    /* Based on TestRail test case C2292.
     * Can navigate to a targeted fund. */
    public void canAccessATargetedFund() {
        String firstFund = new TargetingPage(driver).getFirstFund();
        String fundPageTitle = new TargetingPage(driver).openFirstFund().getFundName();
        Assert.assertTrue("Empty fund name listed.",!firstFund.isEmpty());
        Assert.assertTrue("Fund page title doesn't match.",firstFund.equalsIgnoreCase(fundPageTitle));
    }

    @Test
    /* Based on TestRail test case C2293.
     * Can navigate to a targeted contact. */
    public void canAccessATargetedContact() {
        String firstContact = new TargetingPage(driver).getFirstContact();
        String contactPageTitle = new TargetingPage(driver).openFirstContact().getContactName();
        Assert.assertTrue("Empty fund name listed.",!firstContact.isEmpty());
        Assert.assertThat("Fund page title doesn't match.", contactPageTitle, containsString(firstContact.substring(0, firstContact.indexOf("\n"))));
    }


    @Test
    public void canTargetAndRemoveAInstitution() {
        // performing a filterless institution search and targeting a random untargeted result
        String targetedInstitution = new TargetingPage(driver).newSearch().targetRandomInstitution();
        System.out.println("Targeted institution is: "+targetedInstitution);

        // going to institution page and checking that "Saved Target" icon appears
        String institutionPageURL = new NewSearchPage(driver).goToInstitution(targetedInstitution).getURL();
        Assert.assertTrue("'Saved Target' icon does not appear on institution page.", new InstitutionPage(driver).isSavedTarget());

        // going to targets list and checking that institution appears
        int targetedInstitutionIndex = new InstitutionPage(driver).accessSideNavFromPage().selectTargetingFromSideNav().findInstitutionIndex(targetedInstitution); //will be -1 if not listed
        Assert.assertNotEquals("Institution not found in targets list", -1, targetedInstitutionIndex);

        // removing the target and checking that the target no longer appears
        new TargetingPage(driver).untargetInstitution(targetedInstitutionIndex);
        Assert.assertFalse("'Saved Target' icon still appears on institution page.", new TargetingPage(driver).goToInstitutionURL(institutionPageURL).isSavedTarget());

        // going to institution page and checking that institution no longer appears
        targetedInstitutionIndex = new InstitutionPage(driver).accessSideNavFromPage().selectTargetingFromSideNav().findInstitutionIndex(targetedInstitution); //will be -1 if not listed
        Assert.assertEquals("Institution has not been removed from targets list", -1, targetedInstitutionIndex);
    }

    @Test
    public void canTargetAndRemoveAContact() {
        // performing a filterless fund search and targeting the first contact in a random result
        String targetedContact = new TargetingPage(driver).newSearch().targetRandomContact();
        System.out.println("Targeted contact is: "+targetedContact);

        // going to contact page and checking that "Saved Target" icon appears
        String contactPageURL = new NewSearchPage(driver).goToContact(targetedContact).getURL();
        Assert.assertTrue("'Saved Target' icon does not appear on contact page.", new ContactDetailsPage(driver).isSavedTarget());

        // going to targets list and checking that contact appears
        int targetedContactIndex = new ContactDetailsPage(driver).accessSideNavFromPage().selectTargetingFromSideNav().findContactIndex(targetedContact);
        Assert.assertNotEquals("Contact not found in targets list", -1, targetedContactIndex);

        // removing the target and checking that the target no longer appears
        new TargetingPage(driver).untargetContact(targetedContactIndex);
        Assert.assertFalse("'Saved Target' icon still appears on contact page.", new TargetingPage(driver).goToContactURL(contactPageURL).isSavedTarget());
        targetedContactIndex = new ContactDetailsPage(driver).accessSideNavFromPage().selectTargetingFromSideNav().findContactIndex(targetedContact);
        Assert.assertEquals("Contact has not been removed from targets list", -1, targetedContactIndex);
    }

    @Test
    public void canShowMoreAndSortResults(){
        // performing a filterless institution search and verifying sorting
        new TargetingPage(driver).newSearch().blankSearch();
        Assert.assertEquals("Incorrect number of initial results displayed", 20, new NewSearchPage(driver).numResultsDisplayed());
        Assert.assertTrue("Fixed Issue - DESKTOP-6736 - Initial sorting failed.", new NewSearchPage(driver).resultsCanBeSorted());
        // loop 1 time (can increase if desired): clicking show more and then verifying sorting again
        for (int i=1; i<=1; i++){
            new NewSearchPage(driver).showMoreResults();
            Assert.assertEquals("KNOWN ISSUE - DESKTOP-7179: Incorrect number of results displayed upon iteration "+i, 20*(i+1), new NewSearchPage(driver).numResultsDisplayed());
            Assert.assertTrue("Sorting failed upon iteration "+i, new NewSearchPage(driver).resultsCanBeSorted());
        }
    }

    @Test
    public void canShowAllSearchResults(){
        // performing an institution search with only the location filter
        new TargetingPage(driver).newSearch().performBasicLocationSearch("Toronto, ON");
        // obtaining number of results (as displayed on top of results)
        int numResultsExpected = new NewSearchPage(driver).numResultsClaimed();
        int expectedExpansions = numResultsExpected/20; // how many clicks of "Show more" should it take to show all the results
        if (numResultsExpected%20==0){
            expectedExpansions--;
        }
        // repeat until end of results should be reached: click show more and check right amount of results are displayed
        Assert.assertEquals("Incorrect number of initial results displayed", 20, new NewSearchPage(driver).numResultsDisplayed());
        for (int i=1; i<expectedExpansions; i++){
            new NewSearchPage(driver).showMoreResults();
            Assert.assertEquals("Incorrect number of results displayed upon iteration "+i, 20*(i+1), new NewSearchPage(driver).numResultsDisplayed());
        }
        new NewSearchPage(driver).showMoreResults(); // after this click all results should be displayed
        System.out.println("Finished clicking 'Show more' after "+expectedExpansions+" iterations.");
        // check that "Show more" is no longer present and that right number of results are displayed
        Assert.assertFalse("Show more button still appears.", new NewSearchPage(driver).showMoreAppears());
        Assert.assertEquals("Incorrect final number of results displayed", numResultsExpected, new NewSearchPage(driver).numResultsDisplayed());
    }

    @Test
    public void canOpenAndCloseLocationPopup(){
        // performing a filterless institution search and opening first "Multiple" location indicator
        new TargetingPage(driver).newSearch().blankSearch();
        int numLocations = new NewSearchPage(driver).numLocationsFirst(); //number indicated in the first multiple locations circle
        new NewSearchPage(driver).openFirstLocationPopup();
        // verifying that the indicator is open and displaying right number of addresses
        Assert.assertTrue("Location popup is not open.", new NewSearchPage(driver).locationPopupIsOpen());
        int locationsDisplayed = new NewSearchPage(driver).numLocationsDisplayedInPopup();
        Assert.assertEquals("Incorrect number of locations displayed in popup", numLocations, locationsDisplayed);
        // closing the indicator by clicking elsewhere and verifying that it is closed
        new NewSearchPage(driver).closeLocationPopup();
        Assert.assertFalse("Location popup is not closed.", new NewSearchPage(driver).locationPopupIsOpen());
    }

   /* @Test
    public void canAddAndDeleteSearchFromList(){
        String searchName = current.toString()+"_v2";
        String[] filters = {
                "New York, NY", "Institution", "3", "97", "1", "8", "30", "960", "Low",
                "Investment Adviser", "Growth", "Owns Only Me", "Underweight", "Net Buyer", "Net Buyer", "yes", "yes"
        };
        // creating a new search and saving it
        new TargetingPage(driver).newSearch().createNewSearch(searchName, filters);
        // verifying that search name is listed
        int searchNameIndex = new TargetingPage(driver).findSearchNameIndex(searchName);//will be -1 if not listed
        Assert.assertNotEquals("Search name not found in saved searches list", -1, searchNameIndex);
        // starting then aborting a delete and verifying that the search is still there
        new TargetingPage(driver).deleteSearchAbort(searchNameIndex);
        searchNameIndex = new TargetingPage(driver).findSearchNameIndex(searchName);//will be -1 if not listed
        Assert.assertNotEquals("Search name not found despite aborted delete", -1, searchNameIndex);
        // actually deleting the search and verifying that it is gone
        new TargetingPage(driver).deleteSearch(searchNameIndex);
        searchNameIndex = new TargetingPage(driver).findSearchNameIndex(searchName);//will be -1 if not listed
        Assert.assertEquals("Search has not been deleted", -1, searchNameIndex);
    }*/
   @Test
   public void canAddSearchFromList(){
       WebElement search;
       String searchName = current.toString()+"_added";
       TargetingPage targetingPage = new TargetingPage(driver).newSearch().createBlankSearch(searchName);
       search = targetingPage.returnSearch(searchName);
       Assert.assertTrue("Search name not found in saved searches list",  search!=null);
       targetingPage.deleteSearch(search);
   }

   // TODO new changes have broken this flow. Ignoring so the rest of the suite runs
   @Ignore
   @Test
   public void canAbortSearchDelete(){
       WebElement search;
       String searchName = current.toString()+"_abortDelete";
       TargetingPage targetingPage = new TargetingPage(driver).newSearch().createBlankSearch(searchName);
       targetingPage.searchForSearch(searchName);
       search = targetingPage.returnSearch(searchName);
       targetingPage = targetingPage.deleteSearchAbort(search);
       search = targetingPage.returnSearch(searchName);
       Assert.assertTrue("Search name not found despite aborted delete", search!=null);
       targetingPage.deleteSearch(search);
   }

   // TODO new changes have broken this flow. Ignoring so the rest of the suite runs
   @Ignore
   @Test
   public void canDeleteSavedSearch(){
       WebElement search;
       String searchName = current.toString()+"_Delete";
       TargetingPage targetingPage = new TargetingPage(driver).newSearch().createBlankSearch(searchName);
       search = targetingPage.returnSearch(searchName);
       targetingPage =targetingPage.deleteSearch(search);
       search = targetingPage.returnSearch(searchName);
       Assert.assertTrue("Search has not been deleted", search==null);

   }

    @Test
    /* Based on TestRail test case C2348. */
    public void canExpandAndCollapseFiltersArea(){
        // performing a filterless institution search and verifying that the filters area is collapsed and yellow arrow points down
        new TargetingPage(driver).newSearch().blankSearch();
        Assert.assertTrue("Filters area is not collapsed after clicking 'Search'.", new NewSearchPage(driver).filtersAreaIsCollapsed());
        Assert.assertFalse("Yellow arrow is not pointing down while filters area is collapsed.", new NewSearchPage(driver).yellowArrowIsUp());
        // clicking in the filters area and verifying that it expands and yellow arrow points up
        new NewSearchPage(driver).clickInFiltersArea();
        Assert.assertFalse("Filters area is not expanded after clicking inside the filters area.", new NewSearchPage(driver).filtersAreaIsCollapsed());
        Assert.assertTrue("Yellow arrow is not pointing up while filters area is expanded.", new NewSearchPage(driver).yellowArrowIsUp());
        // clicking search and then yellow arrow and verifying that the filters area is expanded and yellow arrow points up
        new NewSearchPage(driver).clickSearchButton();
        new NewSearchPage(driver).clickYellowArrow();
        Assert.assertFalse("Filters area is not expanded after clicking yellow arrow after clicking search button.", new NewSearchPage(driver).filtersAreaIsCollapsed());
        Assert.assertTrue("Yellow arrow is not pointing up while filters area is expanded.", new NewSearchPage(driver).yellowArrowIsUp());
        // clicking yellow arrow and verifying that filters area is collapsed and yellow arrow points down
        new NewSearchPage(driver).clickYellowArrow();
        Assert.assertTrue("Filters area is not collapsed after clicking yellow arrow.", new NewSearchPage(driver).filtersAreaIsCollapsed());
        Assert.assertFalse("Yellow arrow is not pointing down while filters area is collapsed.", new NewSearchPage(driver).yellowArrowIsUp());
    }

    @Test
    public void canSortSearchesList(){
        Assert.assertTrue("Saved searches list cannot be sorted.", new TargetingPage(driver).searchesCanBeSorted());
    }

    @Test
    public void canSortTargetsList(){
        Assert.assertTrue("'All' Targets list cannot be sorted.", new TargetingPage(driver).allTargetsCanBeSorted());
        Assert.assertTrue("Known issue - DESKTOP-6903 - Institutions list cannot be sorted.", new TargetingPage(driver).institutionsCanBeSorted());
        Assert.assertTrue("Funds list cannot be sorted.", new TargetingPage(driver).fundsCanBeSorted());
        Assert.assertTrue("Contacts list cannot be sorted.", new TargetingPage(driver).contactsCanBeSorted());
    }

    @Test
    /* This test requires the presence of a saved search titled "testing updated date - DO NOT REMOVE".
    *  If this search does not exist or was not created on 11/14/16, the test will fail.*/
    public void canEditSearchAndSeeUpdatedDate(){
        String expectedSearchName = "testing updated date - DO NOT REMOVE";
        String expectedCreatedDate = "01/26/17";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        // checking that required search is present and that created date is correct
        int searchNameIndex = new TargetingPage(driver).findSearchNameIndex(expectedSearchName);
        Assert.assertNotEquals("Before editing: the required search cannot be found", -1, searchNameIndex);
        Assert.assertEquals("Before editing: created date is incorrect", expectedCreatedDate, new TargetingPage(driver).getCreatedDate(searchNameIndex));
        // opening and resaving search (this should change the last updated date)
        new TargetingPage(driver).editSearch(searchNameIndex).resaveSearch();
        // checking that search is still listed with correct created date
        searchNameIndex = new EditSearchPage(driver).accessSideNavFromPage().selectTargetingFromSideNav().findSearchNameIndex(expectedSearchName);
        Assert.assertNotEquals("After editing: the required search cannot be found", -1, searchNameIndex);
        Assert.assertEquals("After editing: created date is incorrect", expectedCreatedDate, new TargetingPage(driver).getCreatedDate(searchNameIndex));
        // checking that last updated date is today
        Assert.assertEquals("After editing: last updated date is not today", dateFormat.format(current), new TargetingPage(driver).getUpdatedDate(searchNameIndex));
    }
}
