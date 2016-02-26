
package org.mdconverter.consolewriter;

import org.mdconverter.api.consolewriter.ConsoleWriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by miso on 23.10.2015.
 */
@Singleton
public class ConsoleWriterImpl extends PrintStream implements ConsoleWriter {

    private final OutputStream errorStream;
    private OutputStream infoStream;
    private Scanner sc = new Scanner(System.in);

    @Inject
    public ConsoleWriterImpl(@Named("INFO") OutputStream out, @Named("ERROR") OutputStream err) {
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

    public String getStringInput() {
        boolean inputOk = false;
        String stringInput = null;
        do {
            try {
                stringInput = sc.nextLine();
                inputOk = true;
            } catch (InputMismatchException e) {
                this.printErrorln("Please define a string.");
                this.resetScanner();
            }
        }
        while(!inputOk);
        return stringInput;
    }

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
