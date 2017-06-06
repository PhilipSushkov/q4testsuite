package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.advancedSearch.AdvancedSearching;
import specs.user.briefingBooks.CreateBriefingBook;

/**
 * Created by andyp on 2017-06-06.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateBriefingBook.class,
        AdvancedSearching.class
})
public class andysuite {
}
