package testrunner;

/**
 * Created by noelc on 2016-12-07.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.targeting.TargetingList;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TargetingList.class
})
public class SuiteE {
}
