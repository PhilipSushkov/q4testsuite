package testrunner;

/**
 * Created by andyp on 2017-07-07.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.activity.SearchForActivity;
import specs.user.sideNavBar.TabNavigationExpanded;
import specs.user.team.Q4TeamPage;
import specs.user.watchlist.EditWatchlist;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TabNavigationExpanded.class,
        EditWatchlist.class,
        Q4TeamPage.class,
        SearchForActivity.class
})
public class TestSuite {
}
