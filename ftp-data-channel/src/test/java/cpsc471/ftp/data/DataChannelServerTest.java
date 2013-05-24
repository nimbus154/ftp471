package cpsc471.ftp.data;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for DataChannelServer
 */
@Test
public class DataChannelServerTest {

    private DataChannelServer dataChannelServer;

    @BeforeMethod
    public void setUp() throws Exception {

        dataChannelServer = new DataChannelServer();
    }

    /**
     * Server should listen on ephemeral port
     */
    public void testListensOnEphemeralPort() {

        Assert.assertTrue(dataChannelServer.getPort() > 1024,
                "Not listening on an ephemeral port: port "
                        + dataChannelServer.getPort());
    }
}
