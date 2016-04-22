package org.mdconverter.api.consolehandler;

/**
 * This interface defines the in- and output controls from and to the console to interact with the user
 * Created by miso on 28.10.2015.
 */
public interface ConsoleHandler {

    /**
     * This enum defines the output stream to be used and the Prefix for each line.
     */
    enum LinePrefix {
        NONE,
        ERROR,
        INFO
    }

    /**
     * Method to print multiple lines to console at once
     * @param prfx defines the Prefix for the given lines
     * @param lines Array to print out multiple lines at once
     */
    void println(LinePrefix prfx, String... lines);

    /**
     * Method prints one line to console without a prefix
     * @see org.mdconverter.api.consolehandler.ConsoleHandler.LinePrefix
     * @param output String printed to console
     */
    void println(String output);

    /**
     * Method prints one line to console with a Error prefix
     * @see org.mdconverter.api.consolehandler.ConsoleHandler.LinePrefix
     * @param output String printed to console
     */
    void printErrorln(String output);

    /**
     * Method prints one line to console with a Info prefix
     * @see org.mdconverter.api.consolehandler.ConsoleHandler.LinePrefix
     * @param output String printed to console
     */
    void printInfoln(String output);

    /**
     * @return the user input as an {@code int}
     */
    int getIntInput();

    /**
     * @return the user input as {@link String}
     */
    String getStringInput();


}
