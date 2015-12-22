package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Bond {

    private String ai;
    private String aj;
    private Integer funcType;
    private BigDecimal r;
    private BigDecimal k;

    public Bond() {
    }

    public Bond(String ai, String aj, Integer funcType, BigDecimal r, BigDecimal k) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.r = r;
        this.k = k;
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

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public BigDecimal getK() {
        return k;
    }

    public void setK(BigDecimal k) {
        this.k = k;
    }
}
