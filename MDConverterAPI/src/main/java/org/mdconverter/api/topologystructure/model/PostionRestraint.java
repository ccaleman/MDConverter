package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by miso on 04.12.2015.
 */
public class PostionRestraint {

    private Integer ai;
    private int func;
    private BigDecimal fx = new BigDecimal(BigInteger.ZERO);
    private BigDecimal fy = new BigDecimal(BigInteger.ZERO);
    private BigDecimal fz = new BigDecimal(BigInteger.ZERO);

    public PostionRestraint() {
    }

    public PostionRestraint(Integer ai, int func, BigDecimal fx, BigDecimal fy, BigDecimal fz) {
        this.ai = ai;
        this.func = func;
        this.fx = fx;
        this.fy = fy;
        this.fz = fz;
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

    public BigDecimal getFx() {
        return fx;
    }

    public void setFx(BigDecimal fx) {
        this.fx = fx;
    }

    public BigDecimal getFy() {
        return fy;
    }

    public void setFy(BigDecimal fy) {
        this.fy = fy;
    }

    public BigDecimal getFz() {
        return fz;
    }

    public void setFz(BigDecimal fz) {
        this.fz = fz;
    }
}
