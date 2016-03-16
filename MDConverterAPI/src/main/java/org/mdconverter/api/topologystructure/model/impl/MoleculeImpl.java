package org.mdconverter.api.topologystructure.model.impl;

import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.Molecule;

/**
 * Created by miso on 04.12.2015.
 */
public class MoleculeImpl implements Molecule {

    //Fields
    private String name;
    private Integer nrMol;

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public MoleculeImpl() {
    }

    /**
     * constructor for molecule
     * @since {@link ModelVersion#V1}
     */
    public MoleculeImpl(String compound, Integer nrMol) {
        this.name = compound;
        this.nrMol = nrMol;
    }

    //Getters & Setters

    /**
     * defines the name of the structure
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the structure
     * @param name a String
     * @since {@link ModelVersion#V1}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * defines the number of molecules
     * @return a String
     * @since {@link ModelVersion#V1}
     */
    public Integer getNrMol() {
        return nrMol;
    }

    /**
     * sets the number of molecules
     * @param nrMol a String
     * @since {@link ModelVersion#V1}
     */
    public void setNrMol(Integer nrMol) {
        this.nrMol = nrMol;
    }
}