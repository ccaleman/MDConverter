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

    private JCommander jc;
    private MainArguments mainArguments;
    private final ConsoleWriter consoleWriter;
    private final InputErrorHandler errorHandler;
    private final PluginLoader pluginLoader;

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

    public void parseArguments(String[] args) {
        // parse the arguments.
        try {
            jc.parse(args);
            if (mainArguments.isHelp()) {
                printUsage();
            } else {
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

                checkFileExtensions();

                consoleWriter.println(ConsoleWriter.LinePrefix.INFO,
                        String.format("Defined reader: %s", reader.getPluginName()),
                        pluginLoader.getReader().getDescription(),
                        String.format("Defined writer: %s", writer.getPluginName()),
                        pluginLoader.getWriter().getDescription());
            }
        } catch (ParameterException e) {
            printUsage();
            consoleWriter.printErrorln(e.getMessage() + "\n");
            throw new IllegalArgumentException("Given arguments are wrong!");
        } catch (IOException | RuntimeException e) {
            consoleWriter.printErrorln(e.getMessage() != null ? e.getMessage() : "Error not defined.");
        } catch (PluginMisconfigurationException e) {
            consoleWriter.printErrorln(e.getMessage() + "\n");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void checkFileExtensions() throws ParameterException {
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

    public JCommander getJc() {
        return jc;
    }

    public MainArguments getMainArguments() {
        return mainArguments;
    }

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

    private LoaderInput checkForFileType(LoaderInput loaderInput) {
        if (getMainArguments().isStructure()) {
            loaderInput.setFileType(FileType.STRUCTURE);
        } else if (getMainArguments().isTopology()) {
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

    private void printUsage() {
        StringBuilder stringBuilder = new StringBuilder();
        getJc().usage(stringBuilder);
        consoleWriter.println(stringBuilder.toString());
    }
}
