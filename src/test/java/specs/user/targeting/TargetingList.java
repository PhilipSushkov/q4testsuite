package specs.user.targeting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.targeting.EditSearchPage;
import pageobjects.user.targeting.NewSearchPage;
import pageobjects.user.targeting.TargetingPage;
import specs.AbstractSpec;

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
    /**Combination of TestRail test cases C2352, C2285, C2286, and C2345.
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

        // creating a new search and saving it
        new TargetingPage(driver).newSearch().createNewSearch(searchName, filters);

        // verifying that search name is listed
        int searchNameIndex = new TargetingPage(driver).findSearchNameIndex(searchName);
        Assert.assertNotEquals("Search name not found in saved searches list", -1, searchNameIndex);

        // opening search and verifying that filters are correct
        boolean filtersMatch = new TargetingPage(driver).editSearch(searchNameIndex).verifyFilters(filters);
        Assert.assertTrue("Filters do not match.", filtersMatch);

        // deleting search using button on filter page
        new EditSearchPage(driver).deleteSearch();

        // verifying that search is gone
        searchNameIndex = new TargetingPage(driver).findSearchNameIndex(searchName);
        Assert.assertEquals("Search has not been deleted", -1, searchNameIndex);
    }

    @Test
    /**Based on TestRail test case C2291.
     * Can navigate to a targeted institution. */
    public void canAccessATargetedInstitution() {
        String firstInstitution = new TargetingPage(driver).getFirstInstitution();
        String institutionPageTitle = new TargetingPage(driver).openFirstInstitution().getInstitutionName();
        Assert.assertTrue("Empty institution name listed.",!firstInstitution.isEmpty());
        Assert.assertTrue("Institution page title doesn't match.",firstInstitution.equalsIgnoreCase(institutionPageTitle));
    }

    @Test
    /**Based on TestRail test case C2292.
     * Can navigate to a targeted fund. */
    public void canAccessATargetedFund() {
        String firstFund = new TargetingPage(driver).getFirstFund();
        String fundPageTitle = new TargetingPage(driver).openFirstFund().getFundName();
        Assert.assertTrue("Empty fund name listed.",!firstFund.isEmpty());
        Assert.assertTrue("Fund page title doesn't match.",firstFund.equalsIgnoreCase(fundPageTitle));
    }

    @Test
    /**Based on TestRail test case C2293.
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
        int targetedInstitutionIndex = new InstitutionPage(driver).accessSideNavFromPage().selectTargetingFromSideNav().findInstitutionIndex(targetedInstitution);
        Assert.assertNotEquals("Institution not found in targets list", -1, targetedInstitutionIndex);

        // removing the target and checking that the target no longer appears
        new TargetingPage(driver).untargetInstitution(targetedInstitutionIndex);
        Assert.assertFalse("'Saved Target' icon still appears on institution page.", new TargetingPage(driver).goToInstitutionURL(institutionPageURL).isSavedTarget());
        targetedInstitutionIndex = new InstitutionPage(driver).accessSideNavFromPage().selectTargetingFromSideNav().findInstitutionIndex(targetedInstitution);
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
}
