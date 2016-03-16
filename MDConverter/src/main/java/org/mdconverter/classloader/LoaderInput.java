package org.mdconverter.classloader;


import org.mdconverter.api.plugin.type.FileType;
import org.mdconverter.api.plugin.type.PluginType;

/**
 * Created by miso on 28.10.2015. <br>
 * contains the loading parameter for the plugins
 */
public class LoaderInput {

    //Fields
    private PluginType pluginType = null;
    private FileType fileType = null;
    private String pluginName = null;

    //Getters & Setters
    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getPluginName() {
        return pluginName;
    }

    public PluginType getPluginType() {
        return pluginType;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }
}
