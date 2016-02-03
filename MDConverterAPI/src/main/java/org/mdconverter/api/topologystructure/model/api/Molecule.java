package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Molecule {

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getName();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    Integer getNrMol();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param name
     */
    void setName(String name);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param nrMol
     */
    void setNrMol(Integer nrMol);
}
