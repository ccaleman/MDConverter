package org.mdconverter.main;


import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.PDBHeader;
import org.biojava.nbio.structure.Structure;
import org.mdconverter.fileparser.ParseInputFile;
import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.reader.AbstractReader;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

/**
 * Created by miso on 04.11.2015.
 */
@Singleton
public class GromacsReader extends AbstractReader {

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Structure getMetaModel() throws InvalidParameterException {
        try {
            Structure structure = (Structure) getStructure();
            PDBHeader pdbHeader = new PDBHeader();
            pdbHeader.setTitle(ParseInputFile.getTitleFromFile(getInputFile()));
            structure.setPDBHeader(pdbHeader);
            List<Chain> modelFromFile = ParseInputFile.getModelFromFile(getInputFile());
            structure.setChains(modelFromFile);
            return structure;
        } catch (IOException e) {
            getConsoleWriter().printErrorln(e.getMessage());
            throw new RuntimeException(getName() + " wasn't able to parse input file!");
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }
}

