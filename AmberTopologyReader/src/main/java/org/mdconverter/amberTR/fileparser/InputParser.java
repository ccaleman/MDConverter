package org.mdconverter.ambertr.fileparser;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.topologystructure.SectionType;
import org.mdconverter.api.topologystructure.model.Section;
import org.mdconverter.api.topologystructure.model.TopologyStructure;
import org.mdconverter.api.topologystructure.model.api.Atom;
import org.mdconverter.api.topologystructure.model.impl.*;
import org.mdconverter.api.topologystructure.model.impl.System;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.mdconverter.ambertr.fileparser.AmberPointer.*;
import static org.mdconverter.ambertr.fileparser.AmberSection.*;

/**
 * Created by miso on 20.01.2016.
 */
@Singleton
public class InputParser {

    private static final Pattern LOGICAL_PATTERN = Pattern.compile("^%.*");
    private static final Pattern VERSION_PATTERN = Pattern.compile("^%VERSION .+");
    private static final Pattern FLAG_PATTERN = Pattern.compile("^%FLAG .+");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("^%COMMENT .*");
    private static final Pattern STRING_FORMAT_PATTERN = Pattern.compile("^%FORMAT\\((20a4)\\)");
    private static final Pattern INT_FORMAT_PATTERN = Pattern.compile("^%FORMAT\\((10I8)\\)");
    private static final Pattern BIG_FORMAT_PATTERN = Pattern.compile("^%FORMAT\\((5E16.8)\\)");

    private TopologyStructure structure;
    private ConsoleWriter cw;
    private Map<String, String> args = Maps.newHashMap();
    Section topSection = new Section(SectionType.STRUCTUREDATA);
    //Pointers
    Map<AmberPointer, Integer> pointers = Maps.newHashMap();

    @Inject
    protected InputParser(ConsoleWriter cw) {
        this.cw = cw;
    }

