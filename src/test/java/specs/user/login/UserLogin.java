package specs.user.login;

import org.junit.Assert;
import org.junit.Test;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
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
        start.customLoginUser("imnotauser@mail.com", "notapassword");

        Assert.assertEquals("Login error message not displayed", errorMessage, start.getErrorMessage());
    }

    @Test
    public void incorrectEmailFormat() {
        String errorMessage = "Please enter username and password\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.customLoginUser("email", "password");

        Assert.assertEquals("Login error message not displayed", errorMessage, start.getErrorMessage());
    }

    @Test
    public void incorrectPassword() {
        String errorMessage = "Wrong username and password combination.\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.customLoginUser("patrickp@q4inc.com", "nottherightpassword");

        Assert.assertEquals("Login error message not displayed", errorMessage, start.getErrorMessage());
    }

    @Test
    public void emptyFields() {
        String errorMessage = "Please enter username and password\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.customLoginUser("","");

        Assert.assertEquals("Login error message not displayed", errorMessage, start.getErrorMessage());
    }

    @Test
    public void dismissErrorThenLogin() {
        String errorMessage = "Wrong username and password combination.\n" +
                "OK";
        String searchFieldText = "What are you looking for?";
        LoginPage start = new LoginPage(driver);
        Dashboard finish = new Dashboard(driver);
        start.customLoginUser("1234@qwer.com", "1235");

        Assert.assertEquals("Login error message not displayed", errorMessage, start.getErrorMessage());

        start.dismissErrorModal()
                .customLoginUser("patrickp@q4inc.com", "patrick!");

        Assert.assertEquals("User was not logged in successfully", searchFieldText, finish.getSearchFieldText());
    }
}
