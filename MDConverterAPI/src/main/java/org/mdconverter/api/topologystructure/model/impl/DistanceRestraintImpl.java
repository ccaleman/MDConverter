package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.DistanceRestraint;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class DistanceRestraintImpl extends ValueHolder implements DistanceRestraint {

    private String ai;
    private String aj;
    private Integer funcType;
    private String type;
    private String label;
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

    public DistanceRestraintImpl() {
    }

    public DistanceRestraintImpl(String ai, String aj, String type, String label, Integer funcType, BigDecimal c1, BigDecimal c2, BigDecimal c3, BigDecimal c4) {
        this.ai = ai;
        this.aj = aj;
        this.type = type;
        this.label = label;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
    }

    public String getAj() {
        return aj;
    }

    public void setAj(String aj) {
        this.aj = aj;
    }

    public String getAi() {
        return ai;
    }

    public void setAi(String ai) {
        this.ai = ai;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
