/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nitin.threadlocal;

/**
 *
 * @author nitinv
 */
public interface AppThreadContext<K,V> {
    public Object getObject(final K key);
    public void setObject(final K key, final V value);
    public void clear();
}
