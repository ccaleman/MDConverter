package org.mdconverter.filegenerator;


import org.apache.commons.lang3.StringUtils;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.topologystructure.SectionType;
import org.mdconverter.api.topologystructure.model.Section;
import org.mdconverter.api.topologystructure.model.TopologyStructure;
import org.mdconverter.api.topologystructure.model.api.Angle;
import org.mdconverter.api.topologystructure.model.api.ValueGS;
import org.mdconverter.api.topologystructure.model.impl.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.System;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by miso on 22.12.2015.
 */
@Singleton
public class FileWriter {

    private ConsoleWriter cw;
    private static String newLine = System.getProperty("line.separator");
    private String output = "";

    @Inject
    public FileWriter(ConsoleWriter cw) {
        this.cw = cw;
    }

    public String parseStructure(boolean ff, TopologyStructure structure) {
        Integer oldFuncType;

        generateHeaderLines(structure.getHeaderComments());
        if (ff) {
            Default def = structure.getDef();
            if (def != null) {
                addLine("[ defaults ]");
                addLine("; nbfunc        comb-rule       gen-pairs       fudgeLJ fudgeQQ");
                addDefaultEntry(def);
            }
            List<AtomType> atomTypes = structure.getAtomTypes();
            if (!atomTypes.isEmpty()) {
                addLine("[ atomtypes ]");
                addLine("; name      at.num  mass     charge ptype  sigma      epsilon");
                atomTypes.forEach(this::addAtomType);
            }
            oldFuncType = -1;
            for (BondType type : structure.getBondTypes()) {
                if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                    addLine("[ bondtypes ]");
                    addLine("; i    j  func       c0          c1          c2          c3");
                }
                oldFuncType = type.getFuncType();
                addBondType(type);
            }
            oldFuncType = -1;
            for (ConstraintType type : structure.getConstraintTypes()) {
                if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                    addLine("[ constrainttypes ]");
                }
                oldFuncType = type.getFuncType();
                addConstraintType(type);
            }
            oldFuncType = -1;
            for (Angle type : structure.getAngleTypes()) {
                if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                    addLine("[ angletypes ]");
                    addLine(";  i    j    k  func       th0       cth");
                }
                oldFuncType = type.getFuncType();
                addAngleType(type);
            }
            oldFuncType = -1;
            for (DihedralType type : structure.getDihedralTypes()) {
                if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                    addLine("[ dihedraltypes ]");
                    addLine(";i  j   k  l   func      phase      kd      pn");
                }
                oldFuncType = type.getFuncType();
                addDihedralType(type);
            }
            List<ImplicitGenbornParam> params = structure.getGenbornParams();
            if (!params.isEmpty()) {
                addLine("[ implicit_genborn_params ]");
                addLine("; atype      sar      st     pi       gbr       hct");
                params.forEach(this::addGenBornParam);
            }
            oldFuncType = -1;
            for (PairType type : structure.getPairTypes()) {
                if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                    addLine("[ pairtypes ]");
                    addLine("; i    j     func          cs6          cs12");
                }
                oldFuncType = type.getFuncType();
                addPairType(type);
            }
            oldFuncType = -1;
            for (NonBondParam type : structure.getNonBondParams()) {
                if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                    addLine("[ pairtypes ]");
                    addLine("; i    j     func          cs6          cs12");
                }
                oldFuncType = type.getFuncType();
                addNonBondParam(type);
            }
            structure.getSections().stream().filter(
                    s -> s.getSectionType().equals(SectionType.FORCEFIELD))
                    .collect(Collectors.toList())
                    .forEach(this::addSection);
        }
        structure.getSections().stream().filter(
                s -> s.getSectionType().equals(SectionType.STRUCTUREDATA))
                .collect(Collectors.toList())
                .forEach(this::addSection);
        addSystem(structure.getSystem());
        addMolecule(structure.getMolecule());
        return output;
    }

    private void addSection(Section section) {
        Integer oldFuncType;
        addMoleculeType(section.getMoleculeType());
        List<Atom> atoms = section.getAtoms();
        if (!atoms.isEmpty()) {
            addLine("[ atoms ]");
            addLine(";   nr       type  resnr residue  atom   cgnr     charge       mass  typeB    chargeB      massB");
            atoms.forEach(this::addAtom);
        }
        if (section.getSettles().isEmpty()) {
            generateBondLines(section);
        }
        oldFuncType = -1;
        for (Pair type : section.getPairs()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ pairs ]");
                addLine(";  ai    aj funct            c1            c2            c3            c4            c5");
            }
            oldFuncType = type.getFuncType();
            addPair(type);
        }
        oldFuncType = -1;
        for (Pair type : section.getPairsNB()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ pairs_nb ]");
                addLine(";  ai    aj funct            c1            c2            c3            c4");
            }
            oldFuncType = type.getFuncType();
            addPair(type);
        }
        if (section.getSettles().isEmpty()) {
            generateAngleLines(section);
        }
        oldFuncType = -1;
        for (Constraint type : section.getConstraints()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ constraints ]");
                addLine("; ai     aj   funct               c1            c2");
            }
            oldFuncType = type.getFuncType();
            addConstraint(type);
        }
        oldFuncType = -1;
        for (Dihedral type : section.getDihedrals()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ dihedrals ]");
                addLine(";  ai    aj    ak    ak funct            c1            c2            c3            c4            c5            c6");
            }
            oldFuncType = type.getFuncType();
            addDihedral(type);
        }
        oldFuncType = -1;
        for (PositionRestraint type : section.getPositionRestraints()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ position_restraints ]");
                addLine("; atom  type      c1      c2      c3");
            }
            oldFuncType = type.getFuncType();
            addPositionRestraint(type);
        }
        oldFuncType = -1;
        List<AngleRestraintZ> zList = section.getAngleRestraints();
        List<AngleRestraintZ> collect = zList.stream()
                .filter(ar -> ar instanceof AngleRestraint).collect(Collectors.toList());
        for (AngleRestraintZ type : collect) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ angle_restraints ]");
                addLine("; ai  aj   ak   al    type      c1      c2      c3");
            }
            oldFuncType = type.getFuncType();
            addAngleRestraint(type);
        }
        oldFuncType = -1;
        collect.stream().filter(zList::remove);
        for (AngleRestraintZ type : zList) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ angle_restraints_z ]");
                addLine("; ai  aj   type      c1      c2      c3");
            }
            oldFuncType = type.getFuncType();
            addAngleRestraint(type);
        }
        oldFuncType = -1;
        for (DihedralRestraint type : section.getDihedralRestraints()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ dihedral_restraints ]");
                addLine("; ai  aj   ak   al    type       label      c1      c2      c3      c4");
            }
            oldFuncType = type.getFuncType();
            addDihedralRestraint(type);
        }
        oldFuncType = -1;
        for (DistanceRestraint type : section.getDistanceRestraints()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ distance_restraints ]");
                addLine("; ai  aj   type   type    label      c1      c2      c3      c4");
            }
            oldFuncType = type.getFuncType();
            addDistanceRestraint(type);
        }
        oldFuncType = -1;
        for (OrientationRestraint type : section.getOrientationRestraints()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ orientation_restraints ]");
                addLine("; ai  aj   type   exp    label    alpha     c1      c2      c3      c4");
            }
            oldFuncType = type.getFuncType();
            addOrientationRestraint(type);
        }
        oldFuncType = -1;
        for (Settle type : section.getSettles()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ settles ]");
                addLine("; OW    type    c1       c2");
            }
            oldFuncType = type.getFuncType();
            addSettle(type);
        }
        List<Exclusion> exclusions = section.getExclusions();
        if (!exclusions.isEmpty()) {
            addLine("[ exclusions ]");
            exclusions.forEach(this::addExclusion);
        }
    }

    private void generateAngleLines(Section section) {
        Integer oldFuncType = -1;
        for (Angle type : section.getAngles()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ angles ]");
                addLine(";  ai    aj    ak funct            c1            c2            c3            c4            c5            c6");
            }
            oldFuncType = type.getFuncType();
            addAngle(type);
        }
    }

    private void generateBondLines(Section section) {
        Integer oldFuncType = -1;
        for (Bond type : section.getBonds()) {
            if (oldFuncType.equals(-1) || !oldFuncType.equals(type.getFuncType())) {
                addLine("[ bonds ]");
                addLine(";  ai    aj funct            c1            c2            c3            c4");
            }
            oldFuncType = type.getFuncType();
            addBond(type);
        }
    }

    private void generateHeaderLines(List<String> headerComments) {
        for (String s : headerComments) {
            addLine("; " + s);
        }
    }

    private void addDefaultEntry(Default def) {
        String out = "";
        out = appendLengthDependant(out, def.getNboudnFT().toString(), 10);
        out = appendLengthDependant(out, def.getCombRule().toString(), 10);
        out = appendLengthDependant(out, def.isGenPair() ? "yes" : "no", 4);
        out = appendLengthDependant(out, round(def.getFudgeLJ(), 4), 10);
        out = appendLengthDependant(out, round(def.getFudgeQQ(), 4), 10);
        addLine(out);
    }

    private void addBondType(BondType bondType) {
        String out = "";
        out = appendLengthDependant(out, bondType.getAi(), 10);
        out = appendLengthDependant(out, bondType.getAj(), 10);
        out = appendLengthDependant(out, bondType.getFuncType().toString(), 3);
        out = addValueGS(bondType, out, 5, 14);
        addLine(out);
    }

    private void addConstraintType(ConstraintType constraintType) {
        String out = "";
        out = appendLengthDependant(out, constraintType.getAi(), 10);
        out = appendLengthDependant(out, constraintType.getAj(), 10);
        out = appendLengthDependant(out, constraintType.getFuncType().toString(), 3);
        out = addValueGS(constraintType, out, 6, 14);
        addLine(out);
    }

    private void addAngleType(Angle angleType) {
        String out = "";
        out = appendLengthDependant(out, angleType.getAi(), 10);
        out = appendLengthDependant(out, angleType.getAj(), 10);
        out = appendLengthDependant(out, angleType.getAk(), 10);
        out = appendLengthDependant(out, angleType.getFuncType().toString(), 3);
        out = addValueGS((AngleImpl) angleType, out, 3, 14);
        addLine(out);
    }

    private void addAtomType(AtomType a) {
        String out = "";
        out = appendLengthDependant(out, a.getName(), 12);
        out = appendLengthDependant(out, a.getNum().toString(), 10);
        out = appendLengthDependant(out, round(a.getC1(), 2), 10);
        out = appendLengthDependant(out, round(a.getC2(), 4), 12);
        out = appendLengthDependant(out, a.getParticleType(), 14);
        out = appendLengthDependant(out, round(a.getC3(), 6), 14);
        out = appendLengthDependant(out, round(a.getC4(), 6), 14);
        addLine(out);
    }

    private void addGenBornParam(ImplicitGenbornParam type) {
        String out = "";
        out = appendLengthDependant(out, type.getAtom(), 10);
        out = addValueGS(type, out, 3, 14);
        addLine(out);
    }

    private void addMolecule(Molecule molecule) {
        addLine("[ molecules ]");
        addLine("; Compound        #mols");
        String out = "";
        out = appendLengthDependant(out, molecule.getName(), molecule.getName().length() + 1);
        out = appendLengthDependant(out, molecule.getNrMol().toString(), 6);
        addLine(out);
    }

    private void addDihedralType(DihedralType type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        if (type.getAk() != null)
            out = appendLengthDependant(out, type.getAk(), 10);
        if (type.getAl() != null)
            out = appendLengthDependant(out, type.getAl(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS(type, out, 5, 14);
        addLine(out);
    }

    private void addPairType(PairType type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS(type, out, 6, 14);
        addLine(out);
    }

    private void addNonBondParam(NonBondParam type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS(type, out, 6, 14);
        addLine(out);
    }

    private void addSystem(org.mdconverter.api.topologystructure.model.impl.System system) {
        addLine("[ system ]");
        addLine("; Name");
        String out = "";
        out = appendLengthDependant(out, system.getName(), system.getName().length() + 1);
        addLine(out);
    }

    private void addAtom(Atom atom) {
        String out = "";
        out = appendLengthDependant(out, String.valueOf(atom.getNr()), 10);
        out = appendLengthDependant(out, atom.getType(), 10);
        out = appendLengthDependant(out, atom.getResNr().toString(), 10);
        out = appendLengthDependant(out, atom.getResName(), 10);
        out = appendLengthDependant(out, atom.getAtomName(), 10);
        out = appendLengthDependant(out, String.valueOf(atom.getChargeGroupNr()), 10);
        out = addValueGS(atom, out, 4, 12);
        addLine(out);
    }

    private void addBond(Bond type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS(type, out, 6, 14);
        addLine(out);
    }

    private void addPair(Pair type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS(type, out, 4, 12);
        addLine(out);
    }

    private void addAngle(Angle type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getAk(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS((AngleImpl) type, out, 4, 12);
        addLine(out);
    }

    private void addDihedral(Dihedral type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getAk(), 10);
        out = appendLengthDependant(out, type.getAl(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS(type, out, 4, 12);
        addLine(out);
    }

    private void addPositionRestraint(PositionRestraint type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS(type, out, 2, 10);
        addLine(out);
    }

    private void addConstraint(Constraint type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS(type, out, 2, 10);
        addLine(out);
    }

    private void addAngleRestraint(AngleRestraintZ type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        if (type instanceof AngleRestraint) {
            AngleRestraint angle = (AngleRestraint) type;
            out = appendLengthDependant(out, angle.getAk(), 10);
            out = appendLengthDependant(out, angle.getAl(), 10);
            out = appendLengthDependant(out, angle.getFuncType().toString(), 3);
            out = addValueGS(angle, out, 2, 10);
        } else {
            out = appendLengthDependant(out, type.getFuncType().toString(), 3);
            out = addValueGS(type, out, 2, 10);
        }
        addLine(out);
    }

    private void addDihedralRestraint(DihedralRestraint type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getAk(), 10);
        out = appendLengthDependant(out, type.getAl(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = appendLengthDependant(out, type.getLabel(), 10);
        out = addValueGS(type, out, 2, 10);
        addLine(out);
    }

    private void addDistanceRestraint(DistanceRestraint type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = appendLengthDependant(out, type.getType(), 10);
        out = appendLengthDependant(out, type.getLabel(), 10);
        out = addValueGS(type, out, 2, 10);
        addLine(out);
    }

    private void addOrientationRestraint(OrientationRestraint type) {
        String out = "";
        out = appendLengthDependant(out, type.getAi(), 10);
        out = appendLengthDependant(out, type.getAj(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = appendLengthDependant(out, type.getExp(), 10);
        out = appendLengthDependant(out, type.getLabel(), 10);
        out = appendLengthDependant(out, type.getAlpha(), 10);
        out = addValueGS(type, out, 2, 10);
        addLine(out);
    }

    private void addSettle(Settle type) {
        String out = "";
        out = appendLengthDependant(out, type.getAtom(), 10);
        out = appendLengthDependant(out, type.getFuncType().toString(), 3);
        out = addValueGS(type, out, 2, 10);
        addLine(out);
    }

    private void addExclusion(Exclusion type) {
        String out = "";
        out = appendLengthDependant(out, String.valueOf(type.getAtomIdx()), 10);
        for (Integer integer : type.getBonds()) {
            out = appendLengthDependant(out, integer.toString(), 6);
        }
        addLine(out);
    }

    private void addMoleculeType(MoleculeType moleculeType) {
        addLine("[ moleculetype ]");
        addLine("; Name            nrexcl");
        String out = "";
        out = appendLengthDependant(out, moleculeType.getName(), moleculeType.getName().length());
        out = appendLengthDependant(out, String.valueOf(moleculeType.getNrExcl()), 5);
        addLine(out);
    }

    private String addValueGS(ValueGS val, String out, int round, int space) {
        try {
            out = appendLengthDependant(out, round(val.getC1(), round), space);
            out = appendLengthDependant(out, round(val.getC2(), round), space);
            out = appendLengthDependant(out, round(val.getC3(), round), space);
            out = appendLengthDependant(out, round(val.getC4(), round), space);
            out = appendLengthDependant(out, round(val.getC5(), round), space);
            out = appendLengthDependant(out, round(val.getC6(), round), space);
        } catch (UnsupportedOperationException | NullPointerException ignored) {
        }
        return out;
    }

    private String appendLengthDependant(String entry, String value, int len) {
        if (len - value.length() < 1) {
            entry += " ";
        }
        return entry + StringUtils.leftPad(value, len);
    }

    private void addLine(String line) {
        output += line + newLine;
    }

    private String round(BigDecimal value, Integer scale) {
        return value.setScale(scale, BigDecimal.ROUND_HALF_EVEN).toString();
    }
}
