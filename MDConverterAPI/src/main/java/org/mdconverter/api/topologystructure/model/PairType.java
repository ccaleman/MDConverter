package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class PairType {

    private String ai;
    private String aj;
    private int funcType;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;
    private BigDecimal c4;
    private BigDecimal c5;

    public PairType() {

    }

    public PairType(String ai, String aj, int funcType, BigDecimal c1, BigDecimal c2) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
    }

    public PairType(String ai, String aj, int funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3, BigDecimal c4) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
    }

    public PairType(String ai, String aj, int funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3, BigDecimal c4, BigDecimal c5) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
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

    public BigDecimal getC4() {
        return c4;
    }

    public void setC4(BigDecimal c4) {
        this.c4 = c4;
    }

    public BigDecimal getC5() {
        return c5;
    }

    public void setC5(BigDecimal c5) {
        this.c5 = c5;
    }
}
