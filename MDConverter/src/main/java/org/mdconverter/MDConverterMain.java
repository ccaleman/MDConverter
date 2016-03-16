package org.mdconverter;

import com.google.common.base.Charsets;
import org.biojava.nbio.structure.StructureImpl;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.plugin.reader.AbstractReader;
import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.plugin.writer.AbstractWriter;
import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.TopologyStructure;
import org.mdconverter.argumentparser.ArgumentParser;
import org.mdconverter.argumentparser.argumentdefinition.MainArguments;
import org.mdconverter.classloader.PluginLoader;
import org.mdconverter.consolewriter.ConsoleWriterImpl;
import org.mdconverter.unitconverter.UnitConverterImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by miso on 28.10.2015.
 */
@Singleton
public class MDConverterMain {

    //Injects
    private final ConsoleWriterImpl consoleWriter;
    private final ArgumentParser argumentParser;
    private final PluginLoader pluginLoader;
    private final UnitConverterImpl unitConverter;

    @Inject
    public MDConverterMain(ArgumentParser argumentParser, ConsoleWriterImpl consoleWriter,
                           PluginLoader pluginLoader, UnitConverterImpl unitConverter) {
        this.consoleWriter = consoleWriter;
        this.argumentParser = argumentParser;
        this.pluginLoader = pluginLoader;
        this.unitConverter = unitConverter;
    }

    /**
     * checks the arguments given by the user <br>
     * differ between {@link FileType} and sets the according Meta Model<br>
     * runs the readerPlugin to get the Meta Model from input file <br>
     * starts the unit conversion process on the Meta Model <br>
     * here some other things should be done in future like atom translation dictionary
     * deliver Meta Model to the writerPlugin and write the output to console or into a file
     *
     * @param args
     */
    public void init(String[] args) {
        argumentParser.parseArguments(args);
        if (!argumentParser.getMainArguments().isHelp()) {
            checkSupportedModelVersion();
            AbstractReader reader = setupReader();
            if (reader.getPluginManifest().getFileType().equals(FileType.STRUCTURE)) {
                reader.setStructure(new StructureImpl());
            } else {
                reader.setStructure(new TopologyStructure(reader.getPluginManifest().getModelVersion()));
            }
            Object structure = null;
            try {
                structure = reader.getMetaModel();
            } catch (Exception e) {
                e.printStackTrace(new PrintStream(consoleWriter.getStream(ConsoleWriter.LinePrefix.ERROR)));
                consoleWriter.printInfoln(reader.getUsage());
                //TODO remove or better output
                throw new RuntimeException();
            }
            AbstractWriter writer = setupWriter();
            FileType fileType = reader.getPluginManifest().getFileType();
            try {
                unitConverter.convertStructure(structure, fileType);
            } catch (Exception e) {
                consoleWriter.printErrorln(e.getMessage());
                e.printStackTrace();
                throw new RuntimeException();
            }
            writer.setStructure(structure);
            try {
                String output = writer.getOutput();
                Path file = argumentParser.getMainArguments().getOutputFile();
                if (file != null) {
                    try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(file.toString()), Charsets.UTF_8))) {
                        out.write(output);
                        consoleWriter.printInfoln("Output was written into " + file.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    consoleWriter.printErrorln("\n" + output);
                }
            } catch (Exception e) {
                String methodName = e.getStackTrace()[0].getMethodName();
                if (methodName.contains("add")) {
                    String replace = methodName.replace("add", "");
                    consoleWriter.printErrorln(String.format("Type: %s is", methodName));
                }
                consoleWriter.printErrorln(e.getMessage());
                consoleWriter.printInfoln(writer.getUsage());
                //TODO remove or better output
                consoleWriter.println(ConsoleWriter.LinePrefix.ERROR, Arrays.toString(e.getStackTrace()));
                throw new RuntimeException();
            }
        }
    }

    /**
     * checks the supported {@link ModelVersion} of the plugins <br>
     * cancel if the ModelVersion of the reader is lower than the one of the writer <br>
     * inform the user about possible data loss if not
     */
    private void checkSupportedModelVersion() {
        if (pluginLoader.getWriter().getPluginManifest().getFileType().equals(FileType.TOPOLOGY)) {
            ModelVersion modelVersionR = pluginLoader.getReader().getPluginManifest().getModelVersion();
            ModelVersion modelVersionW = pluginLoader.getWriter().getPluginManifest().getModelVersion();
            if (!modelVersionR.equals(modelVersionW)) {
                if (ModelVersion.getVersionNumber(modelVersionR) > ModelVersion.getVersionNumber(modelVersionW)) {
                    consoleWriter.printInfoln("The ModelVersion of Reader is higher than the writer's one");
                    consoleWriter.printInfoln("DATA COULD BE LOST!");
                } else {
                    consoleWriter.printErrorln("ModelVersions of selected Plugins are incompatible!");
                    consoleWriter.printErrorln(String.format("Reader model version: %s", modelVersionR));
                    consoleWriter.printErrorln(String.format("Writer model version: %s", modelVersionW));
                    throw new RuntimeException();
                }
            }
        }
    }

    /**
     * initializes the requested readerPlugin
     * unspecified arguments are always null
     * @return a configured readerPlugin
     */
    private AbstractReader setupReader() {
        AbstractReader reader = pluginLoader.getReader();
        MainArguments mainArguments = argumentParser.getMainArguments();
        reader.setArguments(mainArguments.getReaderParams());
        reader.setInputFile(mainArguments.getInputFile());
        reader.setUnspecifiedArguments(mainArguments.getUnknown());
        unitConverter.setReaderUnits(reader.getPluginManifest().getMeasurementUnits());
        return reader;
    }

    /**
     * initializes the requested writerPlugin
     * unspecified arguments are always null
     * @return a configured writerPlugin
     */
    private AbstractWriter setupWriter() {
        AbstractWriter writer = pluginLoader.getWriter();
        MainArguments mainArguments = argumentParser.getMainArguments();
        writer.setArguments(mainArguments.getWriterParams());
        writer.setUnspecifiedArguments(mainArguments.getUnknown());
        unitConverter.setWriterUnits(writer.getPluginManifest().getMeasurementUnits());
        return writer;
    }
}
