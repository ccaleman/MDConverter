package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Default {

    /**
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    Integer getNboudnFT();

    /**
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    Integer getCombRule();

    /**
     * @return a boolean
     * @since {@link ModelVersion#V1}
     */
    boolean isGenPair();

    /**
     * @param nboudnFT an Integer
     * @since {@link ModelVersion#V1}
     */
    void setNboudnFT(Integer nboudnFT);

    /**
     * @param combRule an Integer
     * @since {@link ModelVersion#V1}
     */
    void setCombRule(Integer combRule);

    /**
     * @param genPair a boolean
     * @since {@link ModelVersion#V1}
     */
    void setGenPair(boolean genPair);
}
