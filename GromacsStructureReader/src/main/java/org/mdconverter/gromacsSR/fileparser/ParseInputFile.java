package org.mdconverter.gromacssr.fileparser;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.biojava.nbio.structure.*;
import org.biojava.nbio.structure.xtal.CrystalCell;
import org.mdconverter.api.consolewriter.ConsoleWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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
    private double highestX;
    private double highestY;
    private double highestZ;
    private double lowestX;
    private double lowestY;
    private double lowestZ;

    private ConsoleWriter cw;

    @Inject
    public ParseInputFile(ConsoleWriter cw) {
        this.cw = cw;
        highestX = 0;
        highestY = 0;
        highestZ = 0;
        lowestX = 0;
        lowestY = 0;
        lowestZ = 0;
    }


    public String getTitleFromFile(Path inputFile) throws IOException {
        BufferedReader reader = null;
        LineNumberReader lineReader = null;
        try {
            reader = Files.newBufferedReader(inputFile, Charsets.UTF_8);
            lineReader = new LineNumberReader(reader);
            String line = lineReader.readLine();
            if (line != null && TITLE_PATTERN.matcher(line).matches()) {
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

    public CrystalCell getCrystlCell(Path inputFile, CrystalCell cell) throws IOException {
        List<String> allLines = Files.readAllLines(inputFile);
        for (String line : allLines) {
            if (CRYSTAL_CELL_PATTERN.matcher(line).matches()) {
                String trim = line.trim();
                List<String> split = Lists.newArrayList(trim.split(" +"));
                List<Double> doubles = split.stream().map(Double::parseDouble).collect(Collectors.toList());
                double[] boxSize = sumBoxSize();
                if (doubles.get(0) >= boxSize[0]) doubles.set(0, boxSize[0]);
                if (doubles.get(1) >= boxSize[1]) doubles.set(1, boxSize[1]);
                if (doubles.get(2) >= boxSize[2]) doubles.set(2, boxSize[2]);
                cell.setA(doubles.get(0));
                cell.setB(doubles.get(1));
                cell.setC(doubles.get(2));
            }
        }
        return cell;
    }

    public List<Chain> getModelFromFile(Path input) throws IOException {
        List<String> strings = Files.readAllLines(input);
        Chain chain = new ChainImpl();
        chain.setAtomGroups(getGroups(strings));
        return Lists.newArrayList(chain);
    }

    private List<Group> getGroups(List<String> groupGroups) {
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

    private Group getAtoms(List<String> groupAtoms, String id) {
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
            atom.setOccupancy(1);
            atom.setName(split.get(1));
            atom.setPDBserial(Integer.parseInt(split.get(2)));
            double x = Double.parseDouble(split.get(3));
            double y = Double.parseDouble(split.get(4));
            double z = Double.parseDouble(split.get(5));
            checkHighestAndLowest(x, y, z);
            atom.setX(x);
            atom.setY(y);
            atom.setZ(z);
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

    private void checkHighestAndLowest(double x, double y, double z) {
        if (highestX <= x) highestX = x;
        else if (lowestX >= x) lowestX = x;
        if (highestY <= y) highestY = y;
        else if (lowestY >= y) lowestY = y;
        if (highestZ <= z) highestZ = z;
        else if (lowestZ >= z) lowestZ = z;
    }

    private double[] sumBoxSize() {
        double[] boxSize = new double[3];
        boxSize[0] = highestX + (-lowestX);
        boxSize[1] = highestY + (-lowestY);
        boxSize[2] = highestZ + (-lowestZ);
        return boxSize;
    }
}