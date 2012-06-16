/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.datastructures.dataspace;

import edu.nitin.datastructures.coordinates.Coordinates;

/**
 * Data points in Cartesian Coordinates.
 * 
 * <pre>
 * 
 * Examples
 * {'G','A'} are two data points.
 * 
 * (1) new DataPoint&lt;Character&gt;('G', new Coordinates(0))
 * (2) new DataPoint&lt;Character&gt;('A', new Coordinates(1))
 * 
 * Examples
 * {{'G'},{'A'}} are two data points.
 * 
 * (1) new DataPoint&lt;Character&gt;('G', new Coordinates(0,0))
 * (2) new DataPoint&lt;Character&gt;('A', new Coordinates(0,1))
 * 
 * </pre>
 * 
 * @author Nitin Verma
 */
public class DataPoint<T extends Comparable> implements Comparable<DataPoint<T>> {
    
    private Coordinates coordinates;
    private T data;
    
    public DataPoint(final T data, final Coordinates coordinates) {
        if (data == null) {
            throw new NullPointerException("'data' can not be null.");
        }
        
        if (coordinates == null) {
            throw new NullPointerException("'coordinates' can not be null.");
        }
        
        this.data = data;
        this.coordinates = coordinates;
    }
    
    public T getData() {
        return data;
    }
    
    public Coordinates getCoordinates() {
        return coordinates;
    }


    public int compareTo(DataPoint<T> otherDataPoint) {
        if (otherDataPoint == null) {
            throw new NullPointerException("Can not compare to null 'DataPoint'.");
        }
        
        return this.getCoordinates().compareTo(otherDataPoint.getCoordinates());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("<'")
                .append(data)
                .append("'-")
                .append(coordinates)
                .append(">");
        return sb.toString();
    }
    
    
}
