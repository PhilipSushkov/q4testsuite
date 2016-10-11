package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.admin.companyPage.CompaniesList;
import specs.admin.companyPage.CompanyDetails;
import specs.admin.loginPage.AdminLogin;
import specs.admin.profiles.Profiles;

/**
 * Created by patrickp on 2016-09-20.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminLogin.class,
        CompaniesList.class,
        CompanyDetails.class,
        Profiles.class
})

public class AdminSuite {
}
