package specs.user.contacts;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.advancedSearchResultsPage.AdvancedSearchResults;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-09-12.
 */
public class ContactDetails extends AbstractSpec {

    private String contactName = "Mr. Christoph Christen";
    private String shortName = "Christoph Christen";

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .searchFor(contactName)
                .selectContactFromSearchResults();
    }

    @Test
    public void canAddTagToContact() {
        String tagName = "CoolTag" + RandomStringUtils.randomAlphanumeric(3);
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver).addTagToContact(tagName);

        Assert.assertThat("New tag is not shown on Contact page", contactDetailsPage.getContactTags(), containsString(tagName));
    }

    @Test
    public void loggedActivityDateCorrect()
    {
        ContactDetailsPage ContactDetailsPage = new ContactDetailsPage(driver);
        canLogActivityFromDropdown();
        String postedDate = ContactDetailsPage.getActivityDate();

        String dateMonth = postedDate.replaceAll("[0-9 : ]","").replace("Posted","").replace("at","").replace("on","")
                .replace("pm","").replace("am","").substring(0,3); //short hand of a month or MMM format

        String hourMins = postedDate.substring(10,18).replaceAll(" ","");
        Integer hour, mins;

        if(hourMins.substring(4,6).equals("pm")) //getting the time in hours and minutes or HHmm format
        {
            NumberFormat formatter = new DecimalFormat("00");
            hour = Integer.parseInt(hourMins.substring(0,hourMins.indexOf(':'))) + 12; //to 24 hour system
            mins = Integer.parseInt(hourMins.substring(hourMins.indexOf(':')+1,hourMins.indexOf(':')+3));
            String newHour = formatter.format(hour); //This to make the ints 2 decimal places (so '0' added when < 10)
            String newMins = formatter.format(mins);
            hourMins = (newHour + newMins).replace(":",""); //Make it so that int is always 2 digits.
        } else
        {
            hourMins = hourMins.replace("am","").replace(":","");
        }

        int length = postedDate.length();
        String dateYear = postedDate.substring(length - 4, length); //yyyy
        String dateDay = postedDate.substring(length-8, length - 6); //dd
        String timeStamp = new SimpleDateFormat("HHmm_dd_MMM_yyyy").format(Calendar.getInstance().getTime());

        if(Math.abs(Integer.parseInt(hourMins) - Integer.parseInt(timeStamp.substring(0,4))) <= 1) {
            hourMins = timeStamp.substring(0,4); //When there is a minute difference (few seconds)
        }

        String activityDate = (hourMins + "_" + dateDay + "_" + dateMonth + "_" + dateYear);

        Assert.assertEquals("Known issue - DESKTOP-6837 - Dates are not equal:", timeStamp, activityDate);
    }

    @Test
    public void canNavigateToInstitutionFromDetailsPage() {
        String institutionName = new ContactDetailsPage(driver).getInstitutionName();
        InstitutionPage institutionPage = new ContactDetailsPage(driver).navigateToInstitution();

        Assert.assertThat("Institution name does not match expected", institutionPage.getInstitutionName(), containsString(institutionName));
    }

    @Test
    public void canViewManagedFundsTab() {
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver).switchToManagedFundsTab();

        Assert.assertThat("Managed funds page does not contain expected fund", contactDetailsPage.getManagedFunds(), containsString("Csa Mixta - Bvg"));
    }

    @Test
    public void canLinkToTagDetails() {
        AdvancedSearchResults advancedSearchResults = new ContactDetailsPage(driver).viewTagResults();

        Assert.assertThat("Advanced search results did not contain contain contact's name", advancedSearchResults.getAdvancedSearchResults(), containsString(shortName));
    }

    @Test
    public void canLogActivityFromDropdown() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        contactDetailsPage.accessContactDropdown()
                .logActivity()
                .enterNoteDetails(comment, note, tag)
                .postActivity()
                .pageRefresh();

        Assert.assertThat("Not text is not visible after creation", contactDetailsPage.getNoteDetails(), containsString(comment));
    }

    @Test
    public void canTargetAndUntargetContactFromPage(){
        // targeting contact and verifying that "Saved Target" icon appears
        new ContactDetailsPage(driver).markAsTarget();
        Assert.assertTrue("'Saved Target' icon does not appear.", new ContactDetailsPage(driver).isSavedTarget());
        // untargeting contact and verifying that "Saved Target" icon no longer appears
        new ContactDetailsPage(driver).removeFromTargets();
        Assert.assertFalse("'Saved Target' icon still appears.", new ContactDetailsPage(driver).isSavedTarget());
    }

}
