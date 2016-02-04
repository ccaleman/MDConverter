package org.mdconverter.gromacstr.fileparser;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.topologystructure.SectionType;
import org.mdconverter.api.topologystructure.model.Section;
import org.mdconverter.api.topologystructure.model.TopologyStructure;
import org.mdconverter.api.topologystructure.model.api.*;
import org.mdconverter.api.topologystructure.model.impl.*;
import org.mdconverter.api.topologystructure.model.impl.System;
import org.mdconverter.gromacstr.main.GromacsTReader;
import org.mdconverter.gromacstr.utils.IncludeHandler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by miso on 07.12.2015.
 */
@Singleton
public class InputParser {

    private static final Pattern COMMENT_PATTERN = Pattern.compile("^;.*$");

    private static final Pattern INCLUDE_PATTERN = Pattern.compile("^[ \t]*#include \"[\\w\\d\\./]*\"(;.*)?$");

    private static final Pattern LOGICAL_PATTERN = Pattern.compile("#((ifdef)|(ifndef)|(else)|(endif)|(define)|(include))+.*$");

    private static final Pattern DEFAULTS_PATTERN_1 = Pattern.compile("^\\[ defaults \\]$");
    private static final Pattern DEFAULTS_PATTERN_2 = Pattern.compile("^[ \\t]*([\\d]+[ \\t]+){2}((no)|(yes))[ \\t]*([\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){2}[ \\t]*(;.*)?$");

    private static final Pattern ATOMS_PATTERN_1 = Pattern.compile("^\\[ atoms \\]$");
    private static final Pattern ATOMS_PATTERN_2 = Pattern.compile("^[ \\t]*[\\d]+[ \\t]+[\\w\\d*-+!]+[ \\t]+[\\d]+[ \\t]+[\\w]+[ \\t]+[\\w\\d+-]+[ \\t]+[\\d]+[ \\t]+([\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){2}([ \\t]*[\\w\\d*]+[ \\t]+([\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){2})?[ \\t]*(;.*)?$");

    private static final Pattern ATOMTYPES_PATTERN_1 = Pattern.compile("^\\[ atomtypes \\]$");
    private static final Pattern ATOMTYPES_PATTERN_2 = Pattern.compile("^[ \\t]*[\\w\\d+*=!]+([ \\t]+[\\d\\w*!+-=]+)+([ \\t]+[\\d-]+[.\\d]*){2}[ \\t]+[\\w]+[ \\t]+([\\d]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){2}[ \\t]*(;.*)?$");

    private static final Pattern BONDS_PATTERN_1 = Pattern.compile("^\\[ bonds \\]$");
    private static final Pattern BONDTYPES_PATTERN_1 = Pattern.compile("^\\[ bondtypes \\]$");
    private static final Pattern BONDS_PATTERN_2 = Pattern.compile("^((([ \\t]*[\\d]+){2,3})|(([ \\t]*[-\\w\\d*]+){2,3})(((([ \\t]+[\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?){2,4})?)|(([ \\t]*[\\w\\d]+){2})))[ \\t]*(;.*)?$");

    private static final Pattern PAIRS_PATTERN_1 = Pattern.compile("^\\[ pairs \\]$");
    private static final Pattern PAIRSNB_PATTERN_1 = Pattern.compile("^\\[ pairs_nb \\]$");
    private static final Pattern PAIRTYPES_PATTERN_1 = Pattern.compile("^\\[ pairtypes \\]$");
    private static final Pattern PAIRS_PATTERN_2 = Pattern.compile("^([ \\t]*[\\w\\d*]+){3}(([ \\t]*[\\d-]+[.\\d]*([Ee][+-][\\d]{2,})?){2,5})?[ \\t]*(;.*)?$");

    private static final Pattern CONSTRAINTS_PATTERN_1 = Pattern.compile("^\\[ constraints \\]$");
    private static final Pattern CONSTRAINTTYPES_PATTERN_1 = Pattern.compile("^\\[ constrainttypes \\]$");
    private static final Pattern CONSTRAINTS_PATTERN_2 = Pattern.compile("^[ \\t]*([\\w\\d*]+[ \\t]+){2}[\\d][ \\t]+([\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){2}[ \\t]*(;.*)?$");

