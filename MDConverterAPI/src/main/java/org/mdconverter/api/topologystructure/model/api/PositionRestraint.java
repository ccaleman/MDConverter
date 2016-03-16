package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface PositionRestraint extends FuncType {

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAi();

    /**
     * @param ai a String
     * @since {@link ModelVersion#V1}
     */
    void setAi(String ai);
}
