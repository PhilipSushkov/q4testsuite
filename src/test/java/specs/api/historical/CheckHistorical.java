package specs.api.historical;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.api.historical.Historical;
import pageobjects.api.historical.HistoricalStockQuote;
import pageobjects.api.login.Auth;
import specs.ApiAbstractSpec;

import java.io.IOException;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class CheckHistorical extends ApiAbstractSpec {

    private static Historical historical;
    private static Auth auth;

    @Before
    public void setUp() throws IOException {
        auth = new Auth();
        Assert.assertTrue("Access Token didn't receive", new Auth().getAccessToken());
        historical = new Historical();
    }

    @Test
    public void CheckQ4DesktopAuth() throws IOException {
        HistoricalStockQuote historicalStockQuote = new HistoricalStockQuote();
        HistoricalStockQuote.dataValidation();
    }
}
