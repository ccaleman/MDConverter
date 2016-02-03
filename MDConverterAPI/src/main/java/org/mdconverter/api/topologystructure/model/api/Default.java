package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Default {

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    Integer getNboudnFT();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    Integer getCombRule();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    boolean isGenPair();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param nboudnFT
     */
    void setNboudnFT(Integer nboudnFT);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param combRule
     */
    void setCombRule(Integer combRule);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param genPair
     */
    void setGenPair(boolean genPair);
}
