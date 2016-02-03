package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface OrientationRestraint extends FuncType {

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getAi();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getAj();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getExp();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getLabel();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getAlpha();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param aj
     */
    void setAj(String aj);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param ai
     */
    void setAi(String ai);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param exp
     */
    void setExp(String exp);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param label
     */
    void setLabel(String label);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param alpha
     */
    void setAlpha(String alpha);
}
