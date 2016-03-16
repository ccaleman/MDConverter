package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface OrientationRestraint extends FuncType {

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
    String getExp();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getLabel();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAlpha();

    /**
     * @param aj a String
     * @since {@link ModelVersion#V1}
     */
    void setAj(String aj);

    /**
     * @param ai a String
     * @since {@link ModelVersion#V1}
     */
    void setAi(String ai);

    /**
     * @param exp a String
     * @since {@link ModelVersion#V1}
     */
    void setExp(String exp);

    /**
     * @param label a String
     * @since {@link ModelVersion#V1}
     */
    void setLabel(String label);

    /**
     * @param alpha a String
     * @since {@link ModelVersion#V1}
     */
    void setAlpha(String alpha);
}
