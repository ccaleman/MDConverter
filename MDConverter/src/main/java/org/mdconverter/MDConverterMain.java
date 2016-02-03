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
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by miso on 28.10.2015.
 */
@Singleton
public class MDConverterMain {

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

    public void init(String[] args) {
        argumentParser.parseArguments(args);
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
            consoleWriter.printErrorln(e.getMessage());
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
                    throw new RuntimeException();
                }
            }
        }
    }

    private AbstractReader setupReader() {
        AbstractReader reader = pluginLoader.getReader();
        MainArguments mainArguments = argumentParser.getMainArguments();
        reader.setArguments(mainArguments.getReaderParams());
        reader.setInputFile(mainArguments.getInputFile());
        reader.setUnspecifiedArguments(mainArguments.getUnknown());
        unitConverter.setReaderUnits(reader.getPluginManifest().getMeasurementUnits());
        return reader;
    }

    private AbstractWriter setupWriter() {
        AbstractWriter writer = pluginLoader.getWriter();
        MainArguments mainArguments = argumentParser.getMainArguments();
        writer.setArguments(mainArguments.getWriterParams());
        writer.setUnspecifiedArguments(mainArguments.getUnknown());
        unitConverter.setWriterUnits(writer.getPluginManifest().getMeasurementUnits());
        return writer;
    }
}
