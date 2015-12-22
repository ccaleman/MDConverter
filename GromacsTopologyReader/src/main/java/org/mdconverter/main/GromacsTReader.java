package org.mdconverter.main;

import com.google.inject.Inject;
import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.reader.AbstractReader;
import org.mdconverter.fileparser.InputParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by miso on 07.12.2015.
 */
public class GromacsTReader extends AbstractReader {

    @Inject
    private InputParser inputParser;

    @Override
    public Object getMetaModel() throws InvalidParameterException, InvalidInputException, NumberFormatException {
        try {
            Path path = checkForParams();
            inputParser.parseInput(Files.readAllBytes(getInputFile()), path, true, null);
            setStructure(inputParser.getStructure());
            return getStructure();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return getStructure();
    }

    @Override
    public String getDescription() {
        return "GromacsTR is able to read *.top files.";
    }

    @Override
    public String getUsage() {
        return "GromacsSR:\n" +
                "\tFor position restraint definition file:\n" +
                "\t\tposres:<path/to/file>";
    }

    private Path checkForParams() {
        Map<String, String> arguments = getArguments();
        if (arguments != null && !arguments.isEmpty()) {
            String posres = arguments.get("posres");
            if (Files.exists(Paths.get(posres))) {
                return Paths.get(posres);
            }
        }
        return null;
    }
}