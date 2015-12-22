package org.mdconverter.api.topologystructure.model;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class AtomType {

    private String name;
    private Integer num;
    private String bondType;
    private BigDecimal mass;
    private BigDecimal charge;
    private String particleType;
    private BigDecimal sigma;
    private BigDecimal epsilon;

    public AtomType() {
    }

    public AtomType(String name, Integer num, String bondType, BigDecimal mass, BigDecimal charge, String particleType, BigDecimal sigma, BigDecimal epsilon) {
        this.name = name;
        this.num = num;
        this.bondType = bondType;
        this.mass = mass;
        this.charge = charge;
        this.particleType = particleType;
        this.sigma = sigma;
        this.epsilon = epsilon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBondType() {
        return bondType;
    }

    public void setBondType(String bondType) {
        this.bondType = bondType;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public String getParticleType() {
        return particleType;
    }

    public void setParticleType(String particleType) {
        this.particleType = particleType;
    }

    public BigDecimal getSigma() {
        return sigma;
    }

    public void setSigma(BigDecimal sigma) {
        this.sigma = sigma;
    }

    public BigDecimal getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(BigDecimal epsilon) {
        this.epsilon = epsilon;
    }
}