    private static final Pattern ANGLES_PATTERN_1 = Pattern.compile("^\\[ angles \\]$");
    private static final Pattern ANGLETYPES_PATTERN_1 = Pattern.compile("^\\[ angletypes \\]$");
    private static final Pattern ANGLES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\w\\d*]+){4}(([ \\t]+[-+\\d]+[.\\d]*([Ee][+-][\\d]{2,})?){2,6})?[ \\t]*(;.*)?$");

    private static final Pattern DIHEDRALS_PATTERN_1 = Pattern.compile("^\\[ dihedrals \\]$");
    private static final Pattern DIHEDRALTYPES_PATTERN_1 = Pattern.compile("^\\[ dihedraltypes \\]$");
    private static final Pattern DIHEDRALS_PATTERN_2 = Pattern.compile("^([ \\t]*[\\w\\d*]+){5}(([ \\t]*[-+\\d]+[.\\d]*([Ee][+-][\\d]{2,})?){0,6})?[ \\t]*(;.*)?$");

    private static final Pattern MOLECULES_PATTERN_1 = Pattern.compile("^\\[ molecules \\]$");
    private static final Pattern MOLECULETYPES_PATTERN_1 = Pattern.compile("^\\[ moleculetype \\]$");
    private static final Pattern MOLECULES_PATTERN_2 = Pattern.compile("^[ \\t]*[\\w\\d]+[ \\t]+[\\d]+[ \\t]*(;.*)?$");

    private static final Pattern IMPLICIT_GENBORN_PARAMS_PATTERN_1 = Pattern.compile("^\\[ implicit_genborn_params \\]$");
    private static final Pattern IMPLICIT_GENBORN_PARAMS_PATTERN_2 = Pattern.compile("^[ \\t]*[\\w\\d*]+[ \\t]+([\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){5}[ \\t]*(;.*)?$");

    private static final Pattern SETTLES_PATTERN_1 = Pattern.compile("^\\[ settles \\]$");
    private static final Pattern SETTLES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\d]+){2}([ \\t]+[\\d-]+[.\\d]*([Ee][+-][\\d]{2,})?){2}[ \\t]*(;.*)?$");

    private static final Pattern SYSTEM_PATTERN_1 = Pattern.compile("^\\[ system \\]$");
    private static final Pattern SYSTEM_PATTERN_2 = Pattern.compile("^[ \\t]*[\\w\\d ]+[ \\t]*(;.*)?$");

    private static final Pattern EXCLUSIONS_PATTERN_1 = Pattern.compile("^\\[ exclusions \\]$");
    private static final Pattern EXCLUSIONS_PATTERN_2 = Pattern.compile("^([ \\t]*[\\d]+){2,}[ \\t]*(;.*)?$");

    private static final Pattern POSRES_PATTERN_1 = Pattern.compile("^\\[ position_restraints \\]$");
    private static final Pattern POSRES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){5}[ \\t]*(;.*)?$");

    private static final Pattern DISTANCERES_PATTERN_1 = Pattern.compile("^\\[ distance_restraints \\]$");
    private static final Pattern DISTANCERES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\w\\d*]+){5}([ \\t]+[\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){4}[ \\t]*(;.*)?$");

    private static final Pattern NONBONDPARAM_PATTERN_1 = Pattern.compile("^\\[ nonbond_params \\]$");
    private static final Pattern NONBONDPARAM_PATTERN_2 = Pattern.compile("^([ \\t]*[\\w\\d*]+){3}([ \\t]+[\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){2,3}[ \\t]*(;.*)?$");

    private static final Pattern DIHEDRALSRES_PATTERN_1 = Pattern.compile("^\\[ dihedral_restraints \\]$");
    private static final Pattern DIHEDRALSRES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\w\\d*]+){5}([ \\t]+[\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){5}[ \\t]*(;.*)?$");

    private static final Pattern ORIENTATIONRES_PATTERN_1 = Pattern.compile("^\\[ orientation_restraints \\]$");
    private static final Pattern ORIENTATIONRES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\w\\d*]+){5}([ \\t]+[\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){4}[ \\t]*(;.*)?$");

    private static final Pattern ANGLERES_PATTERN_1 = Pattern.compile("^\\[ angle_restraints \\]$");
    private static final Pattern ANGLERESZ_PATTERN_1 = Pattern.compile("^\\[ angle_restraints_z \\]$");
    private static final Pattern ANGLERES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\w\\d*]+){3,5}([ \\t]+[\\d-]+[.\\d]*([Ee][\\+-][\\d]{2,})?[ \\t]*){3}[ \\t]*(;.*)?$");

    private static final String define = "#define";
    private static final String ifdef = "#ifdef";
    private static final String ifndef = "#ifndef";
    private static final String elsse = "#else";
    private static final String endif = "#endif";

    private TopologyStructure structure;
    private ConsoleWriter cw;
    private Map<String, String> args = Maps.newHashMap();
    private Map<String, String> defines = Maps.newHashMap();

    @Inject
    protected InputParser(ConsoleWriter cw) {
        this.cw = cw;
    }

    public void parseInput(byte[] input, Path posres, boolean header, String previousPath, Map<String, String> arguments) throws IOException, URISyntaxException, NumberFormatException {
        //TODO: exclude unnecessary data
        if (input == null) return;
        if (arguments != null) {
            args = arguments;
        }
        boolean defaults = false;
        boolean pairs = false;
        boolean pairNB = false;
        boolean pairTypes = false;
        boolean atoms = false;
        boolean atomTypes = false;
        boolean bonds = false;
        boolean bondTypes = false;
        boolean constraints = false;
        boolean constraintTypes = false;
        boolean angles = false;
        boolean angleTypes = false;
        boolean dihedrals = false;
        boolean dihedralTypes = false;
        boolean molecules = false;
        boolean moleculeTypes = false;
        boolean genbornParams = false;
        boolean settles = false;
        boolean system = false;
        boolean exclusions = false;
        boolean positionrestraints = false;
        boolean distanceRes = false;
        boolean nonbondParams = false;
        boolean dihedralsRes = false;
        boolean orientationRes = false;
        boolean angleRes = false;
        boolean skipIfPath = false;
        boolean skipElsePath = true;
        boolean elseReached = false;

        Section actualSection = null;
        String line;
        InputStream stream = new ByteArrayInputStream(input);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            line = replaceDefineCalls(line);
            if (LOGICAL_PATTERN.matcher(line).matches()) {
                if (line.contains("HEAVY_H")) {
                    if (line.contains(ifdef)) {
                        if (args.get("heavyW").equals("false")) {
                            skipIfPath = true;
                            skipElsePath = false;
                        }
                    } else if (line.contains(ifndef)) {
                        if (args.get("heavyW").equals("true")) {
                            skipIfPath = true;
                            skipElsePath = false;
                        }
                    }
                } else if (line.contains("FLEXIBLE")) {
                    if (line.contains(ifdef)) {
                        if (args.get("flex").equals("false")) {
                            skipIfPath = true;
                            skipElsePath = false;
                        }
                    } else if (line.contains(ifndef)) {
                        if (args.get("flex").equals("true")) {
                            skipIfPath = true;
                            skipElsePath = false;
                        }
                    }
                } else if (line.contains("POSRES_WATER")) {
                    if (line.contains(ifdef)) {
                        if (args.get("posreswat").equals("false")) {
                            skipIfPath = true;
                            skipElsePath = false;
                        }
                    } else if (line.contains(ifndef)) {
                        if (args.get("posreswat").equals("true")) {
                            skipIfPath = true;
                            skipElsePath = false;
                        }
                    }
                } else if (line.contains("define")) {
                    //List<String> strings = reworkLineRemovePrefix(line, define);
                    //defines.put(strings.get(0), Joiner.on(" ").join(strings.subList(1, strings.size())));
                } else if (line.contains(elsse)) {
                    elseReached = true;
                    if (skipIfPath) skipElsePath = false;
                    skipIfPath = false;
                } else if (line.contains(endif)) {
                    skipIfPath = false;
                    skipElsePath = true;
                    elseReached = false;
                }
                if (INCLUDE_PATTERN.matcher(line).matches()) {
                    String file = line.split(" ")[1];
                    file = file.substring(1, file.length() - 1);
                    if (posres != null && posres.endsWith(file)) {
                        parseInput(Files.readAllBytes(posres), null, false, previousPath != null ? previousPath : file.split("/")[0], null);
                        continue;
                    }
                    if (previousPath != null) {
                        file = previousPath + "/" + file;
                    }
                    byte[] byName = IncludeHandler.getFileByName(GromacsTReader.class, file);
                    if (byName == null) {
                        throw new RuntimeException(String.format("File: %s could not be found!", file));
                    }
                    parseInput(byName, null, false, previousPath != null ? previousPath : file.split("/")[0], null);
                }
                continue;
            }
            if (skipIfPath || (skipElsePath && elseReached)) {
                continue;
            }
            if (COMMENT_PATTERN.matcher(line).matches() && header) {
                if (line.length() > 0) {
                    addHeader(line);
                }
            } else {
                header = false;
            }
            if (DEFAULTS_PATTERN_1.matcher(line).matches() || defaults) {
                defaults = true;
                if (DEFAULTS_PATTERN_2.matcher(line).matches()) {
                    addDefault(line);
                } else if (isDataRow(line) && !DEFAULTS_PATTERN_1.matcher(line).matches()) {
                    defaults = false;
                }
            }
            if (PAIRS_PATTERN_1.matcher(line).matches() || pairs) {
                pairs = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for pairs found.");
                    }
                }
                if (PAIRS_PATTERN_2.matcher(line).matches()) {
                    addPair(line, actualSection);
                } else if (isDataRow(line) && !PAIRS_PATTERN_1.matcher(line).matches()) {
                    pairs = false;
                }
            }
            if (PAIRSNB_PATTERN_1.matcher(line).matches() || pairNB) {
                pairNB = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for nonbonded pairs found.");
                    }
                }
                if (PAIRS_PATTERN_2.matcher(line).matches()) {
                    addPairNB(line, actualSection);
                } else if (isDataRow(line) && !PAIRSNB_PATTERN_1.matcher(line).matches()) {
                    pairNB = false;
                }
            }
            if (PAIRTYPES_PATTERN_1.matcher(line).matches() || pairTypes) {
                pairTypes = true;
                if (PAIRS_PATTERN_2.matcher(line).matches()) {
                    addPairType(line);
                } else if (isDataRow(line) && !PAIRTYPES_PATTERN_1.matcher(line).matches()) {
                    pairTypes = false;
                }
            }
            if (ATOMS_PATTERN_1.matcher(line).matches() || atoms) {
                atoms = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for atoms found.");
                    }
                }
                if (ATOMS_PATTERN_2.matcher(line).matches()) {
                    addAtom(line, actualSection);
                } else if (isDataRow(line) && !ATOMS_PATTERN_1.matcher(line).matches()) {
                    atoms = false;
                }
            }
            if (ATOMTYPES_PATTERN_1.matcher(line).matches() || atomTypes) {
                atomTypes = true;
                if (ATOMTYPES_PATTERN_2.matcher(line).matches()) {
                    addAtomType(line);
                } else if (isDataRow(line) && !ATOMTYPES_PATTERN_1.matcher(line).matches()) {
                    atomTypes = false;
                }
            }
            if (BONDS_PATTERN_1.matcher(line).matches() || bonds) {
                bonds = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for bonds found.");
                    }
                }
                if (BONDS_PATTERN_2.matcher(line).matches()) {
                    addBond(line, actualSection);
                } else if (isDataRow(line) && !BONDS_PATTERN_1.matcher(line).matches()) {
                    bonds = false;
                }
            }
            if (BONDTYPES_PATTERN_1.matcher(line).matches() || bondTypes) {
                bondTypes = true;
                if (BONDS_PATTERN_2.matcher(line).matches()) {
                    addBondType(line);
                } else if (isDataRow(line) && !BONDTYPES_PATTERN_1.matcher(line).matches()) {
                    bondTypes = false;
                }
            }
            if (CONSTRAINTS_PATTERN_1.matcher(line).matches() || constraints) {
                constraints = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for constraints found.");
                    }
                }
                if (CONSTRAINTS_PATTERN_2.matcher(line).matches()) {
                    addConstraint(line, actualSection);
                } else if (isDataRow(line) && !CONSTRAINTTYPES_PATTERN_1.matcher(line).matches()) {
                    constraints = false;
                }
            }
            if (CONSTRAINTTYPES_PATTERN_1.matcher(line).matches() || constraintTypes) {
                constraintTypes = true;
                if (CONSTRAINTS_PATTERN_2.matcher(line).matches()) {
                    addConstraintType(line);
                } else if (isDataRow(line) && !CONSTRAINTTYPES_PATTERN_1.matcher(line).matches()) {
                    constraintTypes = false;
                }
            }
            if (ANGLES_PATTERN_1.matcher(line).matches() || angles) {
                angles = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for angles found.");
                    }
                }
                if (ANGLES_PATTERN_2.matcher(line).matches()) {
                    addAngle(line, actualSection);
                } else if (isDataRow(line) && !ANGLES_PATTERN_1.matcher(line).matches()) {
                    angles = false;
                }
            }
            if (ANGLETYPES_PATTERN_1.matcher(line).matches() || angleTypes) {
                angleTypes = true;
                if (ANGLES_PATTERN_2.matcher(line).matches()) {
                    addAngleType(line);
                } else if (isDataRow(line) && !ANGLETYPES_PATTERN_1.matcher(line).matches()) {
                    angleTypes = false;
                }
            }
            if (DIHEDRALS_PATTERN_1.matcher(line).matches() || dihedrals) {
                dihedrals = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for dihedrals found.");
                    }
                }
                if (DIHEDRALS_PATTERN_2.matcher(line).matches()) {
                    addDihedral(line, actualSection);
                } else if (isDataRow(line) && !DIHEDRALS_PATTERN_1.matcher(line).matches()) {
                    dihedrals = false;
                }
            }
            if (DIHEDRALTYPES_PATTERN_1.matcher(line).matches() || dihedralTypes) {
                dihedralTypes = true;
                if (DIHEDRALS_PATTERN_2.matcher(line).matches()) {
                    addDihedralType(line);
                } else if (isDataRow(line) && !DIHEDRALTYPES_PATTERN_1.matcher(line).matches()) {
                    dihedralTypes = false;
                }
            }
            if (MOLECULES_PATTERN_1.matcher(line).matches() || molecules) {
                molecules = true;
                if (MOLECULES_PATTERN_2.matcher(line).matches() && structure.getMolecule() == null) {
                    List<String> split = reworkLine(line);
                    structure.setMolecule(new MoleculeImpl(split.get(0), Integer.parseInt(split.get(1))));
                } else if (isDataRow(line) && !MOLECULES_PATTERN_1.matcher(line).matches()) {
                    molecules = false;
                }
            }
            if (MOLECULETYPES_PATTERN_1.matcher(line).matches() || moleculeTypes) {
                if (!moleculeTypes) {
                    actualSection = null;
                }
                moleculeTypes = true;
                if (MOLECULES_PATTERN_2.matcher(line).matches() && actualSection == null) {
                    List<String> split = reworkLine(line);
                    Molecule moleculeType = new MoleculeImpl(split.get(0), Integer.parseInt(split.get(1)));
                    if (previousPath == null) {
                        actualSection = new Section(SectionType.STRUCTUREDATA);
                    } else {
                        actualSection = new Section(SectionType.FORCEFIELD);
                    }
                    actualSection.setMoleculeType(moleculeType);
                    structure.getSections().add(actualSection);
                } else if (isDataRow(line) && !MOLECULETYPES_PATTERN_1.matcher(line).matches()) {
                    moleculeTypes = false;
                }
            }
            if (IMPLICIT_GENBORN_PARAMS_PATTERN_1.matcher(line).matches() || genbornParams) {
                genbornParams = true;
                if (IMPLICIT_GENBORN_PARAMS_PATTERN_2.matcher(line).matches()) {
                    addGenbornParam(line);
                } else if (isDataRow(line) && !IMPLICIT_GENBORN_PARAMS_PATTERN_1.matcher(line).matches()) {
                    genbornParams = false;
                }
            }
            if (SETTLES_PATTERN_1.matcher(line).matches() || settles) {
                settles = true;
                if (SETTLES_PATTERN_2.matcher(line).matches()) {
                    addSettle(line, actualSection);
                } else if (isDataRow(line) && !SETTLES_PATTERN_1.matcher(line).matches()) {
                    settles = false;
                }
            }
            if (SYSTEM_PATTERN_1.matcher(line).matches() || system) {
                system = true;
                if (SYSTEM_PATTERN_2.matcher(line).matches() && structure.getSystem() == null) {
                    structure.setSystem(new System(line));
                } else if (isDataRow(line) && !SYSTEM_PATTERN_1.matcher(line).matches()) {
                    system = false;
                }
            }
            if (EXCLUSIONS_PATTERN_1.matcher(line).matches() || exclusions) {
                exclusions = true;
                if (actualSection == null && EXCLUSIONS_PATTERN_2.matcher(line).matches()) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.FORCEFIELD);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for exclusions found.");
                    }
                }
                if (EXCLUSIONS_PATTERN_2.matcher(line).matches()) {
                    addExclusion(line, actualSection);
                } else if (isDataRow(line) && !EXCLUSIONS_PATTERN_1.matcher(line).matches()) {
                    exclusions = false;
                }
            }
            if (POSRES_PATTERN_1.matcher(line).matches() || positionrestraints) {
                positionrestraints = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for position restraints found.");
                    }
                }
                if (POSRES_PATTERN_2.matcher(line).matches()) {
                    addPosres(line, actualSection);
                } else if (isDataRow(line) && !POSRES_PATTERN_1.matcher(line).matches()) {
                    positionrestraints = false;
                }
            }
            if (DISTANCERES_PATTERN_1.matcher(line).matches() || distanceRes) {
                distanceRes = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for distance restraints found.");
                    }
                }
                if (DISTANCERES_PATTERN_2.matcher(line).matches()) {
                    addDistanceRes(line, actualSection);
                } else if (isDataRow(line) && !DISTANCERES_PATTERN_1.matcher(line).matches()) {
                    distanceRes = false;
                }
            }
            if (NONBONDPARAM_PATTERN_1.matcher(line).matches() || nonbondParams) {
                nonbondParams = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.STRUCTUREDATA);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for non bond params found.");
                    }
                }
                if (NONBONDPARAM_PATTERN_2.matcher(line).matches()) {
                    addNonbondParams(line);
                } else if (isDataRow(line) && !NONBONDPARAM_PATTERN_1.matcher(line).matches()) {
                    nonbondParams = false;
                }
            }
            if (DIHEDRALSRES_PATTERN_1.matcher(line).matches() || dihedralsRes) {
                dihedralsRes = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.FORCEFIELD);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for dihedral restraints found.");
                    }
                }
                if (DIHEDRALSRES_PATTERN_2.matcher(line).matches()) {
                    addDihedralRes(line, actualSection);
                } else if (isDataRow(line) && !DIHEDRALSRES_PATTERN_1.matcher(line).matches()) {
                    dihedralsRes = false;
                }
            }
            if (ORIENTATIONRES_PATTERN_1.matcher(line).matches() || orientationRes) {
                orientationRes = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.FORCEFIELD);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for orientation restraints found.");
                    }
                }
                if (ORIENTATIONRES_PATTERN_2.matcher(line).matches()) {
                    addOrientationRes(line, actualSection);
                } else if (isDataRow(line) && !ORIENTATIONRES_PATTERN_1.matcher(line).matches()) {
                    orientationRes = false;
                }
            }
            if (ANGLERES_PATTERN_1.matcher(line).matches() || ANGLERESZ_PATTERN_1.matcher(line).matches() || angleRes) {
                angleRes = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = setSection(sections, SectionType.FORCEFIELD);
                    }
                    if (actualSection == null) {
                        throw new RuntimeException("no valid Section for angle restraints found.");
                    }
                }
                if (ANGLERES_PATTERN_2.matcher(line).matches()) {
                    addAngleRes(line, actualSection);
                } else if (isDataRow(line) &&
                        (!ANGLERES_PATTERN_1.matcher(line).matches() || !ANGLERESZ_PATTERN_1.matcher(line).matches())) {
                    angleRes = false;
                }
            }
        }
        if (arguments != null) {
            removeUnnecessaryData();
        }
    }

    private void removeUnnecessaryData() {
        Set<AtomType> clear = Sets.newHashSet();
        List<Atom> atoms = Lists.newArrayList();
        List<AtomType> atomTypes = structure.getAtomTypes();

        for (Section section : structure.getSections()) {
            if (section.getSectionType().equals(SectionType.STRUCTUREDATA)) {
                atoms.addAll(section.getAtoms());
            }
        }
        for (AtomType atomType : atomTypes) {
            AtomType delete = atomType;
            for (Atom atom : atoms) {
                if (atomType.getName().contains(atom.getType())) {
                    delete = null;
                }
            }
            if (delete != null) {
                clear.add(delete);
            }
        }
        clear.forEach(atomTypes::remove);
    }

    private String replaceDefineCalls(String line) {
        Set<String> strings = defines.keySet();
        for (String s : strings) {
            if (line.contains(s)) {
                line = line.replace(s, defines.get(s));
            }
        }
        return line;
    }

    private void addPair(String line, Section actualSection) {
        if (actualSection != null) {
            List<Pair> pairs = actualSection.getPairs();
            List<String> split = reworkLine(line);
            PairImpl p = new PairImpl();
            int length = split.size();
            if (length >= 3) {
                p.setAi(split.get(0));
                p.setAj(split.get(1));
                p.setFuncType(Integer.parseInt(split.get(2)));
            }
            if (length >= 5) {
                p.setC1(new BigDecimal(split.get(3)));
                p.setC2(new BigDecimal(split.get(4)));
            }
            if (length >= 7) {
                p.setC3(new BigDecimal(split.get(5)));
                p.setC4(new BigDecimal(split.get(6)));
            }
            if (length == 8) {
                p.setC5(new BigDecimal(split.get(7)));
            }
            if (length < 3 || length > 8) {
                cw.printErrorln(String.format("some PAIRS values are lost! --> %s", line));
            } else {
                pairs.add(p);
            }
        }
    }

    private void addPairNB(String line, Section actualSection) {
        if (actualSection != null) {
            List<PairNB> pairsNB = actualSection.getPairsNB();
            List<String> split = reworkLine(line);
            if (split.size() == 7) {
                PairNB p = new PairNB();
                p.setAi(split.get(0));
                p.setAj(split.get(1));
                p.setFuncType(Integer.parseInt(split.get(2)));
                p.setC1(new BigDecimal(split.get(3)));
                p.setC2(new BigDecimal(split.get(4)));
                p.setC3(new BigDecimal(split.get(5)));
                p.setC4(new BigDecimal(split.get(6)));
                pairsNB.add(p);
            } else {
                cw.printErrorln(String.format("some PAIRS values are lost! --> %s", line));
            }
        }
    }

    private void addPairType(String line) {
        List<String> split = reworkLine(line);
        List<Pair> pairTypes = structure.getPairTypes();
        PairImpl pt = new PairImpl();
        int length = split.size();
        if (length >= 3) {
            pt.setAi(split.get(0));
            pt.setAj(split.get(1));
            pt.setFuncType(Integer.parseInt(split.get(2)));
        }
        if (length >= 5) {
            pt.setC1(new BigDecimal(split.get(3)));
            pt.setC2(new BigDecimal(split.get(4)));
        }
        if (length >= 7) {
            pt.setC3(new BigDecimal(split.get(5)));
            pt.setC4(new BigDecimal(split.get(6)));
        }
        if (length == 8) {
            pt.setC5(new BigDecimal(split.get(7)));
        }
        if (length < 3 || length > 8) {
            cw.printErrorln(String.format("some PAIRTYPES values are lost! --> %s", line));
        } else {
            pairTypes.add(pt);
        }
    }

    private void addDefault(String line) {
        List<String> split = reworkLine(line);
        if (split.size() == 5) {
            DefaultImpl def = new DefaultImpl();
            def.setNboudnFT(Integer.parseInt(split.get(0)));
            def.setCombRule(Integer.parseInt(split.get(1)));
            def.setGenPair(split.get(2).equals("yes"));
            def.setC1(new BigDecimal(split.get(3)));
            def.setC2(new BigDecimal(split.get(4)));
            structure.setDef(def);
        } else {
            cw.printErrorln(String.format("some DEFAULTS values are lost! --> %s", line));
        }
    }

    private void addHeader(String line) {
        if (line.length() >= 3) {
            List<String> headers = structure.getHeaderComments();
            headers.add(line.substring(2, line.length()));
        } else {
            cw.printErrorln(String.format("some HEADERS values are lost! --> %s", line));
        }
    }

    private void addAtom(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            List<Atom> atoms = actualSection.getAtoms();
            AtomImpl a = new AtomImpl();
            int length = split.size();
            if (length >= 7) {
                a.setNr(Integer.parseInt(split.get(0)));
                a.setType(split.get(1));
                a.setResNr(Integer.parseInt(split.get(2)));
                a.setResName(split.get(3));
                a.setAtomName(split.get(4));
                a.setChargeGroupNr(Integer.parseInt(split.get(5)));
                a.setC1(new BigDecimal(split.get(6)));
            }
            if (length >= 8) {
                a.setC2(new BigDecimal(split.get(7)));
            }
            if (length == 11) {
                a.setTypeB(split.get(8));
                a.setC3(new BigDecimal(split.get(9)));
                a.setC4(new BigDecimal(split.get(10)));
            }
            if (length < 7 || length > 11) {
                cw.printErrorln(String.format("some ATOMS values are lost! --> %s", line));
            } else {
                atoms.add(a);
            }
        }
    }

    private void addAtomType(String line) {
        List<String> split = reworkLine(line);
        if (split.size() == 8) {
            String s = split.get(0);
            String s1 = split.get(1);
            split.remove(0);
            split.remove(0);
            split.add(0, s + " " + s1);
        }
        if (split.size() == 7) {
            List<AtomType> atomTypes = structure.getAtomTypes();
            AtomTypeImpl at = new AtomTypeImpl();
            at.setName(split.get(0));
            at.setNum(split.get(1));
            at.setC1(new BigDecimal(split.get(2)));
            at.setC2(new BigDecimal(split.get(3)));
            at.setParticleType(split.get(4));
            at.setC3(new BigDecimal(split.get(5)));
            at.setC4(new BigDecimal(split.get(6)));
            atomTypes.add(at);
        } else {
            cw.printErrorln(String.format("some ATOMTYPES values are lost! --> %s", line));
        }
    }

    private void addBond(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            List<Bond> bonds = actualSection.getBonds();
            int length = split.size();
            BondImpl b = new BondImpl();
            if (length >= 3) {
                b.setAi(split.get(0));
                b.setAj(split.get(1));
                b.setFuncType(Integer.parseInt(split.get(2)));
            }
            if (length >= 5) {
                b.setC1(new BigDecimal(split.get(3)));
                b.setC2(new BigDecimal(split.get(4)));
            }
            if (length >= 6) {
                b.setC3(new BigDecimal(split.get(5)));
            }
            if (length >= 7) {
                b.setC4(new BigDecimal(split.get(6)));
            }
            if (length < 3 || length > 7) {
                cw.printErrorln(String.format("some BONDS values are lost! --> %s", line));
            } else {
                bonds.add(b);
            }
        }
    }

    private void addBondType(String line) {
        List<String> split = reworkLine(line);
        List<Bond> bondTypes = structure.getBondTypes();
        int length = split.size();
        BondImpl bt = new BondImpl();
        if (length >= 3) {
            bt.setAi(split.get(0));
            bt.setAj(split.get(1));
            bt.setFuncType(Integer.parseInt(split.get(2)));
        }
        if (length >= 5) {
            bt.setC1(new BigDecimal(split.get(3)));
            bt.setC2(new BigDecimal(split.get(4)));
        }
        if (length >= 6) {
            bt.setC3(new BigDecimal(split.get(5)));
        }
        if (length >= 7) {
            bt.setC4(new BigDecimal(split.get(6)));
        }
        if (length < 3 || length > 7) {
            cw.printErrorln(String.format("some BONDTYPES values are lost! --> %s", line));
        } else {
            bondTypes.add(bt);
        }
    }

    private void addConstraint(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            List<Constraint> constraints = actualSection.getConstraints();
            ConstraintImpl c = new ConstraintImpl();
            int length = split.size();
            if (length >= 4) {
                c.setAi(split.get(0));
                c.setAj(split.get(1));
                c.setFuncType(Integer.parseInt(split.get(2)));
                c.setC1(new BigDecimal(split.get(3)));
            }
            if (length == 5) {
                c.setC2(new BigDecimal(split.get(4)));
            }
            if (length < 4 || length > 5) {
                cw.printErrorln(String.format("some CONSTRAINTS values are lost! --> %s", line));
            } else {
                constraints.add(c);
            }
        }
    }

    private void addConstraintType(String line) {
        List<String> split = reworkLine(line);
        List<Constraint> constraintTypes = structure.getConstraintTypes();
        ConstraintImpl ct = new ConstraintImpl();
        int length = split.size();
        if (length >= 4) {
            ct.setAi(split.get(0));
            ct.setAj(split.get(1));
            ct.setFuncType(Integer.parseInt(split.get(2)));
            ct.setC1(new BigDecimal(split.get(3)));
        }
        if (length == 5) {
            ct.setC2(new BigDecimal(split.get(4)));
        }
        if (length < 4 || length > 5) {
            cw.printErrorln(String.format("some CONSTRAINTTYPES values are lost! --> %s", line));
        } else {
            constraintTypes.add(ct);
        }
    }

    private void addAngle(String line, Section actualSection) {
        if (actualSection != null) {
            List<Angle> angles = actualSection.getAngles();
            List<String> split = reworkLine(line);
            int length = split.size();
            AngleImpl a = new AngleImpl();
            if (length >= 4) {
                a.setAi(split.get(0));
                a.setAj(split.get(1));
                a.setAk(split.get(2));
                a.setFuncType(Integer.parseInt(split.get(3)));
            }
            if (length >= 6) {
                a.setC1(new BigDecimal(split.get(4)));
                a.setC2(new BigDecimal(split.get(5)));
            }
            if (length >= 7) {
                a.setC3(new BigDecimal(split.get(6)));
            }
            if (length >= 8) {
                a.setC4(new BigDecimal(split.get(7)));
            }
            if (length >= 10) {
                a.setC5(new BigDecimal(split.get(8)));
                a.setC6(new BigDecimal(split.get(9)));
            }
            if (length < 4 || length > 10) {
                cw.printErrorln(String.format("some ANGLES values are lost! --> %s", line));
            } else {
                angles.add(a);
            }
        }
    }

    private void addAngleType(String line) {
        List<Angle> angleTypes = structure.getAngleTypes();
        List<String> split = reworkLine(line);
        int length = split.size();
        AngleImpl a = new AngleImpl();
        if (length >= 6) {
            a.setAi(split.get(0));
            a.setAj(split.get(1));
            a.setAk(split.get(2));
            a.setFuncType(Integer.parseInt(split.get(3)));
            a.setC1(new BigDecimal(split.get(4)));
            a.setC2(new BigDecimal(split.get(5)));
        }
        if (length >= 7) {
            a.setC3(new BigDecimal(split.get(6)));
        }
        if (length >= 8) {
            a.setC4(new BigDecimal(split.get(7)));
        }
        if (length >= 10) {
            a.setC5(new BigDecimal(split.get(8)));
            a.setC6(new BigDecimal(split.get(9)));
        }
        if (length < 6 || length > 10) {
            cw.printErrorln(String.format("some ANGLETYPES values are lost! --> %s", line));
        } else {
            angleTypes.add(a);
        }
    }

    private void addDihedral(String line, Section actualSection) {
        if (actualSection != null) {
            List<Dihedral> dihedrals = actualSection.getDihedrals();
            List<String> split = reworkLine(line);
            DihedralImpl dh = new DihedralImpl();
            int length = split.size();
            if (length >= 5) {
                dh.setAi(split.get(0));
                dh.setAj(split.get(1));
                dh.setAk(split.get(2));
                dh.setAl(split.get(3));
                dh.setFuncType(Integer.parseInt(split.get(4)));
            }
            if (length >= 7) {
                dh.setC1(new BigDecimal(split.get(5)));
                dh.setC2(new BigDecimal(split.get(6)));
            }
            if (length >= 8) {
                dh.setC3(new BigDecimal(split.get(7)));
            }
            if (length >= 9) {
                dh.setC4(new BigDecimal(split.get(8)));
            }
            if (length >= 10) {
                dh.setC5(new BigDecimal(split.get(9)));
            }
            if (length == 11) {
                dh.setC6(new BigDecimal(split.get(10)));
            }
            if (length < 5 || length > 11) {
                cw.printErrorln(String.format("some DIHEDRALS values are lost! --> %s", line));
            } else {
                dihedrals.add(dh);
            }
        }
    }

    private void addDihedralType(String line) {
        List<Dihedral> dihedralTypes = structure.getDihedralTypes();
        List<String> split = reworkLine(line);
        DihedralImpl dt = new DihedralImpl();
        int length = split.size();
        if (length == 6) {
            dt.setAi(split.get(0));
            dt.setAj(split.get(1));
            dt.setFuncType(Integer.parseInt(split.get(2)));
            dt.setC1(new BigDecimal(split.get(3)));
            dt.setC2(new BigDecimal(split.get(4)));
            dt.setC3(new BigDecimal(split.get(5)));
        } else if (length >= 7) {
            dt.setAi(split.get(0));
            dt.setAj(split.get(1));
            dt.setAk(split.get(2));
            dt.setAl(split.get(3));
            dt.setFuncType(Integer.parseInt(split.get(4)));
            dt.setC1(new BigDecimal(split.get(5)));
            dt.setC2(new BigDecimal(split.get(6)));
        }
        if (length >= 8) {
            dt.setC3(new BigDecimal(split.get(7)));
        }
        if (length >= 9) {
            dt.setC4(new BigDecimal(split.get(8)));
        }
        if (length >= 10) {
            dt.setC5(new BigDecimal(split.get(9)));
        }
        if (length == 11) {
            dt.setC6(new BigDecimal(split.get(10)));
        }
        if (length < 6 || length > 11) {
            cw.printErrorln(String.format("some DIHEDRALTYPES values are lost! --> %s", line));
        } else {
            dihedralTypes.add(dt);
        }
    }

    private void addGenbornParam(String line) {
        List<String> split = reworkLine(line);
        if (split.size() == 6) {
            List<ImplicitGenbornParam> genbornParams = structure.getGenbornParams();
            ImplicitGenbornParamImpl gp = new ImplicitGenbornParamImpl();
            gp.setAtom(split.get(0));
            gp.setC1(new BigDecimal(split.get(1)));
            gp.setC2(new BigDecimal(split.get(2)));
            gp.setC3(new BigDecimal(split.get(3)));
            gp.setC4(new BigDecimal(split.get(4)));
            gp.setC5(new BigDecimal(split.get(5)));
            genbornParams.add(gp);
        } else {
            cw.printErrorln(String.format("some GENBORN values are lost! --> %s", line));
        }
    }

    private void addSettle(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            if (split.size() == 4) {
                List<Settle> settles = actualSection.getSettles();
                SettleImpl s = new SettleImpl();
                s.setAtom(split.get(0));
                s.setFuncType(Integer.parseInt(split.get(1)));
                s.setC1(new BigDecimal(split.get(2)));
                s.setC2(new BigDecimal(split.get(3)));
                settles.add(s);
            } else {
                cw.printErrorln(String.format("some SETTLES values are lost! --> %s", line));
            }
        }
    }

    private void addExclusion(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            if (split.size() >= 2) {
                List<Exclusion> exclusions = actualSection.getExclusions();
                Exclusion e = new Exclusion();
                e.setAtomIdx(Integer.parseInt(split.get(0)));
                List<Integer> bonds = e.getBonds();
                for (int i = 1; i < split.size(); i++) {
                    bonds.add(Integer.parseInt(split.get(i)));
                }
                exclusions.add(e);
            } else {
                cw.printErrorln(String.format("some EXCLUSIONS values are lost! --> %s", line));
            }
        }
    }

    private void addPosres(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            if (split.size() == 5) {
                List<PositionRestraint> posres = actualSection.getPositionRestraints();
                PositionRestraintImpl pr = new PositionRestraintImpl();
                pr.setAi(split.get(0));
                pr.setFuncType(Integer.parseInt(split.get(1)));
                pr.setC1(new BigDecimal(split.get(2)));
                pr.setC2(new BigDecimal(split.get(3)));
                pr.setC3(new BigDecimal(split.get(4)));
                posres.add(pr);
            } else {
                cw.printErrorln(String.format("some POSRES values are lost! --> %s", line));
            }
        }
    }

    private void addDistanceRes(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            if (split.size() == 9) {
                List<DistanceRestraint> distRes = actualSection.getDistanceRestraints();
                DistanceRestraintImpl res = new DistanceRestraintImpl();
                res.setAi(split.get(0));
                res.setAj(split.get(1));
                res.setFuncType(Integer.parseInt(split.get(2)));
                res.setType(split.get(3));
                res.setLabel(split.get(4));
                res.setC1(new BigDecimal(split.get(5)));
                res.setC2(new BigDecimal(split.get(6)));
                res.setC3(new BigDecimal(split.get(7)));
                res.setC4(new BigDecimal(split.get(8)));
                distRes.add(res);
            } else {
                cw.printErrorln(String.format("some DISTANCERES values are lost! --> %s", line));
            }
        }
    }

    private void addNonbondParams(String line) {
        List<String> split = reworkLine(line);
        List<NonBondParam> nonbondRes = structure.getNonBondParams();
        int length = split.size();
        NonBondParamImpl param = new NonBondParamImpl();
        if (length >= 5) {
            param.setAi(split.get(0));
            param.setAj(split.get(1));
            param.setFuncType(Integer.parseInt(split.get(2)));
            param.setC1(new BigDecimal(split.get(3)));
            param.setC2(new BigDecimal(split.get(4)));
        }
        if (length >= 6) {
            param.setC3(new BigDecimal(split.get(5)));
        }
        if (length < 5 || length > 6) {
            cw.printErrorln(String.format("some nonbond params values are lost! --> %s", line));
        } else {
            nonbondRes.add(param);
        }
    }

    private void addDihedralRes(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            List<DihedralRestraint> diheRes = actualSection.getDihedralRestraints();
            DihedralRestraintImpl res = new DihedralRestraintImpl();
            if (split.size() == 7) {
                res.setAi(split.get(0));
                res.setAj(split.get(1));
                res.setAk(split.get(2));
                res.setAl(split.get(3));
                res.setFuncType(Integer.parseInt(split.get(4)));
                res.setC1(new BigDecimal(split.get(5)));
                res.setC2(new BigDecimal(split.get(6)));
                diheRes.add(res);
            } else if (split.size() == 10) {
                res.setAi(split.get(0));
                res.setAj(split.get(1));
                res.setAk(split.get(2));
                res.setAl(split.get(3));
                res.setFuncType(Integer.parseInt(split.get(4)));
                res.setLabel(split.get(5));
                res.setC1(new BigDecimal(split.get(6)));
                res.setC2(new BigDecimal(split.get(7)));
                res.setC3(new BigDecimal(split.get(8)));
                res.setC4(new BigDecimal(split.get(9)));
                diheRes.add(res);
            } else {
                cw.printErrorln(String.format("some dihedral restraints values are lost! --> %s", line));
            }
        }
    }

    private void addOrientationRes(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            if (split.size() == 9) {
                List<OrientationRestraint> oriRes = actualSection.getOrientationRestraints();
                OrientationRestraintImpl res = new OrientationRestraintImpl();
                res.setAi(split.get(0));
                res.setAj(split.get(1));
                res.setFuncType(Integer.parseInt(split.get(2)));
                res.setExp(split.get(3));
                res.setLabel(split.get(3));
                res.setAlpha(split.get(3));
                res.setC1(new BigDecimal(split.get(3)));
                res.setC2(new BigDecimal(split.get(3)));
                res.setC3(new BigDecimal(split.get(3)));
                oriRes.add(res);
            } else {
                cw.printErrorln(String.format("some orientation restraints values are lost! --> %s", line));
            }
        }
    }

    private void addAngleRes(String line, Section actualSection) {
        if (actualSection != null) {
            List<String> split = reworkLine(line);
            List<AngleRestraintZImpl> angleRes = actualSection.getAngleRestraints();
            if (split.size() == 6) {
                AngleRestraintZImpl resZ = new AngleRestraintZImpl();
                resZ.setAi(split.get(0));
                resZ.setAj(split.get(1));
                resZ.setFuncType(Integer.parseInt(split.get(2)));
                resZ.setC1(new BigDecimal(split.get(3)));
                resZ.setC2(new BigDecimal(split.get(4)));
                resZ.setC3(new BigDecimal(split.get(5)));
                angleRes.add(resZ);
            } else if (split.size() == 8) {
                AngleRestraintImpl res = new AngleRestraintImpl();
                res.setAi(split.get(0));
                res.setAj(split.get(1));
                res.setAk(split.get(2));
                res.setAl(split.get(3));
                res.setFuncType(Integer.parseInt(split.get(4)));
                res.setC1(new BigDecimal(split.get(5)));
                res.setC2(new BigDecimal(split.get(6)));
                res.setC3(new BigDecimal(split.get(7)));
                angleRes.add(res);
            } else {
                cw.printErrorln(String.format("some angles restraints(z) values are lost! --> %s", line));
            }
        }
    }

    private Section setSection(List<Section> sections, SectionType structuredata) {
        Optional<Section> section = sections.stream().filter(s ->
                s.getSectionType().equals(structuredata)).findFirst();
        if (section.isPresent()) {
            return section.get();
        }
        return null;
    }

    private boolean isDataRow(String line) {
        if (LOGICAL_PATTERN.matcher(line).matches()) {
            cw.printInfoln(String.format("LOGICAL: %s", line));
        }
        return !COMMENT_PATTERN.matcher(line).matches() && !LOGICAL_PATTERN.matcher(line).matches() && !line.trim().isEmpty();
    }

    private List<String> reworkLine(String line) {
        line = line.replaceAll("\\s+", " ");
        String[] split = line.split(";");
        return Lists.newArrayList(split[0].split(" "));
    }

    private List<String> reworkLineRemovePrefix(String line, String prefix) {
        line = line.replace(prefix, "").trim();
        line = line.replaceAll("\\s+", " ");
        String[] split = line.split(";");
        return Lists.newArrayList(split[0].split(" "));
    }

    public TopologyStructure getStructure() {
        return structure;
    }

    public void setStructure(TopologyStructure structure) {
        this.structure = structure;
    }
}
