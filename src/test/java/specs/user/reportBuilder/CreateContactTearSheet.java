package specs.user.reportBuilder;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.reportBuilder.NewReport;
import pageobjects.user.reportBuilder.ReportBuilderPage;
import pageobjects.user.reportBuilder.ReportDetailsPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by patrickp on 2016-09-14.
 */
public class CreateContactTearSheet extends AbstractSpec {
    private String contactName = "Mr. Christoph Christen";

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .searchFor(contactName)
                .selectContactFromSearchResults();
    }

    @Test
    public void canCreateTearSheet() {
        ReportDetailsPage reportDetailsPage = new ReportDetailsPage(driver);
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        NewReport newReport = new NewReport(driver);

        String reportTitle = "New Report" + RandomStringUtils.randomAlphanumeric(3);
        contactDetailsPage.accessContactDropdown()
                .createTearSheet(reportTitle);

        Assert.assertThat(reportDetailsPage.getPageTitle(), containsString(reportTitle));

        reportDetailsPage.generateTearSheet()
                .switchToNewTab();

        Assert.assertThat(newReport.getReportPageTitle(), containsString(reportTitle));
    }

    @Test
    public void canDeleteTearSheet() {
        ReportBuilderPage reportBuilderPage = new ReportBuilderPage(driver);
        ReportDetailsPage reportDetailsPage = new ReportDetailsPage(driver);
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);

        String reportTitle = "New Report" + RandomStringUtils.randomAlphanumeric(3);
        contactDetailsPage.accessContactDropdown()
                .createTearSheet(reportTitle);

        reportDetailsPage.editTearSheet()
                .deleteTearSheet();

        Assert.assertThat(reportBuilderPage.getReportList(), is(not(containsString(reportTitle))));
    }

    @Test
    public void canAddMoreTags() {
        ReportDetailsPage reportDetailsPage = new ReportDetailsPage(driver);
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        String existingTag = "TestTagKQWwo8 ";
        String reportTitle = "New Report" + RandomStringUtils.randomAlphanumeric(3);
        String comment = "This is a test commentIF9Ltc";
        contactDetailsPage.accessContactDropdown()
                .createTearSheet(reportTitle);

        reportDetailsPage.editTearSheet()
                .addMoreTags(existingTag)
                .saveChanges();

        Assert.assertThat(reportDetailsPage.getContent(), containsString(comment));
    }
}
