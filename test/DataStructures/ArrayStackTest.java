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
public class ArrayStackTest {
    
    public ArrayStackTest() {
    }

    /**
     * Test of isEmpty method, of class ArrayStack.
     */
    @Test
    public void testIsEmpty() {
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        assertTrue(stack.isEmpty());
        stack.push(6);
        assertFalse(stack.isEmpty());
    }

    /**
     * Test of peek method, of class ArrayStack.
     */
    @Test(expected=EmptyCollectionException.class)
    public void testPeek1() throws Exception {
        //TODO peek on the top of an empty stack
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        stack.peek();
    }

    /**
     * Test of peek method, of class ArrayStack.
     */
    @Test
    public void testPeek2() throws Exception {
        //TODO peek on the top of an non-empty stack
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        stack.push(4);
        assertEquals(stack.peek().intValue(), 4);
        stack.push(6);
        assertEquals(stack.peek().intValue(), 6);
        stack.pop();
        assertEquals(stack.peek().intValue(), 4);
    }
    /**
     * Test of pop method, of class ArrayStack.
     */
    @Test(expected=EmptyCollectionException.class)
    public void testPop1() throws Exception {
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        stack.pop();
    }
    /**
     * Test of pop method, of class LinkedQueue.
     */
    @Test
    public void testDequeue2() throws Exception {
        //TODO test your pop method of LinkedQueue<Integer>
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        stack.push(4);
        stack.push(6);
        assertEquals(stack.size(), 2);
        try{
            assertEquals(stack.pop().intValue(), 6);
            assertEquals(stack.peek().intValue(), 4);
            assertEquals(stack.pop().intValue(), 4);
            assertEquals(stack.size(), 0);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Test of push method, of class ArrayStack.
     */
    @Test
    public void testPush() {
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        stack.push(4);
        assertEquals(stack.size(), 1);
        try{
            assertEquals(stack.peek().intValue(), 4);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        stack.push(6);
        assertEquals(stack.size(), 2);
        try{
            assertEquals(stack.peek().intValue(), 6);
            stack.pop();
            assertEquals(stack.peek().intValue(), 4);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Test of size method, of class ArrayStack.
     */
    @Test
    public void testSize() {
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        stack.push(4);
        assertEquals(stack.size(), 1);
        try{
            stack.pop();
            assertEquals(stack.size(), 0);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    } 
}
