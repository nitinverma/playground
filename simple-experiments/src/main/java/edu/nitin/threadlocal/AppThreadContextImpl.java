/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.threadlocal;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nitinv
 */
public class AppThreadContextImpl implements AppThreadContext<String, Object> {

    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>() {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    public Object getObject(final String key) {
        return threadLocal.get().get(key);
    }

    public void setObject(final String key, final Object value) {
        threadLocal.get().put(key, value);
    }

    public void clear() {
        threadLocal.get().clear();
    }
    
}
