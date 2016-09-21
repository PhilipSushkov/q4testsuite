package specs.admin.companyPage;

import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.companyPage.CompanyPage;
import pageobjects.admin.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompanyDetails extends AbstractSpec {

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
}
