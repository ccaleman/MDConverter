package org.mdconverter.api.topologystructure.model;

import com.google.common.collect.Lists;
import org.biojava.nbio.structure.BondType;
import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.SectionType;
import org.mdconverter.api.topologystructure.model.api.*;
import org.mdconverter.api.topologystructure.model.impl.*;
import org.mdconverter.api.topologystructure.model.impl.System;

import java.util.List;

/**
 * Created by miso on 04.12.2015. <br>
 * Implementation of the Topology Meta Model
 */
public class TopologyStructure implements MetaModel {

    //Fields
    private ModelVersion modelVersion;
    private List<String> headerComments = Lists.newArrayList();
    private DefaultImpl def;
    private List<Bond> bondTypes = Lists.newArrayList();
    private List<Constraint> constraintTypes = Lists.newArrayList();
    private List<Angle> angleTypes = Lists.newArrayList();
    private List<AtomType> atomTypes = Lists.newArrayList();
    private List<NonBondParam> nonBondParams = Lists.newArrayList();
    private List<Pair> pairTypes = Lists.newArrayList();
    private List<Dihedral> dihedralTypes = Lists.newArrayList();
    private List<ImplicitGenbornParam> genbornParams = Lists.newArrayList();
    private System system = null;
    private Molecule molecule = null;
    private List<Section> sections = Lists.newArrayList();


    //Constructor

    /**
     * Generates a empty {@link TopologyStructure} <br>
     * initializes all lists empty (allows to call the get-Method and modify the returned List)
     *
     * @param modelVersion the version of the actual {@link TopologyStructure}
     * @since {@link ModelVersion#V1}
     */
    public TopologyStructure(ModelVersion modelVersion) {
        this.modelVersion = modelVersion;
    }


    //Getter & Setter

    /**
     * all comments which should be at the beginning of the file
     * @return a List of Strings
     * @since {@link ModelVersion#V1}
     */
    public List<String> getHeaderComments() {
        return headerComments;
    }

    /**
     * sets the given List
     * @param headerComments a List of Strings
     * @since {@link ModelVersion#V1}
     */
    public void setHeaderComments(List<String> headerComments) {
        this.headerComments = headerComments;
    }

    /**
     * defines which unit definitions of the manifest will be used during runtime <br>
     * <b>example:</b> <br>
     * if {@link DefaultImpl#combRule} equals 1 then the alternative units from manifest.json will be used <br>
     *      they are defined like c1_1: some unit <br>
     *      it's not possible to add c1_1 somewhere if its not defined by default (see manual of MDConverter)
     * @return a {@link DefaultImpl}
     * @since {@link ModelVersion#V1}
     */
    public DefaultImpl getDef() {
        return def;
    }

    /**
     * sets the {@link DefaultImpl}
     * @param def a definition for the {@link TopologyStructure}
     * @since {@link ModelVersion#V1}
     */
    public void setDef(DefaultImpl def) {
        this.def = def;
    }

    /**
     * defines the {@link BondType} for the topology
     * @return a List of {@link Bond}
     * @since {@link ModelVersion#V1}
     */
    public List<Bond> getBondTypes() {
        return bondTypes;
    }

    /**
     * sets the List of {@link BondType}
     * @param bondTypes a List of {@link Bond}
     * @since {@link ModelVersion#V1}
     */
    public void setBondTypes(List<Bond> bondTypes) {
        this.bondTypes = bondTypes;
    }

    /**
     * defines the {@link ConstraintImpl} for the topology
     * @return a List of {@link Constraint}
     * @since {@link ModelVersion#V1}
     */
    public List<Constraint> getConstraintTypes() {
        return constraintTypes;
    }

    /**
     * sets the List of {@link Constraint}
     * @param constraintTypes a List of {@link Constraint}
     * @since {@link ModelVersion#V1}
     */
    public void setConstraintTypes(List<Constraint> constraintTypes) {
        this.constraintTypes = constraintTypes;
    }

    /**
     * defines the {@link AngleImpl} for the topology
     * @return a List of {@link Angle}
     * @since {@link ModelVersion#V1}
     */
    public List<Angle> getAngleTypes() {
        return angleTypes;
    }

