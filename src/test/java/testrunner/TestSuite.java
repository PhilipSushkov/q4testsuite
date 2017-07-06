package testrunner;

/**
 * Created by andyp on 2017-07-04.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.activity.ActivityDetails;
import specs.user.activity.LogActivity;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LogActivity.class
})
public class TestSuite {

}
