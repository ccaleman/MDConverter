package org.mdconverter.argumentparser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.internal.Lists;
import com.google.common.io.Files;
import org.mdconverter.api.consolehandler.ConsoleHandler;
import org.mdconverter.api.plugin.reader.AbstractReader;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.plugin.type.PluginType;
import org.mdconverter.api.plugin.writer.AbstractWriter;
import org.mdconverter.argumentparser.argumentdefinition.MainArguments;
import org.mdconverter.pluginloader.LoaderInput;
import org.mdconverter.pluginloader.PluginLoader;
import org.mdconverter.pluginloader.PluginMisconfigurationException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by miso on 23.10.2015.
 */
@Singleton
public class ArgumentParser {

    //Injects
    private final ConsoleHandler consoleHandler;
    private final InputErrorHandler errorHandler;
    private final PluginLoader pluginLoader;

    //Fields
    private JCommander jc;
    private MainArguments mainArguments;
    private List<InputError> argumentsErrors = Lists.newArrayList();
    private String programName;

    @Inject
    public ArgumentParser(@Named("project.version") String version, ConsoleHandler consoleHandler,
                          InputErrorHandler errorHandler, PluginLoader pluginLoader) {
        mainArguments = new MainArguments();
        this.consoleHandler = consoleHandler;
        this.errorHandler = errorHandler;
        this.pluginLoader = pluginLoader;
        jc = new JCommander(mainArguments);
        programName = "MDConverter-" + version + ".jar";
        jc.setProgramName(programName);
    }

