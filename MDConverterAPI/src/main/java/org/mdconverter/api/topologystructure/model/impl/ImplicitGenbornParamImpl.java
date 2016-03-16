package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.ImplicitGenbornParam;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class ImplicitGenbornParamImpl extends ValueHolder implements ImplicitGenbornParam {

    //Fields
    private String atom;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;
    private BigDecimal c4;
    private BigDecimal c5;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public ImplicitGenbornParamImpl() {
    }

    /**
     * constructor for four convertible values
     * @since {@link ModelVersion#V1}
     */
    public ImplicitGenbornParamImpl(String atom, BigDecimal c1, BigDecimal c2, BigDecimal c3, BigDecimal c4, BigDecimal c5) {
        this.atom = atom;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
    }

    //Getters & Setters

    /**
     * defines the atom name
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    public String getAtom() {
        return atom;
    }

    /**
     * sets the atom name
     * @param atom an Integer
     * @since {@link ModelVersion#V1}
     */
    public void setAtom(String atom) {
        this.atom = atom;
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
}
