package org.mdconverter.api.consolewriter;

/**
 * This interface defines the in- and output controls from and to the console to interact with the user
 * Created by miso on 28.10.2015.
 */
public interface ConsoleWriter {

    /**
     * This enum defines the output stream to be used and the Prefix for each line.
     */
    public enum LinePrefix{
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
     * @see ConsoleWriter.LinePrefix
     * @param output String printed to console
     */
    void println(String output);

    /**
     * Method prints one line to console with a Error prefix
     * @see ConsoleWriter.LinePrefix
     * @param output String printed to console
     */
    void printErrorln(String output);

    /**
     * Method prints one line to console with a Info prefix
     * @see ConsoleWriter.LinePrefix
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
