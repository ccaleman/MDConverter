package org.mdconverter.classloader;


import org.mdconverter.plugin.type.FileType;
import org.mdconverter.plugin.type.PluginType;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Path;

/**
 * Created by miso on 28.10.2015.
 */
public class LoaderInput {

    private PluginType pluginType = null;
    private FileType fileType = null;
    private String pluginName = null;

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
