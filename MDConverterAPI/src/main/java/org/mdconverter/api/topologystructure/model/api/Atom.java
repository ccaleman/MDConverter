package org.mdconverter.api.topologystructure.model.api;

import org.mdconverter.api.topologystructure.ModelVersion;

/**
 * Created by miso on 03.02.2016.
 */
public interface Atom {

    /**
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    Integer getNr();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getType();

    /**
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    Integer getResNr();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getResName();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getAtomName();

    /**
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    Integer getChargeGroupNr();

    /**
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    String getTypeB();

    /**
     * @param nr an Integer
     * @since {@link ModelVersion#V1}
     */
    void setNr(Integer nr);

    /**
     * @param type a String
     * @since {@link ModelVersion#V1}
     */
    void setType(String type);

    /**
     * @param resNr an Integer
     * @since {@link ModelVersion#V1}
     */
    void setResNr(Integer resNr);

    /**
     * @param resName a String
     * @since {@link ModelVersion#V1}
     */
    void setResName(String resName);

    /**
     * @param atomName a String
     * @since {@link ModelVersion#V1}
     */
    void setAtomName(String atomName);

    /**
     * @param chargeGroupNr an Integer
     * @since {@link ModelVersion#V1}
     */
    void setChargeGroupNr(Integer chargeGroupNr);

    /**
     * @param typeB a String
     * @since {@link ModelVersion#V1}
     */
    void setTypeB(String typeB);
}
