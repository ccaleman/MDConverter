package org.mdconverter.argumentparser;

import com.beust.jcommander.internal.Lists;
import org.mdconverter.api.consolehandler.ConsoleHandler;
import org.mdconverter.api.plugin.PluginManifest;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.plugin.type.PluginType;
import org.mdconverter.pluginloader.LoaderInput;
import org.mdconverter.pluginloader.PluginLoader;
import org.mdconverter.pluginloader.PluginMisconfigurationException;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by miso on 11.11.2015.
 */
public class InputErrorHandler {

    //Injects
    private final ConsoleHandler ch;
    private final PluginLoader pl;

    @Inject
    public InputErrorHandler(ConsoleHandler ch, PluginLoader pl) {
        this.ch = ch;
        this.pl = pl;
    }

    /**
     * Checks the given List of {@link InputError} and handle them if known (defined in {@link InputError})
     *
     * @param errors a List of found {@link InputError} (unfortunately the list needs a predefined order, see ArgumentParser)
     * @param reader the {@link LoaderInput} for the readerPlugin
     * @param writer the {@link LoaderInput} for the writerPlugin
     * @return two well configured {@link LoaderInput} or aborts the MDConverter
     * @throws PluginMisconfigurationException | IOException | URISyntaxException
     */
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
                    ch.println("Following file types can be choosen:");
                    ch.println("(0) exit");
                    ch.println("(1) structure");
                    ch.println("(2) topology");
                    Integer intInput = ch.getIntInput();
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
                    ch.printErrorln("Such an error is not defined in InputErrorHandler!");
            }
        }
        if (checkLoaderInput(reader) && checkLoaderInput(writer)) {
            errors.clear();
            return Lists.newArrayList(reader, writer);
        }
        throw new RuntimeException("System was not able to complete user inputs!");
    }

    /**
     * Generates the select options depending on the available Plugins for the given {@link PluginType}
     * @param input a configured LoaderInput
     * @param pluginType the Type of the LoaderInput plugin
     * @throws IOException | PluginMisconfigurationException | URISyntaxException
     */
    private void setPluginForLoaderInput(LoaderInput input, PluginType pluginType) throws IOException, PluginMisconfigurationException, URISyntaxException {
        input.setPluginType(pluginType);
        ch.println(String.format("Following %s can be choosen:", input.getPluginType().getValue()));
        List<PluginManifest> pluginManifests = pl.getPluginManifestsByLoaderInput(input);
        int i=0;
        ch.println(String.format("(%s) exit", i));
        for (i = 0; i < pluginManifests.size(); i++) {
            PluginManifest pluginManifest = pluginManifests.get(i);
            ch.println(String.format("(%s) " + pluginManifest.getPluginName(), i + 1));
        }
        Integer intInput = ch.getIntInput();
        if (intInput > 0 && intInput < pluginManifests.size()+1) {
            input.setPluginName(pluginManifests.get(intInput-1).getPluginName());
        } else {
            throw new RuntimeException("User aborted task.");
        }
    }

    /**
     * checks if the given LoaderInput is well configured or if something is missing
     *
     * @param input a configered {@link LoaderInput}
     * @return a boolean
     */
    private boolean checkLoaderInput(LoaderInput input) {
        if (input.getFileType() == null || input.getPluginName() == null || input.getPluginType() == null) {
            return false;
        }
        return true;
    }
}