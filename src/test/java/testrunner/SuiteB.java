package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.contacts.ContactDetails;
import specs.user.dashboard.DashboardLinks;
import specs.user.dashboard.DashboardLogActivity;

/**
 * Created by noelc on 2016-12-07.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ContactDetails.class,
        DashboardLinks.class,
        DashboardLogActivity.class,
})
public class SuiteB {
}
