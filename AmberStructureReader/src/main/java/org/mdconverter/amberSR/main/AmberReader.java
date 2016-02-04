package org.mdconverter.ambersr.main;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.ambersr.api.ReadStructure;
import org.mdconverter.api.jython.JythonObjectFactory;
import org.mdconverter.api.plugin.InvalidInputException;
import org.mdconverter.api.plugin.InvalidParameterException;
import org.mdconverter.api.plugin.reader.AbstractReader;

/**
 * Created by miso on 19.11.2015.
 */
public class AmberReader extends AbstractReader {

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Structure getMetaModel() throws InvalidParameterException, InvalidInputException {
        return LoadJythonScripts();
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }

    private Structure LoadJythonScripts() throws InvalidInputException {
        JythonObjectFactory jof = getJythonObjectFactory();
        ReadStructure readFile = (ReadStructure) jof.createObject(ReadStructure.class, "ReadFile", "Lib.processfile.");
        Structure structure = readFile.readFileToStructure(getInputFile().toString(), (Structure) getStructure());
        getConsoleWriter().printErrorln(structure.getName());
        return structure;
    }
}