    public void parseInput(byte[] input, Path path, boolean header, String previousPath, Map<String, String> arguments) throws IOException, URISyntaxException, NumberFormatException {

        if (input == null) return;
        if (arguments != null) {
            args = arguments;
        }

        structure.getSections().add(topSection);

        String line;
        AmberSection activeAS = null;

        List<Integer> counters = null;
        //Exclusions
        List<Integer> eal = Lists.newArrayList();
        List<Integer> nea = Lists.newArrayList();
        //Coefficients/potentials
        List<Integer> ati = Lists.newArrayList();
        List<Integer> npi = Lists.newArrayList();
        List<BigDecimal> ljA = Lists.newArrayList();
        List<BigDecimal> ljB = Lists.newArrayList();
        List<BigDecimal> hbA = Lists.newArrayList();
        List<BigDecimal> hbB = Lists.newArrayList();
        //Atoms
        List<String> atomNames = Lists.newArrayList();
        List<BigDecimal> charges = Lists.newArrayList();
        List<Integer> atomicNrs = Lists.newArrayList();
        List<BigDecimal> aMass = Lists.newArrayList();
        List<String> atomTypes = Lists.newArrayList();
        List<String> resNames = Lists.newArrayList();
        List<Integer> resPointers = Lists.newArrayList();
        //Bonds
        List<BigDecimal> bondForce = Lists.newArrayList();
        List<BigDecimal> bondEquil = Lists.newArrayList();
        List<Integer> bondInclHydro = Lists.newArrayList();
        List<Integer> bondWoHydro = Lists.newArrayList();
        //Angles
        List<BigDecimal> angleForce = Lists.newArrayList();
        List<BigDecimal> angleEquil = Lists.newArrayList();
        List<Integer> angleInclHydro = Lists.newArrayList();
        List<Integer> angleWoHydro = Lists.newArrayList();
        //Dihedrals
        List<BigDecimal> dihedralForce = Lists.newArrayList();
        List<BigDecimal> dihedralPeriod = Lists.newArrayList();
        List<BigDecimal> dihedralPhase = Lists.newArrayList();
        List<BigDecimal> sceeFactor = Lists.newArrayList();
        List<BigDecimal> scnbFactor = Lists.newArrayList();
        List<Integer> dihedralInclHydro = Lists.newArrayList();
        List<Integer> dihedralWoHydro = Lists.newArrayList();
        //TreeChain
        List<TreeChain> treeChain = Lists.newArrayList();
        //Radii
        RadiusSet radiSet = null;
        List<BigDecimal> radii = Lists.newArrayList();
        //polarized
        List<BigDecimal> polarized = Lists.newArrayList();


        InputStream stream = new ByteArrayInputStream(input);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        if (path != null) {
            parseInput(Files.readAllBytes(path), null, false, null, args);
        }

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (LOGICAL_PATTERN.matcher(line).matches()) {
                if (VERSION_PATTERN.matcher(line).matches()) {
                    //TODO: read version line?
                } else if (FLAG_PATTERN.matcher(line).matches()) {
                    activeAS = AmberSection.valueOf(reworkLogicalLine(line)[1]);
                    continue;
                } else if (STRING_FORMAT_PATTERN.matcher(line).matches()) {
                    continue;
                } else if (INT_FORMAT_PATTERN.matcher(line).matches()) {
                    continue;
                } else if (BIG_FORMAT_PATTERN.matcher(line).matches()) {
                    continue;
                } else if (COMMENT_PATTERN.matcher(line).matches()) {
                    //TODO: summarizes all comments into header comments
                    List<String> strings = Lists.newArrayList(line.trim().split(" "));
                    strings.remove(0);
                    getStructure().getHeaderComments().add(StringUtils.join(strings, " "));
                    continue;
                }
            }
            if (activeAS != null) {
                if (activeAS.equals(TITLE)) {
                    //checkLineReader(activeLineReader, activeAS);
                    structure.setSystem(new System(line));
                    continue;
                }
                if (activeAS.equals(POINTERS)) {
                    //checkLineReader(activeLineReader, activeAS);
                    if (counters == null) {
                        counters = Lists.newArrayList();
                    }
                    counters.addAll(readLineI(line));
                    for (int i = 0; i < counters.size(); i++) {
                        pointers.put(AmberPointer.valueOf(i), counters.get(i));
                    }
                    generateEmptyAtoms(pointers.get(NATOM), topSection);
                    continue;
                }
                if (!pointers.isEmpty()) {
                    if (activeAS.equals(ATOM_NAME)) {
                        //checkLineReader(activeLineReader, activeAS);
                        atomNames.addAll(readLineS(line));
                        if (atomNames.size() == pointers.get(NATOM)) {
                            for (int i = 0; i < atomNames.size(); i++) {
                                Atom atom = topSection.getAtoms().get(i);
                                atom.setAtomName(atomNames.get(i));
                                atom.setNr(i + 1);
                                atom.setChargeGroupNr(i + 1);
                            }
                        }
                    }
                    if (activeAS.equals(CHARGE)) {
                        //checkLineReader(activeLineReader, activeAS);
                        charges.addAll(readLineB(line));
                        if (charges.size() == pointers.get(NATOM)) {
                            for (int i = 0; i < charges.size(); i++) {
                                ((AtomImpl) topSection.getAtoms().get(i)).setC1(charges.get(i));
                            }
                        }
                    }
                    if (activeAS.equals(ATOMIC_NUMBER)) {
                        //TODO since amber 12 before ??????
                        //build switch in arguments?????
                        //checkLineReader(activeLineReader, activeAS);
                        atomicNrs.addAll(readLineI(line));
                        if (atomicNrs.size() == pointers.get(NATOM)) {
                            for (int i = 0; i < atomicNrs.size(); i++) {
                                topSection.getAtoms().get(i).setNr(atomicNrs.get(i));
                            }
                        }
                    }
                    if (activeAS.equals(MASS)) {
                        //checkLineReader(activeLineReader, activeAS);
                        aMass.addAll(readLineB(line));
                        if (aMass.size() == pointers.get(NATOM)) {
                            for (int i = 0; i < aMass.size(); i++) {
                                ((AtomImpl) topSection.getAtoms().get(i)).setC2(aMass.get(i));
                            }
                        }
                    }
                    if (activeAS.equals(NUMBER_EXCLUDED_ATOMS)) {
                        //checkLineReader(activeLineReader, activeAS);
                        nea.addAll(readLineI(line));
                        if (nea.size() == pointers.get(NATOM)) {
                            generateExclusions(nea, eal);
                        }
                    }
                    if (activeAS.equals(EXCLUDED_ATOMS_LIST)) {
                        //checkLineReader(activeLineReader, activeAS);
                        eal.addAll(readLineI(line));
                        if (eal.size() == pointers.get(NNB)) {
                            generateExclusions(nea, eal);
                        }
                    }
                    if (activeAS.equals(ATOM_TYPE_INDEX)) {
                        //checkLineReader(activeLineReader, activeAS);
                        ati.addAll(readLineI(line));
                        if (ati.size() == pointers.get(NATOM)) {
                            generatePotentials(ati, npi, ljA, ljB, hbA, hbB, atomTypes);
                        }
                    }
                    if (activeAS.equals(NONBONDED_PARM_INDEX)) {
                        //checkLineReader(activeLineReader, activeAS);
                        npi.addAll(readLineI(line));
                        if (npi.size() == (int) Math.sqrt(pointers.get(NTYPES))) {
                            generatePotentials(ati, npi, ljA, ljB, hbA, hbB, atomTypes);
                        }
                    }
                    if (activeAS.equals(LENNARD_JONES_ACOEF)) {
                        //checkLineReader(activeLineReader, activeAS);
                        ljA.addAll(readLineB(line));
                        if (ljA.size() == (pointers.get(NTYPES) * (pointers.get(NTYPES) + 1)) / 2) {
                            generatePotentials(ati, npi, ljA, ljB, hbA, hbB, atomTypes);
                        }
                    }
                    if (activeAS.equals(LENNARD_JONES_BCOEF)) {
                        //checkLineReader(activeLineReader, activeAS);
                        ljB.addAll(readLineB(line));
                        if (ljB.size() == (pointers.get(NTYPES) * (pointers.get(NTYPES) + 1)) / 2) {
                            generatePotentials(ati, npi, ljA, ljB, hbA, hbB, atomTypes);
                        }
                    }
                    if (activeAS.equals(HBOND_ACOEF)) {
                        //checkLineReader(activeLineReader, activeAS);
                        hbA.addAll(readLineB(line));
                        if (hbA.size() == pointers.get(NPHB)) {
                            generatePotentials(ati, npi, ljA, ljB, hbA, hbB, atomTypes);
                        }
                    }
                    if (activeAS.equals(HBOND_BCOEF)) {
                        //checkLineReader(activeLineReader, activeAS);
                        hbB.addAll(readLineB(line));
                        if (hbB.size() == pointers.get(NPHB)) {
                            generatePotentials(ati, npi, ljA, ljB, hbA, hbB, atomTypes);
                        }
                    }
                    if (activeAS.equals(AMBER_ATOM_TYPE)) {
                        atomTypes.addAll(readLineS(line));
                        if (atomTypes.size() == pointers.get(NATOM)) {
                            generatePotentials(ati, npi, ljA, ljB, hbA, hbB, atomTypes);
                            addTypeToAtoms(atomTypes);
                        }
                    }
                    if (activeAS.equals(RESIDUE_LABEL)) {
                        resNames.addAll(readLineS(line));
                        if (resNames.size() == pointers.get(NRES)) {
                            setResidueForAtoms(resNames, resPointers);
                        }
                    }
                    if (activeAS.equals(RESIDUE_POINTER)) {
                        resPointers.addAll(readLineI(line));
                        if (resPointers.size() == pointers.get(NRES)) {
                            setResidueForAtoms(resNames, resPointers);
                        }
                    }
                    if (activeAS.equals(BOND_FORCE_CONSTANT)) {
                        bondForce.addAll(readLineB(line));
                        if (bondForce.size() == pointers.get(NUMBND) && pointers.get(NUMBND) != 0) {
                            generateBonds(bondForce, bondEquil, bondInclHydro, bondWoHydro);
                        }
                    }
                    if (activeAS.equals(BOND_EQUIL_VALUE)) {
                        bondEquil.addAll(readLineB(line));
                        if (bondEquil.size() == pointers.get(NUMBND) && pointers.get(NUMBND) != 0) {
                            generateBonds(bondForce, bondEquil, bondInclHydro, bondWoHydro);
                        }
                    }
                    if (activeAS.equals(BONDS_INC_HYDROGEN)) {
                        bondInclHydro.addAll(readLineI(line));
                        if (bondInclHydro.size() == 3 * pointers.get(NBONH) && pointers.get(NBONH) != 0) {
                            generateBonds(bondForce, bondEquil, bondInclHydro, bondWoHydro);
                        }
                    }
                    if (activeAS.equals(BONDS_WITHOUT_HYDROGEN)) {
                        bondWoHydro.addAll(readLineI(line));
                        if (bondWoHydro.size() == 3 * pointers.get(NBONA) && pointers.get(NBONA) != 0) {
                            generateBonds(bondForce, bondEquil, bondInclHydro, bondWoHydro);
                        }
                    }
                    if (activeAS.equals(ANGLE_FORCE_CONSTANT)) {
                        angleForce.addAll(readLineB(line));
                        if (angleForce.size() == pointers.get(NUMANG) && pointers.get(NUMANG) != 0) {
                            generateAngles(angleForce, angleEquil, angleInclHydro, angleWoHydro);
                        }
                    }
                    if (activeAS.equals(ANGLE_EQUIL_VALUE)) {
                        angleEquil.addAll(readLineB(line));
                        if (angleEquil.size() == pointers.get(NUMANG) && pointers.get(NUMANG) != 0) {
                            generateAngles(angleForce, angleEquil, angleInclHydro, angleWoHydro);
                        }
                    }
                    if (activeAS.equals(ANGLES_INC_HYDROGEN)) {
                        angleInclHydro.addAll(readLineI(line));
                        if (angleInclHydro.size() == 4 * pointers.get(NTHETH) && pointers.get(NTHETH) != 0) {
                            generateAngles(angleForce, angleEquil, angleInclHydro, angleWoHydro);
                        }
                    }
                    if (activeAS.equals(ANGLES_WITHOUT_HYDROGEN)) {
                        angleWoHydro.addAll(readLineI(line));
                        if (angleWoHydro.size() == 4 * pointers.get(NTHETA) && pointers.get(NTHETA) != 0) {
                            generateAngles(angleForce, angleEquil, angleInclHydro, angleWoHydro);
                        }
                    }
                    if (activeAS.equals(DIHEDRAL_FORCE_CONSTANT)) {
                        dihedralForce.addAll(readLineB(line));
                        if (dihedralForce.size() == pointers.get(NPTRA) && pointers.get(NPTRA) != 0) {
                            generateDihedrals(dihedralForce, dihedralPeriod, dihedralPhase, dihedralInclHydro, dihedralWoHydro, sceeFactor, scnbFactor);
                        }
                    }
                    if (activeAS.equals(DIHEDRAL_PERIODICITY)) {
                        dihedralPeriod.addAll(readLineB(line));
                        if (dihedralPeriod.size() == pointers.get(NPTRA) && pointers.get(NPTRA) != 0) {
                            generateDihedrals(dihedralForce, dihedralPeriod, dihedralPhase, dihedralInclHydro, dihedralWoHydro, sceeFactor, scnbFactor);
                        }
                    }
                    if (activeAS.equals(DIHEDRAL_PHASE)) {
                        dihedralPhase.addAll(readLineB(line));
                        if (dihedralPhase.size() == pointers.get(NPTRA) && pointers.get(NPTRA) != 0) {
                            generateDihedrals(dihedralForce, dihedralPeriod, dihedralPhase, dihedralInclHydro, dihedralWoHydro, sceeFactor, scnbFactor);
                        }
                    }
                    if (activeAS.equals(DIHEDRALS_INC_HYDROGEN)) {
                        dihedralInclHydro.addAll(readLineI(line));
                        if (dihedralInclHydro.size() == 5 * pointers.get(NPHIH) && pointers.get(NPHIH) != 0) {
                            generateDihedrals(dihedralForce, dihedralPeriod, dihedralPhase, dihedralInclHydro, dihedralWoHydro, sceeFactor, scnbFactor);
                        }
                    }
                    if (activeAS.equals(DIHEDRALS_WITHOUT_HYDROGEN)) {
                        dihedralWoHydro.addAll(readLineI(line));
                        if (dihedralWoHydro.size() == 5 * pointers.get(NPHIA) && pointers.get(NPHIA) != 0) {
                            generateDihedrals(dihedralForce, dihedralPeriod, dihedralPhase, dihedralInclHydro, dihedralWoHydro, sceeFactor, scnbFactor);
                        }
                    }
                    if (activeAS.equals(SCEE_SCLAE_FACTOR)) {
                        //TODO since amber 11 before ??????
                        //build switch in arguments?????
                        sceeFactor.addAll(readLineB(line));
                        if (sceeFactor.size() == pointers.get(NPTRA) && pointers.get(NPTRA) != 0) {
                            generateDihedrals(dihedralForce, dihedralPeriod, dihedralPhase, dihedralInclHydro, dihedralWoHydro, sceeFactor, scnbFactor);
                        }
                    }
                    if (activeAS.equals(SCNB_SCLAE_FACTOR)) {
                        //TODO since amber 11 before ??????
                        //build switch in arguments?????
                        scnbFactor.addAll(readLineB(line));
                        if (scnbFactor.size() == pointers.get(NPTRA) && pointers.get(NPTRA) != 0) {
                            generateDihedrals(dihedralForce, dihedralPeriod, dihedralPhase, dihedralInclHydro, dihedralWoHydro, sceeFactor, scnbFactor);
                        }
                    }
                    if (activeAS.equals(SOLTY)) {
                        //unused at the moment
                    }
                    if (activeAS.equals(HBCUT)) {
                        //outdated --> not longer used
                    }
                    if (activeAS.equals(TREE_CHAIN_CLASSIFICATION)) {
                        treeChain.addAll(readLineS(line).stream().map(TreeChain::valueOf).collect(Collectors.toList()));
                        if (treeChain.size() == pointers.get(NATOM)) {
                            //nothing to do at the moment
                        }
                    }
                    if (activeAS.equals(JOIN_ARRAY)) {
                        //outdated --> not longer used
                    }
                    if (activeAS.equals(IROTAT)) {
                        //unused at the moment
                    }
                    if (activeAS.equals(SOLVENT_POINTERS)) {
                        if (pointers.get(IFBOX) != 0) {
                            throw new NotImplementedException(String.format("IFBOX conditions aren't checked for %s at the moment", activeAS));
                        }
                    }
                    if (activeAS.equals(ATOMS_PER_MOLECULE)) {
                        if (pointers.get(IFBOX) != 0) {
                            throw new NotImplementedException(String.format("IFBOX conditions aren't checked for %s at the moment", activeAS));
                        }
                    }
                    if (activeAS.equals(BOX_DIMENSIONS)) {
                        if (pointers.get(IFBOX) != 0) {
                            throw new NotImplementedException(String.format("IFBOX conditions aren't checked for %s at the moment", activeAS));
                        }
                    }
                    if (activeAS.equals(CAP_INFO)) {
                        if (pointers.get(IFCAP) != 0) {
                            throw new NotImplementedException(String.format("IFCAP conditions aren't checked for %s at the moment", activeAS));
                        }
                    }
                    if (activeAS.equals(CAP_INFO2)) {
                        if (pointers.get(IFCAP) != 0) {
                            throw new NotImplementedException(String.format("IFCAP conditions aren't checked for %s at the moment", activeAS));
                        }
                    }
                    if (activeAS.equals(RADIUS_SET)) {
                        radiSet = RadiusSet.fromString(line);
                        //nothing to do at the moment
                    }
                    if (activeAS.equals(RADII)) {
                        radii.addAll(readLineB(line));
                        if (radii.size() == pointers.get(NATOM)) {
                            //nothing to do at the moment
                        }
                    }
                    if (activeAS.equals(IPOL)) {
                        //TODO since amber 12 before ??????
                        //build switch in arguments?????
                        Integer ipol = readLineI(line).get(0);
                        if (ipol == 0) {
                            /*
                            This section contains a single integer that is 0 for fixed-charge force fields
                            and 1 for force fields that contain polarization.
                            */
                        } else {
                            //nothing to do at the moment
                        }
                    }
                    if (activeAS.equals(POLARIZABILITY)) {
                        polarized.addAll(readLineB(line));
                        if (polarized.size() == pointers.get(NATOM)) {
                            //nothing to do at the moment
                        }
                    }
                    if (activeAS.equals(SCREEN)) {
                        //unknown section maybe deprecated
                    }
                } else {
                    throw new IOException("POINTERS section in prmtop is missing!");
                }
            }
        }
        topSection.setMoleculeType(new MoleculeImpl(structure.getSystem().getName(), pointers.get(NATOM)));
        structure.setMolecule(new MoleculeImpl(structure.getSystem().getName(), pointers.get(NRES)));
        structure.setDef(new DefaultImpl(1, 2, true, new BigDecimal(0.5), new BigDecimal(0.8333)));
    }

    private void addTypeToAtoms(List<String> atomType) {
        List<Atom> atoms = topSection.getAtoms();
        if (atoms.size() == pointers.get(NATOM)) {
            for (int i = 0; i < atoms.size(); i++) {
                Atom atom = atoms.get(i);
                atom.setType(atomType.get(i));
            }
        }
    }

    private void generateDihedrals(List<BigDecimal> dihedralForce, List<BigDecimal> dihedralPeriod, List<BigDecimal> dihedralPhase, List<Integer> dihedralInclHydro, List<Integer> dihedralWoHydro, List<BigDecimal> sceeFactor, List<BigDecimal> scnbFactor) {
        if (dihedralForce.size() == pointers.get(NPTRA) && dihedralPeriod.size() == pointers.get(NPTRA)
                && dihedralPhase.size() == pointers.get(NPTRA) && dihedralInclHydro.size() == 5 * pointers.get(NPHIH)
                && dihedralWoHydro.size() == 5 * pointers.get(NPHIA) && sceeFactor.size() == pointers.get(NPTRA)
                && scnbFactor.size() == pointers.get(NPTRA)) {
            //check for negative ak and al??????? page 12
            for (int i = 0; i < dihedralInclHydro.size(); i += 5) {
                Integer ai = dihedralInclHydro.get(i) / 3 + 1;
                Integer aj = dihedralInclHydro.get(i + 1) / 3 + 1;
                Integer ak = dihedralInclHydro.get(i + 2) / 3 + 1;
                Integer al = dihedralInclHydro.get(i + 3) / 3 + 1;
                Integer idx = dihedralInclHydro.get(i + 4) - 1;
                topSection.getDihedrals().add(new DihedralImpl(ai.toString(), aj.toString(), ak.toString(), al.toString(), 3, dihedralPeriod.get(idx), dihedralForce.get(idx), BigDecimal.ZERO, dihedralPhase.get(idx), BigDecimal.ZERO, BigDecimal.ZERO));
            }
            for (int i = 0; i < dihedralWoHydro.size(); i += 5) {
                Integer ai = dihedralWoHydro.get(i) / 3 + 1;
                Integer aj = dihedralWoHydro.get(i + 1) / 3 + 1;
                Integer ak = dihedralWoHydro.get(i + 2) / 3 + 1;
                Integer al = dihedralWoHydro.get(i + 3) / 3 + 1;
                Integer idx = dihedralWoHydro.get(i + 4) - 1;
                topSection.getDihedrals().add(new DihedralImpl(ai.toString(), aj.toString(), ak.toString(), al.toString(), 3, dihedralPeriod.get(idx), dihedralForce.get(idx), BigDecimal.ZERO, dihedralPhase.get(idx), BigDecimal.ZERO, BigDecimal.ZERO));
            }
        }
    }

    private void generateAngles(List<BigDecimal> angleForce, List<BigDecimal> angleEquil, List<Integer> angleInclHydro, List<Integer> angleWoHydro) {
        if (angleForce.size() == pointers.get(NUMANG) && angleEquil.size() == pointers.get(NUMANG)
                && angleInclHydro.size() == 4 * pointers.get(NTHETH) && angleWoHydro.size() == 4 * pointers.get(NTHETA)) {
            for (int i = 0; i < angleInclHydro.size(); i += 4) {
                Integer ai = angleInclHydro.get(i) / 3 + 1;
                Integer aj = angleInclHydro.get(i + 1) / 3 + 1;
                Integer ak = angleInclHydro.get(i + 2) / 3 + 1;
                Integer idx = angleInclHydro.get(i + 3) - 1;
                topSection.getAngles().add(new AngleImpl(ai.toString(), aj.toString(), ak.toString(), 1, angleEquil.get(idx), angleForce.get(idx)));
            }
            for (int i = 0; i < angleWoHydro.size(); i += 4) {
                Integer ai = angleWoHydro.get(i) / 3 + 1;
                Integer aj = angleWoHydro.get(i + 1) / 3 + 1;
                Integer ak = angleWoHydro.get(i + 2) / 3 + 1;
                Integer idx = angleWoHydro.get(i + 3) - 1;
                topSection.getAngles().add(new AngleImpl(ai.toString(), aj.toString(), ak.toString(), 1, angleEquil.get(idx), angleForce.get(idx)));
            }
        }
    }

    private void generateBonds(List<BigDecimal> bondForce, List<BigDecimal> bondEquil, List<Integer> bondInclHydro, List<Integer> bondWoHydro) {
        if (bondForce.size() == pointers.get(NUMBND) && bondEquil.size() == pointers.get(NUMBND)
                && bondInclHydro.size() == 3 * pointers.get(NBONH) && bondWoHydro.size() == 3 * pointers.get(NBONA)) {
            for (int i = 0; i < bondInclHydro.size(); i += 3) {
                Integer ai = bondInclHydro.get(i) / 3 + 1;
                Integer aj = bondInclHydro.get(i + 1) / 3 + 1;
                Integer idx = bondInclHydro.get(i + 2) - 1;
                topSection.getBonds().add(new BondImpl(ai.toString(), aj.toString(), 1, bondEquil.get(idx), bondForce.get(idx)));
            }
            for (int i = 0; i < bondWoHydro.size(); i += 3) {
                Integer ai = bondWoHydro.get(i) / 3 + 1;
                Integer aj = bondWoHydro.get(i + 1) / 3 + 1;
                Integer idx = bondWoHydro.get(i + 2) - 1;
                topSection.getBonds().add(new BondImpl(ai.toString(), aj.toString(), 1, bondEquil.get(idx), bondForce.get(idx)));
            }
        }
    }

    private void setResidueForAtoms(List<String> resNames, List<Integer> resPointers) {
        if (resNames.size() == pointers.get(NRES) && resPointers.size() == pointers.get(NRES))
            for (int i = 0; i < resPointers.size(); i++) {
                Integer pointer = resPointers.get(i);
                List<Atom> atoms = topSection.getAtoms();
                Integer counter;
                if (i == resPointers.size() - 1) {
                    counter = atoms.size();
                } else {
                    counter = resPointers.get(i + 1);
                }
                String name = resNames.get(i);
                for (int a = pointer - 1; a < counter; a++) {
                    Atom atom = atoms.get(a);
                    atom.setResName(name);
                    atom.setResNr(i);
                }
            }
    }

    private void generatePotentials(List<Integer> ati, List<Integer> npi, List<BigDecimal> ljA, List<BigDecimal> ljB,
                                    List<BigDecimal> hbA, List<BigDecimal> hbB, List<String> atomTypes) {
        if (ati.size() == pointers.get(NATOM) && npi.size() == pointers.get(NTYPES) * pointers.get(NTYPES)
                && ljA.size() == (pointers.get(NTYPES) * (pointers.get(NTYPES) + 1)) / 2
                && ljB.size() == (pointers.get(NTYPES) * (pointers.get(NTYPES) + 1)) / 2
                && atomTypes.size() == pointers.get(NATOM)) {
            for (Integer i = 0; i < pointers.get(NTYPES); i++) {
                Integer j = npi.get(i * (pointers.get(NTYPES) + 1)) - 1;
                structure.getAtomTypes().add(new AtomTypeImpl(atomTypes.get(i), atomTypes.get(i), BigDecimal.ZERO, BigDecimal.ZERO, "A", ljA.get(j), ljB.get(j)));
                //TODO page 7 nonbond_param_index --> Eq. 1 negative???????
                //hbA && hbB would be used instead of ljA && ljB
                //throw new RuntimeException("Calculation for pairs gone wrong!");
            }
        }
    }

    private void generateExclusions(List<Integer> nea, List<Integer> eal) {
        if (nea.size() == pointers.get(NATOM) && eal.size() == pointers.get(NNB)) {
            for (int i = 0; i < nea.size(); i++) {
                Exclusion exclusion = new Exclusion();
                exclusion.setAtomIdx(i + 1);
                Integer noExcl = nea.get(i);
                if (noExcl > 1) {
                    for (int a = 0; a < noExcl; a++) {
                        exclusion.getBonds().add(eal.get(a));
                    }
                    topSection.getExclusions().add(exclusion);
                }
            }
        }
    }

    private void generateEmptyAtoms(Integer nAtom, Section topSection) {
        if (topSection.getAtoms().size() != nAtom) {
            for (Integer i = 0; i < nAtom; i++) {
                topSection.getAtoms().add(new AtomImpl());
            }
        }
    }

    private void checkLineReader(Class activeLineReader, AmberSection activeAS) throws IOException {
        if (activeLineReader != String.class) {
            throw new IOException(String.format("Wrong FORMAT for %s: %s", activeAS, activeLineReader));
        }
    }

    private List<String> readLineS(String line) {
        List<String> list = Lists.newArrayList(Splitter.fixedLength(4).split(line));
        if (!line.isEmpty()) {
            return list.stream().map(String::trim).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    private List<BigDecimal> readLineB(String line) {
        line = line.replaceAll("\\s+", " ");
        if (!line.isEmpty()) {
            return Lists.newArrayList(Splitter.on(" ").split(line)).stream().map(BigDecimal::new).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    private List<Integer> readLineI(String line) {
        line = line.replaceAll("\\s+", " ");
        if (!line.isEmpty()) {
            return Lists.newArrayList(Splitter.on(" ").split(line)).stream().map(Integer::parseInt).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    private String[] reworkLogicalLine(String line) {
        return line.trim().split(" ");
    }

    public TopologyStructure getStructure() {
        return structure;
    }

    public void setStructure(TopologyStructure structure) {
        this.structure = structure;
    }
}