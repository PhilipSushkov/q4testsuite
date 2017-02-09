package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.reportBuilder.CreateBriefingBook;

/**
 * Created by patrickp on 2016-08-08.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
       CreateBriefingBook.class
})
public class SanityTest {
}
