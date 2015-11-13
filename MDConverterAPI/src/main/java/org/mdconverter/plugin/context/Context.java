package org.mdconverter.plugin.context;

import org.mdconverter.plugin.PluginManifest;

/**
 * Created by miso on 28.10.2015.
 */
public interface Context {
    String getDescription();
    String getName();
    PluginManifest getPluginManifest();
}
