package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface DihedralRestraint extends FuncType {

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
     * @param ak a String
     * @since {@link ModelVersion#V1}
     */
    void setAk(String ak);

    /**
     * @param al a String
     * @since {@link ModelVersion#V1}
     */
    void setAl(String al);

    /**
     * @param label a String
     * @since {@link ModelVersion#V1}
     */
    void setLabel(String label);
}
