package org.mdconverter.api.plugin.context;

import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.PluginManifest;

import java.util.List;
import java.util.Map;

/**
 * This interface defines the common ground for reader and writer plugins
 * Created by miso on 28.10.2015.
 */
public interface Context {

    /**
     * Is called by the framework during plugin loading to inform user about plugin functionality
     * @return a {@link String} with the description of the plugin.
     */
    String getDescription();

    /**
     * Is called by the framework to display user a readable name of the plugin (name defined in manifest.json)
     * @return a {@link String} with the name of the plugin.
     */
    String getName();

    /**
     * @return a {@link PluginManifest} containing the configuration parameters defined in manifest.json of the plugin
     */
    PluginManifest getPluginManifest();

    /**
     * Provides the possibility to the framework to commit given user input arguments to the plugin
     * @param args a {@code Map<String, String>} holding the user arguments for the plugin
     */
    void setArguments(Map<String, String> args);

    /**
     * Provides the possibility to the framework to commit given user input arguments which aren't defined nor
     * added as plugin specific argument
     * @param args a {@code List<String>} containing unspecified arguments
     */
    void setUnspecifiedArguments(List<String> args);

    /**
     * This method is called by the framework, if plugin throws {@link InvalidParameterException}
     * @return a {@link String} containing the help for a plugin
     */
    String getUsage();
}
