package org.mdconverter.api.plugin.type;

import com.google.gson.annotations.SerializedName;

/**
 * Defines available file types for plugins
 * Created by miso on 28.10.2015.
 */
public enum FileType {
    @SerializedName("structure")
    STRUCTURE ("structure"),
    @SerializedName("topology")
    TOPOLOGY ("topology");

    private final String value;
    public String getValue() {
        return value;
    }

    FileType(String value) {
        this.value = value;
    }
}
