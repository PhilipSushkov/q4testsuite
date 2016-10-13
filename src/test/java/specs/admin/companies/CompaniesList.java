package specs.admin.companies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.admin.companyPage.CompanyPage;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompaniesList extends AdminAbstractSpec {

    @Before
    public void setUp() {
        new AdminLoginPage(driver).loginAdmin()
                .navigateToCompanyPage();
    }

    @Ignore
    @Test
    public void canAddCompanyToList() {
        String companyName = "Canopy Growth Corp.";
        CompanyPage companyPage = new CompanyPage(driver).addNewCompany(companyName);
    }

    @Ignore
    @Test
    public void canEditCompanyNameWhenAdding() {

    }

    @Test
    public void canViewCompanyDetails() {
        String companyName = new CompanyPage(driver).getFirstCompanyName();
        CompanyPage companyPage = new CompanyPage(driver).selectFirstCompanyInList();

        Assert.assertThat(companyPage.getAdminPageTitle(), containsString(companyName));
    }

    @Test
    public void canSearchForCompany() {
        String companyName = "Yum";
        CompanyPage companyPage = new CompanyPage(driver).searchForCompany(companyName);

        Assert.assertThat(companyPage.getCompanyList(), containsString(companyName));
    }

    @Test
    public void unknownSearchReturnsNoResults() {
        String fakeName = "1234";
        CompanyPage companyPage = new CompanyPage(driver).searchForCompany(fakeName);

        Assert.assertThat(companyPage.getCompanyList(), containsString("No records found"));
    }

    @Test
    public void canCancelAddModal() {
        new CompanyPage(driver).triggerAddCompanyModal()
                .cancelAddCompany();

        Assert.assertEquals(1, driver.findElements(By.className("ui-dialog-title")).size());
    }

    @Test
    public void canDismissAddModal() {
        new CompanyPage(driver).triggerAddCompanyModal()
                .exitAddCompanyModal();

        Assert.assertEquals(1, driver.findElements(By.className("ui-dialog-title")).size());
    }
}
