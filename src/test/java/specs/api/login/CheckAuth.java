package specs.api.login;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.api.login.Auth;

import java.io.IOException;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class CheckAuth {
    private static Auth auth;

    @Before
    public void setUp() throws IOException {
        auth = new Auth();
    }

    @Test
    public void CheckQ4DesktopAuth() throws IOException {
        Assert.assertTrue("Access Token didn't receive", auth.getAccessToken());
    }

}
