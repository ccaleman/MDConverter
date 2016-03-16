package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.AtomType;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class AtomTypeImpl extends ValueHolder implements AtomType {

    //Fields
    private String name;
    private String num;
    private String particleType;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;
    private BigDecimal c4;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public AtomTypeImpl() {
    }

    /**
     * constructor for four convertible values
     * @since {@link ModelVersion#V1}
     */
    public AtomTypeImpl(String name, String num, BigDecimal c1, BigDecimal c2, String particleType, BigDecimal c3, BigDecimal c4) {
        this.name = name;
        this.num = num;
        this.c1 = c1;
        this.c2 = c2;
        this.particleType = particleType;
        this.c3 = c3;
        this.c4 = c4;
    }

    //Getters & Setters

    /**
     * defines the name of the atomType
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the atomType in the model
     * @param name a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * defines the atomType number of the atomType
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public String getNum() {
        return num;
    }

    /**
     * sets the atomType number of the atomType
     * @param num a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * defines the particleType of the atomType
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public String getParticleType() {
        return particleType;
    }

    /**
     * sets the particleType of the atomType
     * @param particleType a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setParticleType(String particleType) {
        this.particleType = particleType;
    }

    /**
     * defines the value for convertible value c1
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC1() {
        return c1;
    }

    /**
     * sets the value for convertible value c1
     * @param c1 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC1(BigDecimal c1) {
        this.c1 = c1;
    }

    /**
     * defines the value for convertible value c2
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC2() {
        return c2;
    }

    /**
     * sets the value for convertible value c2
     * @param c2 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC2(BigDecimal c2) {
        this.c2 = c2;
    }

    /**
     * defines the value for convertible value c3
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC3() {
        return c3;
    }

    /**
     * sets the value for convertible value c3
     * @param c3 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC3(BigDecimal c3) {
        this.c3 = c3;
    }

    /**
     * defines the value for convertible value c4
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC4() {
        return c4;
    }

    /**
     * sets the value for convertible value c4
     * @param c4 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC4(BigDecimal c4) {
        this.c4 = c4;
    }
}
