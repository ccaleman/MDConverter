package org.mdconverter.plugin.type;

import com.google.gson.annotations.SerializedName;

/**
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
