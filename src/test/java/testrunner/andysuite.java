package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.advancedSearch.AdvancedSearching;

/**
 * Created by andyp on 2017-06-06.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdvancedSearching.class
})
public class andysuite {
}
