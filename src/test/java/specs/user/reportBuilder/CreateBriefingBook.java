package specs.user.reportBuilder;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.briefingBooks.BriefingBookList;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-09-14.
 */
public class CreateBriefingBook extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectBriefingBookFromSideNav();
    }

    @Test
    public void canCreateNewBriefingBook() {
        String briefingBookName = "New Briefing Book" + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookList briefingBookList = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName);

        Assert.assertThat(briefingBookList.getBriefingBookList(), containsString(briefingBookName));
    }
}
