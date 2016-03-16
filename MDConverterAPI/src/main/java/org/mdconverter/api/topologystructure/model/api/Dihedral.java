package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Dihedral extends FuncType {

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAi();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAj();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAk();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAl();

    /**
     * @param ai a String
     * @since {@link ModelVersion#V1}
     */
    void setAi(String ai);

    /**
     * @param aj a String
     * @since {@link ModelVersion#V1}
     */
    void setAj(String aj);

    /**
     * @param ak a String
     * @since {@link ModelVersion#V1}
     */
    void setAk(String ak);

    /**
     * @param al a String
     * @since {@link ModelVersion#V1}
     */
    void setAl(String al);
}
