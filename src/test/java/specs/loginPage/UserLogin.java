package specs.loginPage;

import org.junit.Assert;
import org.junit.Test;
import pageobjects.dashboard.Dashboard;
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

        Assert.assertEquals(errorMessage, start.getErrorMessage());
    }

    @Test
    public void incorrectEmailFormat() {
        String errorMessage = "Please enter username and password\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.loginUser("email", "password");

        Assert.assertEquals(errorMessage, start.getErrorMessage());
    }

    @Test
    public void incorrectPassword() {
        String errorMessage = "Wrong username and password combination.\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.loginUser("patrickp@q4inc.com", "nottherightpassword");

        Assert.assertEquals(errorMessage, start.getErrorMessage());
    }

    @Test
    public void emptyFields() {
        String errorMessage = "Please enter username and password\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.loginUser("","");

        Assert.assertEquals(errorMessage, start.getErrorMessage());
    }

    @Test
    public void dismissErrorThenLogin() {
        String errorMessage = "Wrong username and password combination.\n" +
                "OK";
        String searchFieldText = "What are you looking for?";
        LoginPage start = new LoginPage(driver);
        Dashboard finish = new Dashboard(driver);
        start.loginUser("1234@qwer.com", "1235");

        Assert.assertEquals(errorMessage, start.getErrorMessage());

        start.dismissErrorModal()
                .loginUser("patrickp@q4inc.com", "patrick!");

        Assert.assertEquals(searchFieldText, finish.getSearchFieldText());
    }
}
