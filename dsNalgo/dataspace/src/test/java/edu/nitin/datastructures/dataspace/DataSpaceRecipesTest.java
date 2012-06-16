/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.datastructures.dataspace;

import edu.nitin.datastructures.coordinates.Coordinates;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Nitin Verma
 */
public class DataSpaceRecipesTest {

    private static final Log LOGGER = LogFactory.getLog(DataSpaceRecipesTest.class);

    @Test
    public void testCreateDataSpace() {
        final DataSpaceComparator dataSpaceComparator = new DataSpaceComparator();

        {
            final DataSpace<Character> dataSpace = DataSpaceRecipes.createDataSpace(
                    new Character[]{'A'});
            LOGGER.info(dataSpace);

            Assert.assertEquals(dataSpace.size(), 1);
            final DataPoint<Character> dataPoint = dataSpace.iterator().next();
            Assert.assertEquals(dataSpaceComparator.compare(dataPoint, new Coordinates(0)), 0);
            Assert.assertEquals(dataPoint.getData().compareTo('A'), 0);

        }

        {
            final DataSpace<Character> dataSpace = DataSpaceRecipes.createDataSpace(
                    new Character[][]{{'A'}, {'B'}});

            LOGGER.info(dataSpace);

            Assert.assertEquals(dataSpace.size(), 2);



            final Iterator<DataPoint<Character>> iterator = dataSpace.iterator();
            {

                final DataPoint<Character> dataPoint = iterator.next();
                Assert.assertEquals(dataSpaceComparator.compare(dataPoint, new Coordinates(0, 0)), 0);
                Assert.assertEquals(dataPoint.getData().compareTo('A'), 0);

            }

            {

                final DataPoint<Character> dataPoint = iterator.next();
                Assert.assertEquals(dataSpaceComparator.compare(dataPoint, new Coordinates(0, 1)), 0);
                Assert.assertEquals(dataPoint.getData().compareTo('B'), 0);

            }
        }

        {
            final DataSpace<Character> dataSpace = DataSpaceRecipes.createDataSpace(
                    new Character[][][]{
                        {
                            {'A'}
                        },
                        {
                            {'B'}
                        },
                        {
                            {'C'}, {'D'}
                        }
                    });

            LOGGER.info(dataSpace);

            Assert.assertEquals(dataSpace.size(), 4);



            final Iterator<DataPoint<Character>> iterator = dataSpace.iterator();
            {

                final DataPoint<Character> dataPoint = iterator.next();
                Assert.assertEquals(dataSpaceComparator.compare(dataPoint, new Coordinates(0, 0, 0)), 0);
                Assert.assertEquals(dataPoint.getData().compareTo('A'), 0);

            }

            {

                final DataPoint<Character> dataPoint = iterator.next();
                Assert.assertEquals(dataSpaceComparator.compare(dataPoint, new Coordinates(0, 0, 1)), 0);
                Assert.assertEquals(dataPoint.getData().compareTo('B'), 0);

            }
            {

                final DataPoint<Character> dataPoint = iterator.next();
                Assert.assertEquals(dataSpaceComparator.compare(dataPoint, new Coordinates(0, 0, 2)), 0);
                Assert.assertEquals(dataPoint.getData().compareTo('C'), 0);

            }

            {

                final DataPoint<Character> dataPoint = iterator.next();
                Assert.assertEquals(dataSpaceComparator.compare(dataPoint, new Coordinates(0, 1, 2)), 0);
                Assert.assertEquals(dataPoint.getData().compareTo('D'), 0);

            }
        }
    }

    @Test
    public void testGetAdjustmentedCoordinates() {

        {
            final Set<Coordinates> toAdjust = new TreeSet<Coordinates>() {

                {
                    add(new Coordinates(0));
                }
            };

            final Set<Integer> adjustments = null;

            try {
                final Set<Coordinates> adjusted = DataSpaceRecipes.getAdjustmentedCoordinates(
                        10, toAdjust, adjustments);
                Assert.fail("Should have not reached this line");
            } catch (final NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }


        }

        {
            final Set<Coordinates> toAdjust = null;

            final Set<Integer> adjustments = new TreeSet<Integer>() {

                {
                    add(-1);
                    add(0);
                    add(1);
                }
            };

            try {
                final Set<Coordinates> adjusted = DataSpaceRecipes.getAdjustmentedCoordinates(
                        10, toAdjust, adjustments);
                Assert.fail("Should have not reached this line");
            } catch (final NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }

        }

        {
            final Set<Coordinates> toAdjust = new TreeSet<Coordinates>() {

                {
                    add(new Coordinates(0));
                }
            };

            final Set<Integer> adjustments = new TreeSet<Integer>() {

                {
                    add(-1);
                    add(0);
                    add(1);
                }
            };

            final Set<Coordinates> adjusted = DataSpaceRecipes.getAdjustmentedCoordinates(
                    10, toAdjust, adjustments);

            LOGGER.debug("Adjusted Coordinates : " + adjusted);

            Assert.assertEquals(adjusted.size(), adjustments.size() * toAdjust.size());


        }
    }

