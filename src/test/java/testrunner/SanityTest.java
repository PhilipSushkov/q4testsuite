package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.targeting.TargetingList;

/**
 * Created by patrickp on 2016-08-08.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
       TargetingList.class
})
public class SanityTest {
}
