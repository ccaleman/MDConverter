package org.mdconverter.api.topologystructure.model.impl;

/**
 * Created by miso on 04.12.2015.
 */
public class Molecule {

    private String name;
    private Integer nrMol;

    public Molecule() {
    }

    public Molecule(String compound, Integer nrMol) {
        this.name = compound;
        this.nrMol = nrMol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNrMol() {
        return nrMol;
    }

    public void setNrMol(Integer nrMol) {
        this.nrMol = nrMol;
    }
}
