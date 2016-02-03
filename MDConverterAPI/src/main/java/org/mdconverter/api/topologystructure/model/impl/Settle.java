package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.ValueGS;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Settle extends ValueGS implements FuncType {

    private String atom;
    private Integer funcType;
    private BigDecimal c1;
    private BigDecimal c2;

    public Settle() {
    }

    public Settle(String atom, Integer funcType, BigDecimal c1, BigDecimal c2) {
        this.atom = atom;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
    }

    public String getAtom() {
        return atom;
    }

    public void setAtom(String atom) {
        this.atom = atom;
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
}
