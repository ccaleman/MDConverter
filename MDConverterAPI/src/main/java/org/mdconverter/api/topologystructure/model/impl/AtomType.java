package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.model.api.ValueGS;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class AtomType extends ValueGS {

    private String name;
    private String num;
    private BigDecimal c1;
    private BigDecimal c2;
    private String particleType;
    private BigDecimal c3;
    private BigDecimal c4;

    public AtomType() {
    }

    public AtomType(String name, String num, BigDecimal c1, BigDecimal c2, String particleType, BigDecimal c3, BigDecimal c4) {
        this.name = name;
        this.num = num;
        this.c1 = c1;
        this.c2 = c2;
        this.particleType = particleType;
        this.c3 = c3;
        this.c4 = c4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getParticleType() {
        return particleType;
    }

    public void setParticleType(String particleType) {
        this.particleType = particleType;
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
