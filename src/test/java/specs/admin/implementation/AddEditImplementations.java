package specs.admin.implementation;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.admin.implementationPage.ImplementationPage;
import pageobjects.admin.loginPage.LoginPage;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-10-12.
 */
public class AddEditImplementations extends AdminAbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginAdmin()
                .navigateToImplementationPage();
    }

    @Test
    public void canAddImplementation() {
        String alias = "Test Alias" + RandomStringUtils.randomAlphanumeric(3);
        String sourceUrl = "www." + RandomStringUtils.randomAlphanumeric(5) + ".com";

        ImplementationPage implementationPage = new ImplementationPage(driver).addNewImplementation(alias, sourceUrl);

        Assert.assertThat(implementationPage.getImplementationList(), containsString(alias));
    }

    @Test
    public void canEditImplementation() {
        String sourceUrl = "www." + RandomStringUtils.randomAlphanumeric(5) + ".com";

        ImplementationPage implementationPage = new ImplementationPage(driver).selectFirstImplementation()
                .editDetails(sourceUrl);

        Assert.assertThat(implementationPage.getImplementationList(), containsString(sourceUrl));
    }

    @Test
    public void cannotAddImplementationWithoutSourceUrl() {
        String alias = "Alias" + RandomStringUtils.randomAlphanumeric(5);
        String emptyUrl = "";
        ImplementationPage implementationPage = new ImplementationPage(driver).addNewImplementation(alias, emptyUrl);

        Assert.assertThat(implementationPage.getError(), containsString("ClientProxy validation failed"));
    }

    @Test
    public void cannotAddImplementationWithoutAlias() {
        String emptyAlias = "";
        String url = "www.test.com";
        ImplementationPage implementationPage = new ImplementationPage(driver).addNewImplementation(emptyAlias, url);

        Assert.assertThat(implementationPage.getError(), containsString("ClientProxy validation failed"));
    }

    @Test
    public void canCancelImplementationCreation() {
        ImplementationPage implementationPage = new ImplementationPage(driver).openNewImplementationModal()
                .cancelImplementationCreation();

        // Assert that modal has been dismissed.
        Assert.assertEquals(1, driver.findElements(By.className("ui-dialog-title")).size());
    }
}
