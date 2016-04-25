package org.mdconverter.pdbsw.main;


import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.biojava.nbio.structure.PDBCrystallographicInfo;
import org.biojava.nbio.structure.Structure;
import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.writer.AbstractWriter;

import javax.inject.Singleton;
import java.util.List;

/**
 * Created by miso on 04.11.2015.
 */
@Singleton
public class PDBWriter extends AbstractWriter {

    @Override
    /**
     * returns the default usage from AbstractWriter
     */
    public String getUsage() {
        return super.getUsage();
    }

    @Override
    /**
     * returns a String formatted in pdb-format
     */
    public String getOutput() throws InvalidParameterException, InvalidInputException {
        try {
            Structure structure = (Structure) getStructure();

            PDBCrystallographicInfo info = structure.getCrystallographicInfo();
            List<String> lines = Lists.newArrayList(structure.toPDB().split(System.lineSeparator()));
            int a = 0;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains("TITLE")) {
                    continue;
                } else {
                    if (info != null) {
                        lines.add(i + a, "REMARK    THIS IS A SIMULATION BOX");
                        ++a;
                        lines.add(i + a, generateCrystEntry(info));
                        ++a;
                    }
                    lines.add(i + a, "MODEL        " + structure.nrModels());
                    break;
                }
            }
            if (structure.nrModels() > 1) {
                getConsoleHandler().printErrorln("Error happened according to number of containing models.\n" +
                        "This is an known issue and will be fixed in future release");
            }
            //TODO check number of models --> generate entries
            lines.add("TER");
            lines.add("ENDMDL");
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                text.append(line);
                if (i < lines.size() - 1) {
                    text.append(System.lineSeparator());
                }
            }
            return text.toString();
        } catch (Exception e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    @Override
    /**
     * returns the description for the plugin
     */
    public String getDescription() {
        return "This plugin provides a given structure in pdb-format.";
    }

    /**
     * generates the CRYST1 entry for Gromacs structure files
     *
     * @param info input data
     * @return a CRYST1 entry
     */
    private String generateCrystEntry(PDBCrystallographicInfo info) {
        String entry = "CRYST1";
        entry = appendLengthDependant(entry, String.valueOf(info.getA()), 10);
        entry = appendLengthDependant(entry, String.valueOf(info.getB()), 10);
        entry = appendLengthDependant(entry, String.valueOf(info.getC()), 10);
        entry = appendLengthDependant(entry, String.valueOf(info.getAlpha()), 10);
        entry = appendLengthDependant(entry, String.valueOf(info.getBeta()), 10);
        entry = appendLengthDependant(entry, String.valueOf(info.getGamma()), 10);
        entry += "  " + info.getSpaceGroup().getShortSymbol();
        entry = appendLengthDependant(entry, "1", 70);
        return entry;
    }

    /**
     * Adds a String to another String to reach a defined length for the appended String
     *
     * @param entry existing String
     * @param value String to add
     * @param len   minimum length of value (filled up spaces leftside)
     * @return entry + value
     */
    private String appendLengthDependant(String entry, String value, int len) {
        int diff = len - value.length();
        if (diff < 1) {
            entry += " ";
        }
        return entry + StringUtils.leftPad(value, len);
    }
}

