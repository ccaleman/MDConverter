package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.model.api.Molecule;

/**
 * Created by miso on 04.12.2015.
 */
public class MoleculeImpl implements Molecule {

    private String name;
    private Integer nrMol;

    public MoleculeImpl() {
    }

    public MoleculeImpl(String compound, Integer nrMol) {
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
