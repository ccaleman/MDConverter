package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.Angle;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 27.12.2015.
 */
public class AngleRestraintZImpl extends ValueHolder implements Angle {

    //Fields
    private String ai;
    private String aj;
    private Integer funcType;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public AngleRestraintZImpl() {
    }

    /**
     * constructor for three convertible values
     * @since {@link ModelVersion#V1}
     */
    public AngleRestraintZImpl(String ai, String aj, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3) {
        this.ai = ai;
        this.aj = aj;
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
     * defines the value for atom reference j
     * @return a String (reference to atom number)
     * @since {@link ModelVersion#V1}
     */
    public String getAj() {
        return aj;
    }

    /**
     * sets the value for atom reference j
     * @param aj a String (reference to atom number)
     * @since {@link ModelVersion#V1}
     */
    public void setAj(String aj) {
        this.aj = aj;
    }

    /**
     * not implemented in this class
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public String getAk() {
        throw new UnsupportedOperationException();
    }

    /**
     * not implemented in this class
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public void setAk(String ak) {
        throw new UnsupportedOperationException();
    }

    /**
     * not implemented in this class
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public String getAl() {
        throw new UnsupportedOperationException();
    }

    /**
     * not implemented in this class
     * @throws UnsupportedOperationException
     * @since {@link ModelVersion#V1}
     */
    public void setAl(String al) {
        throw new UnsupportedOperationException();
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
