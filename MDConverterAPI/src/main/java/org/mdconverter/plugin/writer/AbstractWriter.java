package org.mdconverter.plugin.writer;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.consolewriter.ConsoleWriter;
import org.mdconverter.plugin.PluginManifest;
import org.mdconverter.unitconverter.UnitConverter;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by miso on 28.10.2015.
 */
public abstract class AbstractWriter implements Writer {

    @Inject
    private ConsoleWriter consoleWriter;
    @Inject
    private UnitConverter unitConverter;

    private PluginManifest pluginManifest;
    private Map<String, String> arguments;
    private List<String> unspecifiedArgs;
    private Structure structure;

    @Override
    public String getOutput() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Description not defined";
    }

    @Override
    public final void setArguments(Map<String, String> args) {
        this.arguments = args;
    }

    @Override
    public final void setUnspecifiedArguments(List<String> args) {
        this.unspecifiedArgs = args;
    }

    @Override
    public final void setStructure(Structure structure) {
        this.structure = structure;
    }

    public final String getName() {
        return pluginManifest.getPluginName();
    }

    public final PluginManifest getPluginManifest() {
        return pluginManifest;
    }

    public final void setPluginManifest(PluginManifest pluginManifest) {
        this.pluginManifest = pluginManifest;
    }

    protected final ConsoleWriter getConsoleWriter() {
        return consoleWriter;
    }

    protected final UnitConverter getUnitConverter() {
        return unitConverter;
    }

    protected final Map<String, String> getArguments() {
        return arguments;
    }

    protected final List<String> getUnspecifiedArgs() {
        return unspecifiedArgs;
    }

    protected final Structure getStructure() {
        return structure;
    }
}
