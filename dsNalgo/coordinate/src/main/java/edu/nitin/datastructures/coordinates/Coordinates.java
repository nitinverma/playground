/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.datastructures.coordinates;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Cartesian Coordinates in infinite dimensions (in theory).
 * To simplify discrete coordinate values bounded by Integer.MAX_VALUE are used.
 * 
 * Coordinates is an immutable object for predictability. 
 * 
 * @author Nitin Verma
 */
public class Coordinates implements Comparable<Coordinates> { // Can be gerelized by template of Number.

    private final List<Integer> coordinates;

    /**
     * A convenience constructor.
     * 
     * @param coordinates 
     */
    public Coordinates(final Integer... coordinates) {
        this(coordinates == null ? Arrays.asList(0) : Arrays.asList(coordinates));
    }
    
    /**
     * Null coordinates would be treated as origin of the Cartesian System.
     * Any null coordinate would be treated on '0' in the given dimension.
     * 
     * @param coordinates 
     */
    public Coordinates(final Collection<Integer> coordinates) {
        this.coordinates = coordinates == null ? new LinkedList<Integer>() : 
                new LinkedList<Integer>(coordinates);
    }
    
    /**
     * @return Max dimension of the defined coordinate.
     */
    public int maxDefinedDimension() {
        return this.coordinates.size();
    }

    /**
     * @param dimension
     * @return The coordinate on the given dimension.
     */
    public Integer getCoordinate(final int dimension) {
        Integer coordinate = null;
        if ( (coordinates.size() - 1) >= dimension ) {
            coordinate = this.coordinates.get(dimension);
        }
        return coordinate == null ? 0 : coordinate;
    }

    /**
     * Compares to another 'Coordinates'.
     * 
     * @param otherCoordinate
     * @return comparison 
     */
    public int compareTo(final Coordinates otherCoordinate) {
        if (otherCoordinate == null) {
            throw new NullPointerException("Can not compare to null 'Coordinates'.");
        }
        
        int compareTo = 0;
        
        final int maxDimension = ( this.maxDefinedDimension() > 
                otherCoordinate.maxDefinedDimension() ) ? this.maxDefinedDimension() : 
                otherCoordinate.maxDefinedDimension();
        
        for (int dimension = 0; dimension < maxDimension; dimension++) {
            compareTo = this.getCoordinate(dimension).compareTo(otherCoordinate.getCoordinate(dimension));
            if (compareTo != 0) {
                break;
            }
        }
        
        return compareTo;
    }
    
    /**
     * Compares to another 'Coordinates'.
     * 
     * @param otherCoordinate
     * @return comparison 
     */
    public Coordinates add(final Coordinates otherCoordinate) {
        if (otherCoordinate == null) {
            throw new NullPointerException("Can not add to null 'Coordinates'.");
        }
        
        final Collection<Integer> collection = new LinkedList<Integer>();
        
        final int maxDimension = ( this.maxDefinedDimension() > 
                otherCoordinate.maxDefinedDimension() ) ? this.maxDefinedDimension() : 
                otherCoordinate.maxDefinedDimension();
        
        for (int dimension = 0; dimension < maxDimension; dimension++) {
            collection.add(this.getCoordinate(dimension) + otherCoordinate.getCoordinate(dimension));
        }
        
        return new Coordinates(collection);
    }
    
    
    @Override
    public String toString() {
        return coordinates.toString();
    }
}
