package specs.admin.users;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pageobjects.admin.companyPage.CompanyDetailsPage;
import pageobjects.admin.companyPage.CompanyList;
import pageobjects.admin.homePage.HomePage;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.usersPage.UserRole;
import pageobjects.admin.usersPage.UserRoleDetails;
import pageobjects.admin.usersPage.UsersPage;
import specs.AdminAbstractSpec;

import java.util.Date;

/**
 * Created by noelc on 2017-07-18.
 */
public class CSMAccess extends AdminAbstractSpec {

    private static String roleEmail = "roleaccess@q4websystems.com";
    private static String rolePassword = "roleaccess!";
    @Before
    public void setUp() {
        UserRoleDetails userDetailsPage = new AdminLoginPage(driver).loginAdmin().navigateToUsersPage().selectUser(roleEmail).disableAllRoles().enableRole(UserRole.CUSTOMER_SUCCESS_MANAGER);
        userDetailsPage.logout();
        AdminLoginPage adminLogin = new AdminLoginPage(driver).customLoginAdmin(roleEmail,rolePassword);

    }

    @Test
    public void canEditCompanyProfile(){
        String editedCompanyName = "Harmonic, Inc. ";
        Date today = new Date();
        CompanyList companyList = new CompanyList(driver).navigateToCompanyPage();
        CompanyDetailsPage companyDetails = companyList.searchForCompany("Harmonic").selectFirstCompanyInList();
        companyDetails.editCompanyName(editedCompanyName+ today);
       Assert.assertTrue("Company name not edited",companyDetails.getCompanyName().contains(editedCompanyName+today));
    }

}
