/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.algorithms.match;

import edu.nitin.datastructures.coordinates.Coordinates;
import edu.nitin.datastructures.dataspace.DataPoint;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Nitin Verma
 */
public class SearchStringContainerTest {

    private static final Log LOGGER = LogFactory.getLog(SearchStringContainerTest.class);

    @Test
    public void creationTest() {
        {
            try {
                final SearchStringContainer searchStringContainer = new SearchStringContainer("TAZBLA9k");
            } catch (final Throwable th) {
                Assert.fail("Failed to create 'SearchStringContainer'", th);
            }
        }

    }

    @Test
    public void testMatches() {
        {
            final SearchStringContainer searchStringContainer = new SearchStringContainer("TAZBLA9k");
            try {
                final Set<DataPoint<Character>> matchedDataPoints = searchStringContainer.matches(null);
                Assert.fail("Should not reach this line.");
            } catch (NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }
        }

        {
            try {
                final SearchStringContainer searchStringContainer = new SearchStringContainer(null);
                Assert.fail("Should not reach this line.");
            } catch (NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }
        }

        {
            final SearchStringContainer searchStringContainer = new SearchStringContainer("TAZBLA9k");
            final Set<DataPoint<Character>> matchedDataPoints = searchStringContainer.matches(new DataPoint<Character>('A', new Coordinates(0)));
            Assert.assertEquals(matchedDataPoints.size(), 2);
        }
        
        {
            final SearchStringContainer searchStringContainer = new SearchStringContainer("9TAZBLA9kA9");
            final Set<DataPoint<Character>> matchedDataPoints = searchStringContainer.matches(new DataPoint<Character>('9', new Coordinates(0)));
            Assert.assertEquals(matchedDataPoints.size(), 3);
        }
    }
    
    @Test
    public void testGetLeft() {
        {
            final SearchStringContainer searchStringContainer = new SearchStringContainer("TAZBLA9k");
            try {
                searchStringContainer.getLeft(null);
                Assert.fail("Should not reach this line.");
            } catch (NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }
        }
        
        {
            final SearchStringContainer searchStringContainer = new SearchStringContainer("9TAZBLA9kA9");
            final Set<DataPoint<Character>> matchedDataPoints = searchStringContainer.matches(new DataPoint<Character>('9', new Coordinates(0)));
            Assert.assertEquals(matchedDataPoints.size(), 3);
            for (final DataPoint dataPoint : matchedDataPoints) {
                final List<DataPoint<Character>> queue = searchStringContainer.getLeft(dataPoint);
                LOGGER.debug("Left queue : " + "|" + dataPoint +" | "+ queue);
            }
        }
        
        {
            final SearchStringContainer searchStringContainer = new SearchStringContainer("*9TAZ9BLA9kA9*");
            final Set<DataPoint<Character>> matchedDataPoints = searchStringContainer.matches(new DataPoint<Character>('9', new Coordinates(0)));
            Assert.assertEquals(matchedDataPoints.size(), 4);
            for (final DataPoint dataPoint : matchedDataPoints) {
                final List<DataPoint<Character>> queue = searchStringContainer.getLeft(dataPoint);
                LOGGER.debug("Left queue : " + "|" + dataPoint + " | "+ queue);
            }
        }
    }
    
    
    @Test
    public void testRightLeft() {
        {
            final SearchStringContainer searchStringContainer = new SearchStringContainer("TAZBLA9k");
            try {
                searchStringContainer.getRight(null);
                Assert.fail("Should not reach this line.");
            } catch (NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }
        }
        
        {
            final SearchStringContainer searchStringContainer = new SearchStringContainer("9TAZBLA9kA9");
            final Set<DataPoint<Character>> matchedDataPoints = searchStringContainer.matches(new DataPoint<Character>('9', new Coordinates(0)));
            Assert.assertEquals(matchedDataPoints.size(), 3);
            for (final DataPoint dataPoint : matchedDataPoints) {
                final List<DataPoint<Character>> queue = searchStringContainer.getRight(dataPoint);
                LOGGER.debug("Right queue : " + "|" + dataPoint +" | "+ queue);
            }
        }
        
        {
            final SearchStringContainer searchStringContainer = new SearchStringContainer("*9TAZ9BLA9kA9*");
            final Set<DataPoint<Character>> matchedDataPoints = searchStringContainer.matches(new DataPoint<Character>('9', new Coordinates(0)));
            Assert.assertEquals(matchedDataPoints.size(), 4);
            for (final DataPoint dataPoint : matchedDataPoints) {
                final List<DataPoint<Character>> queue = searchStringContainer.getRight(dataPoint);
                LOGGER.debug("Right queue : " + "|" + dataPoint +" | "+ queue);
            }
        }
    }
}
