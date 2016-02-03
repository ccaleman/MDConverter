package org.mdconverter.fileparser;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.topologystructure.SectionType;
import org.mdconverter.api.topologystructure.model.Section;
import org.mdconverter.api.topologystructure.model.TopologyStructure;
import org.mdconverter.api.topologystructure.model.impl.Atom;

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

import static org.mdconverter.fileparser.AmberSection.*;

/**
 * Created by miso on 20.01.2016.
 */
@Singleton
public class InputParserT {

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

    @Inject
    protected InputParserT(ConsoleWriter cw) {
        this.cw = cw;
    }

    public void parseInput(byte[] input, Path path, boolean header, String previousPath, Map<String, String> arguments) throws IOException, URISyntaxException, NumberFormatException {
        if (input == null) return;
        if (arguments != null) {
            args = arguments;
        }

        String line;
        Class activeLineReader = null;
        AmberSection activeAS = null;
        List<Integer> pointers = Lists.newArrayList();
        Section topSection = new Section(SectionType.STRUCTUREDATA);
        Section ffSection = new Section(SectionType.FORCEFIELD);


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
                    activeLineReader = String.class;
                    continue;
                } else if (INT_FORMAT_PATTERN.matcher(line).matches()) {
                    activeLineReader = Integer.class;
                    continue;
                } else if (BIG_FORMAT_PATTERN.matcher(line).matches()) {
                    activeLineReader = BigDecimal.class;
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
                    checkLineReader(activeLineReader, activeAS);
                    structure.getSystem().setName(line);
                }
                if (activeAS.equals(POINTERS)) {
                    checkLineReader(activeLineReader, activeAS);
                    pointers.addAll(readLineI(line));
                    generateEmptyAtoms(pointers, topSection);
                }
                if (!pointers.isEmpty()) {
                    if (activeAS.equals(ATOM_NAME)) {
                        checkLineReader(activeLineReader, activeAS);
                        List<String> list = readLineS(line);
                        for (int i = 0; i < list.size(); i++) {
                            Atom atom = topSection.getAtoms().get(i);
                            atom.setAtomName(list.get(i));
                            atom.setNr(i);
                        }
                    }
                    if (activeAS.equals(CHARGE)) {
                        checkLineReader(activeLineReader, activeAS);
                        List<BigDecimal> list = readLineB(line);
                        for (int i = 0; i < list.size(); i++) {
                            topSection.getAtoms().get(i).setC1(list.get(i));
                        }
                    }
                    if (activeAS.equals(ATOMIC_NUMBER)) {
                        checkLineReader(activeLineReader, activeAS);
                        List<Integer> list = readLineI(line);
                        for (int i = 0; i < list.size(); i++) {
                            topSection.getAtoms().get(i).setNr(list.get(i));
                        }
                    }
                    if (activeAS.equals(MASS)) {
                        checkLineReader(activeLineReader, activeAS);
                        List<BigDecimal> list = readLineB(line);
                        for (int i = 0; i < list.size(); i++) {
                            topSection.getAtoms().get(i).setC2(list.get(i));
                        }
                    }
                    if (activeAS.equals(ATOMIC_TYPE_INDEX)) {
                        //pairs funcType always 1
                        //V & W from LJ-A/B over NONBOND_PARAM_INDEX
                    }
                    if (activeAS.equals(NUMBER_EXCLUDED_ATOMS)) {
                        //Exclusion in structure
                        //calculation unclear
                        //maybe ignoreable
                    }
                    if (activeAS.equals(NONBONDED_PARM_INDEX)) {

                    }
                    if (activeAS.equals(RESIDUE_LABEL)) {

                    }
                    if (activeAS.equals(RESIDUE_POINTER)) {

                    }
                    if (activeAS.equals(BOND_FORCE_CONSTANT)) {

                    }
                    if (activeAS.equals(BOND_EQUIL_VALUE)) {

                    }
                    if (activeAS.equals(ANGLE_FORCE_CONSTANT)) {

                    }
                    if (activeAS.equals(ANGLE_EQUIL_VALUE)) {

                    }
                    if (activeAS.equals(DIHEDRAL_FORCE_CONSTANT)) {

                    }
                    if (activeAS.equals(DIHERDRAL_PERIODICITY)) {

                    }
                    if (activeAS.equals(DIHEDRAL_PHASE)) {

                    }
                    if (activeAS.equals(SCEE_SCLAE_FACTOR)) {

                    }
                    if (activeAS.equals(SCNB_SCLAE_FACTOR)) {

                    }
                    if (activeAS.equals(SOLTY)) {

                    }
                    if (activeAS.equals(LENNARD_JONES_ACOEF)) {

                    }
                    if (activeAS.equals(LENNARD_JONES_BCOEF)) {

                    }
                    if (activeAS.equals(BONDS_INC_HYDROGEN)) {

                    }
                    if (activeAS.equals(BONDS_WITHOUT_HYDROGEN)) {

                    }
                    if (activeAS.equals(ANGLES_INC_HYDROGEN)) {

                    }
                    if (activeAS.equals(ANGLES_WITHOUT_HYROGEN)) {

                    }
                    if (activeAS.equals(DIHEDRALS_INC_HYDROGEN)) {

                    }
                    if (activeAS.equals(DIHEDRALS_WITHOUT_HYROGEN)) {

                    }
                    if (activeAS.equals(EXCLUDED_ATOMS_LIST)) {

                    }
                    if (activeAS.equals(HBOND_ACOEF)) {

                    }
                    if (activeAS.equals(HBOND_BCOEF)) {

                    }
                    if (activeAS.equals(HBCUT)) {

                    }
                    if (activeAS.equals(AMBER_ATOM_TYPE)) {

                    }
                    if (activeAS.equals(TREE_CHAIN_CLASSIFICATION)) {

                    }
                    if (activeAS.equals(JOIN_ARRAY)) {

                    }
                    if (activeAS.equals(IROTAT)) {

                    }
                    if (activeAS.equals(SOLVENT_POINTERS)) {

                    }
                    if (activeAS.equals(ATOMS_PER_MOLECULE)) {

                    }
                    if (activeAS.equals(BOX_DIMENSIONS)) {

                    }
                    if (activeAS.equals(CAP_INFO)) {

                    }
                    if (activeAS.equals(CAP_INFO2)) {

                    }
                    if (activeAS.equals(RADIUS_SET)) {

                    }
                    if (activeAS.equals(RADII)) {

                    }
                    if (activeAS.equals(IPOL)) {

                    }
                    if (activeAS.equals(POLARIZABILITY)) {

                    }
                } else {
                    throw new IOException("POINTERS section in prmtop is missing!");
                }
            }
        }

    }

    private void generateEmptyAtoms(List<Integer> pointers, Section topSection) {
        if (topSection.getAtoms().size() != pointers.get(0)) {
            for (Integer i = 0; i < pointers.get(0); i++) {
                topSection.getAtoms().add(new Atom());
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
        return list.stream().map(String::trim).collect(Collectors.toList());
    }

    private List<BigDecimal> readLineB(String line) {
        line = line.replaceAll("\\s+", " ");
        return Lists.newArrayList(Splitter.on(" ").split(line)).stream().map(BigDecimal::new).collect(Collectors.toList());
    }

    private List<Integer> readLineI(String line) {
        line = line.replaceAll("\\s+", " ");
        return Lists.newArrayList(Splitter.on(" ").split(line)).stream().map(Integer::parseInt).collect(Collectors.toList());
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
