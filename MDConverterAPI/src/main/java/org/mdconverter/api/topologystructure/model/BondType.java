package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class BondType {

    private String ai;
    private String aj;
    private int funcType;
    private BigDecimal b0;
    private BigDecimal kb;

    public BondType() {
    }

    public BondType(String ai, String aj, int funcType, BigDecimal b0, BigDecimal kb) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.b0 = b0;
        this.kb = kb;
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

    public int getFuncType() {
        return funcType;
    }

    public void setFuncType(int funcType) {
        this.funcType = funcType;
    }

    public BigDecimal getB0() {
        return b0;
    }

    public void setB0(BigDecimal b0) {
        this.b0 = b0;
    }

    public BigDecimal getKb() {
        return kb;
    }

    public void setKb(BigDecimal kb) {
        this.kb = kb;
    }
}
