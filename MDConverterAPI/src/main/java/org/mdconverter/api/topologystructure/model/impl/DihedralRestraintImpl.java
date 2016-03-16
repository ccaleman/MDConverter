package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.DihedralRestraint;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class DihedralRestraintImpl extends ValueHolder implements DihedralRestraint {

    //Fields
    private String ai;
    private String aj;
    private String ak;
    private String al;
    private String label;
    private Integer funcType;
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
    public DihedralRestraintImpl() {
    }

    /**
     * constructor for two convertible values
     *
     * @since {@link ModelVersion#V1}
     */
    public DihedralRestraintImpl(String ai, String aj, String ak, String al, Integer funcType, BigDecimal c1,
                                 BigDecimal c2) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
    }

    /**
     * constructor for four convertible values
     *
     * @since {@link ModelVersion#V1}
     */
    public DihedralRestraintImpl(String ai, String aj, String ak, String al, Integer funcType, String label, BigDecimal c1,
                                 BigDecimal c2, BigDecimal c3, BigDecimal c4) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.label = label;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
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

    /**
     * defines the label for the restraint
     * @return a String
     */
    public String getLabel() {
        return label;
    }

    /**
     * sets the label
     * @param label a String
     */
    public void setLabel(String label) {
        this.label = label;
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
