package org.mdconverter.argumentparser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.internal.Lists;
import com.google.common.io.Files;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.plugin.type.PluginType;
import org.mdconverter.argumentparser.argumentdefinition.MainArguments;
import org.mdconverter.classloader.LoaderInput;
import org.mdconverter.classloader.PluginLoader;
import org.mdconverter.classloader.PluginMisconfigurationException;

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
    private final ConsoleWriter consoleWriter;
    private final InputErrorHandler errorHandler;
    private final PluginLoader pluginLoader;

    //Fields
    private JCommander jc;
    private MainArguments mainArguments;
    private List<InputError> argumentsErrors = Lists.newArrayList();

    @Inject
    public ArgumentParser(@Named("project.version") String version, ConsoleWriter consoleWriter,
                          InputErrorHandler errorHandler, PluginLoader pluginLoader) {
        mainArguments = new MainArguments();
        this.consoleWriter = consoleWriter;
        this.errorHandler = errorHandler;
        this.pluginLoader = pluginLoader;
        jc = new JCommander(mainArguments);
        jc.setProgramName("MDConverter-" + version + ".jar");
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
            do {
                pluginLoader.loadPlugin(reader);
                pluginLoader.loadPlugin(writer);
                if (!pluginLoader.getInputErrors().isEmpty()) {
                    errorHandler.handleErrors(pluginLoader.getInputErrors(), reader, writer);
                }
                pluginLoader.getInputErrors().clear();
            } while ((pluginLoader.getReader() == null && pluginLoader.getWriter() == null));

            consoleWriter.println(ConsoleWriter.LinePrefix.INFO,
                    String.format("Defined reader: %s", reader.getPluginName()),
                    pluginLoader.getReader().getDescription(), mainArguments.isHelp() ? pluginLoader.getReader().getUsage() : "",
                    String.format("Defined writer: %s", writer.getPluginName()),
                    pluginLoader.getWriter().getDescription(), mainArguments.isHelp() ? pluginLoader.getWriter().getUsage() : "");
            checkFileExtensions();
            errorHandler.handleErrors(argumentsErrors, reader, writer);
        } catch (ParameterException e) {
            if (!mainArguments.isHelp()) {
                printUsage();
            }
            consoleWriter.printErrorln(e.getMessage() + "\n");
            throw new IllegalArgumentException("Given options are wrong!");
        } catch (IOException | RuntimeException e) {
            consoleWriter.printErrorln(e.getMessage() != null ? e.getMessage() : "Error not defined.");
            throw new RuntimeException();
        } catch (PluginMisconfigurationException e) {
            consoleWriter.printErrorln(e.getMessage() + "\n");
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
                consoleWriter.printErrorln("No file type defined!");
            } else if (!argumentsErrors.get(0).equals(InputError.NO_FILETYPE)) {
                argumentsErrors.remove(InputError.NO_FILETYPE);
                argumentsErrors.add(0, InputError.NO_FILETYPE);
                consoleWriter.printErrorln("No file type defined!");
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
            consoleWriter.printErrorln("No Reader defined!");
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
            consoleWriter.printErrorln("No Writer defined!");
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
        consoleWriter.println(stringBuilder.toString());
    }
}
