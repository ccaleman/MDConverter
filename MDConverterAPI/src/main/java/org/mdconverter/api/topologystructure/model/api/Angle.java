package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Angle extends FuncType {

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAi();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAj();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAk();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAl();

    /**
     * @param ai
     * @since {@link ModelVersion#V1}
     */
    void setAi(String ai);

    /**
     * @param aj
     * @since {@link ModelVersion#V1}
     */
    void setAj(String aj);

    /**
     * @param ak
     * @since {@link ModelVersion#V1}
     */
    void setAk(String ak);

    /**
     * @param al
     * @since {@link ModelVersion#V1}
     */
    void setAl(String al);
}
