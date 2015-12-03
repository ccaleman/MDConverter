package org.mdconverter.api.plugin;

/**
 * This exception is thrown if a plugin detects an error in input files
 * Created by miso on 19.11.2015.
 */
public class InvalidInputException extends Exception {

    /**
     * @param message will be displayed to the user
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
