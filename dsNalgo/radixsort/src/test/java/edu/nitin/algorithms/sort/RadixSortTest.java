/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.algorithms.sort;

import edu.nitin.datastructures.dataspace.DataPoint;
import edu.nitin.datastructures.dataspace.DataSpaceRecipes;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Nitin Verma
 */
public class RadixSortTest {

    private static final Log LOGGER = LogFactory.getLog(RadixSortTest.class);

    @Test
    public void testSort() {
        final RadixSort radixSort = new RadixSortImpl();

        final List<DataPoint<Character>> dataPoints = DataSpaceRecipes.createDataPoints(
                new Character[]{'B', 'ॐ', 'A', 'B', 'ॐ', '9', 'k', 'K', (char) 1, (char) 0, (char) 0, (char) ~0, (char) -1});

        radixSort.sort(dataPoints);

    }

    @Test
    public void testInvalidInput() {
        {
            final RadixSort radixSort = new RadixSortImpl();
            try {
                radixSort.sort(null);
                Assert.fail("Should not reach this line.");
            } catch (NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }
        }
    }
}
