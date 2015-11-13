package org.mdconverter.consolewriter;

/**
 * Created by miso on 28.10.2015.
 */
public interface ConsoleWriter {

    public enum LinePrefix{
        NO,
        ERROR,
        INFO
    }

    void println(LinePrefix prfx, String... lines);

    void println(String output);

    void printErrorln(String output);

    void printInfoln(String output);

    int getIntInput();

    String getStringInput();

    void resetScanner();
}
