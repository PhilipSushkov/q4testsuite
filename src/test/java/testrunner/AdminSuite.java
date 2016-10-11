package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.admin.companyPage.CompaniesList;
import specs.admin.companyPage.CompanyDetails;
import specs.admin.loginPage.AdminLogin;

/**
 * Created by patrickp on 2016-09-20.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminLogin.class,
        CompaniesList.class,
        CompanyDetails.class
})

public class AdminSuite {
}
