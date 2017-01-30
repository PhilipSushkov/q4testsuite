package testrunner;

/**
 * Created by noelc on 2016-12-07.
 *
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.activity.FilterActivity;
import specs.user.activity.LogActivity;
import specs.user.activity.SearchForActivity;
import specs.user.contacts.ContactList;
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LogActivity.class,
        FilterActivity.class,
        ContactList.class,

})


public class SuiteA {
}
