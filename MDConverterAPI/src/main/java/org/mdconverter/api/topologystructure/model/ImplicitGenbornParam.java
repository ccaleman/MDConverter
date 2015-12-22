package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class ImplicitGenbornParam {

    private String atom;
    private BigDecimal sar;
    private BigDecimal st;
    private BigDecimal pi;
    private BigDecimal gbr;
    private BigDecimal hct;

    public ImplicitGenbornParam() {
    }

    public ImplicitGenbornParam(String atom, BigDecimal sar, BigDecimal st, BigDecimal pi, BigDecimal gbr, BigDecimal hct) {
        this.atom = atom;
        this.sar = sar;
        this.st = st;
        this.pi = pi;
        this.gbr = gbr;
        this.hct = hct;
    }

    public String getAtom() {
        return atom;
    }

    public void setAtom(String atom) {
        this.atom = atom;
    }

    public BigDecimal getSar() {
        return sar;
    }

    public void setSar(BigDecimal sar) {
        this.sar = sar;
    }

    public BigDecimal getSt() {
        return st;
    }

    public void setSt(BigDecimal st) {
        this.st = st;
    }

    public BigDecimal getPi() {
        return pi;
    }

    public void setPi(BigDecimal pi) {
        this.pi = pi;
    }

    public BigDecimal getGbr() {
        return gbr;
    }

    public void setGbr(BigDecimal gbr) {
        this.gbr = gbr;
    }

    public BigDecimal getHct() {
        return hct;
    }

    public void setHct(BigDecimal hct) {
        this.hct = hct;
    }
}
