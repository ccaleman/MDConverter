package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Settle extends FuncType {

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAtom();

    /**
     * @param atom a String
     * @since {@link ModelVersion#V1}
     */
    void setAtom(String atom);
}
