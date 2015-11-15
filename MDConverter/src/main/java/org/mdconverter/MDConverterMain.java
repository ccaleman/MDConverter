package org.mdconverter;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureImpl;
import org.mdconverter.argumentparser.ArgumentParser;
import org.mdconverter.argumentparser.argumentdefinition.MainArguments;
import org.mdconverter.classloader.PluginLoader;
import org.mdconverter.consolewriter.ConsoleWriterImpl;
import org.mdconverter.plugin.reader.AbstractReader;
import org.mdconverter.plugin.type.FileType;
import org.mdconverter.plugin.type.PluginType;
import org.mdconverter.plugin.writer.AbstractWriter;
import org.mdconverter.unitconverter.UnitConverterImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

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
        try {
            argumentParser.parseArguments(args);




            AbstractReader reader = setupReader();
            Structure structure = reader.getMetaModel();
            AbstractWriter writer = setupWriter();


            unitConverter.convertStructure(structure, FileType.STRUCTURE);
            writer.setStructure(structure);
            String output = writer.getOutput();
            consoleWriter.printErrorln("\n" + output);






        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private AbstractReader setupReader() {
        AbstractReader reader = pluginLoader.getReader();
        MainArguments mainArguments = argumentParser.getMainArguments();
        reader.setArguments(mainArguments.getReaderParams());
        reader.setInputFile(mainArguments.getInputFile());
        reader.setStructure(new StructureImpl());
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
