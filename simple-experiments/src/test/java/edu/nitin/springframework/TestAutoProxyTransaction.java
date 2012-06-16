/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nitin.springframework;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author nitinv
 */
public class TestAutoProxyTransaction extends TestProxyTransaction {

    @Override
    protected Resource[] getResource() {
        return new Resource[] {new ClassPathResource("database-autoproxy.xml")};
    }

}
