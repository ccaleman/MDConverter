package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.Angle;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class AngleImpl extends ValueHolder implements Angle {

    //Fields
    private String ai;
    private String aj;
    private String ak;
    private Integer funcType;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;
    private BigDecimal c4;
    private BigDecimal c5;
    private BigDecimal c6;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public AngleImpl() {
    }

    /**
     * constructor for two convertible values
     * @since {@link ModelVersion#V1}
     */
    public AngleImpl(String ai, String aj, String ak, Integer funcType, BigDecimal c1, BigDecimal c2) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
    }


    /**
     * constructor for three convertible values
     * @since {@link ModelVersion#V1}
     */
    public AngleImpl(String ai, String aj, String ak, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }


    /**
     * constructor for four convertible values
     * @since {@link ModelVersion#V1}
     */
    public AngleImpl(String ai, String aj, String ak, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3,
                     BigDecimal c4) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
    }


    /**
     * constructor for six convertible values
     * @since {@link ModelVersion#V1}
     */
    public AngleImpl(String ai, String aj, String ak, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3,
                     BigDecimal c4, BigDecimal c5, BigDecimal c6) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
        this.c6 = c6;
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

    /**
     * defines the value for convertible value c5
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC5() {
        return c5;
    }

    /**
     * sets the value for convertible value c5
     * @param c5 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC5(BigDecimal c5) {
        this.c5 = c5;
    }

    /**
     * defines the value for convertible value c6
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC6() {
        return c6;
    }

    /**
     * sets the value for convertible value c6
     * @param c6 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC6(BigDecimal c6) {
        this.c6 = c6;
    }
}
