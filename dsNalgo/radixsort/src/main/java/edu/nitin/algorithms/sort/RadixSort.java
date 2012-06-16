/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.algorithms.sort;

import edu.nitin.datastructures.dataspace.DataPoint;
import java.util.List;
import java.util.Queue;

/**
 * A Linear time sort for strings <code>(List<DataPoint<Character>>)</code>.
 *
 * @author Nitin Verma
 */
public interface RadixSort {
    /**
     * Radix sort for strings.
     * 
     * @param dataPoints List of DataPoint representation of a string. 
     * @return Sorted Collection of DataPoint.
     */
    public Queue<DataPoint<Character>> sort(final List<DataPoint<Character>> dataPoints);
}
