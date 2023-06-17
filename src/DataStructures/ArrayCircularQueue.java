package DataStructures;


import Exceptions.EmptyCollectionException;
import ADTs.QueueADT;


/**
 * An array-based QueueADT.
 * ArrayCircularQueue which implement QueueADT
 Use an array as the QueueADT container, and use the
 variable size to represent the total number of elements
 in the queue.
 In the array, new data element will always be stored in 
 the end of queue,
 which is represented in the index of (front + size) % 
 array length.
 *
 * @author ITSC 2214
 * @version 1.0
 * @param <E> 
 */
public class ArrayCircularQueue<E> implements QueueADT<E> {
    /** Default capacity in this QueueADT. */
    private final static int DEFAULT_CAPACITY = 10;
    
    /** Array of items in this QueueADT. */
    private E[] data;

    /** Declare an int variable named front, which represents 
 the index of the front-most element in this QueueADT. */
    private int front;

    /** Declare an int variable named size, which represents 
 the number of items currently in this QueueADT. */
    private int size;

    /** The QueueADT is initialized to be empty. 
     * Hints:
     * Constructor method is a special method which method name is the same as 
     * the class name and it has no return type. The goal of the constructor method
     * is to initialize the data member variables.
     * In this case, the ArrayCircularQueue instance,data, is initialized to be 
     * empty. This is the same as what we discussed about how to initialize a 
     * generic array.
     * In details, we need to initialize the data members, front and size
     */
    @SuppressWarnings("unchecked")
    public ArrayCircularQueue() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size=0;

    }

    /**
     * Insert an element in the end of the queue
     * @param target input element
     * Hints:
     * We need to correctly maintain the status of the data reference variables
     * and the front and size variables in the enqueue() method:
     *    1) to make sure that the input target has been inserted into the logical end of the array queue
     *       and the front reference variable refers to the index of the beginning item 
     *       of the queue
     *    2) to make sure that the size variable increased by one after enqueueing an item 
     *    in the queue
     * The procedure of enqueueing a node is as follows:
     *    a) to expand the arrayqueue container if the array queue is full
     *    b) assign the input argument reference variable, target, to the proper location
     *       at the logical end of the arrayqueue (namely, next available index)
     *    c) to increase the size by 1
     */
    @Override
    public void enqueue(E target) {
        /** if queue is full, expand capacity the 
         * array-based data collection,
         *  for example, doubling its size and copying 
         * the original data items into the new expanded array.
         **/
        if (isFull()) {
            expandCapacity();
        }
        
        data[size] = target;
        size++;
        /**
         * TODO insert a new data item with reference to 
         * the input target into the queue
         * Do not forget to change the size
        **/

    }

    /**
     * Remove from the beginning of the queue
     * @return the removed element
     * @throws EmptyCollectionException 
     * Hints:
     * We need to correctly maintain the status of the data reference variables
     * and the front and size variables in the enqueue() method:
     *    1) to make sure that the input target has been removed from the logical beginning of 
     *       of the queue
     *    2) to make sure that the size variable decreased by one after dequeueing an item 
     *    from the queue
     * The procedure of dequeueing a node is as follows:
     *    a) to retrieve the item at the beginning of the queue
     *    b) re-assign the front variable to the index where next dequeue() would remove
     *    c) to decrease the size by 1
     *    d) return the item resulted in a)
     */
    @Override
    public E dequeue() throws EmptyCollectionException {
        /** If queue is empty, throw an exception. **/
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        
        
        E temp = data[0];
        for(int i = 1; i<size; i++){
            data[i-1]=data[i];
        }
        data[size-1]=null;
        size--;
        return temp;
        /**
        * TODO Then remove the data item from the queue, which 
        * corresponds to save element at the front index 
        * to a variable, named result,
        * and move the front to its next index in circular 
        * array.
        * 
        * Do not forget to change the size
        * Return the variable result.
        **/
        

    }

    /** Double the length of data. */
    @SuppressWarnings("unchecked")
    protected void expandCapacity() {
        E[] newData = (E[])(new Object[data.length * 2]); // Warning
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        data = newData;
        front = 0;
    }

    /**
     * Examine whether the queue is empty
     * @return true: if the queue is empty
     *         false: if the queue is not empty
     */
    @Override
    public boolean isEmpty() {
        return (size==0);

    }

     /**
     * Examine whether the queue array is full
     * @return Return true if data is full, 
     *         or else false
     */
    protected boolean isFull() {
        return !(data.length == size) ;

    }
    
    /**
     * Retrieve the first
     * @return the element in the beginning of the queue
     * @throws EmptyCollectionException 
     */
    @Override
    public E first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException(
                    "Remove item from empty queue");
        }
        
        return data[0];
        /**TODO return element in the frontmost position of the array **/
       
    }

    /**
     * Retrieve the size
     * @return number of elements in the queue
     */
    @Override
    public int size() {
        // Return the size of the QueueADT
        return size;
    }

    @Override
    public void add(E target) {
        this.enqueue(target);
    }

    @Override
    public E poll() {
        if (isEmpty()) return null;
        E result = data[front];
        front = (front + 1) % data.length;
        size--;
        return result;    }
}