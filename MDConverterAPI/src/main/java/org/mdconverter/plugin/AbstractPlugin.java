package org.mdconverter.plugin;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.consolewriter.ConsoleWriter;
import org.mdconverter.jython.JythonObjectFactory;
import org.mdconverter.plugin.context.Context;
import org.mdconverter.plugin.type.PluginType;
import org.mdconverter.plugin.type.ScriptType;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;


/**
 * Abstract class which combine identically reader and writer functionality
 * @param <T> can either be a {@link Structure} or a TOPOLOGY STRUCTURE
 * Created by miso on 26.11.2015.
 */
//TODO: replace TOPOLOGY STRUCTURE with link
public abstract class AbstractPlugin<T> implements Context {

    /**
     * Injected by framework to provide access to console
     */
    @Inject
    private ConsoleWriter consoleWriter;
    /**
     * Injected by framework to allow access to Python modules
     * only be used if plugin has own Python scripts and is defined as {@link ScriptType#JYTHON}
     */
    @Inject
    private JythonObjectFactory jythonObjectFactory;

    /**
     * Holds the {@link PluginManifest} of the plugin
     */
    private PluginManifest pluginManifest;

    /**
     * Holds the arguments given by the user for argument -r for {@link PluginType#READER}
     */
    private Map<String, String> arguments;

    /**
     * Holds all arguments given by the user which aren't defined in framework
     */
    private List<String> unspecifiedArgs;

    /**
     * Holds the MetaModel given by the framework
     */
    private T structure;

    /**
     * @see Context#getDescription()
     * @return if no description is defined in plugin implementation 'Description not defined' will be returned
     */
    public String getDescription() {
        return "Description not defined";
    }

    /**
     * Allows framework to set the plugin specific structure
     * @param structure can be {@link Structure} or a TOPOLOGY STRUCTURE
     */
    public final void setStructure(T structure) {
        this.structure = structure;
    }

    /**
     * @see Context#setUnspecifiedArguments(List)
     * @param args a {@code List<String>} containing unspecified arguments
     */
    @Override
    public final void setUnspecifiedArguments(List<String> args) {
        this.unspecifiedArgs=args;
    }

    /**
     * @see Context#getUsage()
     * @return if no usage is defined in plugin implementation 'No Usage defined!' will be returned
     */
    @Override
    public String getUsage() {
        return "No Usage defined!";
    }

    /**
     * @see Context#getPluginManifest()
     * @return the {@link PluginManifest} for the loaded plugin
     */
    public final PluginManifest getPluginManifest() {
        return this.pluginManifest;
    }

    /**
     * @see Context#getName()
     * @return the name for the loaded plugin
     */
    public final String getName() {
        return getPluginManifest().getPluginName();
    }

    /**
     * This method is called by the framework during loading process
     * @param pluginManifest contains plugin configuration loaded from manifest.json
     */
    public final void setPluginManifest(PluginManifest pluginManifest) {
        this.pluginManifest = pluginManifest;
    }

    /**
     * @see Context#setArguments(Map)
     * @param args a {@code Map<String, String>} holding the user arguments for the plugin
     */
    public final void setArguments(Map<String, String> args) {
        this.arguments = args;
    }

    /**
     * provides access to the unspecified arguments set by the framework
     * @return a {@code List<String>} with user arguments
     */
    protected final List<String> getUnspecifiedArgs() {
        return unspecifiedArgs;
    }

    /**
     * provides access to the arguments specified by the user for the plugin
     * @return a {@code Map<String, String>} with all arguments for plugin
     */
    protected final Map<String, String> getArguments() {
        return arguments;
    }

    /**
     * provides access to the MetaModel set by the framework
     * @return {@link Structure} or TOPOLOGY STRUCTURE
     */
    //TODO: replace TOPOLOGY STRUCTURE with link
    protected final T getStructure() {
        return structure;
    }

    /**
     * @return the {@link ConsoleWriter}
     */
    protected final ConsoleWriter getConsoleWriter() {
        return consoleWriter;
    }

    /**
     * @return the {@link JythonObjectFactory}
     */
    protected final JythonObjectFactory getJythonObjectFactory() {
        return jythonObjectFactory;
    }
}
