package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Default {

    private Integer nboudnFT;
    private Integer combRule;
    private boolean genPair;
    private BigDecimal fudgeLJ;
    private BigDecimal fudgeQQ;

    public Default() {
    }

    public Default(Integer nboudnFT, Integer combRule, boolean genPair, BigDecimal fudgeLJ, BigDecimal fudgeQQ) {
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

    public BigDecimal getFudgeLJ() {
        return fudgeLJ;
    }

    public void setFudgeLJ(BigDecimal fudgeLJ) {
        this.fudgeLJ = fudgeLJ;
    }

    public BigDecimal getFudgeQQ() {
        return fudgeQQ;
    }

    public void setFudgeQQ(BigDecimal fudgeQQ) {
        this.fudgeQQ = fudgeQQ;
    }
}
