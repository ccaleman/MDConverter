package org.mdconverter.main;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.plugin.InvalidParameterException;
import org.mdconverter.plugin.reader.AbstractReader;

/**
 * Created by miso on 19.11.2015.
 */
public class AmberReader extends AbstractReader {

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Structure getMetaModel() throws InvalidParameterException {
        return (Structure) getStructure();
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }
}
