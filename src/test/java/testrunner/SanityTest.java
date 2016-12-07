package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.activity.FilterActivity;
import specs.user.activity.SearchForActivity;
import specs.user.contacts.ContactList;
import specs.user.login.ForgotPassword;
import specs.user.login.UserLogin;
import specs.user.targeting.TargetingList;
import specs.user.watchlist.EditWatchlist;

/**
 * Created by patrickp on 2016-08-08.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
       TargetingList.class
})
public class SanityTest {
}
