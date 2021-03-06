package org.mdconverter.api.plugin.type;

import com.google.gson.annotations.SerializedName;

/**
 * Defines available plugin types
 * Created by miso on 28.10.2015.
 */
public enum PluginType {

    @SerializedName("reader")
    READER ("reader"),
    @SerializedName("writer")
    WRITER ("writer");

    private final String value;
    public String getValue() {
        return value;
    }

    PluginType(String value) {
        this.value = value;
    }
}
