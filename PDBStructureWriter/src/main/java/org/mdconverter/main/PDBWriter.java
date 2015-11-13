package org.mdconverter.main;


import org.mdconverter.plugin.writer.AbstractWriter;

import javax.inject.Singleton;

/**
 * Created by miso on 04.11.2015.
 */
@Singleton
public class PDBWriter extends AbstractWriter {

    @Override
    public String getOutput() {
        return getStructure().toPDB();
    }

    @Override
    public String getDescription() {
        return "This plugin provides a given structure in pdb-format.";
    }
}

