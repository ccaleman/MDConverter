package org.mdconverter.main;


import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Structure;
import org.mdconverter.fileparser.ParseInputFile;
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
    public Structure getMetaModel() {
        try {
            List<Chain> modelFromFile = ParseInputFile.getModelFromFile(getInputFile());
            getStructure().setChains(modelFromFile);
            return getStructure();
        } catch (IOException e) {
            getConsoleWriter().printErrorln(e.getMessage());
            throw new RuntimeException(getName() + " wasn't able to parse input file!");
        }
    }
}

