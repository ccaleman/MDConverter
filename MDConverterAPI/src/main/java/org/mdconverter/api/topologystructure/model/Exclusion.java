package org.mdconverter.api.topologystructure.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by miso on 04.12.2015.
 */
public class Exclusion {

    private Integer aIdx;
    private List<Integer> bonds = Lists.newArrayList();

    public Exclusion() {
    }

    public Exclusion(Integer aIdx, List<Integer> bonds) {
        this.aIdx = aIdx;
        this.bonds = bonds;
    }

    public Integer getaIdx() {
        return aIdx;
    }

    public void setaIdx(Integer aIdx) {
        this.aIdx = aIdx;
    }

    public List<Integer> getBonds() {
        return bonds;
    }

    public void setBonds(List<Integer> bonds) {
        this.bonds = bonds;
    }
}
