/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.datastructures.dataspace;

import edu.nitin.datastructures.coordinates.Coordinates;
import java.util.Comparator;

/**
 *
 * @author Nitin Verma
 */
public class DataSpaceComparator implements Comparator {

    public int compare(final Object object1, final Object object2) {
        int comparison = 0;
        if (object1 == null || object2 == null) {
            throw new NullPointerException("Can compare nulls.");
        } else if (object1 instanceof DataPoint && object2 instanceof DataPoint) {
            comparison = compare((DataPoint) object1, (DataPoint) object2);
        } else if (object1 instanceof Coordinates && object2 instanceof Coordinates) {
            comparison = compare((Coordinates) object1, (Coordinates) object2);
        } else if (object1 instanceof DataPoint && object2 instanceof Coordinates) {
            comparison = compare((DataPoint) object1, (Coordinates) object2);
        } else if (object1 instanceof Coordinates && object2 instanceof DataPoint) {
            comparison = compare((DataPoint) object2, (Coordinates) object1);
        } else {
            throw new UnsupportedOperationException(
                    "Can only compare 'DataPoint' and 'Coordinates'. object1's class = " + 
                    ( object1 == null ? null : object1.getClass() ) + "; object2's class = " + 
                    ( object2 == null ? null : object2.getClass() )
                    );
        }
        
        return comparison;
    }

    private int compare(final DataPoint dataPoint1, final DataPoint dataPoint2) {
        return dataPoint1.compareTo(dataPoint2);
    }

    private int compare(final Coordinates coordinates1, final Coordinates coordinates2) {
        return coordinates1.compareTo(coordinates2);
    }

    private int compare(final DataPoint dataPoint, final Coordinates coordinates) {
        return dataPoint.getCoordinates().compareTo(coordinates);
    }
}
