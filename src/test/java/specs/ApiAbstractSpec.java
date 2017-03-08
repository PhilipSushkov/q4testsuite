package specs;

import java.io.IOException;
import java.util.Properties;
import org.junit.Before;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public abstract class ApiAbstractSpec extends util.Functions {
    private static final String PATHTO_API_PROP = "api/ApiMap.properties";
    public static Properties propAPI;

    @Before
    public void init() throws IOException {
        setupPropUI();
    }

    public static void setupPropUI() throws IOException {
        propAPI = ConnectToPropUI(PATHTO_API_PROP);
    }

}
