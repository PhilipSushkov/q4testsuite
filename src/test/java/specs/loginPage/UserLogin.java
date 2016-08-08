package specs.loginPage;

import org.junit.Assert;
import org.junit.Test;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-08-08.
 */
public class UserLogin extends AbstractSpec {

    @Test
    public void incorrectUserName() {
        String errorMessage = "Wrong username and password combination.\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.loginUser("imnotauser@mail.com", "notapassword");

        Assert.assertEquals(errorMessage, start.getIncorrectCombinationMessage());
    }

    @Test
    public void incorrectEmailFormat() {
        String errorMessage = "Please enter username and password\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.loginUser("email", "password");

        Assert.assertEquals(errorMessage, start.getIncorrectCombinationMessage());
    }
}
