package org.mdconverter.api.topologystructure.model;

import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.model.api.*;
import org.mdconverter.api.topologystructure.model.impl.DefaultImpl;
import org.mdconverter.api.topologystructure.model.impl.System;

import java.util.List;

/**
 * Created by miso on 04.12.2015.
 */
public class TopologyStructure implements MetaModel {

    /**
     * added at {@link ModelVersion#V1}
     */
    private ModelVersion modelVersion;
    /**
     * added at {@link ModelVersion#V1}
     */
    private DefaultImpl def;
    /**
     * added at {@link ModelVersion#V1}
     */
    private System system = null;
    /**
     * added at {@link ModelVersion#V1}
     */
    private Molecule molecule = null;
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<String> headerComments = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Bond> bondTypes = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Constraint> constraintTypes = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Angle> angleTypes = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<AtomType> atomTypes = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<NonBondParam> nonBondParams = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Dihedral> dihedralTypes = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Pair> pairTypes = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<ImplicitGenbornParam> genbornParams = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Section> sections = Lists.newArrayList();


    public TopologyStructure(ModelVersion modelVersion) {
        this.modelVersion = modelVersion;
    }

    public List<String> getHeaderComments() {
        return headerComments;
    }

    public void setHeaderComments(List<String> headerComments) {
        this.headerComments = headerComments;
    }

    public DefaultImpl getDef() {
        return def;
    }

    public void setDef(DefaultImpl def) {
        this.def = def;
    }

    public List<Bond> getBondTypes() {
        return bondTypes;
    }

    public void setBondTypes(List<Bond> bondTypes) {
        this.bondTypes = bondTypes;
    }

    public List<Constraint> getConstraintTypes() {
        return constraintTypes;
    }

    public void setConstraintTypes(List<Constraint> constraintTypes) {
        this.constraintTypes = constraintTypes;
    }

    public List<Angle> getAngleTypes() {
        return angleTypes;
    }

    public void setAngleTypes(List<Angle> angleTypes) {
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

    public List<Pair> getPairTypes() {
        return pairTypes;
    }

    public void setPairTypes(List<Pair> pairTypes) {
        this.pairTypes = pairTypes;
    }

    public List<Dihedral> getDihedralTypes() {
        return dihedralTypes;
    }

    public void setDihedralTypes(List<Dihedral> dihedralTypes) {
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

    @Override
    public ModelVersion getVersion() {
        return this.modelVersion;
    }
}
