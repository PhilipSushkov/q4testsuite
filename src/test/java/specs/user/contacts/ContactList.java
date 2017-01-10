package specs.user.contacts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by patrickp on 2016-08-25.
 */
public class ContactList extends AbstractSpec {
//testing
    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canAddAndRemoveContactFromList() {
        String contactName = "Andrew C. McCormick";
        ContactPage contactPage = new ContactPage(driver);

        new Dashboard(driver).searchFor(contactName)
                .selectContactFromSearchResults()
                .addToContacts()
                .accessSideNavFromPage()
                .selectContactsFromSideNav()
                .searchForContact(contactName);

        Assert.assertThat("Contact does not appear in contact list", contactPage.getContacts(), containsString(contactName));

        new ContactPage(driver).viewContactDetails()
                .removeContactFromList()
                .accessSideNavFromPage()
                .selectContactsFromSideNav()
                .searchForContact(contactName);

        Assert.assertThat("Contact was not removed from contact list", contactPage.getContacts(), is(not(containsString(contactName))));
    }

    @Ignore
    @Test
    public void canSearchForContact() {

    }
}
