/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.httpserver;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

/**
 *
 * @author nitinv
 */
public class TestServer {

    private static final Log LOGGER = LogFactory.getLog(Server.class);

    @Test
    public void test() throws IOException, InterruptedException {
        Server server = new Server("localhost", 12345);
        try {
            LOGGER.info("calling start");
            server.start();
            LOGGER.info("start called");
            Thread.sleep(10000);
        } finally {
            server.stop();
        }
    }
}
