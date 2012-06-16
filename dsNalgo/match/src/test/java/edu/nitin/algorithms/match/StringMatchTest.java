package edu.nitin.algorithms.match;

import edu.nitin.datastructures.coordinates.Coordinates;
import edu.nitin.datastructures.dataspace.DataPoint;
import edu.nitin.datastructures.dataspace.DataSpaceComparator;
import edu.nitin.datastructures.dataspace.DataSpaceRecipes;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Nitin Verma
 */
public class StringMatchTest {

    private static final Log LOGGER = LogFactory.getLog(StringMatchTest.class);

    @Test
    public void test0() {
        final String searchString = "ADBCD";
        final Character[][] searchSpace = new Character[][]{
            {'B', 'D', 'B', 'S', 'B', 'C'},
            {'X', 'X', 'A', 'D', 'B', 'X', 'D', 'X'},
            {'X', 'B', 'S', 'B'}
        };

        final StringMatch stringMatch = new StringMatch(2, DataSpaceRecipes.createDataSpace(searchSpace));
        final Set<DataPoint<Character>> matchedSet = stringMatch.match(searchString);

        // [<'A'-[2, 1]>, <'D'-[3, 1]>, <'B'-[4, 1]> || <'B'-[4, 0]>, <'C'-[5, 0]>, <'D'-[6, 1]>]
        final DataSpaceComparator dataSpaceComparator = new DataSpaceComparator();
        Assert.assertEquals(matchedSet.size(), searchString.length());
        final Iterator<DataPoint<Character>> matchedSetIterator = matchedSet.iterator();

        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(2, 1))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(3, 1))));
        Assert.assertTrue(
                matchedSet.contains(new DataPoint<Character>('*', new Coordinates(4, 1)))
                || matchedSet.contains(new DataPoint<Character>('*', new Coordinates(4, 0))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(5, 0))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(6, 1))));

        LOGGER.debug("===test0===end==");
    }

    @Test
    public void test2() {
        final String searchString = "NBBNB";
        final Character[][] searchSpace = new Character[][]{
            {'N', 'B', 'k', 'S', 'u', 'C'},
            {'B', 'N', 'i', 'G', 'K', 'j', 'D', 'X'},
            {'X', 'n', 'S', 'B'}
        };

        final StringMatch stringMatch = new StringMatch(2, DataSpaceRecipes.createDataSpace(searchSpace));
        final Set<DataPoint<Character>> matchedSet = stringMatch.match(searchString);

        // No match
        Assert.assertEquals(matchedSet.size(), 0);

        LOGGER.debug("===test2===end==");
    }

    @Test
    public void test3() {
        final String searchString = "NITIN";
        final Character[][] searchSpace = new Character[][]{
            {'B', 'I', 'T', 'S', 'u', 'C'},
            {'N', 'N', 'I', 'G', 'K', 'j', 'D', 'X'},
            {'X', 'n', 'S', 'B'}
        };

        final StringMatch stringMatch = new StringMatch(2, DataSpaceRecipes.createDataSpace(searchSpace));
        final Set<DataPoint<Character>> matchedSet = stringMatch.match(searchString);

        // [<'N'-[0, 1]>, <'I'-[1, 0]>, <'T'-[2, 0]>, <'I'-[2, 1]>], <'N'-[1, 1]>
        Assert.assertEquals(matchedSet.size(), searchString.length());
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(0, 1))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(1, 0))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(2, 0))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(2, 1))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(1, 1))));

        LOGGER.debug("===test3===end==");
    }

    @Test
    public void test4() {
        final String searchString = "NITIN";
        final Character[][][] searchSpace = new Character[][][]{
            {
                {'1', 'N', '3', 'S', 'u', 'C'},
                {'0', '2', '0', 'G', 'K', 'j', 'D', 'X'},
                {'X', 'n', 'S', 'B'}
            },
            {
                {'1', 'I', 'T', '9'},
                {'2', 'X', 'I', '8'},
                {'3', '4', '6', '7'}
            },
            {
                {'1', '0', 'N', '9'},
                {'2', '9', '8', '8'},
                {'3', '4', '6', '7'}
            },};

        final StringMatch stringMatch = new StringMatch(3, DataSpaceRecipes.createDataSpace(searchSpace));
        final Set<DataPoint<Character>> matchedSet = stringMatch.match(searchString);

        //[<'N'-[1, 0, 0]>, <'I'-[1, 0, 1]>, <'T'-[2, 0, 1]>, <'N'-[2, 0, 2]>, <'I'-[2, 1, 1]>]
        Assert.assertEquals(matchedSet.size(), searchString.length());
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(1, 0, 0))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(1, 0, 1))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(2, 0, 1))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(2, 1, 1))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(2, 0, 2))));

        LOGGER.debug("===test4===end==");
    }

    @Test
    public void test5() {
        final String searchString = "AMAZON";
        final Character[][][][] searchSpace = new Character[][][][]{
            {
                {
                    {'A', 'L', '0', 'S', 'u', 'C'},
                    {'0', '0', '0', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                },
                {
                    {'B', '0', '0', 'S'},
                    {'K', 'a', 'I', 'G'},
                    {'X', 'n', 'm', 'B'}
                },
                {
                    {'B', 'n', '0', 'S', 'u', 'C'},
                    {'0', '0', '0', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                }
            },
            {
                {
                    {'M', '9', '0', 'S', 'u', 'C'},
                    {'0', '0', 'T', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'z', 'B'}
                },
                {
                    {'B', 'I', 'T', 'S'},
                    {'K', 'i', 'o', 'G'},
                    {'X', 'n', 'S', 'B'}
                },
                {
                    {'B', 'N', '0', 'S', 'u', 'C'},
                    {'0', '0', '0', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                }
            },
            {
                {
                    {'A', '1', '9', 'S', 'u', 'C'},
                    {'9', '8', '9', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                },
                {
                    {'B', 'Z', 'T', 'S'},
                    {'K', '9', 'I', 'G'},
                    {'X', 'n', 'S', 'B'}
                },
                {
                    {'O', '8', '0', 'S', 'u', 'C'},
                    {'0', '0', '0', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                }
            }
        };

        final StringMatch stringMatch = new StringMatch(4, DataSpaceRecipes.createDataSpace(searchSpace));
        final Set<DataPoint<Character>> matchedSet = stringMatch.match(searchString);

        // [<'A'-[0, 0, 0, 0]>, <'M'-[0, 0, 0, 1]>, <'A'-[0, 0, 0, 2]>, <'Z'-[1, 0, 1, 2]>, <'O'-[0, 0, 2, 2]> , <'N'-[1, 0, 2, 1]>]
        Assert.assertEquals(matchedSet.size(), searchString.length());
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(0, 0, 0, 0))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(0, 0, 0, 1))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(0, 0, 0, 2))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(1, 0, 1, 2))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(0, 0, 2, 2))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(1, 0, 2, 1))));

        LOGGER.debug("===test5===end==");
    }

    @Test
    public void test6() {
        final String searchString = "ॐॐॐॐॐॐ";
        final Character[][][][] searchSpace = new Character[][][][]{
            {
                {
                    {'ॐ'/*
                         * 0,0,0,0
                         */, 'N', '0', 'S', 'u', 'C'},
                    {'0', '0', '0', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                },
                {
                    {'B', '0', '0', 'S'},
                    {'K', 'A', 'I', 'G'},
                    {'X', 'n', 'M', 'B'}
                },
                {
                    {'B', 'N', '0', 'S', 'u', 'C'},
                    {'0', '0', '0', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                }
            },
            {
                {
                    {'ॐ'/*
                         * 0,0,0,1
                         */, 'N', '0', 'S', 'u', 'C'},
                    {'0', '0', 'T', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'Z', 'B'}
                },
                {
                    {'B', 'I', 'T', 'S'},
                    {'K', 'N', 'O', 'G'},
                    {'X', 'n', 'S', 'B'}
                },
                {
                    {'B', 'ॐ'/*
                         * 1,0,2,1
                         */, '0', 'S', 'u', 'C'},
                    {'0', '0', '0', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                }
            },
            {
                {
                    {'ॐ'/*
                         * 0,0,0,2
                         */, 'N', '0', 'S', 'u', 'C'},
                    {'0', '0', '0', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                },
                {
                    {'X', 'ॐ'/*
                         * 1,0,1,2
                         */, 'T', 'S'},
                    {'K', 'N', 'I', 'G'},
                    {'X', 'n', 'S', 'B'}
                },
                {
                    {'ॐ'/*
                         * 0,0,2,2
                         */, 'N', '0', 'S', 'u', 'C'},
                    {'N', '0', '0', 'G', 'K', 'j', 'D', 'X'},
                    {'X', 'n', 'S', 'B'}
                }
            }
        };

        final StringMatch stringMatch = new StringMatch(4, DataSpaceRecipes.createDataSpace(searchSpace));
        final Set<DataPoint<Character>> matchedSet = stringMatch.match(searchString);

        // [<'A'-[0, 0, 0, 0]>, <'M'-[0, 0, 0, 1]>, <'A'-[0, 0, 0, 2]>, <'O'-[0, 0, 2, 2]>, <'Z'-[1, 0, 1, 2]>, <'N'-[1, 0, 2, 1]>]
        Assert.assertEquals(matchedSet.size(), searchString.length());
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(0, 0, 0, 0))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(0, 0, 0, 1))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(0, 0, 0, 2))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(0, 0, 2, 2))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(1, 0, 1, 2))));
        Assert.assertTrue(matchedSet.contains(new DataPoint<Character>('*', new Coordinates(1, 0, 2, 1))));

        LOGGER.debug("===test5===end==");
    }

    @Test
    public void testInvalidInputs() {
        {
            final String searchString = null;
            final Character[][] searchSpace = new Character[][]{
                {'N', 'B', 'k', 'S', 'u', 'C'},
                {'B', 'N', 'i', 'G', 'K', 'j', 'D', 'X'},
                {'X', 'n', 'S', 'B'}
            };

            final StringMatch stringMatch = new StringMatch(2, DataSpaceRecipes.createDataSpace(searchSpace));
            
            try {
                final Set<DataPoint<Character>> matchedSet = stringMatch.match(searchString);
                Assert.fail("Should not reach this line.");
            } catch (NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }
        }
        {
            final Character[][] searchSpace = new Character[][]{
                {'N', 'B', 'k', 'S', 'u', 'C'},
                {'B', 'N', 'i', 'G', 'K', 'j', 'D', 'X'},
                {'X', 'n', 'S', 'B'}
            };
            
            try {
                final StringMatch stringMatch = new StringMatch(0, DataSpaceRecipes.createDataSpace(searchSpace));
                Assert.fail("Should not reach this line.");
            } catch (IllegalArgumentException iae) {
                LOGGER.trace("PASS", iae);
            }
        }
        {
            try {
                final StringMatch stringMatch = new StringMatch(2, null);
                Assert.fail("Should not reach this line.");
            } catch (NullPointerException npe) {
                LOGGER.trace("PASS", npe);
            }
        }
    }
}
