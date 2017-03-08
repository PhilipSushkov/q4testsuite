package specs.api.login;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.api.login.Auth;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class CheckAuth {
    private static Auth auth;

    @Before
    public void setUp() {
    }

    @Test
    public void CheckQ4DesktopAuth() {
        Assert.assertTrue("Access Token didn't receive", auth.getAccessToken());
    }

}
