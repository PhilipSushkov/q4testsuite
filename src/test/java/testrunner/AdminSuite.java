package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.admin.companies.CompaniesList;
import specs.admin.companies.CompanyDetails;
import specs.admin.implementation.AddEditImplementations;
import specs.admin.intelligence.IntelligenceList;
import specs.admin.loginPage.AdminLogin;
import specs.admin.profiles.EditProfilesList;

/**
 * Created by patrickp on 2016-09-20.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminLogin.class,
        CompaniesList.class,
        CompanyDetails.class,
        EditProfilesList.class,
        AddEditImplementations.class,
        IntelligenceList.class
})

public class AdminSuite {
}
