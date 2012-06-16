/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.datastructures.coordinates;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Nitin Verma
 */
public class CoordinatesTest {
    
    private static final Log LOGGER = LogFactory.getLog(CoordinatesTest.class);
    
    @Test
    public void creationTest() {
        {
            try {
                final Coordinates coordinates = new Coordinates(0, 0, 0, 0);
            } catch (final Throwable th) {
                Assert.fail("Failed to create 'Coordinates'", th);
            }
        }
        
    }
    
    @Test
    public void originTest() {
        {
            final Coordinates coordinates1 = new Coordinates((Collection) null);
            final Coordinates coordinates2 = new Coordinates(0, 0, 0, 0);
            
            Assert.assertEquals(coordinates1.compareTo(coordinates2), 0);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates((Collection) null);
            final Coordinates coordinates2 = new Coordinates((Integer[]) null);
            
            Assert.assertEquals(coordinates1.compareTo(coordinates2), 0);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates((Integer[]) null);
            final Coordinates coordinates2 = new Coordinates((Integer[]) null);
            
            Assert.assertEquals(coordinates1.compareTo(coordinates2), 0);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates(1, 2, null, 7);
            final Coordinates coordinates2 = new Coordinates(1, 2, 0, 7);
            
            Assert.assertEquals(coordinates1.compareTo(coordinates2), 0);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates(0, 0, 0);
            final Coordinates coordinates2 = new Coordinates(0, 0, 0, 0, 0, 0, 0, 0);
            
            Assert.assertEquals(coordinates1.compareTo(coordinates2), 0);
        }
    }
    
    @Test
    public void compareTest() {
        {
            final Coordinates coordinates1 = new Coordinates(1, 2, null, 6);
            final Coordinates coordinates2 = new Coordinates(1, 2, 0, 7);
            
            Assert.assertEquals(coordinates1.compareTo(coordinates2), -1);
            Assert.assertEquals(coordinates2.compareTo(coordinates1), 1);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates(1, 3, null, 9);
            final Coordinates coordinates2 = new Coordinates(1, 2, 0, 7);
            
            Assert.assertEquals(coordinates1.compareTo(coordinates2), 1);
            Assert.assertEquals(coordinates2.compareTo(coordinates1), -1);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates(1, 3, null, 9);
            final Coordinates coordinates2 = new Coordinates(1, 2, 0, 7, 9, 10);
            
            Assert.assertEquals(coordinates1.compareTo(coordinates2), 1);
            Assert.assertEquals(coordinates2.compareTo(coordinates1), -1);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates(15, null, 9);
            final Coordinates coordinates2 = new Coordinates(15, 0, 9, 0, 0, 0, 0, 0);
            
            Assert.assertEquals(coordinates1.compareTo(coordinates2), 0);
            Assert.assertEquals(coordinates2.compareTo(coordinates1), 0);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates((Collection<Integer>)null);
            try {
                coordinates1.compareTo(null);
                Assert.fail("Should have not reached this line");
            } catch (final NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }
        }
    }
    
    @Test
    public void addTest() {
        {
            final Coordinates coordinates1 = new Coordinates(1, 2, null, 6);
            final Coordinates coordinates2 = new Coordinates(1, 0, 0, 1);
            final Coordinates coordinates3 = new Coordinates(2, 2, 0, 7);
            
            Assert.assertEquals(coordinates1.add(coordinates2).compareTo(coordinates3), 0);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates(1, 3, null, 9);
            final Coordinates coordinates2 = new Coordinates(-2, -1, -8, 7, 9, -10);
            final Coordinates coordinates3 = new Coordinates(-1, 2, -8, 16, 9, -10);
            
            Assert.assertEquals(coordinates1.add(coordinates2).compareTo(coordinates3), 0);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates(1, 3, null, 9);
            final Coordinates coordinates2 = new Coordinates(1, 2, 0, 7, 9, 10);
            final Coordinates coordinates3 = new Coordinates(2, 5, 0, 16, 9, 10);
            
            Assert.assertEquals(coordinates1.add(coordinates2).compareTo(coordinates3), 0);
        }
        
        {
            final Coordinates coordinates1 = new Coordinates(1, 3, null, 9);
            try {
                coordinates1.add(null);
                Assert.fail("Should have not reached this line");
            } catch (final NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }
        }
        
    }
}
