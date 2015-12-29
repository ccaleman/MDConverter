package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by miso on 04.12.2015.
 */
public class PositionRestraint {

    private Integer ai;
    private int func;
    private BigDecimal c1 = new BigDecimal(BigInteger.ZERO);
    private BigDecimal c2 = new BigDecimal(BigInteger.ZERO);
    private BigDecimal c3 = new BigDecimal(BigInteger.ZERO);

    public PositionRestraint() {
    }

    public PositionRestraint(Integer ai, int func, BigDecimal c1, BigDecimal c2, BigDecimal c3) {
        this.ai = ai;
        this.func = func;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    public Integer getAi() {
        return ai;
    }

    public void setAi(Integer ai) {
        this.ai = ai;
    }

    public int getFunc() {
        return func;
    }

    public void setFunc(int func) {
        this.func = func;
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
}
