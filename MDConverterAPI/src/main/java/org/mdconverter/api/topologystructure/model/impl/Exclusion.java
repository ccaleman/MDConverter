package org.mdconverter.api.topologystructure.model.impl;

import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.ModelVersion;

import java.util.List;

/**
 * Created by miso on 04.12.2015.
 */
public class Exclusion {

    //Fields
    private Integer atomIdx;
    private List<Integer> bonds = Lists.newArrayList();

    //Constructors

    /**
     * default constructor
     *
     * @since {@link ModelVersion#V1}
     */
    public Exclusion() {
    }

    /**
     * constructor for exclusion list
     * @since {@link ModelVersion#V1}
     */
    public Exclusion(Integer atomIdx, List<Integer> bonds) {
        this.atomIdx = atomIdx;
        this.bonds = bonds;
    }

    //Getters & Setters

    /**
     * defines the atom index for the exclusion
     * @return an Integer
     * @since {@link ModelVersion#V1}
     */
    public Integer getAtomIdx() {
        return atomIdx;
    }

    /**
     * sets the atom index
     * @param atomIdx an Integer
     * @since {@link ModelVersion#V1}
     */
    public void setAtomIdx(Integer atomIdx) {
        this.atomIdx = atomIdx;
    }

    /**
     * defines the exclusions indexes for the defined atom index
     * @return a List of Integer
     * @since {@link ModelVersion#V1}
     */
    public List<Integer> getBonds() {
        return bonds;
    }

    /**
     * sets a List of atom indexes
     * @param bonds a List of Integer
     * @since {@link ModelVersion#V1}
     */
    public void setBonds(List<Integer> bonds) {
        this.bonds = bonds;
    }
}
