package specs.admin.users;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pageobjects.admin.homePage.HomePage;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.usersPage.UserRole;
import pageobjects.admin.usersPage.UserRoleDetails;
import pageobjects.admin.usersPage.UsersPage;
import specs.AdminAbstractSpec;
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
    public void userSelectTest (){



    }
    @Test
    public  void whatever2(){

    }
}
