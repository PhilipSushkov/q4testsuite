package specs.admin.users;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.usersPage.UserRole;
import pageobjects.admin.usersPage.UsersPage;
import specs.AdminAbstractSpec;

/**
 * Created by noelc on 2016-11-22.
 */
public class UserRoles extends AdminAbstractSpec{
    @Before
    public void setUp() {
        new AdminLoginPage(driver).loginAdmin().navigateToUsersPage();

    }


    @Test @Ignore
    public void filterAllUsers(){
        UsersPage userPage = new UsersPage(driver);
        userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.ALL);
        userPage.areUsersFilteredByRole(UserRole.ALL);

    }

    @Test
    public void filterUsersByAdminRole(){
        UsersPage userPage = new UsersPage(driver);
        userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.ADMIN);
        Assert.assertTrue("Users were not filtered by the Admin role",userPage.areUsersFilteredByRole(UserRole.ADMIN));
    }

    @Test
    public void filterUsersByUserRole(){
        UsersPage userPage = new UsersPage(driver);
        userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.USER);
        Assert.assertTrue("Users were not filtered by the user role",userPage.areUsersFilteredByRole(UserRole.USER));
    }

    @Test
    public void filterUserByDeveloperRole(){
        UsersPage userPage = new UsersPage(driver);
        userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.DEVOPS);
        Assert.assertTrue("Users were not filtered by the developer role",userPage.areUsersFilteredByRole(UserRole.DEVOPS));
    }

    @Test
    public void filterUserByIntelligenceRole(){
        UsersPage userPage = new UsersPage(driver);
        userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.INTELLIGENCE_ANALYST);
        Assert.assertTrue("Users were not filtered by the Intelligence role",userPage.areUsersFilteredByRole(UserRole.INTELLIGENCE_ANALYST));
    }

    @Test
    public void filterUserByImplementationRole(){
        UsersPage userPage = new UsersPage(driver);
        userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.IMPLEMENTATION_MANAGER);
        Assert.assertTrue("Users were not filtered by the Implementation role",userPage.areUsersFilteredByRole(UserRole.IMPLEMENTATION_MANAGER));
    }

    @Test
    public void filterUserByCustomerSuccessRole(){
        UsersPage userPage = new UsersPage(driver);
        userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.CUSTOMER_SUCCESS_MANAGER);
        Assert.assertTrue("Users were not filtered by the Customer Success role",userPage.areUsersFilteredByRole(UserRole.CUSTOMER_SUCCESS_MANAGER));
    }

    @Test
    public void filterUserBySalesManagerRole(){
        UsersPage userPage = new UsersPage(driver);
        userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.SALES_MANAGER);
        Assert.assertTrue("Users were not filtered by the Sales Manager role",userPage.areUsersFilteredByRole(UserRole.SALES_MANAGER));
    }
}
