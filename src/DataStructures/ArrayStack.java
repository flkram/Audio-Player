package DataStructures;


import Exceptions.EmptyCollectionException;
import ADTs.StackADT;

/**
 * An array-based StackADT.
 * ArrayCircularQueue which implement QueueADT
 * Use an array as the stack container, and use the
 * variable size to represent the total number of elements in the queue. 
 * In the array, new data element will always be stored in the end of queue, 
 * which is represented in the index of (front + size) % array length.
 * 
 * @author ITSC 2214
 * @version 1.0
 * @param <E> 
 */
public class ArrayStack<E> implements StackADT<E> {
    /** Default capacity in this QueueADT. */
    private final static int DEFAULT_CAPACITY = 10;
    
    /** Array of items in this StackADT. */
    private E[] data;

    /** Number of items currently in this StackADT. */
    private int top;

    /** Constructor
     * Hints:
     * Constructor method is a special method which method name is the same as 
     * the class name and it has no return type. The goal of the constructor method
     * is to initialize the data member variables.
     * In this case, the StackADT is initialized to be empty. This is the same
     * as what we discussed about how to initialize a generic array.
     * In details, we need to initialize the data members, data and top
    */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        //TODO Instantiate the array-based data collection
        // with the default capacity constant, DEFAULT_CAPACITY
        top = 0;
        data = (E[]) new Object[DEFAULT_CAPACITY];
        

    }

    /**
     * Insert an element on the top of the stack
     * @param target input element
     * Hints:
     * We need to correctly maintain the status of the array container and the 
     * top variable in the push() method:
     *    1) to make sure that the input target has been inserted in the array 
     *       container (which reference variable name is data) 
     *    2) the variable, top, has two meanings:
     *       a) the total number of elements in the array stack
     *       b) the element index in the array that can be used to store incoming item
     * The procedure of pushing a node is as follows:
     *    a) if the array stack is full, expand its array capacity
     *    b) to assign the input target at the index of top in the array stack
     *    c) to increase the size by 1
     */
    @Override
    public void push(E target) {
        if (isFull()) {
            expandCapacity();
        }
        
        data[top] = target;
        top++;
        //TODO Add targer to the top of the stack (data array) and increase top by one

    }

    /** Double the length of data. */
    @SuppressWarnings("unchecked")
    protected void expandCapacity() {
        E[] newData = (E[])(new Object[data.length * 2]); // Warning
        for (int i = 0; i < data.length; i++)
            newData[i] = data[i];

        data = newData;
    }
    
    /**
     * Remove out of the top of the stack
     * @return the removed element
     * @throws EmptyCollectionException 
     * Hints:
     * We need to correctly maintain the status of the array container and the 
     * top variable in the pop() method:
     *    to make sure that the top variable decreased by one after popping an item 
     *    out of the stack since the variable, top, serves in folds:
     *       a) the total number of elements in the array stack
     *       b) the element index in the array that can be used to store incoming item
     * The procedure of popping an item out of the stack is as follows:
     *    a) to decrease the top variable by 1
     *    d) return the data element at the index, top, of the array stack
     */
    @Override
    public E pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        
        E temp = data[top-1];
        data[top-1]=null;
        top--;
        return temp;
        //TODO Remove and return the top item on the stack 
        //(data array)
        
    }
    
    /**
     * Retrieve the element on the top of the stack
     * @return the element on the top of the stack
     * @throws EmptyCollectionException 
     */    
    @Override
    public E peek() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException();
        
        return data[top-1];
        //TODO Retrieve element at the end of the stack 
        //(index of the data array: top -1) 
        //Do not modify the StackADT.


    }

     /**
     * Examine whether the stack is empty
     * @return true: if the stack is empty
     *         false: if the stack is not empty
     */
    @Override
    public boolean isEmpty() {
        return (top==0);
        //TODO Evaluate whether the stack is empty

        
    }

     /**
     * Examine whether the stack array is full
     * @return Return true if data is full, 
     *         or else false
     */
    protected boolean isFull() {
        if (isEmpty()) return false;
        return (data[top-1] != null);
        //TODO Evaluate whether the queue is full

        
    }
    
     /**
     * Retrieve the size
     * @return number of elements in the queue
     */
    @Override
    public int size() {
        //Return the size of the stack, identified by 
        //the variable top
        return top;
    }
}

