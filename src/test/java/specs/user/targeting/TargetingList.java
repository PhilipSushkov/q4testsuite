package specs.user.targeting;

import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
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
    /**Combination of TestRail test cases C2285, C2286, and C2345.
     * Will create a new search with specified filters, save it, view it, verify
     * filters are correct, then delete it using button on filter page. */
    public void canAddViewAndDeleteASavedSearch() {
        String searchName = current.toString(); // makes title of search to equal current time
        String[] filters = {
            "New York, NY", // City
                "Fund", // Institution or Fund
                "70", "90", // Quality Rating (lower and upper bounds, between 0 and 100)
                "0", "10", // Purchasing Power (lower and upper bounds, between 0 and 10)
                "0", "1000", // AUM (lower and upper bounds, between 0 and 1000)
                "Very Low", // Turnover
                "Open-End Fund", // Type
                "Index", // Style
                "", // Ownership
                "All", // Ownership in My Stock
                "All", // Sector Activity
                "All", // Peer Activity
                "no", // Exclude Activists
                "no" // Logged Activity
        };

        // creating a new search and saving it
        new TargetingPage(driver).newSearch().createNewSearch(searchName, filters);

    }
}
