package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Constraint {

    private String ai;
    private String aj;
    private int funcType;
    private BigDecimal lengthA;
    private BigDecimal lengthB;

    public Constraint() {
    }

    public Constraint(String ai, String aj, int funcType, BigDecimal lengthA, BigDecimal lengthB) {
        this.ai = ai;
        this.aj = aj;
        this.funcType = funcType;
        this.lengthA = lengthA;
        this.lengthB = lengthB;
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

    public BigDecimal getLengthA() {
        return lengthA;
    }

    public void setLengthA(BigDecimal lengthA) {
        this.lengthA = lengthA;
    }

    public BigDecimal getLengthB() {
        return lengthB;
    }

    public void setLengthB(BigDecimal lengthB) {
        this.lengthB = lengthB;
    }
}
