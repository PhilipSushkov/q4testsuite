package specs.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.companyPage.CompanyPage;
import pageobjects.contactPage.ContactPage;
import pageobjects.dashboard.Dashboard;
import pageobjects.fundPage.FundPage;
import pageobjects.institutionPage.InstitutionPage;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

public class DashboardSearch extends AbstractSpec{

    @Before
    public void logInUserBeforeEachTest() {
        new LoginPage(driver).loginUser("patrickp@q4inc.com", "patrick!");
    }

    @Test
    public void canSearchForCompany() {
        String companyName = "Tesla Motors, Inc.";
        CompanyPage finish = new CompanyPage(driver);
        new Dashboard(driver).searchForCompany(companyName)
                .selectCompanyFromSearch();

        Assert.assertEquals(companyName, finish.getCompanyName());
    }

    @Test
    public void cantSearchForUnknownCompany() {
        String companyName = "12345qwerty";
        Dashboard start = new Dashboard(driver);
        start.searchForCompany(companyName);

        Assert.assertEquals(0, driver.findElements(By.className("company-name")).size());
    }

    @Test
    public void specialCharactersShouldntBreakSearch() {
        String crazyCharacters = "!!!$$$%%%";
        String companyName = "Tesla Motors, Inc.";
        CompanyPage finish = new CompanyPage(driver);
        new Dashboard(driver).searchForCompany(crazyCharacters)
                .clearSearchField()
                .searchForCompany(companyName)
                .selectCompanyFromSearch();

        Assert.assertEquals(companyName, finish.getCompanyName());
    }

    @Test
    public void canSearchForInstitution() {
        String institutionName = "JPMorgan Investment Management, Inc.";
        InstitutionPage finish = new InstitutionPage(driver);
        new Dashboard(driver).searchForCompany(institutionName)
                .selectInstitutionFromSearchResults();

        Assert.assertThat(finish.getInstitutionName(), containsString(institutionName));
    }

    @Test
    public void canSearchForContact() {
        String contactName = "Mr. Christoph Christen";
        ContactPage finish = new ContactPage(driver);
        new Dashboard(driver).searchForCompany(contactName)
                .selectContactFromSearchResults();

        Assert.assertEquals(contactName, finish.getContactName());
    }

    @Test
    public void canSearchForFund() {
        String fundName = "Fundy";
        String pageTitle = "Fundy\n" +
                "Open-End Fund";
        FundPage finish = new FundPage(driver);
        new Dashboard(driver).searchForCompany(fundName)
                .selectFundFromSearchResults();

        Assert.assertEquals(pageTitle, finish.getFundName());
    }
}

