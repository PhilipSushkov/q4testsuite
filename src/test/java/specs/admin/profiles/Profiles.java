package specs.admin.profiles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.loginPage.LoginPage;
import pageobjects.admin.profilesPage.ProfilesList;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-10-11.
 */
public class Profiles extends AdminAbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginAdmin();
    }

    @Test
    public void canSearchForProfile() {
        String userName = "Patrick";
        ProfilesList profilesList = new ProfilesList(driver).navigateToProfilesPage()
                .searchForProfile(userName);

        Assert.assertThat(profilesList.getProfileList(), containsString(userName));
    }
}
