package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class DistanceRestraint {

    private String ai;
    private String aj;
    private int type;
    private Integer idx;
    private int typeTwo;
    private BigDecimal low;
    private BigDecimal up1;
    private BigDecimal up2;
    private BigDecimal fac;

    public DistanceRestraint() {
    }

    public DistanceRestraint(String ai, String aj, int type, Integer idx, int typeTwo, BigDecimal low, BigDecimal up1, BigDecimal up2, BigDecimal fac) {
        this.ai = ai;
        this.aj = aj;
        this.type = type;
        this.idx = idx;
        this.typeTwo = typeTwo;
        this.low = low;
        this.up1 = up1;
        this.up2 = up2;
        this.fac = fac;
    }

    public String getAj() {
        return aj;
    }

    public void setAj(String aj) {
        this.aj = aj;
    }

    public String getAi() {
        return ai;
    }

    public void setAi(String ai) {
        this.ai = ai;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public int getTypeTwo() {
        return typeTwo;
    }

    public void setTypeTwo(int typeTwo) {
        this.typeTwo = typeTwo;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getUp1() {
        return up1;
    }

    public void setUp1(BigDecimal up1) {
        this.up1 = up1;
    }

    public BigDecimal getUp2() {
        return up2;
    }

    public void setUp2(BigDecimal up2) {
        this.up2 = up2;
    }

    public BigDecimal getFac() {
        return fac;
    }

    public void setFac(BigDecimal fac) {
        this.fac = fac;
    }
}
