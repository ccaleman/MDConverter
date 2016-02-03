package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.ValueGS;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class BondType extends ValueGS implements FuncType {

    private String ai;
    private String aj;
    private Integer funcType;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;
    private BigDecimal c4;

    public BondType() {
    }

    public BondType(String ai, String aj, Integer funcType, BigDecimal c1, BigDecimal c2) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
    }

    public BondType(String ai, String aj, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    public BondType(String ai, String aj, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3, BigDecimal c4) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
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
}
