package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class DihedralType {

    private String ai;
    private String aj;
    private String ak;
    private String al;
    private int funcType;
    private BigDecimal phase;
    private BigDecimal kd;
    private BigDecimal pn;

    public DihedralType() {
    }

    public DihedralType(String ai, String aj, String ak, String al, int funcType, BigDecimal phase, BigDecimal kd, BigDecimal pn) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.phase = phase;
        this.kd = kd;
        this.pn = pn;
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

    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public int getFuncType() {
        return funcType;
    }

    public void setFuncType(int funcType) {
        this.funcType = funcType;
    }

    public BigDecimal getPhase() {
        return phase;
    }

    public void setPhase(BigDecimal phase) {
        this.phase = phase;
    }

    public BigDecimal getKd() {
        return kd;
    }

    public void setKd(BigDecimal kd) {
        this.kd = kd;
    }

    public BigDecimal getPn() {
        return pn;
    }

    public void setPn(BigDecimal pn) {
        this.pn = pn;
    }
}
