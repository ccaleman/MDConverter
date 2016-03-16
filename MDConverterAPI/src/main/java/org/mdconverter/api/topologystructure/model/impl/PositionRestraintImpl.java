package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.PositionRestraint;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by miso on 04.12.2015.
 */
public class PositionRestraintImpl extends ValueHolder implements PositionRestraint {

    //Fields
    private String ai;
    private Integer funcType;
    private BigDecimal c1 = new BigDecimal(BigInteger.ZERO);
    private BigDecimal c2 = new BigDecimal(BigInteger.ZERO);
    private BigDecimal c3 = new BigDecimal(BigInteger.ZERO);

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public PositionRestraintImpl() {
    }

    /**
     * constructor for three convertible values
     * @since {@link ModelVersion#V1}
     */
    public PositionRestraintImpl(String ai, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3) {
        this.ai = ai;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    //Getters & Setters

    /**
     * defines the value for atom reference i
     * @return a String (reference to atom number)
     * @since {@link ModelVersion#V1}
     */
    public String getAi() {
        return ai;
    }

    /**
     * sets the value for atom reference i
     * @param ai a String (reference to atom number)
     * @since {@link ModelVersion#V1}
     */
    public void setAi(String ai) {
        this.ai = ai;
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
}
