/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.algorithms.match;

import edu.nitin.algorithms.sort.RadixSort;
import edu.nitin.algorithms.sort.RadixSortImpl;
import edu.nitin.datastructures.dataspace.DataPoint;
import edu.nitin.datastructures.dataspace.DataSpaceRecipes;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Queue;

/**
 * A class to keep search string operations together.
 *
 * @author Nitin Verma
 */
public class SearchStringContainer {

    final private List<DataPoint<Character>> dataPoints;
    final private Queue<DataPoint<Character>> sortedDataPoints;

    /**
     * Constructor for SearchStringContainer.
     *
     * @param searchString The search string.
     */
    public SearchStringContainer(final String searchString) {

        if (searchString == null) {
            throw new NullPointerException("The Search String should not be null");
        }

        final char[] chars = searchString.toCharArray();
        final Character characters[] = new Character[chars.length];

        //System.arraycopy does not help in this case
        for (int i = 0; i < chars.length; i++) {
            characters[i] = chars[i];
        }

        dataPoints = DataSpaceRecipes.createDataPoints(characters);
        
        /*
         * Linear time sort assuming we'll find long Search String on n-dimesional Search Space.
         */

        final RadixSort radixSort = new RadixSortImpl(); // TODO: Factory or Spring to avoid implementations in the code.

        sortedDataPoints = radixSort.sort(dataPoints);
    }

    /**
     * Matches Search Space DataPoint to the Search String DataPoints.
     *
     * @param dataPoint DataPoint in the give Search Space (aka DataSpace)
     * @return All Search String DataPoints that match the give Search Space
     * DataPoints
     */
    public Set<DataPoint<Character>> matches(final DataPoint<Character> dataPoint) {

        if (dataPoint == null) {
            throw new NullPointerException("dataPoint should not ne null");
        }

        final Set<DataPoint<Character>> matchSet = new TreeSet<DataPoint<Character>>();

        for (DataPoint<Character> innerDataPoint : sortedDataPoints) {

            if (innerDataPoint.getData().compareTo(dataPoint.getData()) == 0) {
                matchSet.add(innerDataPoint);
            } else {
                if (matchSet.size() > 0) {
                    break;
                }
            }
        }

        return matchSet;
    }

    /**
     * Gets the left hand Search String DataPoints.
     *
     * @param dataPoint Search String DataPoint
     * @return Search String DataPoints on the left hand.
     */
    public List<DataPoint<Character>> getLeft(final DataPoint<Character> dataPoint) {

        if (dataPoint == null) {
            throw new NullPointerException("dataPoint should not ne null");
        }

        final LinkedList<DataPoint<Character>> queue = new LinkedList<DataPoint<Character>>();
        final int coordinate = dataPoint.getCoordinates().getCoordinate(0);
        if (coordinate > 0) {
            for (int i = coordinate - 1; i >= 0; i--) {
                queue.add(dataPoints.get(i));
            }
        }
        return queue;
    }

    /**
     * Gets the right hand Search String DataPoints.
     *
     * @param dataPoint Search String DataPoint
     * @return Search String DataPoints on the right hand.
     */
    public List<DataPoint<Character>> getRight(final DataPoint<Character> dataPoint) {

        if (dataPoint == null) {
            throw new NullPointerException("dataPoint should not ne null");
        }

        final LinkedList<DataPoint<Character>> queue = new LinkedList<DataPoint<Character>>();
        final int coordinate = dataPoint.getCoordinates().getCoordinate(0);
        if (coordinate < dataPoints.size()) {
            for (int i = coordinate + 1; i < dataPoints.size(); i++) {
                queue.add(dataPoints.get(i));
            }
        }
        return queue;
    }
}
