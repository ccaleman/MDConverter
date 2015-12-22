package org.mdconverter.fileparser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.mdconverter.api.consolewriter.ConsoleWriter;
import org.mdconverter.api.topologystructure.Section;
import org.mdconverter.api.topologystructure.TopologyStructure;
import org.mdconverter.api.topologystructure.model.*;
import org.mdconverter.api.topologystructure.model.System;
import org.mdconverter.main.GromacsTReader;
import org.mdconverter.utils.IncludeHandler;

import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by miso on 07.12.2015.
 */
@Singleton
public class InputParser {

    private static final Pattern COMMENT_PATTERN = Pattern.compile("^;.*$");
    private static final Pattern INCLUDE_PATTERN = Pattern.compile("^[ \t]*#include \"[\\w\\d\\./]*\"(;.*)?$");
    private static final Pattern DEFAULTS_PATTERN_1 = Pattern.compile("^\\[ defaults \\]$");
    private static final Pattern DEFAULTS_PATTERN_2 = Pattern.compile("^[ \\t]*([\\d]+[ \\t]+){2}((no)|(yes))[ \\t]*([\\d-]+[.\\d]*(e[\\+-][\\d]{2,})?[ \\t]*){2}([ \\t]*;.*)?$");
    private static final Pattern ATOMTYPES_PATTERN_1 = Pattern.compile("^\\[ atomtypes \\]$");
    private static final Pattern ATOMTYPES_PATTERN_2 = Pattern.compile("^[ \\t]*[\\w\\d*]+[ \\t]+[\\d]+([ \\t]+[\\d-]+[.\\d]*){2}[ \\t]+[\\w]+[ \\t]+([\\d]+[.\\d]*(e[\\+-][\\d]{2,})?[ \\t]*){2}([ \\t]*;.*)?$");
    private static final Pattern BONDTYPES_PATTERN_1 = Pattern.compile("^\\[ bondtypes \\]$");
    private static final Pattern BONDTYPES_PATTERN_2 = Pattern.compile("^[ \\t]*([\\w\\d*]+[ \\t]+){2}[\\d]{1,2}[ \\t]+([\\d-]+[.\\d]*(e[\\+-][\\d]{2,})?[ \\t]*){2}([ \\t]*;.*)?$");
    private static final Pattern CONSTRAINTTYPES_PATTERN_1 = Pattern.compile("^\\[ constrainttypes \\]$");
    private static final Pattern CONSTRAINTTYPES_PATTERN_2 = Pattern.compile("^[ \\t]*([\\w\\d*]+[ \\t]+){2}[\\d][ \\t]+([\\d-]+[.\\d]*(e[\\+-][\\d]{2,})?[ \\t]*){2}([ \\t]*;.*)?$");
    private static final Pattern ANGLETYPES_PATTERN_1 = Pattern.compile("^\\[ angletypes \\]$");
    private static final Pattern ANGLETYPES_PATTERN_2 = Pattern.compile("^[ \\t]*([\\w\\d*]+[ \\t]+){3}[\\d]+[ \\t]+([\\d-]+[.\\d]*(e[\\+-][\\d]{2,})?[ \\t]*){2}([ \\t]*;.*)?$");
    private static final Pattern DIHEDRALTYPES_PATTERN_1 = Pattern.compile("^\\[ dihedraltypes \\]$");
    private static final Pattern DIHEDRALTYPES_PATTERN_2 = Pattern.compile("^[ \\t]*([\\w\\d*]+[ \\t]+){4}[\\d]+[ \\t]+([\\d-]+[.\\d]*(e[\\+-][\\d]{2,})?[ \\t]*){2}[\\d]+([ \\t]*;.*)?$");
    private static final Pattern IMPLICIT_GENBORN_PARAMS_PATTERN_1 = Pattern.compile("^\\[ implicit_genborn_params \\]$");
    private static final Pattern IMPLICIT_GENBORN_PARAMS_PATTERN_2 = Pattern.compile("^[ \\t]*[\\w\\d*]+[ \\t]+([\\d-]+[.\\d]*(e[\\+-][\\d]{2,})?[ \\t]*){5}([ \\t]*;.*)?$");
    private static final Pattern SYSTEM_PATTERN_1 = Pattern.compile("^\\[ system \\]$");
    private static final Pattern SYSTEM_PATTERN_2 = Pattern.compile("^[ \\t]*[\\w\\d]+([ \\t]*;.*)?$");
    private static final Pattern MOLECULETYPES_PATTERN_1 = Pattern.compile("^\\[ moleculetype \\]$");
    private static final Pattern MOLECULETYPES_PATTERN_2 = Pattern.compile("^[ \\t]*[\\w\\d]+[ \\t]+[\\d]+([ \\t]*;.*)?$");
    private static final Pattern ATOMS_PATTERN_1 = Pattern.compile("^\\[ atoms \\]$");
    private static final Pattern ATOMS_PATTERN_2 = Pattern.compile("^[ \\t]*[\\d]+[ \\t]+[\\w\\d*]+[ \\t]+[\\d]+[ \\t]+[\\w]+[ \\t]+[\\w\\d+-]+[ \\t]+[\\d]+[ \\t]+([\\d-]+[.\\d]*(e[\\+-][\\d]{2,})?[ \\t]*){2}([ \\t]*[\\w\\d*]+[ \\t]+([\\d-]+[.\\d]*(e[\\+-][\\d]{2,})?[ \\t]*){2})?([ \\t]*;.*)?$");
    private static final Pattern BONDS_PATTERN_1 = Pattern.compile("^\\[ bonds \\]$");
    private static final Pattern BONDS_PATTERN_2 = Pattern.compile("^((([ \\t]*[\\d]+){2,3})|(([ \\t]+[-\\w\\d]*){2,3})(((([ \\t]+[\\d]+[.\\d]*(e[\\+-][\\d]{2,})){2,4})?)|(([ \\t]*[\\w\\d]*){2}))[ \\t]*)([ \\t]*;.*)?$");
    private static final Pattern PAIRS_PATTERN_1 = Pattern.compile("^\\[ pairs \\]$");
    private static final Pattern PAIRS_PATTERN_2 = Pattern.compile("^([ \\t]*[\\d]+){3}(([ \\t]+[\\d]+[.\\d]*(e[+-][\\d]{2,})?){2,4})?([ \\t]*;.*)?$");
    private static final Pattern ANGLES_PATTERN_1 = Pattern.compile("^\\[ angles \\]$");
    private static final Pattern ANGLES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\d]+){4}(([ \\t]+[\\d]+[.\\d]*(e[+-][\\d]{2,})?){2})?([ \\t]*;.*)?$");
    private static final Pattern DIHEDRALS_PATTERN_1 = Pattern.compile("^\\[ dihedrals \\]$");
    private static final Pattern DIHEDRALS_PATTERN_2 = Pattern.compile("^([ \\t]*[\\d]+){5}(([ \\t]+[\\d]+[.\\d]*(e[+-][\\d]{2,})?){2,4})?([ \\t]*;.*)?$");
    private static final Pattern POSRES_PATTERN_1 = Pattern.compile("^\\[ position_restraints \\]$");
    private static final Pattern POSRES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\d]+){5}([ \\t]*;.*)?$");
    private static final Pattern MOLECULES_PATTERN_1 = Pattern.compile("^\\[ molecules \\]$");
    private static final Pattern MOLECULES_PATTERN_2 = Pattern.compile("^[ \\t]*[\\w\\d]+[ \\t]+[\\d]+([ \\t]*;.*)?$");
    private static final Pattern SETTLES_PATTERN_1 = Pattern.compile("^\\[ settles \\]$");
    private static final Pattern SETTLES_PATTERN_2 = Pattern.compile("^([ \\t]*[\\d]+){2}([ \\t]+[\\d]+[.\\d]*(e[+-][\\d]{2,})?){2}([ \\t]*;.*)?$");
    private static final Pattern EXCLUSIONS_PATTERN_1 = Pattern.compile("^\\[ exclusions \\]$");
    private static final Pattern EXCLUSIONS_PATTERN_2 = Pattern.compile("^([ \\t]*[\\d]+){2,}([ \\t]*;.*)?$");

    private TopologyStructure structure;

    private ConsoleWriter cw;

    @Inject
    protected InputParser(ConsoleWriter cw) {
        this.structure = new TopologyStructure();
        this.cw = cw;
    }

    public void parseInput(byte[] input, Path posres, boolean header, String previousPath) throws IOException, URISyntaxException, NumberFormatException {
        if (input == null) return;
        String line;
        InputStream stream = new ByteArrayInputStream(input);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        boolean defaults = false;
        boolean atomTypes = false;
        boolean bondTypes = false;
        boolean constraintTypes = false;
        boolean angleTypes = false;
        boolean dihedralTypes = false;
        boolean genbornParams = false;
        boolean system = false;
        boolean moleculeTypes = false;
        boolean atoms = false;
        boolean bonds = false;
        boolean pairs = false;
        boolean angles = false;
        boolean dihedrals = false;
        boolean positionrestraints = false;
        boolean molecules = false;
        boolean settles = false;
        boolean exclusions = false;
        Section actualSection = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (COMMENT_PATTERN.matcher(line).matches() && header) {
                if (line.length() > 0) {
                    addHeader(line);
                }
            } else {
                header = false;
            }
            if (INCLUDE_PATTERN.matcher(line).matches()) {
                String file = line.split(" ")[1];
                file = file.substring(1, file.length() - 1);
                if (posres != null && posres.endsWith(file)) {
                    parseInput(Files.readAllBytes(posres), null, false, previousPath != null ? previousPath : file.split("/")[0]);
                    continue;
                }
                if (previousPath != null) {
                    file = previousPath + "/" + file;
                }
                byte[] byName = IncludeHandler.getFileByName(GromacsTReader.class, file);
                parseInput(byName, null, false, previousPath != null ? previousPath : file.split("/")[0]);
            }
            if (DEFAULTS_PATTERN_1.matcher(line).matches() || defaults) {
                defaults = true;
                if (DEFAULTS_PATTERN_2.matcher(line).matches()) {
                    addDefault(line);
                } else if (isDataRow(line) && !DEFAULTS_PATTERN_1.matcher(line).matches()) {
                    defaults = false;
                }
            }
            if (ATOMTYPES_PATTERN_1.matcher(line).matches() || atomTypes) {
                atomTypes = true;
                if (ATOMTYPES_PATTERN_2.matcher(line).matches()) {
                    addAtomTypes(line);
                } else if (isDataRow(line) && !ATOMTYPES_PATTERN_1.matcher(line).matches()) {
                    atomTypes = false;
                }
            }
            if (BONDTYPES_PATTERN_1.matcher(line).matches() || bondTypes) {
                bondTypes = true;
                if (BONDTYPES_PATTERN_2.matcher(line).matches()) {
                    addBondTypes(line);
                } else if (isDataRow(line) && !BONDTYPES_PATTERN_1.matcher(line).matches()) {
                    bondTypes = false;
                }
            }
            if (CONSTRAINTTYPES_PATTERN_1.matcher(line).matches() || constraintTypes) {
                constraintTypes = true;
                if (CONSTRAINTTYPES_PATTERN_2.matcher(line).matches()) {
                    addConstraintTypes(line);
                } else if (isDataRow(line) && !CONSTRAINTTYPES_PATTERN_1.matcher(line).matches()) {
                    constraintTypes = false;
                }
            }
            if (ANGLETYPES_PATTERN_1.matcher(line).matches() || angleTypes) {
                angleTypes = true;
                if (ANGLETYPES_PATTERN_2.matcher(line).matches()) {
                    addAngleTypes(line);
                } else if (isDataRow(line) && !ANGLETYPES_PATTERN_1.matcher(line).matches()) {
                    angleTypes = false;
                }
            }
            if (DIHEDRALTYPES_PATTERN_1.matcher(line).matches() || dihedralTypes) {
                dihedralTypes = true;
                if (DIHEDRALTYPES_PATTERN_2.matcher(line).matches()) {
                    addDihedralTypes(line);
                } else if (isDataRow(line) && !DIHEDRALTYPES_PATTERN_1.matcher(line).matches()) {
                    dihedralTypes = false;
                }
            }
            if (IMPLICIT_GENBORN_PARAMS_PATTERN_1.matcher(line).matches() || genbornParams) {
                genbornParams = true;
                if (IMPLICIT_GENBORN_PARAMS_PATTERN_2.matcher(line).matches()) {
                    addGenbornParams(line);
                } else if (isDataRow(line) && !IMPLICIT_GENBORN_PARAMS_PATTERN_1.matcher(line).matches()) {
                    genbornParams = false;
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
            if (MOLECULES_PATTERN_1.matcher(line).matches() || molecules) {
                molecules = true;
                if (MOLECULES_PATTERN_2.matcher(line).matches() && structure.getMolecule() == null) {
                    String[] split = reworkLine(line).split(" ");
                    structure.setMolecule(new Molecule(split[0], Integer.parseInt(split[1])));
                } else if (isDataRow(line) && !MOLECULES_PATTERN_1.matcher(line).matches()) {
                    molecules = false;
                }
            }
            if (MOLECULETYPES_PATTERN_1.matcher(line).matches() || moleculeTypes) {
                if (!moleculeTypes) {
                    actualSection = null;
                }
                moleculeTypes = true;
                if (MOLECULETYPES_PATTERN_2.matcher(line).matches() && actualSection == null) {
                    String[] split = reworkLine(line).split(" ");
                    MoleculeType moleculeType = new MoleculeType(split[0], Integer.parseInt(split[1]));
                    actualSection = new Section();
                    actualSection.setMoleculeType(moleculeType);
                    structure.getSections().add(actualSection);
                } else if (isDataRow(line) && !MOLECULETYPES_PATTERN_1.matcher(line).matches()) {
                    moleculeTypes = false;
                }
            }
            if (ATOMS_PATTERN_1.matcher(line).matches() || atoms) {
                atoms = true;
                if (ATOMS_PATTERN_2.matcher(line).matches()) {
                    addAtoms(line, actualSection);
                } else if (isDataRow(line) && !ATOMS_PATTERN_1.matcher(line).matches()) {
                    atoms = false;
                }
            }
            if (BONDS_PATTERN_1.matcher(line).matches() || bonds) {
                bonds = true;
                if (BONDS_PATTERN_2.matcher(line).matches()) {
                    addBonds(line, actualSection);
                } else if (isDataRow(line) && !BONDS_PATTERN_1.matcher(line).matches()) {
                    bonds = false;
                }
            }
            if (PAIRS_PATTERN_1.matcher(line).matches() || pairs) {
                pairs = true;
                if (PAIRS_PATTERN_2.matcher(line).matches()) {
                    addPairs(line, actualSection);
                } else if (isDataRow(line) && !PAIRS_PATTERN_1.matcher(line).matches()) {
                    pairs = false;
                }
            }
            if (ANGLES_PATTERN_1.matcher(line).matches() || angles) {
                angles = true;
                if (ANGLES_PATTERN_2.matcher(line).matches()) {
                    addAngles(line, actualSection);
                } else if (isDataRow(line) && !ANGLES_PATTERN_1.matcher(line).matches()) {
                    angles = false;
                }
            }
            if (DIHEDRALS_PATTERN_1.matcher(line).matches() || dihedrals) {
                dihedrals = true;
                if (DIHEDRALS_PATTERN_2.matcher(line).matches()) {
                    addDihedrals(line, actualSection);
                } else if (isDataRow(line) && !DIHEDRALS_PATTERN_1.matcher(line).matches()) {
                    dihedrals = false;
                }
            }
            if (POSRES_PATTERN_1.matcher(line).matches() || positionrestraints) {
                positionrestraints = true;
                if (actualSection == null) {
                    List<Section> sections = structure.getSections();
                    if (!sections.isEmpty()) {
                        actualSection = sections.get(0);
                    }
                }
                if (POSRES_PATTERN_2.matcher(line).matches()) {
                    addPosres(line, actualSection);
                } else if (isDataRow(line) && !POSRES_PATTERN_1.matcher(line).matches()) {
                    positionrestraints = false;
                }
            }
            if (SETTLES_PATTERN_1.matcher(line).matches() || settles) {
                settles = true;
                if (SETTLES_PATTERN_2.matcher(line).matches()) {
                    addSettles(line, actualSection);
                } else if (isDataRow(line) && !SETTLES_PATTERN_1.matcher(line).matches()) {
                    settles = false;
                }
            }
            if (EXCLUSIONS_PATTERN_1.matcher(line).matches() || exclusions) {
                exclusions = true;
                if (EXCLUSIONS_PATTERN_2.matcher(line).matches()) {
                    addExclusions(line, actualSection);
                } else if (isDataRow(line) && !EXCLUSIONS_PATTERN_1.matcher(line).matches()) {
                    exclusions = false;
                }
            }
        }
    }

    public TopologyStructure getStructure() {
        return structure;
    }

    //TODO: catch NumberFormatException

    private void addDefault(String line) {
        Default def = new Default();
        List<Default> defaults = structure.getDefaults();
        String[] split = reworkLine(line).split(" ");
        if (split.length == 5) {
            def.setNboudnFT(Integer.parseInt(split[0]));
            def.setCombRule(Integer.parseInt(split[1]));
            def.setGenPair(split[2].equals("yes"));
            def.setFudgeLJ(new BigDecimal(split[3]));
            def.setFudgeQQ(new BigDecimal(split[4]));
            defaults.add(def);
        } else {
            cw.printErrorln("some DEFAULTS values are lost!");
        }
    }

    private void addHeader(String line) {
        List<String> headers = structure.getHeaderComments();
        if (line.length() >= 3) {
            headers.add(line.substring(2, line.length()));
        } else {
            cw.printErrorln("some HEADERS values are lost!");
        }
    }

    private void addAtomTypes(String line) {
        List<AtomType> atomTypes = structure.getAtomTypes();
        String[] split = reworkLine(line).split(" ");
        if (split.length == 7) {
            AtomType at = new AtomType();
            at.setName(split[0]);
            at.setNum(Integer.parseInt(split[1]));
            at.setMass(new BigDecimal(split[2]));
            at.setCharge(new BigDecimal(split[3]));
            at.setParticleType(split[4]);
            at.setSigma(new BigDecimal(split[5]));
            at.setEpsilon(new BigDecimal(split[6]));
            atomTypes.add(at);
        } else {
            cw.printErrorln("some ATOMTYPES values are lost!");
        }
    }

    private void addBondTypes(String line) {
        List<BondType> bondTypes = structure.getBondTypes();
        String[] split = reworkLine(line).split(" ");
        if (split.length == 5) {
            BondType bt = new BondType();
            bt.setAi(split[0]);
            bt.setAj(split[1]);
            bt.setFuncType(Integer.parseInt(split[2]));
            bt.setB0(new BigDecimal(split[3]));
            bt.setKb(new BigDecimal(split[4]));
            bondTypes.add(bt);
        } else {
            cw.printErrorln("some BONDTYPES values are lost!");
        }
    }

    private void addConstraintTypes(String line) {
        List<ConstraintType> constraintTypes = structure.getConstraintTypes();
        String[] split = reworkLine(line).split(" ");
        if (split.length == 4) {
            ConstraintType ct = new ConstraintType();
            ct.setAi(split[0]);
            ct.setAj(split[1]);
            ct.setFuncType(Integer.parseInt(split[2]));
            ct.setB0(new BigDecimal(split[3]));
            constraintTypes.add(ct);
        } else {
            cw.printErrorln("some CONSTRAINTTYPES values are lost!");
        }
    }

    private void addAngleTypes(String line) {
        List<AngleType> angleTypes = structure.getAngleTypes();
        String[] split = reworkLine(line).split(" ");
        if (split.length == 6) {
            AngleType at = new AngleType();
            at.setAi(split[0]);
            at.setAj(split[1]);
            at.setAk(split[2]);
            at.setFuncType(Integer.parseInt(split[3]));
            at.setTh0(new BigDecimal(split[4]));
            at.setCth(new BigDecimal(split[5]));
            angleTypes.add(at);
        } else {
            cw.printErrorln("some ANGLETYPES values are lost!");
        }
    }

    private void addDihedralTypes(String line) {
        List<DihedralType> dihedralTypes = structure.getDihedralTypes();
        String[] split = reworkLine(line).split(" ");
        if (split.length == 8) {
            DihedralType dt = new DihedralType();
            dt.setAi(split[0]);
            dt.setAj(split[1]);
            dt.setAk(split[2]);
            dt.setAl(split[3]);
            dt.setFuncType(Integer.parseInt(split[4]));
            dt.setPhase(new BigDecimal(split[5]));
            dt.setKd(new BigDecimal(split[6]));
            dt.setPn(new BigDecimal(split[7]));
            dihedralTypes.add(dt);
        } else {
            cw.printErrorln("some DIHEDRALTYPES values are lost!");
        }
    }

    private void addGenbornParams(String line) {
        List<ImplicitGenbornParam> genbornParams = structure.getGenbornParams();
        String[] split = reworkLine(line).split(" ");
        if (split.length == 6) {
            ImplicitGenbornParam gp = new ImplicitGenbornParam();
            gp.setAtom(split[0]);
            gp.setSar(new BigDecimal(split[1]));
            gp.setSt(new BigDecimal(split[2]));
            gp.setPi(new BigDecimal(split[3]));
            gp.setGbr(new BigDecimal(split[4]));
            gp.setHct(new BigDecimal(split[5]));
            genbornParams.add(gp);
        } else {
            cw.printErrorln("some GENBORN values are lost!");
        }
    }

    private void addAtoms(String line, Section actualSection) {
        if (actualSection != null) {
            List<Atom> atoms = actualSection.getAtoms();
            String[] split = reworkLine(line).split(" ");
            if (split.length >= 7) {
                Atom a = new Atom();
                a.setNr(Integer.parseInt(split[0]));
                a.setType(split[1]);
                a.setResNr(Integer.parseInt(split[2]));
                a.setResName(split[3]);
                a.setAtomName(split[4]);
                a.setChargeGroupNr(Integer.parseInt(split[5]));
                a.setCharge(new BigDecimal(split[6]));
                if (split.length >= 8) {
                    a.setMass(new BigDecimal(split[7]));
                }
                if (split.length == 11) {
                    a.setTypeB(split[8]);
                    a.setChargeB(new BigDecimal(split[9]));
                    a.setMassB(new BigDecimal(split[10]));
                }
                atoms.add(a);
            } else {
                cw.printErrorln("some ATOMS values are lost!");
            }
        }
    }

    private void addBonds(String line, Section actualSection) {
        if (actualSection != null) {
            List<Bond> bonds = actualSection.getBonds();
            String[] split = reworkLine(line).split(" ");
            if (split.length >= 3) {
                Bond b = new Bond();
                b.setAi(split[0]);
                b.setAj(split[1]);
                b.setFuncType(Integer.parseInt(split[2]));
                if (split.length >= 5) {
                    b.setR(new BigDecimal(split[3]));
                    b.setK(new BigDecimal(split[4]));
                }
                bonds.add(b);
            } else {
                cw.printErrorln("some BONDS values are lost!");
            }
        }
    }

    private void addPairs(String line, Section actualSection) {
        if (actualSection != null) {
            List<Pair> pairs = actualSection.getPairs();
            String[] split = reworkLine(line).split(" ");
            if (split.length >= 3) {
                Pair p = new Pair();
                p.setAi(split[0]);
                p.setAj(split[1]);
                p.setFuncType(Integer.parseInt(split[2]));
                if (split.length >= 5) {
                    p.setC0(new BigDecimal(split[3]));
                    p.setC1(new BigDecimal(split[4]));
                }
                if (split.length >= 7) {
                    p.setC2(new BigDecimal(split[5]));
                    p.setC3(new BigDecimal(split[6]));
                }
                pairs.add(p);
            } else {
                cw.printErrorln("some PAIRS values are lost!");
            }
        }
    }

    private void addAngles(String line, Section actualSection) {
        if (actualSection != null) {
            List<Angle> angles = actualSection.getAngles();
            String[] split = reworkLine(line).split(" ");
            if (split.length >= 4) {
                Angle a = new Angle();
                a.setAi(split[0]);
                a.setAj(split[1]);
                a.setAk(split[2]);
                a.setFuncType(Integer.parseInt(split[3]));
                if (split.length >= 6) {
                    a.setTh0(new BigDecimal(split[4]));
                    a.setCth(new BigDecimal(split[5]));
                }
                angles.add(a);
            } else {
                cw.printErrorln("some ANGLES values are lost!");
            }
        }
    }

    private void addDihedrals(String line, Section actualSection) {
        if (actualSection != null) {
            List<Dihedral> dihedrals = actualSection.getDihedrals();
            String[] split = reworkLine(line).split(" ");
            if (split.length >= 5) {
                Dihedral dh = new Dihedral();
                dh.setAi(split[0]);
                dh.setAj(split[1]);
                dh.setAk(split[2]);
                dh.setAl(split[3]);
                dh.setFuncType(Integer.parseInt(split[4]));
                if (split.length == 11) {
                    dh.setC0(new BigDecimal(split[5]));
                    dh.setC1(new BigDecimal(split[6]));
                    dh.setC2(new BigDecimal(split[7]));
                    dh.setC3(new BigDecimal(split[8]));
                    dh.setC4(new BigDecimal(split[9]));
                    dh.setC5(new BigDecimal(split[10]));
                } else if (split.length > 5) {
                    cw.printErrorln("some Dihedrals values are lost!");
                }
                dihedrals.add(dh);
            } else {
                cw.printErrorln("some DIHEDRALS values are lost!");
            }
        }
    }

    private void addPosres(String line, Section actualSection) {
        if (actualSection != null) {
            List<PostionRestraint> posres = actualSection.getPostionRestraints();
            String[] split = reworkLine(line).split(" ");
            if (split.length == 5) {
                PostionRestraint pr = new PostionRestraint();
                pr.setAi(Integer.parseInt(split[0]));
                pr.setFunc(Integer.parseInt(split[1]));
                pr.setFx(new BigDecimal(split[2]));
                pr.setFy(new BigDecimal(split[3]));
                pr.setFz(new BigDecimal(split[4]));
                posres.add(pr);
            } else {
                cw.printErrorln("some POSRES values are lost!");
            }
        }
    }

    private void addSettles(String line, Section actualSection) {
        if (actualSection != null) {
            List<Settle> settles = actualSection.getSettles();
            String[] split = reworkLine(line).split(" ");
            if (split.length == 4) {
                Settle s = new Settle();
                s.setAtom(Integer.parseInt(split[0]));
                s.setFuncType(Integer.parseInt(split[1]));
                s.setDoh(new BigDecimal(split[2]));
                s.setDhh(new BigDecimal(split[3]));
                settles.add(s);
            } else {
                cw.printErrorln("some SETTLES values are lost!");
            }
        }
    }

    private void addExclusions(String line, Section actualSection) {
        if (actualSection != null) {
            List<Exclusion> exclusions = actualSection.getExclusions();
            String[] split = reworkLine(line).split(" ");
            if (split.length >= 2) {
                Exclusion e = new Exclusion();
                e.setaIdx(Integer.parseInt(split[0]));
                List<Integer> bonds = e.getBonds();
                for (int i = 1; i < split.length; i++) {
                    bonds.add(Integer.parseInt(split[i]));
                }
                exclusions.add(e);
            } else {
                cw.printErrorln("some EXCLUSIONS values are lost!");
            }
        }
    }

    private boolean isDataRow(String line) {
        return !COMMENT_PATTERN.matcher(line).matches() && !line.trim().isEmpty();
    }

    private String reworkLine(String line) {
        line = line.replaceAll("\\s+", " ");
        String[] split = line.split(";");
        return split[0];
    }
}
