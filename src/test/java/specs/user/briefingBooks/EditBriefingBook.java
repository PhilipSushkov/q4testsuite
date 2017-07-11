package specs.user.briefingBooks;

import org.junit.*;
import pageobjects.user.briefingBooks.BriefingBookDetailsPage;
import pageobjects.user.briefingBooks.BriefingBookList;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by noelc on 2016-11-23.
 */
public class EditBriefingBook extends AbstractSpec {
    private final static String keyword = "**AUTOMATION**";
    private  static final String nightlyBook = keyword+" nightlyTestingBriefingBook";
    private final String fund = "Canada Pension Plan";
    private final String contact = "Samuel Stursberg";
    private final String Insitution = "Fidelity Capital Investors, Inc.";
    private static boolean setupIsDone =false;


    @Before
    public void setupTest(){

        if(setupIsDone) {
            new LoginPage(driver).loginUser()
                    .accessSideNav()
                    .selectBriefingBookFromSideNav();
        }
        else{
            new LoginPage(driver).loginUser()
                    .accessSideNav()
                    .selectBriefingBookFromSideNav();
            BriefingBookList briefingBookList = new BriefingBookList(driver).addNewBriefingBook().saveBriefingBook(nightlyBook);
            setupIsDone = true;
        }
    }

    @After
    public void cleanUp(){
        try {
            BriefingBookList briefingBookList = new BriefingBookDetailsPage(driver).accessSideNavFromPage().selectBriefingBookFromSideNav();
            briefingBookList.searchFor(keyword);
            briefingBookList.deleteAllBriefingBooks(keyword);
        }
        catch(Exception e) {

        }
    }

    @Test
    public void canDeleteFundInBriefingBook(){
        BriefingBookList briefingBookList = new BriefingBookList(driver);
        BriefingBookDetailsPage briefingBookDetailsPage = briefingBookList.viewNewBriefingBook().addFund(fund).deleteEntity(fund);

        Assert.assertFalse(briefingBookDetailsPage.doesEntityExist(fund));

    }



}