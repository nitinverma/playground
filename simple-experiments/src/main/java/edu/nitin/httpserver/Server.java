/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.httpserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.InterruptedException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author nitinv
 */
public class Server {

    private static final Log LOGGER = LogFactory.getLog(Server.class);
    private String host;
    private int port;
    private ServerSocket serverSocket = null;
    private Thread serverThread = null;
    private List<Thread> socketThreads = new ArrayList<Thread>();

    public Server(final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    public Thread start() {
        final Server thisServer = this;
        final Runnable theServerThreadRunnable = new Runnable() {

            public void run() {
                try {
                    run0();
                } catch (final Throwable throwable) {
                    LOGGER.error("theServerThreadRunnable gets an exception", throwable);
                }
            }

            public void run0() throws IOException, InterruptedException {
                thisServer.run();
            }
        };

        serverThread = new Thread(theServerThreadRunnable, "TheServerThread");
        serverThread.start();

        return serverThread;
    }

    public void run() throws IOException, InterruptedException {
        final InetAddress inetAddress = InetAddress.getByName(host);
        final SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
        serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(socketAddress, 10);
        LOGGER.info("serverSocket " + serverSocket);

        final Runnable serverThreadRunnable = new Runnable() {

            public void run() {
                try {
                    boolean isInterrupted = Thread.currentThread().isInterrupted();
                    while (!isInterrupted) {

                        try {
                            run0();
                        } catch (final InterruptedException interruptedException) {
                            throw interruptedException;
                        } catch (final Throwable throwable) {
                            LOGGER.warn("serverThreadRunnable gets an exception", throwable);
                        }

                        if (!isInterrupted) {
                            isInterrupted = Thread.currentThread().isInterrupted();
                        }
                    }
                } catch (final InterruptedException interruptedException) {
                    LOGGER.info("Got interruptedException", interruptedException);
                }
            }

            public void run0() throws IOException, InterruptedException {
                final Socket socket = serverSocket.accept();
                LOGGER.info("socket " + socket);
                try {
                    final InputStream inputStream = socket.getInputStream();
                    final String content = readInputStream(inputStream);
                    LOGGER.info(content);
                    final boolean isRedirect = content.contains("/redirect");
                    final boolean has100 = content.contains("100-continue");

                    if (!isRedirect) {
                        final OutputStream outputStream = socket.getOutputStream();
                        try {
                            outputStream.write("HTTP/1.1 307 Temporary Redirect\r\n".getBytes());
                            outputStream.write(("Location: http://" + host + ":" + port + "/redirect \r\n").getBytes());
                            outputStream.write("\r\n".getBytes());
                        } finally {
                            outputStream.close();
                        }

                    } else if (has100) {
                        final OutputStream outputStream = socket.getOutputStream();
                        try {
                            outputStream.write("HTTP/1.1 100 Continue\r\n".getBytes());
                            outputStream.write("\r\n".getBytes());
                            final String content2 = readInputStream(inputStream);
                            outputStream.write("<html><body>done</body></html>\r\n".getBytes());
                            LOGGER.info(content2);
                        } finally {
                            outputStream.close();
                        }
                    } else {
                        final OutputStream outputStream = socket.getOutputStream();
                        try {
                            outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                            outputStream.write("\r\n".getBytes());
                            outputStream.write("<html><body>done</body></html>\r\n".getBytes());
                        } finally {
                            outputStream.close();
                        }
                    }


                } finally {
                    socket.close();
                }

            }
        };

        socketThreads.add(new Thread(serverThreadRunnable, "socketThread-1"));
        socketThreads.add(new Thread(serverThreadRunnable, "socketThread-2"));

        for (final Thread thread : socketThreads) {
            thread.start();
        }

        for (final Thread thread : socketThreads) {
            thread.join();
        }
    }

    public void stop() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
        if (serverThread != null) {
            serverThread.interrupt();
        }
        for (final Thread thread : socketThreads) {
            thread.interrupt();
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
