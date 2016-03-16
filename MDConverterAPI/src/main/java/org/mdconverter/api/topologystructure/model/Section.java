package org.mdconverter.api.topologystructure.model;

import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.SectionType;
import org.mdconverter.api.topologystructure.model.api.*;
import org.mdconverter.api.topologystructure.model.impl.*;

import java.util.List;

/**
 * Created by miso on 04.12.2015. <br>
 * Implementation of a Section, which is a sub-part of the {@link TopologyStructure}
 */
public class Section {

    //Fields
    private Molecule moleculeType;
    private List<Atom> atoms = Lists.newArrayList();
    private List<Bond> bonds = Lists.newArrayList();
    private List<Pair> pairs = Lists.newArrayList();
    private List<PairNB> pairsNB = Lists.newArrayList();
    private List<Angle> angles = Lists.newArrayList();
    private List<Dihedral> dihedrals = Lists.newArrayList();
    private List<Constraint> constraints = Lists.newArrayList();
    private List<PositionRestraint> positionRestraints = Lists.newArrayList();
    private List<AngleRestraintZImpl> angleRestraints = Lists.newArrayList();
    private List<DihedralRestraint> dihedralRestraints = Lists.newArrayList();
    private List<DistanceRestraint> distanceRestraints = Lists.newArrayList();
    private List<OrientationRestraint> orientationRestraints = Lists.newArrayList();
    private List<Settle> settles = Lists.newArrayList();
    private List<Exclusion> exclusions = Lists.newArrayList();
    private SectionType type;

    //Constructor

    /**
     * Generates a empty {@link Section} <br>
     * initializes all lists empty (allows to call the get-Method and modify the returned List)
     *
     * @param sectionType the type of the actual {@link Section}
     * @since {@link ModelVersion#V1}
     */
    public Section(SectionType sectionType) {
        type = sectionType;
    }

    //Getter & Setter

    /**
     * defines the {@link MoleculeImpl} of the Section
     * @return a {@link Molecule}
     * @since {@link ModelVersion#V1}
     */
    public Molecule getMoleculeType() {
        return moleculeType;
    }

    /**
     * sets the given {@link Molecule}
     * @param moleculeType a {@link Molecule}
     * @since {@link ModelVersion#V1}
     */
    public void setMoleculeType(Molecule moleculeType) {
        this.moleculeType = moleculeType;
    }

    /**
     * defines the {@link AtomImpl} of the Section
     * @return a List of {@link Atom}
     * @since {@link ModelVersion#V1}
     */
    public List<Atom> getAtoms() {
        return atoms;
    }

    /**
     * sets the given List
     * @param atoms a List of {@link Atom}
     * @since {@link ModelVersion#V1}
     */
    public void setAtoms(List<Atom> atoms) {
        this.atoms = atoms;
    }

    /**
     * defines the {@link BondImpl} of the Section
     * @return a List of {@link Bond}
     * @since {@link ModelVersion#V1}
     */
    public List<Bond> getBonds() {
        return bonds;
    }

    /**
     * sets the given List
     * @param bonds a List of {@link Bond}
     * @since {@link ModelVersion#V1}
     */
    public void setBonds(List<Bond> bonds) {
        this.bonds = bonds;
    }

    /**
     * defines the {@link PairImpl} of the Section
     * @return a List of {@link Pair}
     * @since {@link ModelVersion#V1}
     */
    public List<Pair> getPairs() {
        return pairs;
    }

    /**
     * sets the given List
     * @param pairs a List of {@link Pair}
     * @since {@link ModelVersion#V1}
     */
    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    /**
     * defines the {@link PairNB} of the Section
     * @return a List of {@link PairNB}
     * @since {@link ModelVersion#V1}
     */
    public List<PairNB> getPairsNB() {
        return pairsNB;
    }

    /**
     * sets the given List
     * @param pairsNB a List of {@link PairNB}
     * @since {@link ModelVersion#V1}
     */
    public void setPairsNB(List<PairNB> pairsNB) {
        this.pairsNB = pairsNB;
    }

    /**
     * defines the {@link AngleImpl} of the Section
     * @return a List of {@link Angle}
     * @since {@link ModelVersion#V1}
     */
    public List<Angle> getAngles() {
        return angles;
    }

    /**
     * sets the given List
     * @param angles a List of {@link Angle}
     * @since {@link ModelVersion#V1}
     */
    public void setAngles(List<Angle> angles) {
        this.angles = angles;
    }

    /**
     * defines the {@link DihedralImpl} of the Section
     * @return a List of {@link Dihedral}
     * @since {@link ModelVersion#V1}
     */
    public List<Dihedral> getDihedrals() {
        return dihedrals;
    }

