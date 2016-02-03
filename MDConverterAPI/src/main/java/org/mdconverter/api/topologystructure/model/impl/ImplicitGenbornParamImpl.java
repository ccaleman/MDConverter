package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.ImplicitGenbornParam;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class ImplicitGenbornParamImpl extends ValueHolder implements ImplicitGenbornParam {

    private String atom;
    /**
     * added at {@link ModelVersion#V1}
     */
    private BigDecimal c1;
    /**
     * added at {@link ModelVersion#V1}
     */
    private BigDecimal c2;
    /**
     * added at {@link ModelVersion#V1}
     */
    private BigDecimal c3;
    /**
     * added at {@link ModelVersion#V1}
     */
    private BigDecimal c4;
    /**
     * added at {@link ModelVersion#V1}
     */
    private BigDecimal c5;

    public ImplicitGenbornParamImpl() {
    }

    public ImplicitGenbornParamImpl(String atom, BigDecimal c1, BigDecimal c2, BigDecimal c3, BigDecimal c4, BigDecimal c5) {
        this.atom = atom;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
    }

    public String getAtom() {
        return atom;
    }

    public void setAtom(String atom) {
        this.atom = atom;
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
