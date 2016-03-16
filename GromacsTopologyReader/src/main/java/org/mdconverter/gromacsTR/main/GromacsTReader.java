package org.mdconverter.gromacstr.main;

import com.google.inject.Inject;
import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.reader.AbstractReader;
import org.mdconverter.api.topologystructure.model.TopologyStructure;
import org.mdconverter.gromacstr.fileparser.InputParser;

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

    //Injects
    private InputParser inputParser;

    @Inject
    public GromacsTReader(InputParser inputParser) {
        this.inputParser = inputParser;
    }

    @Override
    public Object getMetaModel() throws InvalidParameterException, InvalidInputException, NumberFormatException {
        try {
            Path path = setDefaultArgs();
            inputParser.setStructure(new TopologyStructure(getPluginManifest().getModelVersion()));
            inputParser.parseInput(Files.readAllBytes(getInputFile()), path, true, null, getArguments());
            return inputParser.getStructure();
        } catch (IOException | URISyntaxException | NumberFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "GromacsTR is able to read *.top files.";
    }

    @Override
    public String getUsage() {
        return "GromacsTR:\n" +
                "\tFor position restraint definition file:\n" +
                "\t\tposres:<path/to/file>\n" +
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

    /**
     * check given arguments and set default if not defined
     *
     * @return the path to positionRestraint file for a topology
     */
    private Path setDefaultArgs() {
        Map<String, String> arguments = getArguments();
        if (arguments != null) {
            String orDefault = arguments.getOrDefault("posreswat", String.valueOf(false));
            if (orDefault.equals(String.valueOf(false))) {
                arguments.put("posreswat", orDefault);
            }
            orDefault = arguments.getOrDefault("heavyW", String.valueOf(false));
            if (orDefault.equals(String.valueOf(false))) {
                arguments.put("heavyW", orDefault);
            }
            orDefault = arguments.getOrDefault("flex", String.valueOf(false));
            if (orDefault.equals(String.valueOf(false))) {
                arguments.put("flex", orDefault);
            }
            String posres = arguments.getOrDefault("posres", String.valueOf(false));
            if (!posres.contains("false") && Files.exists(Paths.get(posres))) {
                arguments.put("posres", posres);
                return Paths.get(posres);
            }
            arguments.put("posres", "false");
        }
        return null;
    }
}