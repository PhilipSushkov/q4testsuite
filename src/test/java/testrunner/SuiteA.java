package testrunner;

/**
 * Created by noelc on 2016-12-07.
 *
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.activity.SearchForActivity;
import specs.user.contacts.ContactDetails;
import specs.user.briefingBooks.CreateBriefingBook;
import specs.user.briefingBooks.EditBriefingBook;
import specs.user.research.ResearchList;
import specs.user.ownership.Ownership;
import specs.user.sideNavBar.TabNavigationExpanded;
import specs.user.targeting.TargetingList;
import specs.user.team.Q4TeamPage;
import specs.user.watchlist.EditWatchlist;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TargetingList.class

})


public class SuiteA {

    /*     ContactList.class,
        DashboardSearch.class,
        Overview.class,
        TabNavigationExpanded.class,
        EditWatchlist.class,
        Q4TeamPage.class,
        TargetingList.class,
        Ownership.class*/
}
