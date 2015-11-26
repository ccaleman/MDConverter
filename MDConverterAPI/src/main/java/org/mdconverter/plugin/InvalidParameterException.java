package org.mdconverter.plugin;

/**
 * This exception is thrown if a plugin misses some necessary arguments to process
 * Created by miso on 19.11.2015.
 */
public class InvalidParameterException extends Exception {

    /**
     * @param message will be displayed to the user
     */
    public InvalidParameterException(String message) {
        super(message);
    }
}
