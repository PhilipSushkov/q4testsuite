package testrunner;

/**
 * Created by noelc on 2016-12-07.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.ownership.Ownership;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Ownership.class
})
public class SuiteE {
}