    @Test
    public void testGetOriginAdjacentCoordinates() {

        {
            final int dimensions = 0;
            try {
                final Set<Coordinates> adjustmentCoordinatesSet =
                        DataSpaceRecipes.getOriginAdjacentCoordinates(dimensions);
                Assert.fail("Should have not reached this line");
            } catch (final IllegalArgumentException npe) {
                LOGGER.trace("PASS", npe);
            }

        }


        {

            final int dimensions = 2;

            final Set<Coordinates> adjustmentCoordinatesSet = DataSpaceRecipes.getOriginAdjacentCoordinates(dimensions);

            LOGGER.debug("Adjustment Coordinates : ****\n" + adjustmentCoordinatesSet + "\n****");


            Assert.assertEquals(adjustmentCoordinatesSet.size(), ((int) Math.pow(3, dimensions)) - 1);


        }

        {

            final int dimensions = 6;

            final Set<Coordinates> adjustmentCoordinatesSet = DataSpaceRecipes.getOriginAdjacentCoordinates(dimensions);

            Assert.assertEquals(adjustmentCoordinatesSet.size(), ((int) Math.pow(3, dimensions)) - 1);


        }
    }

    @Test
    public void testGetAdjacentCoordinates() {
        {

            final int dimensions = 2;

            final Set<Coordinates> adjacentCoordinates = DataSpaceRecipes.getAdjacentCoordinates(new Coordinates(0, 1, 7), dimensions);

            LOGGER.debug("Adjacent Coordinates : ****\n" + adjacentCoordinates + "\n****");


            Assert.assertEquals(adjacentCoordinates.size(), ((int) Math.pow(3, dimensions)) - 1);


        }

        {

            final int dimensions = 6;

            final Set<Coordinates> adjacentCoordinates = DataSpaceRecipes.getAdjacentCoordinates(new Coordinates(0, 1, 7), dimensions);

            //LOGGER.debug("Adjacent Coordinates : ****\n" + adjacentDataPoints + "\n****");


            Assert.assertEquals(adjacentCoordinates.size(), ((int) Math.pow(3, dimensions)) - 1);


        }
    }

    @Test
    public void testGetAdjacentDataPoint() {
        {

            final DataSpace<Character> dataSpace = DataSpaceRecipes.createDataSpace(
                    new Character[][][]{
                        {
                            {'A'}
                        },
                        {
                            {'B'}
                        },
                        {
                            {'C'}, {'D'}
                        }
                    });

            final int dimensions = 3;

            final Set<DataPoint<Character>> adjacentDataPoints =
                    DataSpaceRecipes.getAdjacentDataPoints(new Coordinates(0, 0, 1), dimensions, dataSpace);

            LOGGER.debug("Adjacent DataPoints : ****\n" + adjacentDataPoints + "\n****");


            Assert.assertEquals(adjacentDataPoints.size(), 3);


        }

        {

            final DataSpace<Character> dataSpace = DataSpaceRecipes.createDataSpace(
                    new Character[][][]{
                        {
                            {'A', 'J'}
                        },
                        {
                            {'B'}
                        },
                        {
                            {'D'}, {'E'}
                        }
                    });

            final int dimensions = 3;

            final Set<DataPoint<Character>> adjacentDataPoints =
                    DataSpaceRecipes.getAdjacentDataPoints(new Coordinates(0, 0, 2), dimensions, dataSpace);

            LOGGER.debug("Adjacent DataPoints : ****\n" + adjacentDataPoints + "\n****");


            Assert.assertEquals(adjacentDataPoints.size(), 2);


        }
    }
}
