package org.mdconverter.api.topologystructure.model.impl;

/**
 * Created by miso on 04.12.2015.
 */
public class MoleculeType {

    private String name;
    private int nrExcl;

    public MoleculeType() {
    }

    public MoleculeType(String name, int nrExcl) {
        this.name = name;
        this.nrExcl = nrExcl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNrExcl() {
        return nrExcl;
    }

    public void setNrExcl(int nrExcl) {
        this.nrExcl = nrExcl;
    }
}
