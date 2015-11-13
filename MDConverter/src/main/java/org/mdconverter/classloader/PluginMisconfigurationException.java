package org.mdconverter.classloader;

import org.mdconverter.plugin.PluginManifest;

/**
 * Created by miso on 07.11.2015.
 */
public class PluginMisconfigurationException extends Exception {

    private PluginManifest pluginManifest;

    public PluginMisconfigurationException(PluginManifest pluginManifest) {
        super();
        this.pluginManifest = pluginManifest;
    }

    public PluginMisconfigurationException(PluginManifest pluginManifest, String message) {
        super(message);
        this.pluginManifest = pluginManifest;
    }

    public PluginMisconfigurationException(PluginManifest pluginManifest, String message, Throwable cause) {
        super(message, cause);
        this.pluginManifest = pluginManifest;
    }

    public PluginMisconfigurationException(PluginManifest pluginManifest, Throwable cause) {
        super(cause);
        this.pluginManifest = pluginManifest;
    }

    public PluginManifest getPluginManifest() {
        return pluginManifest;
    }
}
