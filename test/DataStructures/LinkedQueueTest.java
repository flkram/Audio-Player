/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Exceptions.EmptyCollectionException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Qiong
 */
public class LinkedQueueTest {
    
    public LinkedQueueTest() {
    }

    /**
     * Test of enqueue method, of class LinkedQueue.
     */
    @Test
    public void testEnqueue() {
        //TODO test your enqueue method of LinkedQueue<Integer>
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.enqueue(4);
        assertEquals(queue.size(), 1);
        try{
            assertEquals(queue.first().intValue(), 4);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        queue.enqueue(6);
        assertEquals(queue.size(), 2);
        try{
            assertEquals(queue.first().intValue(), 4);
            queue.dequeue();
            assertEquals(queue.first().intValue(), 6);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Test of isEmpty method, of class LinkedQueue.
     */
    @Test
    public void testIsEmpty() {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        assertTrue(queue.isEmpty());
        queue.enqueue(6);
        assertFalse(queue.isEmpty());
    }
    
    /**
     * Test of dequeue method, of class LinkedQueue.
     */
    @Test(expected=EmptyCollectionException.class)
    public void testDequeue1() throws Exception {
        //TODO test your dequeue method of LinkedQueue<Integer>
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.dequeue();
    }
    /**
     * Test of dequeue method, of class LinkedQueue.
     */
    @Test
    public void testDequeue2() throws Exception {
        //TODO test your dequeue method of LinkedQueue<Integer>
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.enqueue(4);
        queue.enqueue(6);
        assertEquals(queue.size(), 2);
        try{
            assertEquals(queue.dequeue().intValue(), 4);
            assertEquals(queue.first().intValue(), 6);
            assertEquals(queue.dequeue().intValue(), 6);
            assertEquals(queue.size(), 0);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Test of first method, of class LinkedQueue.
     */
    @Test(expected=EmptyCollectionException.class)
    public void testFirst1() throws Exception {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.first();
    }
        
    /**
     * Test of first method, of class LinkedQueue.
     */
    @Test
    public void testFirst2() throws Exception {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.enqueue(4);
        assertEquals(queue.first().intValue(), 4);
        queue.enqueue(6);
        assertEquals(queue.first().intValue(), 4);
        queue.dequeue();
        assertEquals(queue.first().intValue(), 6);
    }

    /**
     * Test of size method, of class LinkedQueue.
     */
    @Test
    public void testSize() {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.enqueue(4);
        assertEquals(queue.size(), 1);
        try{
            queue.dequeue();
            assertEquals(queue.size(), 0);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Test of dequeue method, of class LinkedQueue.
     */
    @Test
    public void testDequeue() throws Exception {
    }

    /**
     * Test of first method, of class LinkedQueue.
     */
    @Test
    public void testFirst() throws Exception {
    }
}
