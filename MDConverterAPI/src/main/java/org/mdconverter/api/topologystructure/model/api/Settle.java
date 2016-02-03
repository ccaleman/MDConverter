package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Settle extends FuncType {

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getAtom();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param atom
     */
    void setAtom(String atom);
}
