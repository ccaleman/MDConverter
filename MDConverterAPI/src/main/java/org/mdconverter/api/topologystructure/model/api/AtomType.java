package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface AtomType {

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getName();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getNum();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getParticleType();

    /**
     * @param name a String
     * @since {@link ModelVersion#V1}
     */
    void setName(String name);

    /**
     * @param num a String
     * @since {@link ModelVersion#V1}
     */
    void setNum(String num);

    /**
     * @param particleType a String
     * @since {@link ModelVersion#V1}
     */
    void setParticleType(String particleType);
}
