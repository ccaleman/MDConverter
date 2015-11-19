package org.mdconverter.main;


import org.biojava.nbio.structure.Structure;
import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.writer.AbstractWriter;

import javax.inject.Singleton;

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
        return structure.toPDB();
    }

    @Override
    public String getDescription() {
        return "This plugin provides a given structure in pdb-format.";
    }
}

