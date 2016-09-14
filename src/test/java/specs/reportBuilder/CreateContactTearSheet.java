package specs.reportBuilder;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.contactPage.ContactDetailsPage;
import pageobjects.loginPage.LoginPage;
import pageobjects.reportBuilder.NewReport;
import pageobjects.reportBuilder.ReportDetailsPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

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
        String reportTitle = "New Report" + RandomStringUtils.randomAlphanumeric(3);
        ReportDetailsPage reportDetailsPage = new ReportDetailsPage(driver);
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        NewReport newReport = new NewReport(driver);
        contactDetailsPage.accessContactDropdown()
                .createTearSheet(reportTitle);

        Assert.assertThat(reportDetailsPage.getPageTitle(), containsString(reportTitle));

        reportDetailsPage.generateTearSheet()
                .switchToNewTab();

        Assert.assertThat(newReport.getReportPageTitle(), containsString(reportTitle));
    }
}
