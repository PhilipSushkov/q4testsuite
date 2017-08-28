package specs.user.briefingBooks;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.briefingBooks.BriefingBookDetailsPage;
import pageobjects.user.briefingBooks.BriefingBookList;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by zacharyk on 2017-08-01.
 */
public class GenerateBriefingBook extends AbstractSpec {

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
            briefingBookList.deleteAllBriefingBooks();
        }
        catch(Exception e) {

        }
    }

    @Test
    public void canGenerateBriefingBook(){
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .waitForListToUpdate()
                .viewNewBriefingBook();

        String briefingBookContent = briefingBookDetailsPage.addContact("Samuel Belzberg")
                .generateBriefingBook(false)
                .getBriefingBookPdfContent(briefingBookName)
                .replaceAll("\\s", "");
        // Test checks pdf content with whitespace removed for consistency

        int briefingBookPages = briefingBookDetailsPage.getBriefingBookPdfNumOfPages(briefingBookName);

        Assert.assertNotNull("Briefing book did not generate properly", briefingBookContent);

        Assert.assertTrue("Briefing book generated with incorrect number of pages", briefingBookPages == 1);
        Assert.assertFalse("Briefing book contained incorrect text", briefingBookContent.contains(briefingBookName.replaceAll("\\s", "")));
    }

    @Test
    public void canGenerateBriefingBookWithCoverPage() {
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .waitForListToUpdate()
                .viewNewBriefingBook();

        String briefingBookContent = briefingBookDetailsPage.addInstitution("Vesta Management")
                .generateBriefingBookWithCoverPage(briefingBookName, "Created by Selenium suite", false)
                .getBriefingBookPdfContent(briefingBookName)
                .replaceAll("\\s", "");
        // Test checks pdf content with whitespace removed for consistency

        int briefingBookPages = briefingBookDetailsPage.getBriefingBookPdfNumOfPages(briefingBookName);

        Assert.assertNotNull("Briefing book did not generate properly", briefingBookContent);

        Assert.assertTrue("Briefing book generated with incorrect number of pages", briefingBookPages == 2);
        Assert.assertTrue("Briefing book did not contain proper text", briefingBookContent.contains("INSTITUTIONTEARSHEET"));
        Assert.assertTrue("Briefing book did not contain cover page", briefingBookContent.contains(briefingBookName.replaceAll("\\s", "")));
    }

    @Test
    public void canGenerateBriefingBookWithActivity() {
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .waitForListToUpdate()
                .viewNewBriefingBook();

        String briefingBookContent = briefingBookDetailsPage.addFund("Poplar Forest Partners Fund")
                .generateBriefingBook(true)
                .getBriefingBookPdfContent(briefingBookName)
                .replaceAll("\\s", "");
        // Test checks pdf content with whitespace removed for consistency

        int briefingBookPages = briefingBookDetailsPage.getBriefingBookPdfNumOfPages(briefingBookName);

        Assert.assertNotNull("Briefing book did not generate properly", briefingBookContent);

        Assert.assertTrue("Briefing book did not generate with activity", briefingBookPages > 1);
        Assert.assertTrue("Briefing book did not contain proper text", briefingBookContent.contains("FUNDTEARSHEET"));
        Assert.assertFalse("Briefing book contained incorrect text", briefingBookContent.contains(briefingBookName.replaceAll("\\s", "")));
    }

    @Test
    public void canGenerateBriefingBookWithActivityAndCoverPage() {
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).addNewBriefingBook()
                .saveBriefingBook(briefingBookName)
                .waitForListToUpdate()
                .viewNewBriefingBook();

        String briefingBookContent = briefingBookDetailsPage.addFund("Poplar Forest Partners Fund")
                .generateBriefingBookWithCoverPage(briefingBookName, "Created by Selenium suite", true)
                .getBriefingBookPdfContent(briefingBookName)
                .replaceAll("\\s", "");
        // Test checks pdf content with whitespace removed for consistency

        int briefingBookPages = briefingBookDetailsPage.getBriefingBookPdfNumOfPages(briefingBookName);

        System.out.println(briefingBookContent);
        Assert.assertNotNull("Briefing book did not generate properly", briefingBookContent);

        Assert.assertTrue("Briefing book generated with incorrect number of pages", briefingBookPages > 2);
        Assert.assertTrue("Briefing book did not contain proper text", briefingBookContent.contains("FUNDTEARSHEET"));
        Assert.assertTrue("Briefing book did not contain cover page", briefingBookContent.contains(briefingBookName.replaceAll("\\s", "")));
    }
}
