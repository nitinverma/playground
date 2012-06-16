/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.threadlocal;

/**
 *
 * @author nitinv
 */
public class MyMain {

    public static void main(final String[] args) throws InterruptedException {
        {
            final MyThread myThread = new MyThread();
            myThread.start();
            myThread.join();
        }
        
        {
            final MyThread myThread = new MyThread();
            myThread.start();
            myThread.join();
        }
    }
}
