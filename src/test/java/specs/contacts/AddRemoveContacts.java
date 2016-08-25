package specs.contacts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.contactPage.ContactPage;
import pageobjects.dashboardPage.Dashboard;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-25.
 */
public class AddRemoveContacts extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
    }

    // TODO finish writing this test. Needs to remove Andrew from contact list after assertion.
    @Ignore
    @Test
    public void canAddContactToList() {
        String contactName = "Andrew C. McCormick";
        ContactPage contactPage = new ContactPage(driver);

        new Dashboard(driver).searchFor(contactName)
                .selectContactFromSearchResults()
                .addToContacts()
                .accessSideNavFromPage()
                .selectContactsFromSideNav();

        Assert.assertThat(contactPage.getContacts(), containsString(contactName));
    }
}
