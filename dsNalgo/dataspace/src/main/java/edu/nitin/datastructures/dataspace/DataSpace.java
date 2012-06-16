/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.datastructures.dataspace;

import edu.nitin.datastructures.coordinates.Coordinates;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * An infinite dimension data space. Capable of defining a contiguous or sparse
 * space.
 *
 * @author Nitin Verma
 */
public class DataSpace<T extends Comparable> {

    private Map<Coordinates, DataPoint<T>> dataMap = new TreeMap<Coordinates, DataPoint<T>>();

    public void add(final DataPoint<T> dataPoint) {
        dataMap.put(dataPoint.getCoordinates(), dataPoint);
    }
    
    public DataPoint<T> get(final DataPoint<T> dataPoint) {
        DataPoint<T> returnDataPoint= null;
        
        final DataPoint<T> inSpaceDataPoint = this.get(dataPoint.getCoordinates());
        if ( inSpaceDataPoint.getData().compareTo(dataPoint.getData()) == 0 ) {
            returnDataPoint = inSpaceDataPoint;
        }
        
        return returnDataPoint;
    }
    
    public DataPoint<T> get(final Coordinates coordinates) {
        return dataMap.get(coordinates);
    }
    
    public boolean contains(final DataPoint<T> dataPoint) {
        final DataPoint<T> inSpaceDataPoint = this.get(dataPoint.getCoordinates());
        return inSpaceDataPoint.getData().compareTo(dataPoint.getData()) == 0;
    }
    
    public boolean contains(final Coordinates coordinates) {
        return dataMap.containsKey(coordinates);
    }
    
    public Iterator<DataPoint<T>> iterator() {
        return dataMap.values().iterator();
    }
    
    public Collection<DataPoint<T>> getDataPoints() {
        return dataMap.values();
    }
    
    public int size() {
        return dataMap.size();
    }

    @Override
    public String toString() {
        return dataMap.values().toString();
    }
}
