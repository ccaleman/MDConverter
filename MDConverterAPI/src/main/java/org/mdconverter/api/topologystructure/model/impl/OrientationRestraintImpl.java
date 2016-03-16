package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.OrientationRestraint;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class OrientationRestraintImpl extends ValueHolder implements OrientationRestraint {

    //Fields
    private String ai;
    private String aj;
    private Integer funcType;
    private String exp;
    private String label;
    private String alpha;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public OrientationRestraintImpl() {
    }

    /**
     * constructor for three convertible values
     * @since {@link ModelVersion#V1}
     */
    public OrientationRestraintImpl(String ai, String aj, Integer funcType, String exp, String label, String alpha,
                                    BigDecimal c1, BigDecimal c2, BigDecimal c3) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.exp = exp;
        this.label = label;
        this.alpha = alpha;
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
     * defines the expression
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public String getExp() {
        return exp;
    }

    /**
     * sets the expression
     * @param exp a String
     * @since {@link ModelVersion#V1}
     */
    public void setExp(String exp) {
        this.exp = exp;
    }

    /**
     * defines the label
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public String getLabel() {
        return label;
    }

    /**
     * sets the label
     * @param label a String
     * @since {@link ModelVersion#V1}
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * defines the alpha
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public String getAlpha() {
        return alpha;
    }

    /**
     * sets the alpha
     * @param alpha a String
     * @since {@link ModelVersion#V1}
     */
    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    /**
     * @return an Integer which points to the manifest.json unit definitions
     * @see FuncType
     * @since {@link ModelVersion#V1}
     */
    public Integer getFuncType() {
        return funcType;
    }

    /**
     * @param funcType an Integer which points to the manifest.json unit definitions
     * @see FuncType
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
