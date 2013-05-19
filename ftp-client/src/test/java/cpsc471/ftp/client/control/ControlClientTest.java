package cpsc471.ftp.client.control;

import org.testng.annotations.Test;

import java.net.UnknownHostException;

/**
 * Test for the control client
 */
@Test
public class ControlClientTest {

    /**
     * Instantiate client with invalid domain name
     */
    @Test(expectedExceptions = UnknownHostException.class)
    public void testInvalidDomainName() throws Exception {

        new ControlClientImpl("@%$!", (short)12);
    }

    /**
     * Valid domain name
     */
    public void testValidDomainName() throws Exception {

        // these values are important. This test actually establishes
        // a socket with Google. There must be a better way to do this.
        new ControlClientImpl("google.com", (short)80);
    }
}
