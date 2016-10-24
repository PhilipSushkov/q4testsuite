package specs.user.targeting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.targeting.EditSearchPage;
import pageobjects.user.targeting.TargetingPage;
import specs.AbstractSpec;

import java.util.Date;

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
        String searchName = current.toString(); // makes title of search to equal current time
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
}
