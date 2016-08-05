package specs.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.companyDetails.CompanyPage;
import pageobjects.dashboard.Dashboard;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

public class DashboardSearch extends AbstractSpec{

    @Before
    public void logInUserBeforeEachTest() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canSearchForCompany() {
        String companyName = "Tesla Motors, Inc.";
        CompanyPage finish = new CompanyPage(driver);
        new Dashboard(driver).searchForCompany(companyName)
                .selectCompanyFromSearch();

        Assert.assertEquals(companyName, finish.getCompanyName());
        System.out.println(finish.getCompanyName());
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
}
