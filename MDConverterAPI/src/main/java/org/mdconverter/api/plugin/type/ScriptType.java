package org.mdconverter.api.plugin.type;

import com.google.gson.annotations.SerializedName;

/**
 * Defines available script types
 * Created by miso on 13.11.2015.
 */
public enum ScriptType {
    @SerializedName("jython")
    JYTHON ("jython"),
    @SerializedName("python")
    PYTHON ("python"),
    @SerializedName("none")
    NONE ("none");

    private final String value;
    public String getValue() {
        return value;
    }

    ScriptType(String value) {
        this.value = value;
    }
}
