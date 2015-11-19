package org.mdconverter.plugin.reader;

import org.mdconverter.consolewriter.ConsoleWriter;
import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.PluginManifest;
import org.mdconverter.unitconverter.UnitConverter;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by miso on 28.10.2015.
 */
public abstract class AbstractReader<T> implements Reader<T> {

    @Inject
    private ConsoleWriter consoleWriter;
    @Inject
    private UnitConverter unitConverter;

    private PluginManifest pluginManifest;
    private Map<String, String> arguments;
    private Path inputFile;
    private List<String> unspecifiedArgs;
    private T structure;

    public String getDescription() {
        return "Description not defined";
    }

    @Override
    public T getMetaModel() throws InvalidParameterException{
        return this.structure;
    }

    @Override
    public final void setStructure(T structure) {
        this.structure = structure;
    }

    @Override
    public final void setUnspecifiedArguments(List<String> args) {
        this.unspecifiedArgs=args;
    }

    @Override
    public String getUsage() {
        return "No Usage defined!";
    }

    public final PluginManifest getPluginManifest() {
        return this.pluginManifest;
    }

    public final String getName() {
        return getPluginManifest().getPluginName();
    }

    public final void setPluginManifest(PluginManifest pluginManifest) {
        this.pluginManifest = pluginManifest;
    }

    public final void setArguments(Map<String, String> args) {
        this.arguments = args;
    }

    public final void setInputFile(Path input) {
        this.inputFile = input;
    }

    protected final List<String> getUnspecifiedArgs() {
        return unspecifiedArgs;
    }

    protected final Map<String, String> getArguments() {
        return arguments;
    }

    protected final Path getInputFile() {
        return inputFile;
    }

    protected final T getStructure() {
        return structure;
    }

    protected final ConsoleWriter getConsoleWriter() {
        return consoleWriter;
    }

    protected final UnitConverter getUnitConverter() {
        return unitConverter;
    }
}
