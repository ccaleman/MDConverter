package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.Atom;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import java.math.BigDecimal;

/**
 * Created by miso on 04.12.2015.
 */
public class AtomImpl extends ValueHolder implements Atom {

    //Fields
    private Integer nr;
    private String type;
    private Integer resNr;
    private String resName;
    private String atomName;
    private Integer chargeGroupNr;
    private String typeB;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;
    private BigDecimal c4;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public AtomImpl() {
    }

    /**
     * constructor for four convertible values
     * @since {@link ModelVersion#V1}
     */
    public AtomImpl(Integer nr, String type, Integer resNr, String resName, String atomName, Integer chargeGroupNr, BigDecimal c1, BigDecimal c2, String typeB, BigDecimal c3, BigDecimal c4) {
        this.nr = nr;
        this.type = type;
        this.resNr = resNr;
        this.resName = resName;
        this.atomName = atomName;
        this.chargeGroupNr = chargeGroupNr;
        this.c1 = c1;
        this.c2 = c2;
        this.typeB = typeB;
        this.c3 = c3;
        this.c4 = c4;
    }

    /**
     * constructor for two convertible values
     * @since {@link ModelVersion#V1}
     */
    public AtomImpl(Integer nr, String type, Integer resNr, String resName, String atomName, Integer chargeGroupNr, BigDecimal c1, BigDecimal c2) {
        this.nr = nr;
        this.type = type;
        this.resNr = resNr;
        this.resName = resName;
        this.atomName = atomName;
        this.chargeGroupNr = chargeGroupNr;
        this.c1 = c1;
        this.c2 = c2;
    }


    //Getters & Setters

    /**
     * defines the number of the atom in the model
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    public Integer getNr() {
        return nr;
    }

    /**
     * sets the number of the atom in the model
     * @param nr an Integer
     * @since {@link ModelVersion#V1}
     */
    public void setNr(Integer nr) {
        this.nr = nr;
    }

    /**
     * defines the type of the atom in the model
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public String getType() {
        return type;
    }

    /**
     * sets the type of the atom in the molecule
     * @param type a String
     * @since {@link ModelVersion#V1}
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * defines the residue number of the atom in the molecule
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    public Integer getResNr() {
        return resNr;
    }

    /**
     * sets the residue number of the atom in the molecule
     * @param resNr an Integer
     * @since {@link ModelVersion#V1}
     */
    public void setResNr(Integer resNr) {
        this.resNr = resNr;
    }

    /**
     * defines the residue name for the atom of the molecule
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public String getResName() {
        return resName;
    }

    /**
     * sets the residue name for the atom of the molecule
     * @param resName a String
     * @since {@link ModelVersion#V1}
     */
    public void setResName(String resName) {
        this.resName = resName;
    }

    /**
     * defines the atomname of the atom in the model
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public String getAtomName() {
        return atomName;
    }

    /**
     * sets the atomname of the atom in the model
     * @param atomName a String
     * @since {@link ModelVersion#V1}
     */
    public void setAtomName(String atomName) {
        this.atomName = atomName;
    }

    /**
     * defines the chargegroup of the atom in the model
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    public Integer getChargeGroupNr() {
        return chargeGroupNr;
    }

    /**
     * sets the chargegroup of the atom in the model
     * @param chargeGroupNr an Integer
     * @since {@link ModelVersion#V1}
     */
    public void setChargeGroupNr(Integer chargeGroupNr) {
        this.chargeGroupNr = chargeGroupNr;
    }

    /**
     * defines the value for convertible value c1
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC1() {
        return c1;
    }

    /**
     * sets the value for convertible value c1
     * @param c1 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC1(BigDecimal c1) {
        this.c1 = c1;
    }

    /**
     * defines the value for convertible value c2
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC2() {
        return c2;
    }

    /**
     * sets the value for convertible value c2
     * @param c2 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC2(BigDecimal c2) {
        this.c2 = c2;
    }

    /**
     * defines the second type of the atom in the molecule
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public String getTypeB() {
        return typeB;
    }

    /**
     * sets the second type of the atom in the molecule
     * @param typeB a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setTypeB(String typeB) {
        this.typeB = typeB;
    }

    /**
     * defines the value for convertible value c3
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC3() {
        return c3;
    }

    /**
     * sets the value for convertible value c3
     * @param c3 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC3(BigDecimal c3) {
        this.c3 = c3;
    }

    /**
     * defines the value for convertible value c4
     * @return a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public BigDecimal getC4() {
        return c4;
    }

    /**
     * sets the value for convertible value c4
     * @param c4 a BigDecimal
     * @since {@link ModelVersion#V1}
     */
    public void setC4(BigDecimal c4) {
        this.c4 = c4;
    }
}
