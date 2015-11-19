package org.mdconverter.plugin.context;

import org.mdconverter.plugin.PluginManifest;

import java.util.List;
import java.util.Map;

/**
 * Created by miso on 28.10.2015.
 */
public interface Context {

    String getDescription();
    String getName();
    PluginManifest getPluginManifest();
    void setArguments(Map<String, String> args);
    void setUnspecifiedArguments(List<String> args);
    String getUsage();
}