    /**
     * sets the List of {@link Angle}
     * @param angleTypes a List of {@link Angle}
     * @since {@link ModelVersion#V1}
     */
    public void setAngleTypes(List<Angle> angleTypes) {
        this.angleTypes = angleTypes;
    }

    /**
     * defines the {@link AtomTypeImpl} for the topology
     * @return a List of {@link AtomType}
     * @since {@link ModelVersion#V1}
     */
    public List<AtomType> getAtomTypes() {
        return atomTypes;
    }

    /**
     * sets a List of {@link AtomType}
     * @param atomTypes a List of {@link AtomType}
     * @since {@link ModelVersion#V1}
     */
    public void setAtomTypes(List<AtomType> atomTypes) {
        this.atomTypes = atomTypes;
    }

    /**
     * defines the {@link NonBondParamImpl} for the topology
     * @return a List of {@link NonBondParam}
     * @since {@link ModelVersion#V1}
     */
    public List<NonBondParam> getNonBondParams() {
        return nonBondParams;
    }

    /**
     * sets the List of {@link NonBondParam}
     * @param nonBondParams a List of {@link NonBondParam}
     * @since {@link ModelVersion#V1}
     */
    public void setNonBondParams(List<NonBondParam> nonBondParams) {
        this.nonBondParams = nonBondParams;
    }

    /**
     * defines the {@link PairImpl} for the topology
     * @return a List of {@link Pair}
     * @since {@link ModelVersion#V1}
     */
    public List<Pair> getPairTypes() {
        return pairTypes;
    }

    /**
     * sets the List of {@link Pair}
     * @param pairTypes a List of {@link Pair}
     * @since {@link ModelVersion#V1}
     */
    public void setPairTypes(List<Pair> pairTypes) {
        this.pairTypes = pairTypes;
    }

    /**
     * defines the {@link DihedralImpl} for the topology
     * @return a List of {@link Dihedral}
     * @since {@link ModelVersion#V1}
     */
    public List<Dihedral> getDihedralTypes() {
        return dihedralTypes;
    }

    /**
     * sets the List of {@link Dihedral}
     * @param dihedralTypes a List of {@link Dihedral}
     * @since {@link ModelVersion#V1}
     */
    public void setDihedralTypes(List<Dihedral> dihedralTypes) {
        this.dihedralTypes = dihedralTypes;
    }

    /**
     * defines the {@link ImplicitGenbornParamImpl} for the topology
     * @return a List of {@link ImplicitGenbornParam}
     * @since {@link ModelVersion#V1}
     */
    public List<ImplicitGenbornParam> getGenbornParams() {
        return genbornParams;
    }

    /**
     * sets the List of {@link ImplicitGenbornParam}
     * @param genbornParams a List of {@link ImplicitGenbornParam}
     * @since {@link ModelVersion#V1}
     */
    public void setGenbornParams(List<ImplicitGenbornParam> genbornParams) {
        this.genbornParams = genbornParams;
    }

    /**
     * defines the name of the topology
     * @return a {@link System}
     * @since {@link ModelVersion#V1}
     */
    public System getSystem() {
        return system;
    }

    /**
     * sets the {@link System}
     * @param system a {@link System}
     * @since {@link ModelVersion#V1}
     */
    public void setSystem(System system) {
        this.system = system;
    }

    /**
     * defines the {@link MoleculeImpl} for the topology
     * @return a {@link Molecule}
     * @since {@link ModelVersion#V1}
     */
    public Molecule getMolecule() {
        return molecule;
    }

    /**
     * sets the {@link Molecule}
     * @param molecule a {@link Molecule}
     * @since {@link ModelVersion#V1}
     */
    public void setMolecule(Molecule molecule) {
        this.molecule = molecule;
    }

    /**
     * defines the {@link Section} for the topology <br>
     * more then one are possible, plugins have to be aware of and check different {@link SectionType}
     * @return a List of {@link Section}
     * @since {@link ModelVersion#V1}
     */
    public List<Section> getSections() {
        return sections;
    }

    /**
     * sets the List of {@link Section}
     * @param sections a List of {@link Section}
     * @since {@link ModelVersion#V1}
     */
    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public ModelVersion getVersion() {
        return this.modelVersion;
    }
}
