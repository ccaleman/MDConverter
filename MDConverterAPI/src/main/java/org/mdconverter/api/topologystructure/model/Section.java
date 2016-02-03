package org.mdconverter.api.topologystructure.model;

import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.ModelVersion;
import org.mdconverter.api.topologystructure.SectionType;
import org.mdconverter.api.topologystructure.model.api.*;
import org.mdconverter.api.topologystructure.model.impl.AngleRestraintZImpl;
import org.mdconverter.api.topologystructure.model.impl.Exclusion;
import org.mdconverter.api.topologystructure.model.impl.PairNB;

import java.util.List;

/**
 * Created by miso on 04.12.2015.
 */
public class Section {

    /**
     * added at {@link ModelVersion#V1}
     */
    private Molecule moleculeType;
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Atom> atoms = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Bond> bonds = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Pair> pairs = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<PairNB> pairsNB = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Angle> angles = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Dihedral> dihedrals = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Constraint> constraints = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<PositionRestraint> positionRestraints = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<AngleRestraintZImpl> angleRestraints = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<DihedralRestraint> dihedralRestraints = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<DistanceRestraint> distanceRestraints = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<OrientationRestraint> orientationRestraints = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Settle> settles = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private List<Exclusion> exclusions = Lists.newArrayList();
    /**
     * added at {@link ModelVersion#V1}
     */
    private SectionType type;


    public Section(SectionType sectionType) {
        type = sectionType;
    }

    public Molecule getMoleculeType() {
        return moleculeType;
    }

    public void setMoleculeType(Molecule moleculeType) {
        this.moleculeType = moleculeType;
    }

    public List<Atom> getAtoms() {
        return atoms;
    }

    public void setAtoms(List<Atom> atoms) {
        this.atoms = atoms;
    }

    public List<Bond> getBonds() {
        return bonds;
    }

    public void setBonds(List<Bond> bonds) {
        this.bonds = bonds;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    public List<PairNB> getPairsNB() {
        return pairsNB;
    }

    public void setPairsNB(List<PairNB> pairsNB) {
        this.pairsNB = pairsNB;
    }

    public List<Angle> getAngles() {
        return angles;
    }

    public void setAngles(List<Angle> angles) {
        this.angles = angles;
    }

    public List<Dihedral> getDihedrals() {
        return dihedrals;
    }

    public void setDihedrals(List<Dihedral> dihedrals) {
        this.dihedrals = dihedrals;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public List<PositionRestraint> getPositionRestraints() {
        return positionRestraints;
    }

    public void setPositionRestraints(List<PositionRestraint> positionRestraints) {
        this.positionRestraints = positionRestraints;
    }

    public List<AngleRestraintZImpl> getAngleRestraints() {
        return angleRestraints;
    }

    public void setAngleRestraints(List<AngleRestraintZImpl> angleRestraints) {
        this.angleRestraints = angleRestraints;
    }

    public List<DihedralRestraint> getDihedralRestraints() {
        return dihedralRestraints;
    }

    public void setDihedralRestraints(List<DihedralRestraint> dihedralRestraints) {
        this.dihedralRestraints = dihedralRestraints;
    }

    public List<DistanceRestraint> getDistanceRestraints() {
        return distanceRestraints;
    }

    public void setDistanceRestraints(List<DistanceRestraint> distanceRestraints) {
        this.distanceRestraints = distanceRestraints;
    }

    public List<OrientationRestraint> getOrientationRestraints() {
        return orientationRestraints;
    }

    public void setOrientationRestraints(List<OrientationRestraint> orientationRestraints) {
        this.orientationRestraints = orientationRestraints;
    }

    public List<Settle> getSettles() {
        return settles;
    }

    public void setSettles(List<Settle> settles) {
        this.settles = settles;
    }

    public List<Exclusion> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<Exclusion> exclusions) {
        this.exclusions = exclusions;
    }

    public SectionType getSectionType() {
        return type;
    }
}
