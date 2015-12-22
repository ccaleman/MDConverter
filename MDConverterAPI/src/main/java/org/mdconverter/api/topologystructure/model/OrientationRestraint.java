package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class OrientationRestraint {

    private String ai;
    private String aj;
    private int type;
    private Integer exp;
    private Integer label;
    private Integer alpha;
    private BigDecimal constant;
    private BigDecimal observeable;
    private BigDecimal weight;

    public OrientationRestraint() {
    }

    public OrientationRestraint(String ai, String aj, int type, Integer exp, Integer label, Integer alpha, BigDecimal constant, BigDecimal observeable, BigDecimal weight) {
        this.ai = ai;
        this.aj = aj;
        this.type = type;
        this.exp = exp;
        this.label = label;
        this.alpha = alpha;
        this.constant = constant;
        this.observeable = observeable;
        this.weight = weight;
    }

    public String getAi() {
        return ai;
    }

    public void setAi(String ai) {
        this.ai = ai;
    }

    public String getAj() {
        return aj;
    }

    public void setAj(String aj) {
        this.aj = aj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) {
        this.constant = constant;
    }

    public BigDecimal getObserveable() {
        return observeable;
    }

    public void setObserveable(BigDecimal observeable) {
        this.observeable = observeable;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
