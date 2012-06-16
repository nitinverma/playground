/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.datastructures.dataspace;

import org.testng.annotations.Test;
import org.testng.Assert;

/**
 *
 * @author nitinv
 */
public class DataSpaceTest {
    
    @Test
    public void creationTest() {
        {
            try {
                final DataSpace<Integer> dataSpace = new DataSpace<Integer>();
            }
            catch(final Throwable th) {
                Assert.fail("Failed to create 'DataSpace'", th);
            }
        }
    }    
}
