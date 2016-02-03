package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Atom {

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    Integer getNr();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getType();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    Integer getResNr();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getResName();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getAtomName();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    Integer getChargeGroupNr();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @return
     */
    String getTypeB();

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param nr
     */
    void setNr(Integer nr);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param type
     */
    void setType(String type);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param resNr
     */
    void setResNr(Integer resNr);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param resName
     */
    void setResName(String resName);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param atomName
     */
    void setAtomName(String atomName);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param chargeGroupNr
     */
    void setChargeGroupNr(Integer chargeGroupNr);

    /**
     * added at {@link ModelVersion#V1}
     *
     * @param typeB
     */
    void setTypeB(String typeB);
}
