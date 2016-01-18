package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Atom extends ValueGS {

    private Integer nr;
    private String type;
    private Integer resNr;
    private String resName;
    private String atomName;
    private Integer chargeGroupNr;
    private BigDecimal c1;
    private BigDecimal c2;
    private String typeB;
    private BigDecimal c3;
    private BigDecimal c4;

    public Atom() {
    }

    public Atom(Integer nr, String type, Integer resNr, String resName, String atomName, Integer chargeGroupNr, BigDecimal c1, BigDecimal c2, String typeB, BigDecimal c3, BigDecimal c4) {
        this.nr = nr;
        this.type = type;
        this.resNr = resNr;
        this.resName = resName;
        this.atomName = atomName;
        this.chargeGroupNr = chargeGroupNr;
        this.c1 = c1;
        this.c2 = c2;
        this.typeB = typeB;
        this.c3 = c3;
        this.c4 = c4;
    }

    public Atom(Integer nr, String type, Integer resNr, String resName, String atomName, Integer chargeGroupNr, BigDecimal c1, BigDecimal c2) {
        this.nr = nr;
        this.type = type;
        this.resNr = resNr;
        this.resName = resName;
        this.atomName = atomName;
        this.chargeGroupNr = chargeGroupNr;
        this.c1 = c1;
        this.c2 = c2;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getResNr() {
        return resNr;
    }

    public void setResNr(Integer resNr) {
        this.resNr = resNr;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getAtomName() {
        return atomName;
    }

    public void setAtomName(String atomName) {
        this.atomName = atomName;
    }

    public Integer getChargeGroupNr() {
        return chargeGroupNr;
    }

    public void setChargeGroupNr(Integer chargeGroupNr) {
        this.chargeGroupNr = chargeGroupNr;
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

    public String getTypeB() {
        return typeB;
    }

    public void setTypeB(String typeB) {
        this.typeB = typeB;
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
