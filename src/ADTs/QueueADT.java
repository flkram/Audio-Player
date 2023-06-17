package ADTs;

import Exceptions.EmptyCollectionException;

/**
 * Interface of Queues. 
 * 
 * @author ITSC 2214 @CS.CCI.UNCC
 * @version 1.0 Friday 2023/03/02
 */

/** A first-in, first-out queue of Objects. */
public interface QueueADT<E> {

    /** Add target to the back of this QueueADT. */
    public void enqueue(E target);
    /** Add target to the back of this QueueADT. */
    public void add(E target);
    
    /** Return true if this QueueADT is empty. */
    public boolean isEmpty();

    /**
     * Remove and return the front item from this QueueADT.
     * @throws EmptyStructureException if the QueueADT is empty.
     */
    public E dequeue() throws EmptyCollectionException;
    /**
     * Remove and return the front item from this QueueADT.
     * @throws EmptyStructureException if the QueueADT is empty.
     */
    public E poll();
    /**
    * Returns (without removing) the element that is in the head of the queue
    * @return the element in the head of the queue
    * @throws EmptyCollectionException 
    */
    public E first() throws EmptyCollectionException;

    /** Number of elements of this StackADT. */
    public int size();
}
