package Exceptions;

/**
 * Class (blueprint) of empty collection exception 
 * 
 * @author ITSC 2214
 *
 * @version 1.0
 */
public class EmptyCollectionException extends Exception {

    /* Constructor */
    public EmptyCollectionException() {
        System.out.println("Structure is empty.");
    }  
    
    /**
     * Constructor
     * @param textMsg text message
     */
    public EmptyCollectionException(String textMsg) {
        System.out.println(textMsg);
    } 
}
