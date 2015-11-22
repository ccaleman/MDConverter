package org.mdconverter;

import com.google.common.base.Charsets;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureImpl;
import org.mdconverter.argumentparser.ArgumentParser;
import org.mdconverter.argumentparser.argumentdefinition.MainArguments;
import org.mdconverter.classloader.PluginLoader;
import org.mdconverter.consolewriter.ConsoleWriterImpl;
import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.reader.AbstractReader;
import org.mdconverter.plugin.type.FileType;
import org.mdconverter.plugin.writer.AbstractWriter;
import org.mdconverter.unitconverter.UnitConverterImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

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
        AbstractReader reader = setupReader();
        if (reader.getPluginManifest().getFileType().equals(FileType.STRUCTURE)) {
            Structure structure = new StructureImpl();
            reader.setStructure(structure);
        } else {
            //TODO: set topology structure
            reader.setStructure(null);
        }
        Object structure = null;
        try {
            structure = reader.getMetaModel();
        } catch (InvalidParameterException e) {
            consoleWriter.printErrorln(e.getMessage());
            consoleWriter.printInfoln(reader.getUsage());
            throw new RuntimeException();
        }
        AbstractWriter writer = setupWriter();
        FileType fileType = reader.getPluginManifest().getFileType();
        unitConverter.convertStructure(structure, fileType);
        if (fileType.equals(FileType.STRUCTURE)) {
            Structure struct = (Structure) structure;
            writer.setStructure(struct);
        } else {
            //TODO: set topology structure
            writer.setStructure(null);
        }
        try {
            String output = writer.getOutput();
            Path file = argumentParser.getMainArguments().getOutputFile();
            if (file != null) {
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file.toString()), Charsets.UTF_8))) {
                    out.write(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                consoleWriter.printErrorln("\n" + output);
            }
        } catch (InvalidParameterException e) {
            consoleWriter.printErrorln(e.getMessage());
            consoleWriter.printInfoln(writer.getUsage());
            throw new RuntimeException();
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
        writer.setArguments(mainArguments.getReaderParams());
        writer.setUnspecifiedArguments(mainArguments.getUnknown());
        unitConverter.setWriterUnits(writer.getPluginManifest().getMeasurementUnits());
        return writer;
    }
}
