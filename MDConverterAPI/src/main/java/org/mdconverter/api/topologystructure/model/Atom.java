package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class Atom {

    private Integer nr;
    private String type;
    private Integer resNr;
    private String resName;
    private String atomName;
    private Integer chargeGroupNr;
    private BigDecimal charge;
    private BigDecimal mass;
    private String typeB;
    private BigDecimal chargeB;
    private BigDecimal massB;

    public Atom() {
    }

    public Atom(Integer nr, String type, Integer resNr, String resName, String atomName, Integer chargeGroupNr, BigDecimal charge, BigDecimal mass, String typeB, BigDecimal chargeB, BigDecimal massB) {
        this.nr = nr;
        this.type = type;
        this.resNr = resNr;
        this.resName = resName;
        this.atomName = atomName;
        this.chargeGroupNr = chargeGroupNr;
        this.charge = charge;
        this.mass = mass;
        this.typeB = typeB;
        this.chargeB = chargeB;
        this.massB = massB;
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

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }

    public String getTypeB() {
        return typeB;
    }

    public void setTypeB(String typeB) {
        this.typeB = typeB;
    }

    public BigDecimal getChargeB() {
        return chargeB;
    }

    public void setChargeB(BigDecimal chargeB) {
        this.chargeB = chargeB;
    }

    public BigDecimal getMassB() {
        return massB;
    }

    public void setMassB(BigDecimal massB) {
        this.massB = massB;
    }
}
