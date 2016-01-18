package org.mdconverter.api.topologystructure;

import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.*;
import org.mdconverter.api.topologystructure.model.System;

import java.util.List;

/**
 * Created by miso on 04.12.2015.
 */
public class TopologyStructure {

    private Default def;
    private List<String> headerComments = Lists.newArrayList();
    private List<BondType> bondTypes = Lists.newArrayList();
    private List<ConstraintType> constraintTypes = Lists.newArrayList();
    private List<AngleType> angleTypes = Lists.newArrayList();
    private List<AtomType> atomTypes = Lists.newArrayList();
    private List<NonBondParam> nonBondParams = Lists.newArrayList();
    private List<DihedralType> dihedralTypes = Lists.newArrayList();
    private List<PairType> pairTypes = Lists.newArrayList();
    private List<ImplicitGenbornParam> genbornParams = Lists.newArrayList();
    private System system = null;
    private Molecule molecule = null;
    private List<Section> sections = Lists.newArrayList();

    public TopologyStructure() {
    }

    public List<String> getHeaderComments() {
        return headerComments;
    }

    public void setHeaderComments(List<String> headerComments) {
        this.headerComments = headerComments;
    }

    public Default getDef() {
        return def;
    }

    public void setDef(Default def) {
        this.def = def;
    }

    public List<BondType> getBondTypes() {
        return bondTypes;
    }

    public void setBondTypes(List<BondType> bondTypes) {
        this.bondTypes = bondTypes;
    }

    public List<ConstraintType> getConstraintTypes() {
        return constraintTypes;
    }

    public void setConstraintTypes(List<ConstraintType> constraintTypes) {
        this.constraintTypes = constraintTypes;
    }

    public List<AngleType> getAngleTypes() {
        return angleTypes;
    }

    public void setAngleTypes(List<AngleType> angleTypes) {
        this.angleTypes = angleTypes;
    }

    public List<AtomType> getAtomTypes() {
        return atomTypes;
    }

    public void setAtomTypes(List<AtomType> atomTypes) {
        this.atomTypes = atomTypes;
    }

    public List<NonBondParam> getNonBondParams() {
        return nonBondParams;
    }

    public void setNonBondParams(List<NonBondParam> nonBondParams) {
        this.nonBondParams = nonBondParams;
    }

    public List<PairType> getPairTypes() {
        return pairTypes;
    }

    public void setPairTypes(List<PairType> pairTypes) {
        this.pairTypes = pairTypes;
    }

    public List<DihedralType> getDihedralTypes() {
        return dihedralTypes;
    }

    public void setDihedralTypes(List<DihedralType> dihedralTypes) {
        this.dihedralTypes = dihedralTypes;
    }

    public List<ImplicitGenbornParam> getGenbornParams() {
        return genbornParams;
    }

    public void setGenbornParams(List<ImplicitGenbornParam> genbornParams) {
        this.genbornParams = genbornParams;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public Molecule getMolecule() {
        return molecule;
    }

    public void setMolecule(Molecule molecule) {
        this.molecule = molecule;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }


}
