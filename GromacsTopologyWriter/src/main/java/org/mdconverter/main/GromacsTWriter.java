package org.mdconverter.main;

import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.writer.AbstractWriter;
import org.mdconverter.api.topologystructure.TopologyStructure;
import org.mdconverter.filegenerator.FileWriter;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by miso on 22.12.2015.
 */
public class GromacsTWriter extends AbstractWriter {

    @Inject
    private FileWriter writer;

    @Override
    public String getOutput() throws InvalidParameterException, InvalidInputException {
        try {
            boolean b = checkForParams();
            return writer.parseStructure(b, (TopologyStructure) getStructure());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "GromacsTW is able to write *.top files.";
    }

    @Override
    public String getUsage() {
        //include ff data
        return "GromacsTW:\n" +
                "\tTo include force field data:\n" +
                "\t\tff:true" +
                "\t\tdefault = false";
    }

    private boolean checkForParams() {
        Map<String, String> arguments = getArguments();
        if (arguments != null && !arguments.isEmpty()) {
            return Boolean.parseBoolean(arguments.get("ff"));
        }
        return false;
    }
}