    /**
     * checks the arguments according to the {@link MainArguments} and sets the values <br>
     * checks if plugins are well defined. If not the input errors will be handled <br>
     * the framework tries to load the defined plugins and handles occurring errors <br>
     * checks the file extensions in the end
     *
     * @param args the given arguments from the user
     */
    public void parseArguments(String[] args) {
        // parse the arguments.
        try {
            jc.parse(args);
            if (mainArguments.isHelp()) {
                printUsage();
            }
            LoaderInput reader = isReaderDefined();
            LoaderInput writer = isWriterDefined();
            if (!argumentsErrors.isEmpty()) {
                List<LoaderInput> loaderInputs = errorHandler.handleErrors(argumentsErrors, reader, writer);
                reader = loaderInputs.get(0);
                writer = loaderInputs.get(1);

            }
            AbstractReader readerPlugin;
            AbstractWriter writerPlugin;
            do {
                pluginLoader.loadPlugin(reader);
                pluginLoader.loadPlugin(writer);
                readerPlugin = pluginLoader.getReader();
                writerPlugin = pluginLoader.getWriter();
                if (!pluginLoader.getInputErrors().isEmpty()) {
                    errorHandler.handleErrors(pluginLoader.getInputErrors(), reader, writer);
                }
                pluginLoader.getInputErrors().clear();
            } while ((readerPlugin == null && writerPlugin == null));
            consoleHandler.println(ConsoleHandler.LinePrefix.INFO,
                    String.format("Defined reader: %s", reader.getPluginName()),
                    readerPlugin.getDescription(), mainArguments.isHelp() ? readerPlugin.getUsage() : "",
                    String.format("Defined writer: %s", writer.getPluginName()),
                    writerPlugin.getDescription(), mainArguments.isHelp() ? writerPlugin.getUsage() : "");
            checkFileExtensions();
            errorHandler.handleErrors(argumentsErrors, reader, writer);
        } catch (ParameterException e) {
            if (!mainArguments.isHelp()) {
                printUsage();
            }
            consoleHandler.printErrorln(e.getMessage() + "\n");
            throw new IllegalArgumentException("Given options are wrong!");
        } catch (IOException | RuntimeException e) {
            consoleHandler.printErrorln(e.getMessage() != null ? e.getMessage() : "Error in ArgumentParser not defined.");
            throw new RuntimeException();
        } catch (PluginMisconfigurationException e) {
            consoleHandler.printErrorln(e.getMessage() != null ? e.getMessage() : String.format("Error not defined for %s", e.getPluginManifest().getPluginName()));
            throw new RuntimeException();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * small security check for the requested plugins <br>
     * checks the file extension of given input/output filename and if the according plugins are able to process (defined in manifest.json)
     * @throws ParameterException
     */
    private void checkFileExtensions() throws ParameterException {
        if (mainArguments.getInputFile() == null) {
            argumentsErrors.add(0, InputError.NO_INPUT);
            return;
        }
        String fileExtension = Files.getFileExtension(mainArguments.getInputFile().toString());
        String extension = pluginLoader.getReader().getPluginManifest().getFileExtension();
        if (!extension.equalsIgnoreCase(fileExtension)) {
            throw new ParameterException(String.format("Given input file can not be processed by chosen reader. (Found: %s / Expected: %s)", fileExtension, extension));
        }
        if (mainArguments.getOutputFile() != null) {
            fileExtension = Files.getFileExtension(mainArguments.getOutputFile().toString());
            extension = pluginLoader.getWriter().getPluginManifest().getFileExtension();
            if (!extension.equalsIgnoreCase(fileExtension)) {
                throw new ParameterException(String.format("Given output file can not be created by chosen writer. (Found: %s / Expected: %s)", fileExtension, extension));
            }
        }
    }

    /**
     * @return the argumentParser
     */
    public JCommander getJc() {
        return jc;
    }

    /**
     * @return the parsed {@link MainArguments}
     */
    public MainArguments getMainArguments() {
        return mainArguments;
    }


    public String getProgramName() {
        return programName;
    }
    /**
     * checks the given {@link LoaderInput} for the {@link FileType} if not defined adds an {@link InputError} to the argumentsErrors
     * !!!!!!!!!!!!!!always on index 0 of the list because of the double checked LoaderInput for Reader & Writer!!!!!!!!!!!!
     * @param loaderInput
     * @return
     */
    private LoaderInput checkForFileType(LoaderInput loaderInput) {
        if (getMainArguments().isStructure() && !getMainArguments().isTopology()) {
            loaderInput.setFileType(FileType.STRUCTURE);
        } else if (getMainArguments().isTopology() && !getMainArguments().isStructure()) {
            loaderInput.setFileType(FileType.TOPOLOGY);
        } else {
            if (!argumentsErrors.contains(InputError.NO_FILETYPE)) {
                argumentsErrors.add(0, InputError.NO_FILETYPE);
                consoleHandler.printErrorln("No file type defined!");
            } else if (!argumentsErrors.get(0).equals(InputError.NO_FILETYPE)) {
                argumentsErrors.remove(InputError.NO_FILETYPE);
                argumentsErrors.add(0, InputError.NO_FILETYPE);
                consoleHandler.printErrorln("No file type defined!");
            }
        }
        return loaderInput;
    }

    /**
     * checks if the {@link LoaderInput} for the readerPlugin is well defined and adds {@link InputError} to the argumentsErrors if not
     * @return the checked {@link LoaderInput} for the readerPlugin
     */
    private LoaderInput isReaderDefined() {
        String fileReader = getMainArguments().getFileReader();
        LoaderInput loaderInput = new LoaderInput();
        if (fileReader != null) {
            loaderInput.setPluginName(fileReader);
            loaderInput.setPluginType(PluginType.READER);
        } else {
            consoleHandler.printErrorln("No Reader defined!");
            argumentsErrors.add(InputError.NO_READER);
        }
        loaderInput = checkForFileType(loaderInput);
        return loaderInput;
    }

    /**
     * checks if the {@link LoaderInput} for the writerPlugin is well defined and adds {@link InputError} to the argumentsErrors if not
     *
     * @return the checked {@link LoaderInput} for the writerPlugin
     */
    private LoaderInput isWriterDefined() {
        String fileWriter = getMainArguments().getFileWriter();
        LoaderInput loaderInput = new LoaderInput();
        if (fileWriter != null) {
            loaderInput.setPluginName(fileWriter);
            loaderInput.setPluginType(PluginType.WRITER);
        } else {
            consoleHandler.printErrorln("No Writer defined!");
            argumentsErrors.add(InputError.NO_WRITER);
        }
        loaderInput = checkForFileType(loaderInput);
        return loaderInput;
    }

    /**
     * prints the usage for the MDConverter framework
     */
    private void printUsage() {
        StringBuilder stringBuilder = new StringBuilder();
        getJc().usage(stringBuilder);
        consoleHandler.println(stringBuilder.toString());
    }
}
