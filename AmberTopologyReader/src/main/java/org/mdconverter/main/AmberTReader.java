package org.mdconverter.main;

import com.google.inject.Inject;
import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.reader.AbstractReader;
import org.mdconverter.api.topologystructure.model.TopologyStructure;
import org.mdconverter.fileparser.InputParserT;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by miso on 20.01.2016.
 */
public class AmberTReader extends AbstractReader {


    @Inject
    private InputParserT inputParserT;

    @Override
    public Object getMetaModel() throws InvalidParameterException, InvalidInputException {
        try {
            Path path = setDefaultArgs();
            inputParserT.setStructure(new TopologyStructure(getPluginManifest().getModelVersion()));
            inputParserT.parseInput(Files.readAllBytes(getInputFile()), path, true, null, getArguments());
            return inputParserT.getStructure();
        } catch (IOException | URISyntaxException | NumberFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "AmberTR is able to read *.prmtop files.";
    }

    @Override
    public String getUsage() {
        //TODO: adopt to amber
        return "AmberTR:\n" +
                "\tFor atom coordinates definition file:\n" +
                "\t\tinpcrd:<path/to/file>\n" +
                "\t\tdefault = null\n" +
                "\tFor position restraint water definition:\n" +
                "\t\tposreswat:true\n" +
                "\t\tdefault = false\n" +
                "\tFor use of heavy water definition:\n" +
                "\t\theavyW:\n" +
                "\t\tdefault = false\n" +
                "\tFor use flexible definition:\n" +
                "\t\tflex:true\n" +
                "\t\tdefault = false\n" +
                "Actual ff resource file version from gromacs: 5.0.5\n";
    }

    private Path setDefaultArgs() {
        Map<String, String> arguments = getArguments();
        if (arguments != null && !arguments.isEmpty()) {
            String inpcrd = arguments.getOrDefault("inpcrd", String.valueOf(false));
            if (!inpcrd.contains("false") && Files.exists(Paths.get(inpcrd))) {
                return Paths.get(inpcrd);
            }
        }
        return null;
    }
}
