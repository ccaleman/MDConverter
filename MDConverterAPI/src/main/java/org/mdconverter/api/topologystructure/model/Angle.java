package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Angle {

    private String ai;
    private String aj;
    private String ak;
    private int funcType;
    private BigDecimal th0;
    private BigDecimal cth;

    public Angle() {
    }

    public Angle(String ai, String aj, String ak, int funcType, BigDecimal th0, BigDecimal cth) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.funcType = funcType;
        this.th0 = th0;
        this.cth = cth;
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

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public int getFuncType() {
        return funcType;
    }

    public void setFuncType(int funcType) {
        this.funcType = funcType;
    }

    public BigDecimal getTh0() {
        return th0;
    }

    public void setTh0(BigDecimal th0) {
        this.th0 = th0;
    }

    public BigDecimal getCth() {
        return cth;
    }

    public void setCth(BigDecimal cth) {
        this.cth = cth;
    }
}
