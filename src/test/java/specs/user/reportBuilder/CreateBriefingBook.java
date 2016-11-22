package specs.user.reportBuilder;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.briefingBooks.BriefingBookDetailsPage;
import pageobjects.user.briefingBooks.BriefingBookList;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

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

        Assert.assertThat("New briefing book was not created", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
    }

    @Test
    public void canDeleteBriefingBookFromDetailsPage() {
        String briefingBookName = "New Briefing Book" + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookList briefingBookList = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .deleteBriefingBookFromDetailsPage();

        Assert.assertThat("Briefing book still appears in the list", briefingBookList.getBriefingBookList(), not(containsString(briefingBookName)));
    }

    @Test
    public void canAddInstitutionToBriefingBook(){
        String briefingBookName = "New Briefing Book" + RandomStringUtils.randomAlphanumeric(6);
        String institution = "Fidelity Capital Investors, Inc.";
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .addInstitution(institution);

        Assert.assertThat("Institution is not listed in the briefing book", briefingBookDetailsPage.getEntityList(), containsString(institution));
    }
}
