/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nitin.httpclient;

import org.testng.annotations.Test;



/**
 *
 * @author nitinv
 */
public class TestMultiThreadedExample {

    @Test
    public void test() throws InterruptedException {
        MultiThreadedExample.runThis();
    }
}
