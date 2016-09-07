package specs.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.securityPage.SecurityOverviewPage;
import pageobjects.contactPage.ContactDetailsPage;
import pageobjects.dashboardPage.Dashboard;
import pageobjects.fundPage.FundPage;
import pageobjects.institutionPage.InstitutionPage;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

public class DashboardSearch extends AbstractSpec{

    @Before
    public void logInUserBeforeEachTest() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canSearchForCompany() {
        String companyName = "Tesla Motors Inc";
        SecurityOverviewPage finish = new SecurityOverviewPage(driver);
        new Dashboard(driver).searchFor(companyName)
                .selectCompanyFromSearch();

        Assert.assertEquals(companyName, finish.getCompanyName());
    }

    @Test
    public void cantSearchForUnknownCompany() {
        String companyName = "12345qwerty";
        new Dashboard(driver).searchFor(companyName);

        Assert.assertEquals(0, driver.findElements(By.className("company-name")).size());
    }

    @Test
    public void specialCharactersShouldntBreakSearch() {
        String crazyCharacters = "!!!$$$%%%";
        String companyName = "Tesla Motors Inc";
        SecurityOverviewPage finish = new SecurityOverviewPage(driver);
        new Dashboard(driver).searchFor(crazyCharacters)
                .clearSearchField()
                .searchFor(companyName)
                .selectCompanyFromSearch();

        Assert.assertEquals(companyName, finish.getCompanyName());
    }

    @Test
    public void canSearchForInstitution() {
        String institutionName = "JPMorgan Investment Management, Inc.";
        InstitutionPage finish = new InstitutionPage(driver);
        new Dashboard(driver).searchFor(institutionName)
                .selectInstitutionFromSearchResults();

        Assert.assertThat(finish.getInstitutionName(), containsString(institutionName));
    }

    @Test
    public void canSearchForContact() {
        String contactName = "Mr. Christoph Christen";
        ContactDetailsPage finish = new ContactDetailsPage(driver);
        new Dashboard(driver).searchFor(contactName)
                .selectContactFromSearchResults();

        Assert.assertEquals(contactName, finish.getContactName());
    }

    @Test
    public void canSearchForFund() {
        String fundName = "Fundy";
        String pageTitle = "Fundy\n" +
                "Open-End Fund";
        FundPage finish = new FundPage(driver);
        new Dashboard(driver).searchFor(fundName)
                .selectFundFromSearchResults();

        Assert.assertEquals(pageTitle, finish.getFundName());
    }
}

