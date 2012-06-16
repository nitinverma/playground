/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.httpclient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.net.SocketFactory;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * An example that performs GETs from multiple threads.
 *
 * @author Michael Becke
 */
public class MultiThreadedExample {

    /**
     * Constructor for MultiThreadedExample.
     */
    public MultiThreadedExample() {
        super();
    }

    public static void runThis() throws InterruptedException {

        // Create an HttpClient with the MultiThreadedHttpConnectionManager.
        // This connection manager must be used if more than one thread will
        // be using the HttpClient.
        final MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
        multiThreadedHttpConnectionManager.getParams().setDefaultMaxConnectionsPerHost(1);
        multiThreadedHttpConnectionManager.getParams().setMaxTotalConnections(100);
        multiThreadedHttpConnectionManager.getParams().setConnectionTimeout(1000);

        HttpClient httpClient = new HttpClient(multiThreadedHttpConnectionManager);
        // Set the default host/protocol for the methods to connect to.
        // This value will only be used if the methods are not given an absolute URI
        httpClient.getHostConfiguration().setHost("hc.apache.org", 80, "http");
        httpClient.getParams().setSoTimeout(1000);


        final List<String> urlList = new ArrayList();
        for (int i = 0; i < 100; i++) {
            urlList.add("/");
        }
        // create an array of URIs to perform GETs on
        String[] urisToGet = urlList.toArray(new String[0]);

        /*= {
        "/",
        "/httpclient-3.x/status.html",
        "/httpclient-3.x/methods/",
        "http://svn.apache.org/viewvc/httpcomponents/oac.hc3x/"
        };*/

        // create a thread for each URI
        GetThread[] threads = new GetThread[urisToGet.length];
        for (int i = 0; i < threads.length; i++) {
            GetMethod get = new GetMethod(urisToGet[i]);
            get.setFollowRedirects(true);
            threads[i] = new GetThread(httpClient, get, i + 1);
        }

        // start the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }

        // join the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].join();
        }

    }

    /**
     * A thread that performs a GET.
     */
    static class GetThread extends Thread {

        private HttpClient httpClient;
        private GetMethod method;
        private int id;

        public GetThread(HttpClient httpClient, GetMethod method, int id) {
            this.httpClient = httpClient;
            this.method = method;
            this.id = id;
        }

        /**
         * Executes the GetMethod and prints some satus information.
         */
        public void run() {

            for (int i = 0; i < 1; i++) {
                InputStream inpurtStream = null;
                try {

                    System.out.println(id + " - about to get something from " + method.getURI());
                    // execute the method
                    httpClient.executeMethod(method);

                    System.out.println(id + " - get executed" + SocketFactory.getDefault().getClass());
                    // get the response body as an array of bytes
                    //byte[] bytes = method.getResponseBody();
                    inpurtStream = method.getResponseBodyAsStream();

                    final String readString = readInputStream(inpurtStream);

                    final byte[] bytes = readString.getBytes();

                    System.out.println(id + " - " + bytes.length + " bytes read");

                } catch (Exception e) {

                    System.out.println(id + " - error: " + e);
                    e.printStackTrace();

                } finally {
                    // always release the connection after we're done
                    if ( inpurtStream != null )  {try {
                            inpurtStream.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
}
                    //method.releaseConnection();
                    //System.out.println(id + " - connection released");
                    //System.out.println(id + " - connection not released");
                }
            }
        }

        private static String readInputStream(final InputStream inputStream) throws IOException {
            final StringBuilder stringBuilder = new StringBuilder();
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            final byte[] bytes = new byte[1024];
            int readCount = 0;

            do {
                readCount = bufferedInputStream.read(bytes);
                if (readCount > 0) {
                    stringBuilder.append(new String(bytes, 0, readCount));
                }
            } while (readCount == 1024);

            return stringBuilder.toString();
        }
    }
}
