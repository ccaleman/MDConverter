package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Settle {

    private Integer atom;
    private int funcType;
    private BigDecimal doh;
    private BigDecimal dhh;

    public Settle() {
    }

    public Settle(Integer atom, int funcType, BigDecimal doh, BigDecimal dhh) {
        this.atom = atom;
        this.funcType = funcType;
        this.doh = doh;
        this.dhh = dhh;
    }

    public Integer getAtom() {
        return atom;
    }

    public void setAtom(Integer atom) {
        this.atom = atom;
    }

    public int getFuncType() {
        return funcType;
    }

    public void setFuncType(int funcType) {
        this.funcType = funcType;
    }

    public BigDecimal getDoh() {
        return doh;
    }

    public void setDoh(BigDecimal doh) {
        this.doh = doh;
    }

    public BigDecimal getDhh() {
        return dhh;
    }

    public void setDhh(BigDecimal dhh) {
        this.dhh = dhh;
    }
}
