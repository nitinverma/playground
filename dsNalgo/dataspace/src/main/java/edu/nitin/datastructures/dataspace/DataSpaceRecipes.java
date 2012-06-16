/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.datastructures.dataspace;

import edu.nitin.datastructures.coordinates.Coordinates;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Recipes related to 'DataSpace'
 *
 * @author Nitin Verma
 */
public class DataSpaceRecipes {

    private static final Log LOGGER = LogFactory.getLog(DataSpaceRecipes.class);

    /**
     * Constructor kept private, as its a group of stateless services.
     */
    private DataSpaceRecipes() {
    }

    /**
     * A convenience method. To create DataSpace from 1-D array.
     *
     * @param <T> Any Comparable
     * @param data One dimensional array
     * @return DataSpace depicting the array
     */
    public static <T extends Comparable> DataSpace<T> createDataSpace(final T[] data) {
        final DataSpace<T> dataSpace = new DataSpace<T>();
        for (int x = 0; x < data.length; x++) {
            dataSpace.add(new DataPoint<T>(data[x], new Coordinates(x)));
        }
        return dataSpace;
    }

    /**
     * A convenience method. To create DataPoint List from 1-D array.
     *
     * @param <T> Any Comparable
     * @param data One dimensional array
     * @return DataPoint List depicting the array
     */
    public static <T extends Comparable> List<DataPoint<T>> createDataPoints(final T[] data) {
        final List<DataPoint<T>> dataPoints = new LinkedList<DataPoint<T>>();
        for (int x = 0; x < data.length; x++) {
            dataPoints.add(new DataPoint<T>(data[x], new Coordinates(x)));
        }
        return dataPoints;
    }

