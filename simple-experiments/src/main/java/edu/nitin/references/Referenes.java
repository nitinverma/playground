/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nitin.references;

import java.util.Map;
import java.util.WeakHashMap;

/**
 *
 * @author nitinv
 */
public class Referenes {
    public static void main ( final String [] args ) throws InterruptedException {
        final Map<Long, Object> map = new WeakHashMap<Long, Object>();
        for (long i = 0; i < 10000l; i++) {
            System.out.println(i + ":" + map.size());
            map.put(i, new Object());
        }
    }
}