    /**
     * sets the given List
     * @param dihedrals a List of {@link Dihedral}
     * @since {@link ModelVersion#V1}
     */
    public void setDihedrals(List<Dihedral> dihedrals) {
        this.dihedrals = dihedrals;
    }

    /**
     * defines the {@link ConstraintImpl} of the Section
     * @return a List of {@link Constraint}
     * @since {@link ModelVersion#V1}
     */
    public List<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * sets the given List
     * @param constraints a List of {@link Constraint}
     * @since {@link ModelVersion#V1}
     */
    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    /**
     * defines the {@link PositionRestraintImpl} of the Section
     * @return a List of {@link PositionRestraint}
     * @since {@link ModelVersion#V1}
     */
    public List<PositionRestraint> getPositionRestraints() {
        return positionRestraints;
    }

    /**
     * sets the given List
     * @param positionRestraints a List of {@link PositionRestraint}
     * @since {@link ModelVersion#V1}
     */
    public void setPositionRestraints(List<PositionRestraint> positionRestraints) {
        this.positionRestraints = positionRestraints;
    }

    /**
     * defines the {@link AngleRestraintZImpl} or {@link AngleRestraintImpl} of the Section
     * @return a List of {@link AngleRestraintZImpl}
     * @since {@link ModelVersion#V1}
     */
    public List<AngleRestraintZImpl> getAngleRestraints() {
        return angleRestraints;
    }

    /**
     * sets the given List
     * @param angleRestraints a List of {@link AngleRestraintZImpl} or {@link AngleRestraintImpl}
     * @since {@link ModelVersion#V1}
     */
    public void setAngleRestraints(List<AngleRestraintZImpl> angleRestraints) {
        this.angleRestraints = angleRestraints;
    }

    /**
     * defines the {@link DihedralRestraintImpl} of the Section
     * @return a List of {@link DihedralRestraint}
     * @since {@link ModelVersion#V1}
     */
    public List<DihedralRestraint> getDihedralRestraints() {
        return dihedralRestraints;
    }

    /**
     * sets the given List
     * @param dihedralRestraints a List of {@link DihedralRestraint}
     * @since {@link ModelVersion#V1}
     */
    public void setDihedralRestraints(List<DihedralRestraint> dihedralRestraints) {
        this.dihedralRestraints = dihedralRestraints;
    }

    /**
     * defines the {@link DistanceRestraintImpl} of the Section
     * @return a List of {@link DistanceRestraint}
     * @since {@link ModelVersion#V1}
     */
    public List<DistanceRestraint> getDistanceRestraints() {
        return distanceRestraints;
    }

    /**
     * sets the given List
     * @param distanceRestraints a List of {@link DistanceRestraint}
     * @since {@link ModelVersion#V1}
     */
    public void setDistanceRestraints(List<DistanceRestraint> distanceRestraints) {
        this.distanceRestraints = distanceRestraints;
    }

    /**
     * defines the {@link OrientationRestraintImpl} of the Section
     * @return a List of {@link OrientationRestraint}
     * @since {@link ModelVersion#V1}
     */
    public List<OrientationRestraint> getOrientationRestraints() {
        return orientationRestraints;
    }

    /**
     * sets the given List
     * @param orientationRestraints a List of {@link OrientationRestraint}
     * @since {@link ModelVersion#V1}
     */
    public void setOrientationRestraints(List<OrientationRestraint> orientationRestraints) {
        this.orientationRestraints = orientationRestraints;
    }

    /**
     * defines the {@link SettleImpl} of the Section
     * @return a List of {@link Settle}
     * @since {@link ModelVersion#V1}
     */
    public List<Settle> getSettles() {
        return settles;
    }

    /**
     * sets the given List
     * @param settles a List of {@link Settle}
     * @since {@link ModelVersion#V1}
     */
    public void setSettles(List<Settle> settles) {
        this.settles = settles;
    }

    /**
     * defines the {@link Exclusion} of the Section
     * @return a List of {@link Exclusion}
     * @since {@link ModelVersion#V1}
     */
    public List<Exclusion> getExclusions() {
        return exclusions;
    }

    /**
     * sets the given List
     * @param exclusions a List of {@link Exclusion}
     * @since {@link ModelVersion#V1}
     */
    public void setExclusions(List<Exclusion> exclusions) {
        this.exclusions = exclusions;
    }

    /**
     * defines the {@link SectionType} of the Section
     * @return a {@link SectionType}
     * @since {@link ModelVersion#V1}
     */
    public SectionType getSectionType() {
        return type;
    }
}
