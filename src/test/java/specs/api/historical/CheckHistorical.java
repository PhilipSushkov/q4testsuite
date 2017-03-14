package specs.api.historical;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.api.historical.Historical;
import specs.ApiAbstractSpec;

import java.io.IOException;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class CheckHistorical extends ApiAbstractSpec {
    private static Historical historical;

    @Before
    public void setUp() throws IOException {
        historical = new Historical();
    }

    @Test
    public void CheckQ4DesktopAuth() throws IOException {
        Assert.assertTrue("Q4 Historical Data doesn't match to Yahoo Finance", historical.compareHistoricalData());
    }
}
