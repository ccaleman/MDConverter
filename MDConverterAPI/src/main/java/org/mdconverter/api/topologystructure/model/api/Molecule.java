package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Molecule {

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getName();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    Integer getNrMol();

    /**
     * @param name a String
     * @since {@link ModelVersion#V1}
     */
    void setName(String name);

    /**
     * @param nrMol a String
     * @since {@link ModelVersion#V1}
     */
    void setNrMol(Integer nrMol);
}
