package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class AngleRestraintImpl extends AngleRestraintZImpl {

    //Fields
    private String ak;
    private String al;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public AngleRestraintImpl() {
    }

    /**
     * constructor for three convertible values
     *
     * @since {@link ModelVersion#V1}
     */
    public AngleRestraintImpl(String ai, String aj, String ak, String al, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3) {
        super(ai, aj, funcType, c1, c2, c3);
        this.ak = ak;
        this.al = al;
    }

    //Getters & Setters

    /**
     * defines the value for atom reference k
     * @return a String (reference to atom number)
     * @since {@link ModelVersion#V1}
     */
    public String getAk() {
        return ak;
    }

    /**
     * sets the value for atom reference k
     * @param ak a String (reference to atom number)
     * @since {@link ModelVersion#V1}
     */
    public void setAk(String ak) {
        this.ak = ak;
    }

    /**
     * defines the value for atom reference l
     * @return a String (reference to atom number)
     * @since {@link ModelVersion#V1}
     */
    public String getAl() {
        return al;
    }

    /**
     * sets the value for atom reference l
     * @param al a String (reference to atom number)
     * @since {@link ModelVersion#V1}
     */
    public void setAl(String al) {
        this.al = al;
    }
}
