
package org.mdconverter.consolewriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by miso on 23.10.2015.
 */
@Singleton
public class ConsoleWriterImpl extends PrintStream implements ConsoleWriter {


    private final OutputStream errorStream;
    private OutputStream infoStram;
    private Scanner sc = new Scanner(System.in);

    @Inject
    public ConsoleWriterImpl(@Named("INFO") OutputStream out, @Named("ERROR") OutputStream err) {
        super(out, true);
        this.errorStream = err;
        this.infoStram = out;
    }

    @Override
    public void println(LinePrefix prfx, String... lines) {
        for (String line : lines) {
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

    private void println(String output, OutputStream stream) {
        byte[] bytes = output.getBytes();
        try {
            stream.write(bytes);
            stream.write('\n');
            this.getInfoStram().flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void println(String output) {
        this.println(output, infoStram);
    }

    @Override
    public void printErrorln(String output) {
        output = "ERROR: " + output;
        this.println(output, errorStream);
    }

    @Override
    public void printInfoln(String output) {
        output = "INFO: " + output;
        this.println(output, infoStram);
    }

    public int getIntInput() {
        return sc.nextInt();
    }

    public String getStringInput() {
        return sc.nextLine();
    }

    public OutputStream getInfoStram() {
        return infoStram;
    }

    public void resetScanner() {
        sc.reset();
        sc = new Scanner(System.in);
    }
}
