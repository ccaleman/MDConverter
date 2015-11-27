package org.mdconverter.main;


import com.google.common.collect.Lists;
import org.biojava.nbio.structure.PDBCrystallographicInfo;
import org.biojava.nbio.structure.Structure;
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
    public String getUsage() {
        return super.getUsage();
    }

    @Override
    public String getOutput() throws InvalidParameterException {
        Structure structure = (Structure) getStructure();

        PDBCrystallographicInfo info = structure.getCrystallographicInfo();
        List<String> lines = Lists.newArrayList(structure.toPDB().split("\r\n"));
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("TITLE")) {
                continue;
            } else {
                lines.add(i, "REMARK    THIS IS A SIMULATION BOX");
                if (info != null) {
                    lines.add(i + 1, generateCrystEntry(info));
                }
                lines.add(i + 2, "MODEL        " + structure.nrModels());
                break;
            }
        }
        lines.add("TER");
        lines.add("ENDMDL");
        StringBuffer text = new StringBuffer();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            text.append(line);
            if (i < lines.size() - 1) {
                text.append(System.lineSeparator());
            }
        }
        return text.toString();
    }

    @Override
    public String getDescription() {
        return "This plugin provides a given structure in pdb-format.";
    }

    private String generateCrystEntry(PDBCrystallographicInfo info) {
        String entry = "CRYST1";
        entry = appendLengthDependant(entry, String.valueOf(info.getA()), 15);
        entry = appendLengthDependant(entry, String.valueOf(info.getB()), 24);
        entry = appendLengthDependant(entry, String.valueOf(info.getC()), 33);
        entry = appendLengthDependant(entry, String.valueOf(info.getAlpha()), 40);
        entry = appendLengthDependant(entry, String.valueOf(info.getBeta()), 47);
        entry = appendLengthDependant(entry, String.valueOf(info.getGamma()), 54);
        entry += "  " + info.getSpaceGroup().getShortSymbol();
        entry = appendLengthDependant(entry, "1", 70);
        return entry;
    }

    private String appendLengthDependant(String entry, String value, int len) {
        int diff = len - value.length() - entry.length();
        for (int i = 0; i < diff; i++) {
            entry += " ";
        }
        return entry += value;
    }
}

