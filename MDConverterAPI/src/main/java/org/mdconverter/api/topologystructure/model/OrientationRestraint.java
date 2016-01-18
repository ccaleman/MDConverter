package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class OrientationRestraint extends ValueGS implements FuncType {

    private String ai;
    private String aj;
    private Integer funcType;
    private String exp;
    private String label;
    private String alpha;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;

    public OrientationRestraint() {
    }

    public OrientationRestraint(String ai, String aj, Integer funcType, String exp, String label, String alpha,
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

    public Integer getFuncType() {
        return funcType;
    }

    public void setFuncType(Integer funcType) {
        this.funcType = funcType;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public BigDecimal getC1() {
        return c1;
    }

    public void setC1(BigDecimal c1) {
        this.c1 = c1;
    }

    public BigDecimal getC2() {
        return c2;
    }

    public void setC2(BigDecimal c2) {
        this.c2 = c2;
    }

    public BigDecimal getC3() {
        return c3;
    }

    public void setC3(BigDecimal c3) {
        this.c3 = c3;
    }
}
