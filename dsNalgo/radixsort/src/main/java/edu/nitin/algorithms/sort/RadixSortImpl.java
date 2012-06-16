/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.algorithms.sort;

import edu.nitin.datastructures.dataspace.DataPoint;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Nitin Verma
 */
public class RadixSortImpl implements RadixSort {

    private static final Log LOGGER = LogFactory.getLog(RadixSortImpl.class);
    final static int RADIX = 2;
    final static int MAX_LENGTH = 16; // Math.log(Character.MAX_VALUE)/Math.log(radix);
    
    public Queue<DataPoint<Character>> sort(final List<DataPoint<Character>> dataPoints) {
        
        if (dataPoints == null) {
            throw new NullPointerException("List of Data-Points can not be null.");
        }
        
        final Queue<DataPoint<Character>> queue = new LinkedList<DataPoint<Character>>(dataPoints);
        final List<Queue<DataPoint<Character>>> placeValueQueueList = 
                new LinkedList<Queue<DataPoint<Character>>>();

        LOGGER.trace("Init --- " + queue);

        for (int placeValue = 0; placeValue < RADIX; placeValue++) {
            placeValueQueueList.add(new LinkedList<DataPoint<Character>>());
        }

        for (int place = 0; place < MAX_LENGTH; place++) {

            LOGGER.trace("Place -- " + place + " " + queue);

            DataPoint<Character> dataPoint;
            while ((dataPoint = queue.poll()) != null) {
                final int characterPlaceValue = getPlaceValue(dataPoint.getData(), place);
                final Queue<DataPoint<Character>> placeValueQueue = 
                        placeValueQueueList.get(characterPlaceValue);
               
                
                placeValueQueue.add(dataPoint);
            }

            LOGGER.trace("Should be drained ---" + queue);

            for (final Queue<DataPoint<Character>> placeValueQueue : placeValueQueueList) {
                queue.addAll(placeValueQueue);
                placeValueQueue.clear();
            }

        }


        LOGGER.trace("Final queue : " + queue);

        return queue;

    }


    private static int getPlaceValue(final int number, final int place) {
        return (number >> place) & 0x1;
    }
}
