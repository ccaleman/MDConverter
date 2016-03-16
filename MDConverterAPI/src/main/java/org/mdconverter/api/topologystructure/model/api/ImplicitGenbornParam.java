package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface ImplicitGenbornParam {

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAtom();

    /**
     * @param atomName a String
     * @since {@link ModelVersion#V1}
     */
    void setAtom(String atomName);
}
