import com.google.common.collect.Lists as Lists
import java.nio.file.Paths as Paths
import org.biojava.nbio.structure.AminoAcidImpl as Group
import org.biojava.nbio.structure.AtomImpl as Atom
import org.biojava.nbio.structure.ChainImpl as ChainImpl
import org.biojava.nbio.structure.Element as Element
import org.biojava.nbio.structure.PDBHeader as PdbHeader
import org.biojava.nbio.structure.ResidueNumber as ResidueNumber
import re as re
from java.lang import Object

import org.mdconverter.api.plugin.InvalidInputException as Invalid
from org.mdconverter.api import ReadStructure


class ReadFile(ReadStructure, Object):
    class Pattern:
        HEADER_DEF_PATTERN = "^@<[A-Z]*>MOLECULE$"
        HEADER_PATTERN = "^([ \\t]*[0-9]+){5}$"
        ATOM_DEF_PATTERN = "^@<[A-Z]*>ATOM$"
        ATOM_PATTERN = "^[ \\t]*[0-9]+ [A-Z0-9]{1,}([ \\t]*-?[0-9]+.[0-9]+){3} [A-z0-9]+[ \\t]+[0-9] [A-Z]{3,}[ \\t]+-?[0-9]+.[0-9]+$"
        BOND_DEF_PATTERN = "^@<[A-Z]*>BOND$"
        STRUCTURE_DEF_PATTERN = "^@<[A-Z]*>SUBSTRUCTURE$"

    def __init__(self):
        pass

    def readFileToStructure(self, path, structure):
        header = PdbHeader()
        header.setTitle(Paths.get(path).getFileName().toString())
        structure.setPDBHeader(header)
        model = ReadFile.getModelFromFile(path)
        structure.setChains(Lists.newArrayList(model))
        return structure

    @staticmethod
    def getModelFromFile(path):
        with open(path) as f:
            all_lines = f.readlines()
        chain = ChainImpl()
        chain.setAtomGroups(ReadFile.getGroups(all_lines))
        return chain

    @staticmethod
    def getGroups(all_lines):
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
                    atom = ReadFile.getAtom(words)
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
                group = groups[sub_check-1]
                words = all_lines[idx].split()
                if group.getPDBName is None:
                    group.setPDBName(words[1])
                    group.setResidueNumber(int(words[0]))
        if sub_check != sub_count:
                raise Invalid('Substructure definition count do not equal found substructures')
        return groups

    @staticmethod
    def getAtom(words):
        atom = Atom()
        atom.setOccupancy(1)
        atom.setPDBserial(int(words[0]))
        atom.setName(words[1])
        atom.setX(float(words[2]))
        atom.setY(float(words[3]))
        atom.setZ(float(words[4]))
        atom.setAltLoc(' ')
        atom.setElement(Element.valueOf(atom.getName()[:1]))
        return atom
