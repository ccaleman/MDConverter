package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class NonBondParam {

    private String ai;
    private String aj;
    private int funcType;
    private BigDecimal V;
    private BigDecimal W;

    public NonBondParam() {
    }

    public NonBondParam(String ai, String aj, int funcType, BigDecimal v, BigDecimal w) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        V = v;
        W = w;
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

    public int getFuncType() {
        return funcType;
    }

    public void setFuncType(int funcType) {
        this.funcType = funcType;
    }

    public BigDecimal getV() {
        return V;
    }

    public void setV(BigDecimal v) {
        V = v;
    }

    public BigDecimal getW() {
        return W;
    }

    public void setW(BigDecimal w) {
        W = w;
    }
}
