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
                .saveBriefingBook(briefingBookName);
        Assert.assertThat("New briefing book was not created", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
        briefingBookList.viewNewBriefingBook()
                .deleteBriefingBookFromDetailsPage();

        Assert.assertThat("Briefing book still appears in the list", briefingBookList.getBriefingBookList(), not(containsString(briefingBookName)));
    }

    @Test
    public void canDeleteBriefingBookFromMainPage(){
        String briefingBookName = "New Briefing Book" + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookList briefingBookList = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName);
        Assert.assertThat("New briefing book was not created", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
        briefingBookList.deleteNewBriefingBook();

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

    @Test
    public void canAddFundToBriefingBook(){
        String briefingBookName = "New Briefing Book" + RandomStringUtils.randomAlphanumeric(6);
        String fund = "Canada Pension Plan";
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .addFund(fund);

        Assert.assertThat("Fund is not listed in the briefing book", briefingBookDetailsPage.getEntityList(), containsString(fund));
    }

    @Test
    public void canAddContactToBriefingBook(){
        String briefingBookName = "New Briefing Book" + RandomStringUtils.randomAlphanumeric(6);
        String contact = "Samuel Stursberg";
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .addContact(contact);

        Assert.assertThat("Contact is not listed in the briefing book", briefingBookDetailsPage.getEntityList(), containsString(contact));
    }

    @Test
    public void canSearchForBriefingBook(){
        String briefingBookName = "Search Test - DO NOT DELETE"; // should be one exact match
        String additionalSearchTerm = "test"; // should be several partial matches
        String randomSearchTerm = "asdsfgdhfasdfb"; // should be no matches
        BriefingBookList briefingBookList = new BriefingBookList(driver).searchFor(briefingBookName);
        Assert.assertThat("Search for known briefing book fails", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
        briefingBookList.searchFor(additionalSearchTerm);
        Assert.assertTrue("Search results are not present.", briefingBookList.briefingBooksAreDisplayed());
        Assert.assertTrue("Search results are invalid.", briefingBookList.allTitlesContain(additionalSearchTerm));
        briefingBookList.searchFor(randomSearchTerm);
        Assert.assertFalse("Results appear for invalid search.", briefingBookList.briefingBooksAreDisplayed());
    }

    @Test
    public void canReorderTearSheets(){
        String briefingBookName = "New Briefing Book" + RandomStringUtils.randomAlphanumeric(6);
        String[] institutions = {"Fidelity Capital Investors, Inc.", "AU & Associates LLC", "Suez Ventures", "HSBC Guyerzeller Bank AG"};
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook();
        for (int i=0; i<institutions.length; i++){
            briefingBookDetailsPage.addInstitution(institutions[i]);
        }
        String lastEntity = briefingBookDetailsPage.getEntity(3);
        briefingBookDetailsPage.reorderEntityToBeginning(3);
        Assert.assertEquals("Reordered entity is not first", lastEntity, briefingBookDetailsPage.getEntity(0));
    }
}
