package org.mdconverter.main;

import org.biojava.nbio.structure.Structure;
import org.mdconverter.api.ReadStructure;
import org.mdconverter.api.jython.JythonObjectFactory;
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
    public Structure getMetaModel() throws InvalidParameterException {
        return LoadJythonScripts();
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }

    private Structure LoadJythonScripts() {
        JythonObjectFactory jof = getJythonObjectFactory();
        jof.addPluginToInterpreter(getPluginPath());
        ReadStructure readStructureImpl = (ReadStructure) jof.createObject(ReadStructure.class, "ReadStructureImpl");
        ReadStructure test = (ReadStructure) jof.createObject(ReadStructure.class, "Test");
        Structure structure = readStructureImpl.readFileToStructure(getInputFile().toString());
        getConsoleWriter().printErrorln(structure.getName());
        return structure;
    }
}
