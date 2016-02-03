package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.ValueGS;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Dihedral extends ValueGS implements FuncType {

    private String ai;
    private String aj;
    private String ak;
    private String al;
    private Integer funcType;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;
    private BigDecimal c4;
    private BigDecimal c5;
    private BigDecimal c6;

    public Dihedral() {
    }

    public Dihedral(String ai, String aj, String ak, String al, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3,
                    BigDecimal c4, BigDecimal c5, BigDecimal c6) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
        this.c6 = c6;
    }

    public Dihedral(String ai, String aj, String ak, String al, Integer funcType, BigDecimal c1, BigDecimal c2) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
    }

    public Dihedral(String ai, String aj, String ak, String al, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    public Dihedral(String ai, String aj, String ak, String al, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3,
                    BigDecimal c4, BigDecimal c5) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
    }

    public Dihedral(String ai, String aj, String ak, String al, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3,
                    BigDecimal c4) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
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

    public BigDecimal getC5() {
        return c5;
    }

    public void setC5(BigDecimal c5) {
        this.c5 = c5;
    }

    public BigDecimal getC6() {
        return c6;
    }

    public void setC6(BigDecimal c6) {
        this.c6 = c6;
    }
}
