package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class DefaultImpl extends ValueHolder implements Default {


    private Integer nboudnFT;
    private Integer combRule;
    private boolean genPair;
    /**
     * added at {@link ModelVersion#V1}
     */
    private BigDecimal fudgeLJ;
    /**
     * added at {@link ModelVersion#V1}
     */
    private BigDecimal fudgeQQ;

    public DefaultImpl() {
    }

    public DefaultImpl(Integer nboudnFT, Integer combRule, boolean genPair, BigDecimal fudgeLJ, BigDecimal fudgeQQ) {
        this.nboudnFT = nboudnFT;
        this.combRule = combRule;
        this.genPair = genPair;
        this.fudgeLJ = fudgeLJ;
        this.fudgeQQ = fudgeQQ;
    }

    public Integer getNboudnFT() {
        return nboudnFT;
    }

    public void setNboudnFT(Integer nboudnFT) {
        this.nboudnFT = nboudnFT;
    }

    public Integer getCombRule() {
        return combRule;
    }

    public void setCombRule(Integer combRule) {
        this.combRule = combRule;
    }

    public boolean isGenPair() {
        return genPair;
    }

    public void setGenPair(boolean genPair) {
        this.genPair = genPair;
    }

    public BigDecimal getC1() {
        return fudgeLJ;
    }

    public void setC1(BigDecimal fudgeLJ) {
        this.fudgeLJ = fudgeLJ;
    }

    public BigDecimal getC2() {
        return fudgeQQ;
    }

    public void setC2(BigDecimal fudgeQQ) {
        this.fudgeQQ = fudgeQQ;
    }
}
