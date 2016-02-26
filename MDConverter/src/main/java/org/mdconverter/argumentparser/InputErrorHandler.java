package org.mdconverter.argumentparser;

import com.beust.jcommander.internal.Lists;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.plugin.PluginManifest;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.plugin.type.PluginType;
import org.mdconverter.classloader.LoaderInput;
import org.mdconverter.classloader.PluginLoader;
import org.mdconverter.classloader.PluginMisconfigurationException;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by miso on 11.11.2015.
 */
public class InputErrorHandler {

    private final ConsoleWriter cw;
    private final PluginLoader pl;

    @Inject
    public InputErrorHandler(ConsoleWriter cw, PluginLoader pl) {
        this.cw = cw;
        this.pl = pl;
    }

    public List<LoaderInput> handleErrors(List<InputError> errors, LoaderInput reader, LoaderInput writer) throws PluginMisconfigurationException, IOException, URISyntaxException {
        for (InputError error : errors) {
            switch (error) {
                case NO_READER:
                    setPluginForLoaderInput(reader, PluginType.READER);
                    break;
                case NO_WRITER:
                    setPluginForLoaderInput(writer, PluginType.WRITER);
                    break;
                case NO_FILETYPE:
                    cw.println("Following file types can be choosen:");
                    cw.println("(0) exit");
                    cw.println("(1) structure");
                    cw.println("(2) topology");
                    Integer intInput = cw.getIntInput();
                    if (intInput.equals(1)) {
                        reader.setFileType(FileType.STRUCTURE);
                        writer.setFileType(FileType.STRUCTURE);
                    } else if (intInput.equals(2)){
                        reader.setFileType(FileType.TOPOLOGY);
                        writer.setFileType(FileType.TOPOLOGY);
                    } else {
                        throw new RuntimeException("User aborted task.");
                    }
                    break;
                case NO_INPUT:
                    throw new RuntimeException("No input file defined!");
                default:
                    cw.printErrorln("Such an error is not defined in InputErrorHandler!");
            }
        }
        if (checkLoderInput(reader) && checkLoderInput(writer)) {
            return Lists.newArrayList(reader, writer);
        }
        throw new RuntimeException("System was not able to complete user inputs!");
    }

    private void setPluginForLoaderInput(LoaderInput input, PluginType pluginType) throws IOException, PluginMisconfigurationException, URISyntaxException {
        input.setPluginType(pluginType);
        cw.println(String.format("Following %s can be choosen:",input.getPluginType().getValue()));
        List<PluginManifest> pluginManifests = pl.getPluginManifestsByLoaderInput(input);
        int i=0;
        cw.println(String.format("(%s) exit", i));
        for (i = 0; i < pluginManifests.size(); i++) {
            PluginManifest pluginManifest = pluginManifests.get(i);
            cw.println(String.format("(%s) " + pluginManifest.getPluginName(), i+1));
        }
        Integer intInput = cw.getIntInput();
        if (intInput > 0 && intInput < pluginManifests.size()+1) {
            input.setPluginName(pluginManifests.get(intInput-1).getPluginName());
        } else {
            throw new RuntimeException("User aborted task.");
        }
    }

    private boolean checkLoderInput(LoaderInput input) {
        if (input.getFileType() == null || input.getPluginName() == null || input.getPluginType() == null) {
            return false;
        }
        return true;
    }
}