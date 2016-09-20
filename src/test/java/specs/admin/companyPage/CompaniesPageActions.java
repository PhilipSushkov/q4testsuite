package specs.admin.companyPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.admin.companyPage.CompanyPage;
import pageobjects.admin.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompaniesPageActions extends AbstractSpec{

    @Before
    public void setUp() {
        new LoginPage(driver).loginAdmin()
                .navigateToCompanyPage();
    }

    // Delete company is currently broken. Add this later.
    @Ignore
    @Test
    public void canAddCompanyToList() {
        CompanyPage companyPage = new CompanyPage(driver).addNewCompany();
    }

    @Test
    public void canViewCompanyDetails() {
        String companyName = new CompanyPage(driver).getFirstCompanyName();
        CompanyPage companyPage = new CompanyPage(driver).selectFirstCompanyInList();

        Assert.assertThat(companyPage.getAdminPageTitle(), containsString(companyName));
    }
}
