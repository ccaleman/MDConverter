package org.mdconverter.api.topologystructure.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by miso on 04.12.2015.
 */
public class Exclusion {

    private Integer atomIdx;
    private List<Integer> bonds = Lists.newArrayList();

    public Exclusion() {
    }

    public Exclusion(Integer atomIdx, List<Integer> bonds) {
        this.atomIdx = atomIdx;
        this.bonds = bonds;
    }

    public Integer getAtomIdx() {
        return atomIdx;
    }

    public void setAtomIdx(Integer atomIdx) {
        this.atomIdx = atomIdx;
    }

    public List<Integer> getBonds() {
        return bonds;
    }

    public void setBonds(List<Integer> bonds) {
        this.bonds = bonds;
    }
}
