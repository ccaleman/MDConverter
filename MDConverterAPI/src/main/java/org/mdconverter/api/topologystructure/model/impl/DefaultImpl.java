package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class DefaultImpl extends ValueHolder implements Default {

    //Fields
    private Integer nboudnFT = 1;
    private Integer combRule = 2;
    private boolean genPair = true;
    private BigDecimal fudgeLJ = new BigDecimal(0.5);
    private BigDecimal fudgeQQ = new BigDecimal(0.8333);


    //Constructors
    /**
     * default constructor
     * @since {@link ModelVersion#V1}
     */
    public DefaultImpl() {
    }

    /**
     * constructor for two convertible values
     *
     * @since {@link ModelVersion#V1}
     */
    public DefaultImpl(Integer nboudnFT, Integer combRule, boolean genPair, BigDecimal fudgeLJ, BigDecimal fudgeQQ) {
        this.nboudnFT = nboudnFT;
        this.combRule = combRule;
        this.genPair = genPair;
        this.fudgeLJ = fudgeLJ;
        this.fudgeQQ = fudgeQQ;
    }

    //Getters & Setters

    /**
     * Use 1 (Lennard-Jones) or 2 (Buckingham) <br>
     * default = 1
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    public Integer getNboudnFT() {
        return nboudnFT;
    }

    /**
     * @param nboudnFT an Integer
     * @since {@link ModelVersion#V1}
     */
    public void setNboudnFT(Integer nboudnFT) {
        this.nboudnFT = nboudnFT;
    }

    /**
     * van der Waals parameters (C or sigma/epsilon) <br>
     * use 1 or 3 for C <br>
     * use 2 for van der Waals <br>
     * default = 2
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    public Integer getCombRule() {
        return combRule;
    }

    /**
     * @param combRule an Integer
     * @since {@link ModelVersion#V1}
     */
    public void setCombRule(Integer combRule) {
        this.combRule = combRule;
    }

    /**
     * <br>
     * default = true
     * @return a boolean
     * @since {@link ModelVersion#V1}
     */
    public boolean isGenPair() {
        return genPair;
    }

    /**
     * @param genPair a boolean
     * @since {@link ModelVersion#V1}
     */
    public void setGenPair(boolean genPair) {
        this.genPair = genPair;
    }

    /**
     * defines the value for convertible value c1 <br>
     * default = 0.5
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC1() {
        return fudgeLJ;
    }

    /**
     * sets the value for convertible value c1
     * @param fudgeLJ a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC1(BigDecimal fudgeLJ) {
        this.fudgeLJ = fudgeLJ;
    }

    /**
     * defines the value for convertible value c2 <br>
     * default = 0.8333
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC2() {
        return fudgeQQ;
    }

    /**
     * sets the value for convertible value c2
     * @param fudgeQQ a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC2(BigDecimal fudgeQQ) {
        this.fudgeQQ = fudgeQQ;
    }
}
