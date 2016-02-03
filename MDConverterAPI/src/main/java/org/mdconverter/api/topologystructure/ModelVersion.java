package org.mdconverter.api.topologystructure;

/**
 * Created by miso on 03.02.2016.
 */
public enum ModelVersion {

    V1(1),
    V2(2);

    int modelVersion;

    ModelVersion(int version) {
        this.modelVersion = version;
    }

    public static int getVersionNumber(ModelVersion version) {
        for (ModelVersion v : ModelVersion.values()) {
            if (v == version) {
                return v.modelVersion;
            }
        }
        return 0;
    }
}
