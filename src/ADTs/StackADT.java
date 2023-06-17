package ADTs;

import Exceptions.EmptyCollectionException;

/**
 * Interface of Stacks. 
 * 
 * @author ITSC 2214 @CS.CCI.UNCC
 * @version 1.0 Friday 2023/03/02
 */

/** A last-in, first-out stack. */
public interface StackADT<E> {

    /** Return true if this StackADT is empty. */
    public boolean isEmpty();

    /**
     * Return the top item on this StackADT, but do not modify the StackADT.
     * @throws EmptyCollectionException if this StackADT is empty.
     */
    public E peek() throws EmptyCollectionException;

    /**
     * Remove and return the top item on this StackADT.
     * @throws EmptyStructureException if this StackADT is empty.
     */
    public E pop() throws EmptyCollectionException;

    /** Add targer to the top of this StackADT. */
    public void push(E target);

    /** Number of elements of this StackADT. */
    public int size();
}
