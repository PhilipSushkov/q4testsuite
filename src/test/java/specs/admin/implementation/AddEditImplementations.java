package specs.admin.implementation;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
}