    /**
     * A convenience method. To create DataSpace from 2-D array.
     *
     * @param <T> Any Comparable
     * @param data Two dimensional array
     * @return DataSpace depicting the array
     */
    public static <T extends Comparable> DataSpace<T> createDataSpace(final T[][] data) {
        final DataSpace<T> dataSpace = new DataSpace<T>();
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                dataSpace.add(new DataPoint<T>(data[y][x], new Coordinates(x, y)));
            }
        }
        return dataSpace;
    }

    /**
     * A convenience method. To create DataSpace from 3-D array.
     *
     * @param <T> Any Comparable
     * @param data Three dimensional array
     * @return DataSpace depicting the array
     */
    public static <T extends Comparable> DataSpace<T> createDataSpace(final T[][][] data) {
        final DataSpace<T> dataSpace = new DataSpace<T>();
        for (int z = 0; z < data.length; z++) {
            for (int y = 0; y < data[z].length; y++) {
                for (int x = 0; x < data[z][y].length; x++) {
                    dataSpace.add(new DataPoint<T>(data[z][y][x], new Coordinates(x, y, z)));
                }
            }
        }
        return dataSpace;
    }

    /**
     * A convenience method. To create DataSpace from 4-D array.
     *
     * @param <T> Any Comparable
     * @param data Four dimensional array
     * @return DataSpace depicting the array
     */
    public static <T extends Comparable> DataSpace<T> createDataSpace(final T[][][][] data) {
        final DataSpace<T> dataSpace = new DataSpace<T>();
        for (int t = 0; t < data.length; t++) {
            for (int z = 0; z < data.length; z++) {
                for (int y = 0; y < data[t][z].length; y++) {
                    for (int x = 0; x < data[t][z][y].length; x++) {
                        dataSpace.add(new DataPoint<T>(data[t][z][y][x], new Coordinates(x, y, z, t)));
                    }
                }
            }
        }
        return dataSpace;
    }

    /**
     * Builds the adjacent coordinates to a given coordinates in n-dimensions.
     * Sub set of `3^dimensions - 1` that match on the DataSpace.
     * 
     * @param <T>
     * @param coordinates Given coordinates
     * @param dimensions Number of dimensions in which adjacent coordinates are
     * needed.
     * @param dataSpace DataSpace
     * @return
     */
    public static <T extends Comparable> Set<DataPoint<T>> getAdjacentDataPoints(
            final Coordinates coordinates, final int dimensions, final DataSpace<T> dataSpace) {
        final Set<Coordinates> adjacentCoordinatesSet = getAdjacentCoordinates(coordinates, dimensions);
        final Set<DataPoint<T>> adjacentDataPoint = new TreeSet<DataPoint<T>>();


        for (final Coordinates adjacentCoordinates : adjacentCoordinatesSet) {
            final DataPoint<T> dataPoint = dataSpace.get(adjacentCoordinates);
            LOGGER.trace("DataPoint : " + dataPoint);
            if (dataPoint != null) {
                adjacentDataPoint.add(dataPoint);
            }
        }


        return adjacentDataPoint;
    }

    /**
     * Builds the adjacent coordinates to a given coordinates in n-dimensions.
     * Number adjacent coordinates are 3^dimensions - 1.
     *
     *
     * @param coordinates Given coordinates
     * @param dimensions Number of dimensions in which adjacent coordinates are
     * needed.
     * @return set of adjacent coordinates
     */
    public static Set<Coordinates> getAdjacentCoordinates(final Coordinates coordinates,
            final int dimensions) {

        final Set<Coordinates> adjacentCoordinatesSet = new TreeSet<Coordinates>();
        final Set<Coordinates> adjustmentCoordinatesSet = getOriginAdjacentCoordinates(dimensions);

        for (final Coordinates adjustmentCoordinates : adjustmentCoordinatesSet) {
            adjacentCoordinatesSet.add(coordinates.add(adjustmentCoordinates));
        }

        return adjacentCoordinatesSet;
    }

    /**
     * Builds the adjacent coordinates of the origin in n-dimensions.
     *
     * @param dimensions Number of dimensions in which adjacent coordinates are
     * needed.
     * @return set of adjacent coordinates
     */
    public static Set<Coordinates> getOriginAdjacentCoordinates(final int dimensions) {
        if (dimensions < 1) {
            throw new IllegalArgumentException("You are expected to use at least one dimension");
        }

        // Adjustment on single dimension.
        final Set<Integer> adjustments = new TreeSet<Integer>() {

            {
                add(-1);
                add(0);
                add(1);
            }
        };

        // Giving origin as a seed
        Set<Coordinates> toAdjust = new TreeSet<Coordinates>() {

            {
                add(new Coordinates(0));
            }
        };


        for (int dimension = 0; dimension < dimensions; dimension++) {
            toAdjust = DataSpaceRecipes.getAdjustmentedCoordinates(
                    dimension, toAdjust, adjustments);
        }

        toAdjust.remove(new Coordinates(0));

        return toAdjust;
    }

    /**
     * To offset coordinates in one of the dimensions.
     *
     * @param dimension The give dimension.
     * @param toAdjust The coordinate set to be adjusted.
     * @param adjustments The set of adjustments (offsets) in a given dimension.
     * @return
     */
    public static Set<Coordinates> getAdjustmentedCoordinates(final int dimension, final Set<Coordinates> toAdjust, final Set<Integer> adjustments) {

        if (dimension < 0) {
            throw new IllegalArgumentException("-ve dimension");
        }

        if (toAdjust == null) {
            throw new NullPointerException("Can not adjust null coordinates");
        }

        if (adjustments == null) {
            throw new NullPointerException("Can not apply null adjustments");
        }

        final Set<Coordinates> adjustmentedSet = new TreeSet<Coordinates>();

        final LinkedList<Integer> coordinate = new LinkedList<Integer>();

        for (int i = 0; i <= dimension; i++) {
            coordinate.add(0);
        }

        LOGGER.trace("origin : " + coordinate);

        final Set<Coordinates> additionCoordinatesSet = new TreeSet<Coordinates>();

        for (final Integer adjustment : adjustments) {
            coordinate.set(dimension, adjustment);
            LOGGER.trace("adjustment : " + coordinate);
            additionCoordinatesSet.add(new Coordinates(coordinate));
        }

        for (final Coordinates additionCoordinates : additionCoordinatesSet) {
            for (final Coordinates otherAdditionCoordinates : toAdjust) {
                adjustmentedSet.add(additionCoordinates.add(otherAdditionCoordinates));
            }
        }

        return adjustmentedSet;
    }
}
