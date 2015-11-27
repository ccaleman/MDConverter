package org.mdconverter.fileparser;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.biojava.nbio.structure.*;
import org.biojava.nbio.structure.xtal.CrystalCell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by miso on 12.11.2015.
 */

public class ParseInputFile {

    private static Pattern ATOM_PATTERN = Pattern.compile("^[ \\t]*[0-9]+[A-Z]{3}.*");
    private static Pattern NUM_PATTERN = Pattern.compile("^[1-9][0-9]*");
    private static Pattern TITLE_PATTERN = Pattern.compile("^[a-zA-z0-9`\\-=\\[\\];',./~!@#$%^&*()_+{}|:\"<>? ]*");
    private static Pattern CRYSTAL_CELL_PATTERN = Pattern.compile("^([ \\t]+[-]?[0-9]+[.][0-9]+){3,6}");

    public static String getTitleFromFile(Path inputFile) throws IOException {
        BufferedReader reader = null;
        LineNumberReader lineReader = null;
        try {
            reader = Files.newBufferedReader(inputFile, Charsets.UTF_8);
            lineReader = new LineNumberReader(reader);
            String line = lineReader.readLine();
            if (TITLE_PATTERN.matcher(line).matches()) {
                return line;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (lineReader != null) {
                lineReader.close();
            }
        }
        return "NO TITLE FOUND!";
    }

    public static Optional<CrystalCell> getCrystlCell(Path inputFile) throws IOException {
        List<String> allLines = Files.readAllLines(inputFile);
        for (String line : allLines) {
            if (CRYSTAL_CELL_PATTERN.matcher(line).matches()) {
                String trim = line.trim();
                List<String> split = Lists.newArrayList(trim.split(" +"));
                List<Double> doubles = split.stream().map(Double::parseDouble).collect(Collectors.toList());
                if (doubles.size() == 3) {
                    doubles.addAll(Lists.newArrayList(9.0, 9.0, 9.0));
                }
                return Optional.of(new CrystalCell(doubles.get(0), doubles.get(1), doubles.get(2), doubles.get(3), doubles.get(4), doubles.get(5)));
            }
        }
        return Optional.empty();
    }

    public static List<Chain> getModelFromFile(Path input) throws IOException {
        List<String> strings = Files.readAllLines(input);
        Chain chain = new ChainImpl();
        chain.setAtomGroups(getGroups(strings));
        return Lists.newArrayList(chain);
    }

    private static List<Group> getGroups(List<String> groupGroups) {
        List<Group> groups = Lists.newArrayList();
        List<String> groupList = Lists.newArrayList();
        Integer i = 1;
        for (String group : groupGroups) {
            if (ATOM_PATTERN.matcher(group).matches()) {
                if (group.trim().startsWith(i.toString())) {
                    groupList.add(group.trim().substring(i.toString().length(), group.trim().length()));
                } else {
                    groups.add(getAtoms(groupList, i.toString()));
                    groupList.clear();
                    i++;
                    groupList.add(group.trim().substring(i.toString().length(), group.trim().length()));
                }
            } else if (NUM_PATTERN.matcher(group).matches()) {

            }
        }
        groups.add(getAtoms(groupList, i.toString()));
        return groups;
    }

    private static Group getAtoms(List<String> groupAtoms, String id) {
        Group group = new AminoAcidImpl();
        String chainId = null;
        for (String groupAtom : groupAtoms) {
            Atom atom = new AtomImpl();
            List<String> split = Lists.newArrayList(groupAtom.split(" +"));
            if (chainId == null) {
                chainId = split.get(0);
                group.setPDBName(chainId);
                ResidueNumber residueNumber = ResidueNumber.fromString(chainId);
                residueNumber.setSeqNum(Integer.parseInt(id));
                group.setResidueNumber(residueNumber);
            }
            atom.setName(split.get(1));
            atom.setPDBserial(Integer.parseInt(split.get(2)));
            atom.setX(Double.parseDouble(split.get(3)));
            atom.setY(Double.parseDouble(split.get(4)));
            atom.setZ(Double.parseDouble(split.get(5)));
            atom.setAltLoc(' ');
            List<String> elements = Lists.newArrayList();
            Lists.newArrayList(Element.values()).stream().forEach(elem -> elements.add(elem.toString()));
            if (elements.contains(atom.getName().substring(0, 1))) {
                atom.setElement(Element.valueOf(atom.getName().substring(0, 1)));
            }
            group.addAtom(atom);
        }
        return group;
    }
}