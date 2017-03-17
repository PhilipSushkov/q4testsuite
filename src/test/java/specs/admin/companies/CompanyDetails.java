package specs.admin.companies;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.companyPage.CompanyDetailsPage;
import pageobjects.admin.companyPage.CompanyList;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

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
        CompanyList companyPage = new CompanyList(driver);
        String companyName = companyPage.getFirstCompanyName();
        companyPage.selectFirstCompanyInList()
                .editCompanyName(newName);

        Assert.assertThat("New company name is not visible on Company details page", companyPage.getCompanyName(), containsString(newName));

        new CompanyDetailsPage(driver).editCompanyName(companyName);
    }

    @Test
    public void canEditPeerNameWhenAdding() {
        String peer = "GOOG";
        String name = "New Test" + RandomStringUtils.randomAlphanumeric(3);
        CompanyList companyPage = new CompanyList(driver).selectFirstCompanyInList()
                .addEditPeer(peer, name);

        Assert.assertThat("New peer name is not shown in peer list", companyPage.getPeerList(), containsString(name));

        companyPage.removePeer();
    }

    @Test
    public void canAddAndRemoveAdditionalTickersToCompany() {
        String companyName = "GOLD";
        String newTicker = "WEED";
        CompanyList companyList = new CompanyList(driver).searchForCompany(companyName)
                .selectFirstCompanyInList()
                .selectTickerTab()
                .addTicker(newTicker);

        driver.navigate().refresh();

        new CompanyDetailsPage(driver).selectTickerTab();

        Assert.assertThat("Added ticker is not visible", companyList.getTickerList(), containsString(newTicker));

        new CompanyDetailsPage(driver).removeTicker();

        driver.navigate().refresh();

        Assert.assertThat("Added ticker is still visible", companyList.getTickerList(), is(not(newTicker)));
    }
}
