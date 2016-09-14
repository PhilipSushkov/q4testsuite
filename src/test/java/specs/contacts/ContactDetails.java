package specs.contacts;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.advancedSearchResultsPage.AdvancedSearchResults;
import pageobjects.contactPage.ContactDetailsPage;
import pageobjects.institutionPage.InstitutionPage;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-09-12.
 */
public class ContactDetails extends AbstractSpec {

    private String contactName = "Mr. Christoph Christen";
    private String shortName = "Christoph Christen";

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .searchFor(contactName)
                .selectContactFromSearchResults();
    }

    @Test
    public void canAddTagToContact() {
        String tagName = "CoolTag" + RandomStringUtils.randomAlphanumeric(3);
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver).addTagToContact(tagName);

        Assert.assertThat(contactDetailsPage.getContactTags(), containsString(tagName));
    }

    @Test
    public void canNavigateToInstitutionFromDetailsPage() {
        String institutionName = new ContactDetailsPage(driver).getInstitutionName();
        InstitutionPage institutionPage = new ContactDetailsPage(driver).navigateToInstitution();

        Assert.assertThat(institutionPage.getInstitutionName(), containsString(institutionName));
    }

    @Test
    public void canViewManagedFundsTab() {
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver).switchToManagedFundsTab();

        Assert.assertThat(contactDetailsPage.getManagedFunds(), containsString("Csa Mixta - Bvg"));
    }

    @Test
    public void canLinkToTagDetails() {
        AdvancedSearchResults advancedSearchResults = new ContactDetailsPage(driver).viewTagResults();

        Assert.assertThat(advancedSearchResults.getAdvancedSearchResults(), containsString(shortName));
    }

    @Test
    public void canLogActivityFromDropdown() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        contactDetailsPage.accessContactDropdown()
                .logActivity()
                .enterNoteDetails(comment, note, tag)
                .pageRefresh();

        Assert.assertThat(contactDetailsPage.getNoteDetails(), containsString(comment));
    }

    @Test
    public void canCancelReportCreation() {
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        contactDetailsPage.accessContactDropdown()
                .selectCreateTearSheet()
                .cancelTearSheetCreation();

        Assert.assertThat(contactDetailsPage.getContactName(), containsString(contactName));
    }
}
