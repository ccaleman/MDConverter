
package org.mdconverter.consolehandler;

import org.mdconverter.api.consolehandler.ConsoleHandler;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by miso on 23.10.2015. <br>
 *     is used by the Framework and all plugins
 */
@Singleton
public class ConsoleHandlerImpl extends PrintStream implements ConsoleHandler {

    //Fields
    private final OutputStream errorStream;
    private OutputStream infoStream;
    private Scanner sc = new Scanner(System.in);

    @Inject
    public ConsoleHandlerImpl(@Named("INFO") OutputStream out, @Named("ERROR") OutputStream err) {
        super(out, true);
        this.errorStream = err;
        this.infoStream = out;
    }

    @Override
    public void println(LinePrefix prfx, String... lines) {
        for (String line : lines) {
            if (line != null && !line.isEmpty()) {
                switch (prfx) {
                    case INFO:
                        printInfoln(line);
                        break;
                    case ERROR:
                        printErrorln(line);
                        break;
                    default:
                        print(line);
                }
            }
        }
    }

    /**
     * guarantees a linebreak for each print call
     *
     * @param output
     * @param stream
     */
    private void println(String output, OutputStream stream) {
        byte[] bytes = output.getBytes();
        try {
            stream.write(bytes);
            stream.write('\n');
            stream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void println(String output) {
        this.println(output, infoStream);
    }

    @Override
    public void printErrorln(String output) {
        output = "ERROR: " + output;
        this.println(output, errorStream);
    }

    @Override
    public void printInfoln(String output) {
        output = "INFO: " + output;
        this.println(output, infoStream);
    }

    /**
     * waits for a user input and requires a Integer value
     *
     * @return the given int value
     */
    @Override
    public int getIntInput() {
        boolean inputOk = false;
        Integer intInput = null;
        do {
            try {
                intInput = sc.nextInt();
                inputOk = true;
            } catch (InputMismatchException e) {
                this.printErrorln("Please define an integer.");
                this.resetScanner();
            }
        }
        while(!inputOk);
        return intInput;
    }

    /**
     * waits for a user input and requires a String value
     *
     * @return the given String value
     */
    @Override
    public String getStringInput() {
        boolean inputOk = false;
        String stringInput = null;
        do {
            try {
                stringInput = sc.nextLine();
                if (stringInput == null || stringInput.isEmpty()) {
                    throw new InputMismatchException("Defined value is not valid");
                }
                inputOk = true;
            } catch (InputMismatchException e) {
                this.printErrorln("Please define a string.");
                this.resetScanner();
            }
        }
        while(!inputOk);
        return stringInput;
    }

    /**
     * only accessible in the MDConverter framework
     * @param type the OutputStream type
     * @return the OutputStream according to the given {@link ConsoleHandler.LinePrefix}
     */
    public OutputStream getStream(LinePrefix type) {
        switch (type) {
            case ERROR:
                return errorStream;
            case INFO:
            case NONE:
            default:
                return infoStream;
        }
    }


    private void resetScanner() {
        sc.reset();
        sc = new Scanner(System.in);
    }
}
