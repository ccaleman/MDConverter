package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface AtomType {

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
    String getNum();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getParticleType();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param name
     */
    void setName(String name);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param num
     */
    void setNum(String num);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param particleType
     */
    void setParticleType(String particleType);
}
