package DataStructures;


/**
 * Represents a node in a linked list.
 * 
 * @author ITSC 2214
 * @version 1.0
 * @param <T> 
 */
public class SinglyLinkedNode<T> {
    //Declare a reference variable named next which 
    //is used to store the link to next linked node
    private SinglyLinkedNode<T> next;

    //Declare a reference variable named element which 
    //is used to store the reference to an object that 
    //the linked node would like to contain. 
    private T element;
 
    /**
     * Creates an empty node.
     */
    public SinglyLinkedNode() {
        //TODO Initialize the member variables
        next = null;
        element = null;
    }
 
    /**
     * Creates a node storing the specified element.
     * @param elem element to be stored
     */
    public SinglyLinkedNode(T elem) {
        //TODO Initialize the member variables
        next = null;
        element = elem;
        
    }
 
    /**
     * Returns the node that follows this one.
     * @return reference to next node
     */
    public SinglyLinkedNode<T> getNext() {
        //TODO retrieve the next linked node
        return next;
    }
 
    /**
     * Sets the node that follows this one.
     * @param node node to follow this one
     */
    public void setNext(SinglyLinkedNode<T> node) {
        //TODO Reset the next reference member variable 
        //to link the node with the given node object
        next = node;

    }
 
    /**
     * Returns the element stored in this node.
     * @return element stored at the node
     */
    public T getElement() {
        //TODO Retrieve the data/information object that 
        //the linked node contains
        return element;

    }
 
    /**
     * Sets the element stored in this node.
     * @param elem element to be stored at this node
     */
    public void setElement(T elem) {
        //TODO Reset the data/information member variable 
        //to the given object
        element = elem;

    }
    
    /**
     * Print the information of contained data object
     */
    public void printNodeData() {
        System.out.println(this.element.toString());
    }
}
