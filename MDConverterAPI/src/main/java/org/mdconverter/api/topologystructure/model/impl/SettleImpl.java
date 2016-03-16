package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.Settle;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class SettleImpl extends ValueHolder implements Settle {

    //Fields
    private String atom;
    private Integer funcType;
    private BigDecimal c1;
    private BigDecimal c2;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public SettleImpl() {
    }

    /**
     * constructor for two convertible values
     * @since {@link ModelVersion#V1}
     */
    public SettleImpl(String atom, Integer funcType, BigDecimal c1, BigDecimal c2) {
        this.atom = atom;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
    }

    //Getters & Setters

    /**
     * defines the atom index
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public String getAtom() {
        return atom;
    }

    /**
     * sets the atom index
     * @param atom a String
     * @since {@link ModelVersion#V1}
     */
    public void setAtom(String atom) {
        this.atom = atom;
    }

    /**
     * @see FuncType
     * @return an Integer which points to the manifest.json unit definitions
     * @since {@link ModelVersion#V1}
     */
    public Integer getFuncType() {
        return funcType;
    }

    /**
     * @see FuncType
     * @param funcType an Integer which points to the manifest.json unit definitions
     * @since {@link ModelVersion#V1}
     */
    public void setFuncType(Integer funcType) {
        this.funcType = funcType;
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
}
