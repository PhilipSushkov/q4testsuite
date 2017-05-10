package specs.euroApi.analysis;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.euroApi.analysis.RequestSource;

import java.io.IOException;

/**
 * Created by philipsushkov on 2017-04-05.
 */

public class CreateRequestSource {
    private static RequestSource requestSource;

    @BeforeTest
    public void setUp() throws IOException {
        requestSource = new RequestSource();
    }

    @Test
    public void CheckRequestSource() throws IOException {
        Assert.assertTrue(requestSource.getRequestSource(), "RequestSource.json file didn't create successfully");
    }

    @DataProvider
    public Object[][] getData() {
        return null;
    }

    @AfterTest
    public void tearDown() {

    }

}
