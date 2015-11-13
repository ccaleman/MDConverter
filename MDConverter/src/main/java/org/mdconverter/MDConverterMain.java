package org.mdconverter;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureImpl;
import org.mdconverter.argumentparser.ArgumentParser;
import org.mdconverter.argumentparser.argumentdefinition.MainArguments;
import org.mdconverter.classloader.PluginLoader;
import org.mdconverter.consolewriter.ConsoleWriterImpl;
import org.mdconverter.plugin.reader.AbstractReader;
import org.mdconverter.plugin.writer.AbstractWriter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.measure.converter.UnitConverter;
import javax.measure.quantity.Quantity;
import javax.measure.unit.NonSI;
import javax.measure.unit.Unit;
import javax.measure.unit.UnitFormat;
import java.time.temporal.IsoFields;

/**
 * Created by miso on 28.10.2015.
 */
@Singleton
public class MDConverterMain {

    private final ConsoleWriterImpl consoleWriter;
    private final ArgumentParser argumentParser;
    private final PluginLoader pluginLoader;
    private Structure structure = new StructureImpl();


    @Inject
    public MDConverterMain(ArgumentParser argumentParser, ConsoleWriterImpl consoleWriter,
                           PluginLoader pluginLoader) {
        this.consoleWriter = consoleWriter;
        this.argumentParser = argumentParser;
        this.pluginLoader = pluginLoader;
    }

    public void init(String[] args) {
        try {
            argumentParser.parseArguments(args);

            AbstractReader reader = setupReader();
            AbstractWriter writer = setupWriter();

            Unit<? extends Quantity> length = Unit.valueOf(reader.getPluginManifest().getMeasurementUnits().get("length"));
            UnitConverter length1 = length.getConverterTo(Unit.valueOf(writer.getPluginManifest().getMeasurementUnits().get("length")));
            reader.getMetaModel();

            String output = writer.getOutput();
            consoleWriter.printErrorln("\n" + output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    private AbstractWriter setupWriter() {
        AbstractWriter writer = pluginLoader.getWriter();
        MainArguments mainArguments = argumentParser.getMainArguments();
        writer.setArguments(mainArguments.getReaderParams());
        writer.setStructure(this.structure);
        writer.setUnspecifiedArguments(mainArguments.getUnknown());
        return writer;
    }

    private AbstractReader setupReader() {
        AbstractReader reader = pluginLoader.getReader();
        MainArguments mainArguments = argumentParser.getMainArguments();
        reader.setArguments(mainArguments.getReaderParams());
        reader.setInputFile(mainArguments.getInputFile());
        reader.setStructure(this.structure);
        reader.setUnspecifiedArguments(mainArguments.getUnknown());
        return reader;
    }
}
