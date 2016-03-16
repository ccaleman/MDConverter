package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface DistanceRestraint extends FuncType {

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
    String getType();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getLabel();

    /**
     * @param ai a String
     * @since {@link ModelVersion#V1}
     */
    void setAi(String ai);

    /**
     * @param aj a String
     * @since {@link ModelVersion#V1}
     */
    void setAj(String aj);

    /**
     * @param type a String
     * @since {@link ModelVersion#V1}
     */
    void setType(String type);

    /**
     * @param label a String
     * @since {@link ModelVersion#V1}
     */
    void setLabel(String label);
}
