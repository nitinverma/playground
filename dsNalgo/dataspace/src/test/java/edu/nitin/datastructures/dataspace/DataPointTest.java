/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.datastructures.dataspace;

import edu.nitin.datastructures.coordinates.Coordinates;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Nitin Verma
 */
public class DataPointTest {
    
    @Test
    public void creationTest() {
        
        {
            try {
                final DataPoint<Character> dataPoint = new DataPoint<Character>('A',new Coordinates(1, 2, null, 7));
            }
            catch(final Throwable th) {
                Assert.fail("Failed to create 'DataPoint'", th);
            }
        }
    }
    
    @Test
    public void compareTest() {
        {
            final DataPoint<Character> dataPoint1 = new DataPoint<Character>('A',new Coordinates(1, 2, null, 7));
            final DataPoint<Character> dataPoint2 = new DataPoint<Character>('A',new Coordinates(1, 2, 0, 7));

            Assert.assertEquals(dataPoint1.compareTo(dataPoint2), 0);
        }
        
        {
            final DataPoint<Character> dataPoint1 = new DataPoint<Character>('A',new Coordinates(1));
            final DataPoint<Character> dataPoint2 = new DataPoint<Character>('G',new Coordinates(0));

            Assert.assertEquals(dataPoint1.compareTo(dataPoint2), 1);
            Assert.assertEquals(dataPoint2.compareTo(dataPoint1), -1);
        }
        
        {
            final Character [][] chars = {{'G'},{'A'}};
            
            final DataPoint<Character> dataPoint1 = new DataPoint<Character>(chars[1][0]/*y,x*/,new Coordinates(0,1)/*x,y*/);
            final DataPoint<Character> dataPoint2 = new DataPoint<Character>(chars[0][0],new Coordinates(0,0));

            Assert.assertEquals(dataPoint1.compareTo(dataPoint2), 1);
            Assert.assertEquals(dataPoint2.compareTo(dataPoint1), -1);
        }
    }
    
}
