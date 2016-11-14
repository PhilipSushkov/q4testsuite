package specs.user.research;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.researchPage.ResearchPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-11-09.
 */
public class ResearchList extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectResearchFromSideNav();
    }

    @Test
    public void canSearchForResearch() {
        String documentName = "Gimme Shelter";
        ResearchPage researchPage = new ResearchPage(driver).searchForDocument(documentName);

        Assert.assertThat(researchPage.getResearchHeadline(), containsString(documentName));
        // Make sure number of returned documents is reflected in interface (should be 1)
        Assert.assertEquals(1, researchPage.getNumberOfDocuments());
    }

    @Test
    public void canAccessFundDetailsPageFromList() {
        String documentName = "Gimme Shelter";
        InstitutionPage institutionPage = new InstitutionPage(driver);
        ResearchPage researchPage = new ResearchPage(driver).searchForDocument(documentName);

        String firmName = researchPage.getFirmNameFromList();

        researchPage.selectFirmFromResearchList();

        Assert.assertThat(institutionPage.getInstitutionName(), containsString(firmName));
    }
}
