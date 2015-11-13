package org.mdconverter.plugin;

import org.mdconverter.plugin.type.FileType;
import org.mdconverter.plugin.type.PluginType;
import org.mdconverter.plugin.type.ScriptType;

import java.util.Map;

/**
 * Created by miso on 28.10.2015.
 */
public class PluginManifest {
    private String pluginName;
    private String version;
    private String className;
    private String fileExtension;
    private FileType fileType;
    private PluginType pluginType;
    private boolean script;
    private ScriptType scriptType;
    private Map<String, String> measurementUnits;

    public String getVersion() {
        return version;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getClassName() {
        return className;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public FileType getFileType() {
        return fileType;
    }

    public PluginType getPluginType() {
        return pluginType;
    }

    public Map<String, String> getMeasurementUnits() {
        return measurementUnits;
    }

    public boolean isScript() {
        return script;
    }

    public ScriptType getScriptType() {
        return scriptType;
    }
}