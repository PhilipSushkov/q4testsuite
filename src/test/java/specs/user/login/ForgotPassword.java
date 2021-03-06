package specs.user.login;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-08-08.
 */
public class ForgotPassword extends AbstractSpec {

    @Test
    public void forgotPasswordModalDoesAppear() {
        String modalTitle = "Forgot Password";
        LoginPage start = new LoginPage(driver);
        start.forgotPassword();

        Assert.assertEquals("Forgot password modal did not appear", modalTitle, start.getForgotPasswordModalTitle());
    }

    @Test
    public void canDismissForgotPasswordModal() {
        LoginPage start = new LoginPage(driver);
        start.forgotPassword()
                .dismissForgotPasswordModal();

        Assert.assertEquals("Forgot password modal was not successfully dismissed", 1, driver.findElements(By.className("q4i-logo")).size());
    }

    @Test
    public void cannotSubmitInvalidEmail() {
        String invalidEmailError = "Please enter a valid email address.\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.forgotPassword()
                .enterForgotPasswordEmail("notanemailaddress");

        Assert.assertEquals("Invalid email address message not shown", invalidEmailError, start.getInvalidEmailError());
    }

    @Test
    public void canSubmitValidEmailAddress() {
        String confirmationModalText = "If an account with that email address exists in our system, then a reset email will be sent with further instructions.\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.forgotPassword()
                .enterForgotPasswordEmail("mail@mail.com");

        Assert.assertEquals(confirmationModalText, start.getConfirmationText());

        start.dismissConfirmationModal();

        Assert.assertEquals("Valid email address not accepted", 1, driver.findElements(By.className("q4i-logo")).size());
    }
}
