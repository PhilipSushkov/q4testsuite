package specs.user.briefingBooks;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.briefingBooks.BriefingBookColumnType;
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
    private final static String keyword = "**AUTOMATION**";
    private final static String briefingBookTitle =keyword+"New Briefing Book";

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectBriefingBookFromSideNav();
    }

    @After
    public void cleanUp(){
        try {
            BriefingBookList briefingBookList = new BriefingBookDetailsPage(driver).accessSideNavFromPage().selectBriefingBookFromSideNav();
            briefingBookList.searchFor(keyword);
            briefingBookList.deleteAllBriefingBooks(briefingBookTitle);
        }
        catch(Exception e) {

        }
    }

    @Test
    public void canCreateNewBriefingBook() {
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookList briefingBookList = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName);

        Assert.assertThat("New briefing book was not created", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
    }

    @Test
    public void canDeleteBriefingBookFromDetailsPage() {
        String briefingBookName =briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookList briefingBookList = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName);
        Assert.assertThat("New briefing book was not created", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
        briefingBookList.viewNewBriefingBook()
                .deleteBriefingBookFromDetailsPage();

        Assert.assertThat("Briefing book still appears in the list", briefingBookList.getBriefingBookList(), not(containsString(briefingBookName)));
    }

    @Test
    public void canDeleteBriefingBookFromMainPage(){
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookList briefingBookList = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName);
        Assert.assertThat("New briefing book was not created", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
        briefingBookList.deleteNewBriefingBook();

        Assert.assertThat("Briefing book still appears in the list", briefingBookList.getBriefingBookList(), not(containsString(briefingBookName)));
    }

    @Test
    public void canAddInstitutionToBriefingBook(){
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String institution = "Fidelity Management & Research Co.";
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .addInstitution(institution);

        Assert.assertThat("KNOWN ISSUE: DESKTOP-8844 - Institution is not listed in the briefing book", briefingBookDetailsPage.getEntityList(), containsString(institution));
    }

    @Test
    public void canAddFundToBriefingBook(){
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String fund = "Canada Pension Plan";
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .addFund(fund);

        Assert.assertThat("Fund is not listed in the briefing book", briefingBookDetailsPage.getEntityList(), containsString(fund));
    }

    @Test
    public void canAddContactToBriefingBook(){
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String contact = "Samuel Stursberg";
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .addContact(contact);

        Assert.assertThat("Contact is not listed in the briefing book", briefingBookDetailsPage.getEntityList(), containsString(contact));
    }

    @Test
    // This test relies on the existence of a briefing book called "Search Test - DO NOT DELETE" in order to pass
    public void canSearchForBriefingBook(){
        String briefingBookName = "Search Test - DO NOT DELETE"; // should be one exact match
        // performing search for which there should be one result and checking that result contains search term
        BriefingBookList briefingBookList = new BriefingBookList(driver).searchFor(briefingBookName);
        Assert.assertThat("Search for known briefing book fails", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
    }

    @Test
    public void searchingForUnknownKeywordReturnsNoResults(){
        String randomSearchTerm = "asdsfgdhfasdfb"; // should be no matches

        // performing search for which there should be no results
        BriefingBookList briefingBookList = new BriefingBookList(driver).searchFor(randomSearchTerm);
        Assert.assertFalse("Results appear for invalid search.", briefingBookList.briefingBooksAreDisplayed());
    }

    @Test
    public void canReorderTearSheets(){
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String[] institutions = {"Fidelity Capital Investors, Inc.", "AU & Associates LLC", "Suez Ventures", "HSBC Guyerzeller Bank AG"};
        // creating and opening blank briefing book
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook();
        // adding institutions to briefing book
        for (int i=0; i<institutions.length; i++){
            briefingBookDetailsPage.addInstitution(institutions[i]);
        }
        // clicking edit, dragging last item to top of list, clicking save, refreshing page, and checking that it is now the first item
        String lastEntity = briefingBookDetailsPage.getEntity(3);
        briefingBookDetailsPage.reorderEntityToBeginning(3);
        Assert.assertEquals("Reordered entity is not first", lastEntity, briefingBookDetailsPage.getEntity(0));
    }

    @Test
    public void briefingBooksSortedByTitle(){
        BriefingBookList list = new BriefingBookList(driver);
        list.clickHeader(BriefingBookColumnType.TITLE);
        Assert.assertTrue("Titles are not sorted alphabetically",list.isSortedBy(BriefingBookColumnType.TITLE));

    }

    @Test
    public void briefingBooksSortedByAuthor(){
        BriefingBookList list = new BriefingBookList(driver);
        list.clickHeader(BriefingBookColumnType.AUTHOR);
        Assert.assertTrue("Authors are not sorted alphabetically",list.isSortedBy(BriefingBookColumnType.AUTHOR));

    }

    @Test
    public void briefingBooksSortedByCreated(){
        BriefingBookList list = new BriefingBookList(driver);
        list.clickHeader(BriefingBookColumnType.CREATED);
        Assert.assertTrue("Created dates not sorted correctly",list.isSortedBy(BriefingBookColumnType.CREATED));

    }

    @Test
    public void briefingBooksSortedByLastUpdated(){
        BriefingBookList list = new BriefingBookList(driver);
        list.clickHeader(BriefingBookColumnType.LAST_UPDATED);
        Assert.assertTrue("Last Updated not sorted correctly",list.isSortedBy(BriefingBookColumnType.LAST_UPDATED));

    }
}
