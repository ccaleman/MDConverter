package org.mdconverter.api.topologystructure.model;

import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.SectionType;
import org.mdconverter.api.topologystructure.model.api.Angle;
import org.mdconverter.api.topologystructure.model.impl.*;

import java.util.List;

/**
 * Created by miso on 04.12.2015.
 */
public class Section {

    private MoleculeType moleculeType;
    private List<Atom> atoms = Lists.newArrayList();
    private List<Bond> bonds = Lists.newArrayList();
    private List<Pair> pairs = Lists.newArrayList();
    private List<Pair> pairsNB = Lists.newArrayList();
    private List<Angle> angles = Lists.newArrayList();
    private List<Dihedral> dihedrals = Lists.newArrayList();
    private List<Constraint> constraints = Lists.newArrayList();
    private List<PositionRestraint> positionRestraints = Lists.newArrayList();
    private List<AngleRestraintZ> angleRestraints = Lists.newArrayList();
    private List<DihedralRestraint> dihedralRestraints = Lists.newArrayList();
    private List<DistanceRestraint> distanceRestraints = Lists.newArrayList();
    private List<OrientationRestraint> orientationRestraints = Lists.newArrayList();
    private List<Settle> settles = Lists.newArrayList();
    private List<Exclusion> exclusions = Lists.newArrayList();
    private SectionType type;

    public Section(SectionType sectionType) {
        type = sectionType;
    }

    public MoleculeType getMoleculeType() {
        return moleculeType;
    }

    public void setMoleculeType(MoleculeType moleculeType) {
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

    public List<Pair> getPairsNB() {
        return pairsNB;
    }

    public void setPairsNB(List<Pair> pairsNB) {
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

    public List<AngleRestraintZ> getAngleRestraints() {
        return angleRestraints;
    }

    public void setAngleRestraints(List<AngleRestraintZ> angleRestraints) {
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
