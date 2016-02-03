package org.mdconverter.api.plugin;

import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.plugin.type.PluginType;
import org.mdconverter.api.plugin.type.ScriptType;
import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.TopologyStructure;

import java.util.Map;

/**
 * This class holds the configuration parameters from the manifest.json of a plugin
 * Created by miso on 28.10.2015.
 */
public class PluginManifest {

    /**
     * The pluginName defined in manifest.json
     */
    private String pluginName;

    /**
     * The version defined in manifest.json
     */
    private String version;

    /**
     * The main class defined in manifest.json
     */
    private String className;

    /**
     * The fileExtension defined in manifest.json
     */
    private String fileExtension;

    /**
     * The {@link FileType} defined in manifest.json
     * either can be {@link FileType#STRUCTURE} or {@link FileType#TOPOLOGY}
     */
    private FileType fileType;

    /**
     * The {@link PluginType} defined in manifest.json
     * either can be {@link PluginType#READER} or {@link PluginType#WRITER}
     */
    private PluginType pluginType;

    /**
     * Defines if the Plugin uses scripts or not
     * Default = false
     */
    private boolean script = false;

    /**
     * The {@link ScriptType} defines which kind of scripts are used
     * either can be {@link ScriptType#JYTHON}, {@link ScriptType#NONE}, {@link ScriptType#PYTHON}
     * at the moment only {@link ScriptType#JYTHON} and {@link ScriptType#NONE} are supported
     * Default = {@link ScriptType#NONE}
     */
    private ScriptType scriptType = ScriptType.NONE;

    /**
     * Holds the different mesurement units for the plugin (depends on simulation tool)
     * will be used by the framework to convert units between different tools
     */
    private Map<String, Map<String, Map<String, String>>> measurementUnits;

    /**
     * Holds the supported {@link ModelVersion} for the plugin (only used for {@link TopologyStructure} model)
     * will be checked before conversion process to ensure compatibility between topology reader- and writer-plugins
     */
    private ModelVersion modelVersion;

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

    public Map<String, Map<String, Map<String, String>>> getMeasurementUnits() {
        return measurementUnits;
    }

    public boolean isScript() {
        return script;
    }

    public ScriptType getScriptType() {
        return scriptType;
    }

    public Map<String, Map<String, String>> unitsForType(Class clazz) {
        return measurementUnits.getOrDefault(clazz.getName(), measurementUnits.get("global"));
    }

    public ModelVersion getModelVersion() {
        return modelVersion;
    }
}