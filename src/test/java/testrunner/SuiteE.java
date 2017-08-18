package testrunner;

/**
 * Created by noelc on 2016-12-07.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.activity.ActivityDetails;
import specs.user.briefingBooks.CreateBriefingBook;
import specs.user.contacts.ContactDetails;
import specs.user.contacts.ContactList;
import specs.user.dashboard.DashboardLogActivity;
import specs.user.targeting.TargetingList;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //TargetingList.class
        ContactList.class,
        ActivityDetails.class,
        ContactDetails.class,
        DashboardLogActivity.class,
        CreateBriefingBook.class

})
public class SuiteE {
}
