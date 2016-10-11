package specs.admin.companyPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.companyPage.CompanyDetailsPage;
import pageobjects.admin.companyPage.CompanyPage;
import pageobjects.admin.loginPage.LoginPage;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompanyDetails extends AdminAbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginAdmin()
                .navigateToCompanyPage();
    }

    @Test
    public void canAddPeer() {
        new CompanyPage(driver).selectFirstCompanyInList()
                .addPeer("GOOGL");
    }

    @Test
    public void canEditCompanyDetails() {
        String newName = "Testing";
        CompanyPage companyPage = new CompanyPage(driver);
        String companyName = companyPage.getFirstCompanyName();
        companyPage.selectFirstCompanyInList()
                .editCompanyName(newName);

        Assert.assertThat(companyPage.getCompanyName(), containsString(newName));

        new CompanyDetailsPage(driver).editCompanyName(companyName);
    }
}
