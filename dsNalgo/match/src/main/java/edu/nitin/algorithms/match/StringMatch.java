package edu.nitin.algorithms.match;


import edu.nitin.datastructures.dataspace.DataPoint;
import edu.nitin.datastructures.dataspace.DataSpace;
import edu.nitin.datastructures.dataspace.DataSpaceRecipes;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * String Match in n-dimensions with O(m^n) time complexity. Assuming 'm' is the
 * average depth over the n-dimensions.
 *
 * <pre>
 * 1-dimensions : 0(m)
 * 2-dimensions : 0(m^2)
 * 3-dimensions : 0(m^3)
 * and so on so forth
 * </pre>
 *
 * <pre>
 * 
 * Glossary:-
 * 
 * Search Space (DataSpace) - The n-dimensional space in which to search.
 * Search String - The search string.
 *
 * This a Dynamic Programming based Algorithm to search a string in n-diemsional DataSpace.
 *
 * Step:-
 * 1. Initial Match (#matchDataPoint) - Match a 'Search Space DataPoint' to all
 * the possible DataPoints in the Search String. For example 'I' in Search Space
 * would match to 1 & 4 (assuming 'N' is at 0 in Search String = "NITIN").
 *
 * 2. Match adjacent ( 3^dimensions - 1; worst case ) for the chars on right and left.
 * Taking above example (N)I(TIN) & (NIT)I(N). (#matchNextDataPoint {a recursive call})
 * Adjacent are calculated finding the all possible in Cartesian Coordinates matched 
 * over the given Search Space barring the ones visited.
 *
 *
 * </pre>
 *
 * @author Nitin Verma
 */
public class StringMatch {

    private static final Log LOGGER = LogFactory.getLog(StringMatch.class);
    private final Set<DataPoint<Character>> memoizer = new TreeSet<DataPoint<Character>>();
    private final int dimensions;
    private final DataSpace<Character> dataSpace;

    /**
     * Constructor for n-dimensions StringMatch.
     *
     * @param dimensions Number of dimensions
     * @param dataSpace Search Space
     */
    public StringMatch(final int dimensions, final DataSpace<Character> dataSpace) {
        if (dimensions < 1) {
            throw new IllegalArgumentException("You are expected to use at least one dimension.");
        }
        if (dataSpace == null) {
            throw new NullPointerException("DataSpace can not be null.");
        }
        this.dimensions = dimensions;
        this.dataSpace = dataSpace;
    }

    /**
     * Method to initial string search over n-dimensions Search Space.
     *
     * @param searchString Search String.
     * @return
     */
    public Set<DataPoint<Character>> match(final String searchString) {
        return match(new SearchStringContainer(searchString));
    }

    private Set<DataPoint<Character>> match(final SearchStringContainer searchStringContainer) {


        final Set<DataPoint<Character>> returnSet = new TreeSet<DataPoint<Character>>();

        final Iterator<DataPoint<Character>> iterator = dataSpace.iterator();
        while (iterator.hasNext()) {
            final Set<DataPoint<Character>> matchedSet = new TreeSet<DataPoint<Character>>();
            final DataPoint<Character> dataPoint = iterator.next();
            final boolean value = matchDataPoint(dataPoint, searchStringContainer, dataSpace, matchedSet);

            if (value) {
                LOGGER.debug(dataPoint + " - " + value + " - " + matchedSet);
                returnSet.addAll(matchedSet);
                break;
            }
        }

        return returnSet;
    }

    private boolean matchDataPoint(final DataPoint<Character> dataPoint,
            final SearchStringContainer searchStringContainer,
            final DataSpace<Character> dataSpace, final Set<DataPoint<Character>> matchedSet) {

        boolean returnValue = false;

        final Set<DataPoint<Character>> matchedDataPoints =
                searchStringContainer.matches(dataPoint);

        if (matchedDataPoints.size() > 0) {
            for (final DataPoint matchedDataPoint : matchedDataPoints) {

                final List<DataPoint<Character>> leftLinkedList =
                        searchStringContainer.getLeft(matchedDataPoint);
                final List<DataPoint<Character>> rightLinkedList =
                        searchStringContainer.getRight(matchedDataPoint);

                LOGGER.debug("Linked List Sizes: " + dataPoint + " -- " + leftLinkedList + " " + rightLinkedList);

                memoizer.add(dataPoint);
                boolean leftMatch;
                boolean rightMatch = false;
                if (leftMatch = matchNextDataPoint(0, leftLinkedList, dataPoint, dataSpace, matchedSet)) {
                    memoizer.addAll(matchedSet);
                    returnValue = rightMatch = matchNextDataPoint(0, rightLinkedList, dataPoint,
                            dataSpace, matchedSet);
                }
                memoizer.clear();

                LOGGER.debug("Final: " + leftMatch + " " + rightMatch);

                if (returnValue) {
                    matchedSet.add(dataPoint);
                    break;
                }
            }
        } else {
            LOGGER.debug("No match for - " + dataPoint);
        }


        return returnValue;
    }

    private boolean matchNextDataPoint(final int level, List<DataPoint<Character>> linkedList,
            final DataPoint<Character> dataPoint, final DataSpace<Character> dataSpace,
            final Set<DataPoint<Character>> matchedSet) {
        boolean returnValue = false;
        if (linkedList.size() > level) {

            final DataPoint<Character> searchStringDataPoint = linkedList.get(level);
            final Set<DataPoint<Character>> adjacentDataPoints =
                    DataSpaceRecipes.getAdjacentDataPoints(
                    dataPoint.getCoordinates(), dimensions, dataSpace);

            for (final DataPoint<Character> mem : memoizer) {
                adjacentDataPoints.remove(mem);
            }

            for (final DataPoint<Character> adjacentDataPoint : adjacentDataPoints) {

                if (adjacentDataPoint.getData().compareTo(searchStringDataPoint.getData()) == 0) {
                    LOGGER.debug("Adjacent DataPoint Match " + adjacentDataPoint);
                    memoizer.add(adjacentDataPoint);
                    returnValue = matchNextDataPoint(level + 1, linkedList, adjacentDataPoint, dataSpace, matchedSet);
                    memoizer.remove(adjacentDataPoint);
                }

                if (returnValue) {
                    matchedSet.add(adjacentDataPoint);
                    break;
                }
            }

        } else {
            LOGGER.debug("Returning true");
            returnValue = true;
        }

        return returnValue;
    }
}
