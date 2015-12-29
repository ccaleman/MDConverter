package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Settle {

    private Integer atom;
    private int funcType;
    private BigDecimal c1;
    private BigDecimal c2;

    public Settle() {
    }

    public Settle(Integer atom, int funcType, BigDecimal c1, BigDecimal c2) {
        this.atom = atom;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
    }

    public Integer getAtom() {
        return atom;
    }

    public void setAtom(Integer atom) {
        this.atom = atom;
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
}
