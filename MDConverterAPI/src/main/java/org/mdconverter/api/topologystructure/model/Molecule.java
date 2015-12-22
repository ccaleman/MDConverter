package org.mdconverter.api.topologystructure.model;

/**
 * Created by miso on 04.12.2015.
 */
public class Molecule {

    private String compound;
    private Integer nrMol;

    public Molecule() {
    }

    public Molecule(String compound, Integer nrMol) {
        this.compound = compound;
        this.nrMol = nrMol;
    }

    public String getCompound() {
        return compound;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public Integer getNrMol() {
        return nrMol;
    }

    public void setNrMol(Integer nrMol) {
        this.nrMol = nrMol;
    }
}
