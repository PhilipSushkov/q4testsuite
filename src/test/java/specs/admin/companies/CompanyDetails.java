package specs.admin.companies;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.companyPage.CompanyDetailsPage;
import pageobjects.admin.companyPage.CompanyPage;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompanyDetails extends AdminAbstractSpec {

    @Before
    public void setUp() {
        new AdminLoginPage(driver).loginAdmin()
                .navigateToCompanyPage();
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

    @Test
    public void canEditPeerNameWhenAdding() {
        String peer = "GOOG";
        String name = "New Test" + RandomStringUtils.randomAlphanumeric(3);
        CompanyPage companyPage = new CompanyPage(driver).selectFirstCompanyInList()
                .addEditPeer(peer, name);

        Assert.assertThat(companyPage.getPeerList(), containsString(name));

        companyPage.removePeer();
    }
}
