import com.google.common.collect.Lists as Lists
import java.lang.Object as Object
import java.nio.file.Paths as Paths
import org.biojava.nbio.structure.AminoAcidImpl as Group
import org.biojava.nbio.structure.AtomImpl as Atom
import org.biojava.nbio.structure.ChainImpl as ChainImpl
import org.biojava.nbio.structure.Element as Element
import org.biojava.nbio.structure.PDBCrystallographicInfo as PdbCryst
import org.biojava.nbio.structure.PDBHeader as PdbHeader
import org.biojava.nbio.structure.ResidueNumber as ResidueNumber
import org.biojava.nbio.structure.xtal.BravaisLattice as BravaisL
import org.biojava.nbio.structure.xtal.SpaceGroup as SpaceGroup
import re as re

import org.mdconverter.api.ReadStructure as ReadStructure
import org.mdconverter.api.plugin.InvalidInputException as Invalid


class ReadFile(ReadStructure, Object):
    class Pattern:
        HEADER_DEF_PATTERN = "^@<[A-Z]*>MOLECULE$"
        HEADER_PATTERN = "^([ \\t]*[0-9]+){5}$"
        ATOM_DEF_PATTERN = "^@<[A-Z]*>ATOM$"
        ATOM_PATTERN = "^[ \\t]*[0-9]+ [A-Z0-9]{1,}([ \\t]*-?[0-9]+.[0-9]+){3} [A-z0-9]+[ \\t]+[0-9] [A-Z]{3,}[ \\t]+-?[0-9]+.[0-9]+$"
        BOND_DEF_PATTERN = "^@<[A-Z]*>BOND$"
        STRUCTURE_DEF_PATTERN = "^@<[A-Z]*>SUBSTRUCTURE$"

    highestX = 0
    highestY = 0
    highestZ = 0
    lowestX = 0
    lowestY = 0
    lowestZ = 0

    def __init__(self):
        global highestX, highestY, highestZ, lowestX, lowestY, lowestZ
        highestX = 0
        highestY = 0
        highestZ = 0
        lowestX = 0
        lowestY = 0
        lowestZ = 0

    def readFileToStructure(self, path, structure):
        header = PdbHeader()
        header.setTitle(Paths.get(path).getFileName().toString())
        structure.setPDBHeader(header)
        model = ReadFile.getModelFromFile(self, path)
        structure.setChains(Lists.newArrayList(model))
        info = PdbCryst()
        info.setSpaceGroup(SpaceGroup(0, 1, 1, "P 1", "P 1", BravaisL.CUBIC))
        info.setCrystalCell(ReadFile.getBox(self, info.getSpaceGroup().getBravLattice().getExampleUnitCell()))
        header.setCrystallographicInfo(info)
        return structure

    def getModelFromFile(self, path):
        with open(path) as f:
            all_lines = f.readlines()
        chain = ChainImpl()
        chain.setAtomGroups(ReadFile.getGroups(self, all_lines))
        return chain

    def getGroups(self, all_lines):
        groups = []
        area = ''
        atom_count = 0
        atom_check = 0
        bond_count = 0
        sub_count = 0
        sub_check = 0
        for idx in range(len(all_lines)):
            if area == '':
                m = re.match(ReadFile.Pattern.HEADER_DEF_PATTERN, all_lines[idx])
                if m is not None:
                    area = 'header'
                    continue
                else:
                    m = re.match(ReadFile.Pattern.ATOM_DEF_PATTERN, all_lines[idx])
                    if m is not None:
                        area = 'atom'
                        continue
                    else:
                        m = re.match(ReadFile.Pattern.STRUCTURE_DEF_PATTERN, all_lines[idx])
                        if m is not None:
                            area = 'sub'
                            continue
                        else:
                            continue
            if area == 'header':
                m = re.match(ReadFile.Pattern.HEADER_PATTERN, all_lines[idx])
                if m is not None:
                    words = all_lines[idx].split()
                    atom_count = int(words[0])
                    bond_count = int(words[1])
                    sub_count = int(words[2])
                    for i in range(sub_count):
                        groups.append(Group())
                    area = ''
                else:
                    continue
            if area == 'atom':
                m = re.match(ReadFile.Pattern.ATOM_PATTERN, all_lines[idx])
                if m is not None:
                    atom_check += 1
                    words = all_lines[idx].split()
                    atom = ReadFile.getAtom(self, words)
                    if len(words) > 5:
                        group = groups[int(words[6]) - 1]
                        if group.getPDBName() is None:
                            group.setPDBName(words[7])
                            test = ResidueNumber.fromString(words[6])
                            test.setSeqNum(int(words[6]))
                            group.setResidueNumber(test)
                        group.addAtom(atom)
                    else:
                        groups[0].addAtom(atom)
                else:
                    if atom_check != atom_count:
                        raise Invalid('Atom definition count do not equal found atoms')
                    area = ''
                    idx -= 1
                    continue
            if area == 'sub':
                sub_check = 1
                group = groups[sub_check - 1]
                words = all_lines[idx].split()
                if group.getPDBName is None:
                    group.setPDBName(words[1])
                    group.setResidueNumber(int(words[0]))
        if sub_check != sub_count:
            raise Invalid('Substructure definition count do not equal found substructures')
        return groups

    def getAtom(self, words):
        global highestX, highestY, highestZ, lowestX, lowestY, lowestZ
        atom = Atom()
        atom.setOccupancy(1)
        atom.setPDBserial(int(words[0]))
        atom.setName(words[1])
        x = float(words[2])
        y = float(words[3])
        z = float(words[4])
        if highestX <= x:
            highestX = x
        elif lowestX >= x:
            lowestX = x
        if highestY <= y:
            highestY = y
        elif lowestY >= y:
            lowestY = y
        if highestZ <= z:
            highestZ = z
        elif lowestZ >= z:
            lowestZ = z
        atom.setX(x)
        atom.setY(y)
        atom.setZ(z)
        atom.setAltLoc(' ')
        atom.setElement(Element.valueOf(atom.getName()[:1]))
        return atom

    def getBox(self, cell):
        global highestX, highestY, highestZ, lowestX, lowestY, lowestZ
        doubles = [highestX + (-lowestX), highestY + (-lowestY), highestZ + (-lowestZ)]
        cell.setA(doubles[0])
        cell.setB(doubles[1])
        cell.setC(doubles[2])
        return cell
