package specs.admin.companyPage;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.admin.companyPage.CompanyPage;
import pageobjects.admin.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-09-20.
 */
public class AddRemoveCompany extends AbstractSpec{

    @Before
    public void setUp() {
        new LoginPage(driver).loginAdmin();
    }

    // Delete company is currently broken. Add this later.
    @Ignore
    @Test
    public void canAddCompanyToList() {
        CompanyPage companyPage = new CompanyPage(driver).navigateToCompanyPage()
                .addNewCompany();
    }
}
